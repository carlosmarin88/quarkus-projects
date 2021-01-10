package org.acme.Validators;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.acme.annotations.NotExpired;

/**
 * primer parametro es la anotacion y el segundo es el tipo del campo a validar
 */
public class NotExpiredValidator implements ConstraintValidator<NotExpired, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        
        
        if(value==null){
            //elemento que no caduca
            return true;
        }
        LocalDate now = LocalDate.now();

        //valido que el value se mas grande que el now
        return ChronoUnit.YEARS.between(now,value) > 0;
    }

    
}
