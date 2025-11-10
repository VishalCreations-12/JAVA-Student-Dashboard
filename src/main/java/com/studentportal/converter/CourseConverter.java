package com.studentportal.converter;

import com.studentportal.dao.CourseDAO;
import com.studentportal.entity.Course;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("courseConverter")
public class CourseConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            CourseDAO courseDAO = new CourseDAO();
            Long courseId = Long.parseLong(value);
            return courseDAO.findById(courseId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        
        if (value instanceof Course) {
            return String.valueOf(((Course) value).getCourseId());
        }
        
        return value.toString();
    }
}

