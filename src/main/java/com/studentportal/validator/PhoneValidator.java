package com.studentportal.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {
    
    private static final String PHONE_PATTERN = 
        "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$";
    
    private Pattern pattern;
    
    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
            throws ValidatorException {
        if (value == null) {
            return;
        }
        
        String phone = value.toString().replaceAll("[\\s()-]", "");
        
        if (phone.length() < 10 || phone.length() > 15) {
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "Invalid phone number",
                "Phone number must be between 10 and 15 digits"));
        }
        
        if (!phone.matches("^[0-9+]+$")) {
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "Invalid phone number",
                "Phone number can only contain digits and + sign"));
        }
    }
}

