package com.ltri.mybatisplus.util;


import com.ltri.mybatisplus.exception.BusinessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class BeanValidator {
    public static <T> void validate(T object) {
        // 获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        // 执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        //for (ConstraintViolation<T> constraintViolation : constraintViolations) {
        //    System.out.println(constraintViolation.getMessageTemplate());
        //}
        throw new BusinessException(99, constraintViolations.iterator().next().getMessageTemplate());
    }
}
