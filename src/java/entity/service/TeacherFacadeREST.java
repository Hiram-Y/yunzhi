/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Teacher;
import entity.TeacherPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Dawson
 */
@Stateless
@Path("entity.teacher")
public class TeacherFacadeREST extends AbstractFacade<Teacher> {

    @PersistenceContext(unitName = "yunzhiPU")
    private EntityManager em;

    private TeacherPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;userId=userIdValue;courseSchoolId=courseSchoolIdValue;courseId=courseIdValue;courseStartDate=courseStartDateValue;courseEndDate=courseEndDateValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.TeacherPK key = new entity.TeacherPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> userId = map.get("userId");
        if (userId != null && !userId.isEmpty()) {
            key.setUserId(userId.get(0));
        }
        java.util.List<String> courseSchoolId = map.get("courseSchoolId");
        if (courseSchoolId != null && !courseSchoolId.isEmpty()) {
            key.setCourseSchoolId(new java.lang.Integer(courseSchoolId.get(0)));
        }
        java.util.List<String> courseId = map.get("courseId");
        if (courseId != null && !courseId.isEmpty()) {
            key.setCourseId(new java.lang.Integer(courseId.get(0)));
        }
        java.util.List<String> courseStartDate = map.get("courseStartDate");
        if (courseStartDate != null && !courseStartDate.isEmpty()) {
            key.setCourseStartDate(new java.util.Date(courseStartDate.get(0)));
        }
        java.util.List<String> courseEndDate = map.get("courseEndDate");
        if (courseEndDate != null && !courseEndDate.isEmpty()) {
            key.setCourseEndDate(new java.util.Date(courseEndDate.get(0)));
        }
        return key;
    }

    public TeacherFacadeREST() {
        super(Teacher.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Teacher entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Teacher entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.TeacherPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Teacher find(@PathParam("id") PathSegment id) {
        entity.TeacherPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Teacher> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Teacher> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
