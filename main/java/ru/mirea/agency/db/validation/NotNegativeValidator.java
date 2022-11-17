package ru.mirea.agency.db.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotNegativeValidator implements ConstraintValidator<NotNegative, Object> {
    private String[] fieldsToCheck;
    private boolean notAllowedZero;

    public void initialize(NotNegative constraintAnnotation) {
        fieldsToCheck = constraintAnnotation.fields();
        notAllowedZero = constraintAnnotation.notZero();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        for (String fieldName : fieldsToCheck) {
            Object fieldValue = new BeanWrapperImpl(value)
                    .getPropertyValue(fieldName);

            if (fieldValue instanceof Number) {
                double numValue = ((Number) fieldValue).doubleValue();
                if (numValue < 0 || (notAllowedZero && numValue == 0)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
