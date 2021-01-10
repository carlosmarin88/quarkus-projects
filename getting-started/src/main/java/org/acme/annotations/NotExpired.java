package org.acme.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.acme.Validators.NotExpiredValidator;

@Target({ ElementType.METHOD, ElementType.FIELD, 
    ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//se utiliza para una constraint en concreto
@Constraint(validatedBy = NotExpiredValidator.class)
public @interface NotExpired {
 
    String message() default "Beer must not be expired";
    //agregar mas validaciones dentro de un grupo de validaciones se permite aca
    Class<?>[] groups() default {};
    //sirve para añadir informacion extra de la metada de porque fallo en este caso
    Class<? extends Payload>[] payload() default {};
}
