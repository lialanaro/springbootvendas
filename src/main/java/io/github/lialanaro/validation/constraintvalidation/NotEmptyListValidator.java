package io.github.lialanaro.validation.constraintvalidation;

import io.github.lialanaro.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator <NotEmptyList, List<Object>>{
    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list !=null && !list.isEmpty();
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {

    }
}
