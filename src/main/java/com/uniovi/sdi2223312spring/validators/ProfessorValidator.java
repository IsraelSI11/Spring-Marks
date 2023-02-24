package com.uniovi.sdi2223312spring.validators;

import com.uniovi.sdi2223312spring.entities.Professor;
import com.uniovi.sdi2223312spring.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProfessorValidator implements Validator {

    @Autowired
    private ProfessorService professorService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Professor.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apellidos", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoria", "Error.empty");

        if (professor.getDni().length() != 9) {
            if(Character.isDigit(professor.getDni().charAt(8))){
                errors.rejectValue("dni", "Error.add.dni.length");
            }
        }
        if(professorService.findProfessorByDni(professor.getDni())!=null){
            errors.rejectValue("dni", "Error.add.dni.exists");
        }
        if (professor.getNombre().length() < 3) {
            errors.rejectValue("nombre", "Error.add.nombre.length");
        }
        if (professor.getApellidos().length() < 3) {
            errors.rejectValue("apellidos", "Error.add.apellidos.length");
        }
        if (professor.getCategoria().length() < 3) {
            errors.rejectValue("categoria", "Error.add.categoria.length");
        }
    }
}
