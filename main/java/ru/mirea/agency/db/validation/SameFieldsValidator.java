package ru.mirea.agency.db.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SameFieldsValidator implements ConstraintValidator<SameFields, Object> {
    private String field;
    private String fieldMatch;

    public void initialize(SameFields constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue) ||
                    (fieldValue.equals("") && (fieldMatchValue == null || fieldMatchValue.equals("")));
        } else {
            return fieldMatchValue == null || fieldMatchValue.equals("");
        }
    }
}
