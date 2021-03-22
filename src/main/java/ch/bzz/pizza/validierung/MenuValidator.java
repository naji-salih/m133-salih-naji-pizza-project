package ch.bzz.pizza.validierung;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class MenuValidator implements ConstraintValidator<MenuConstraint, String> {
    @Override
    public void initialize(MenuConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String menuName,
                           ConstraintValidatorContext cxt) {
        return menuName.toLowerCase().contains("menu")
                && (menuName.length() > 2) && (menuName.length() < 30);
    }

}
