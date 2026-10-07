package org.example.bankService.web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.example.bankService.context.ApplicationContext;
import org.example.bankService.web.controller.AccountController;
import org.example.bankService.web.dto.RequestAccountDto;
import org.example.bankService.web.dto.ResponseAccountDto;
import org.example.bankService.web.utilits.ResponseWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DispatcherServlet extends HttpServlet {
    private final AccountController accountController;

    public DispatcherServlet(AccountController accountController) {
        this.accountController = accountController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long logStart = System.nanoTime();
        prepareDataForLogging();
        String pathFromOriginHttpRequest = "/" + request.getPathInfo().split("/")[1];
        Map<String, String[]> queryParameters = request.getParameterMap();
        String body = new String(
                request.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        try {
            int responseStatus;
            switch (pathFromOriginHttpRequest) {
                case "/accounts" -> {
                    List<ResponseAccountDto> responseProfessorDto = accountController.read();
                    responseStatus = responseProfessorDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseProfessorDto, responseStatus, response);
                    //log.info(EXIT_LOG_MSG, "/professors", responseProfessorDto, (System.nanoTime() - logStart) / 1_000_000);
                }
                default -> {
                    response.sendError(404);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ThreadContext.clearAll();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathFromOriginHttpRequest = getShortPath(request.getPathInfo());
        String body = new String(
                request.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
        try {
            int responseStatus;
            switch (pathFromOriginHttpRequest) {
                case "/accounts" -> {
                    RequestAccountDto requestAccountDto = ApplicationContext.objectMapper.readValue(body, RequestAccountDto.class);
                    List<ResponseAccountDto> responseAccountDto = accountController.create(requestAccountDto);
                    responseStatus = responseAccountDto.isEmpty() ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_OK;
                    ResponseWriter.responseSender(responseAccountDto, responseStatus, response);
                    //log.info(EXIT_LOG_MSG, "/accounts", responseAccountDto, (System.nanoTime() - logStart) / 1_000_000);
                }

                default -> {
                    response.sendError(404);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ThreadContext.clearAll();
        }
    }

    public static String getShortPath(String getPathInfo) {
        String pathFromOriginHttpRequest = "/" + getPathInfo.split("/")[1];
        //log.info(" http path[1] = {}, request.getPathInfo()={} ", pathFromOriginHttpRequest, getPathInfo);
        return pathFromOriginHttpRequest;
    }

    public static void prepareDataForLogging() {
        String correlationId = UUID.randomUUID().toString();
        ThreadContext.put("X-Flow-Id", correlationId);
    }

    public static Integer getPathVariable(String path) {
        int pathLength = path.split("/").length;
        return pathLength == 3 ? Integer.parseInt(path.split("/")[2]) : null;
    }

    public static String getStringPathVariable(String path) {
        int pathLength = path.split("/").length;
        return pathLength == 3 ? path.split("/")[2] : null;
    }

    public static String[] getCompoundPathVariable(String path) {
        int pathLength = path.split("/").length;
        String rawPathVariable = pathLength == 3 ? (path.split("/")[2]) : null;
        String[] compoundVariable = rawPathVariable != null ? rawPathVariable.split("-") : new String[]{null, null};
        return compoundVariable;
    }

}
