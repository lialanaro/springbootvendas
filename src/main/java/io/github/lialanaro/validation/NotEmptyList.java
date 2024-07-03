package io.github.lialanaro.validation;

import io.github.lialanaro.validation.constraintvalidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static io.github.lialanaro.domain.entity.helper.Constants.LISTA_VAZIA;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {
    String message() default  LISTA_VAZIA;
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
