package ru.kata.spring.boot_security.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;


    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User userInDB = userRepository.findByUsername(user.getUsername()).orElse(null);

        if((userInDB != null)
                && (user.getId() != userInDB.getId())) {

            errors.rejectValue("username", "",
                    "There are already exists user with this username!");
        }
    }
}