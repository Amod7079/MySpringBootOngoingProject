package com.coderXAmod.ElectronicStore.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class ImageBameValidator implements ConstraintValidator<ImageNameValid,String> {


private Logger logger= LoggerFactory.getLogger(ImageBameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
logger.info("Message from isvalid:{}",value);
        if(value.isBlank())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
