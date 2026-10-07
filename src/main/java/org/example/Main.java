package org.example;


import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.example.aopPoligon.BankAspect;
import org.example.bankService.context.AccountContext;
import org.example.bankService.web.DispatcherServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, LifecycleException {
        System.out.println("MAIN");
        System.out.println(
                BankAspect.class.getDeclaredMethod("aspectOf")
        );

        System.out.println(
                BankAspect.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
        );

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        Connector connector = tomcat.getConnector(); //
        Context path = tomcat.addContext("", new File(".").getAbsolutePath());// створює у пам’яті Java об'єкт, який «вказує» на поточну папку (це параметр ocBase → де лежать ресурси).

        AccountContext accountContext = new AccountContext();
        DispatcherServlet dispatcherController = new DispatcherServlet(
                accountContext.getAccountcontroller());

        Tomcat.addServlet(path, "DispatcherController", dispatcherController);
        path.addServletMappingDecoded("/*", "DispatcherController");

        tomcat.start(); // Запуск сервера
        System.out.println("Піднімаємо сервер");
        tomcat.getServer().await(); // Очікування запитів
    }
}


