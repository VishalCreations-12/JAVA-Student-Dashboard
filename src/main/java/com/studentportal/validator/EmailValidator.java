package com.studentportal.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
    
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    private Pattern pattern;
    
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
            throws ValidatorException {
        if (value == null) {
            return;
        }
        
        String email = value.toString();
        
        if (!pattern.matcher(email).matches()) {
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "Invalid email format",
                "Please enter a valid email address"));
        }
    }
}

