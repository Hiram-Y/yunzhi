/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Course;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import web.SearchController;

/**
 *
 * @author Dawson
 */
@FacesConverter( "courseConverter")
public class CourseConverter implements Converter{

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            SearchController searchController = (SearchController) fc.getApplication()
                .getVariableResolver().resolveVariable(fc, "searchController");
            for(Course c:searchController.getCourseList()){
                if(c.getName().equals(value)){
                    return c;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Course) object).getName());
        } else {
            return null;
        }
    }
}
