package org.example.bankService.web.dtoValidation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.bankService.web.dto.RequestAccountDto;

public class Validation {

    public static void validateRequestAccountDto (RequestAccountDto dto){
       System.out.println("Start Validation");
        try( ValidatorFactory factory= jakarta.validation.Validation.buildDefaultValidatorFactory()){
            Validator validator= factory.getValidator();
            for(ConstraintViolation<RequestAccountDto> v : validator.validate(dto)){
                System.out.println(v.getPropertyPath()+ ":"+v.getMessage());
            }
        }
        System.out.println("End Validation");

    }

}
