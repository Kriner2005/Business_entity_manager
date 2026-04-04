package co.edu.uptc.interfaces;

import co.edu.uptc.model.validation.ValidationResult;;

public interface IValidator<T> {
    ValidationResult validate(T entity);
}