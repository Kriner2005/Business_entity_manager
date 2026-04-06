package co.edu.uptc.model.validation;

import co.edu.uptc.interfaces.IValidator;
import co.edu.uptc.model.entities.Person;
import co.edu.uptc.model.validation.rules.DateRule;
import co.edu.uptc.model.validation.rules.NameLengthRule;

public class PersonValidator implements IValidator<Person> {

    private final NameLengthRule nameRule;
    private final NameLengthRule lastNameRule;
    private final DateRule dateRule;

    public PersonValidator(NameLengthRule nameRule,
            NameLengthRule lastNameRule,
            DateRule dateRule) {
        this.nameRule = nameRule;
        this.lastNameRule = lastNameRule;
        this.dateRule = dateRule;
    }

    @Override
    public ValidationResult validate(Person person) {
        ValidationResult name = nameRule.validate(person.getName());
        if (!name.isValid())
            return name;

        ValidationResult lastName = lastNameRule.validate(person.getLastName());
        if (!lastName.isValid())
            return lastName;

        ValidationResult date = dateRule.validate(person.getBirthDate());
        if (!date.isValid())
            return date;

        return validateGender(person.getGender());
    }

    private ValidationResult validateGender(char gender) {
        if (gender != 'M' && gender != 'F')
            return ValidationResult.fail("Género debe ser M o F");
        return ValidationResult.ok();
    }

}
