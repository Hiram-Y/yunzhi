/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Chapter;
import entity.ChapterPK;
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
@Path("entity.chapter")
public class ChapterFacadeREST extends AbstractFacade<Chapter> {

    @PersistenceContext(unitName = "yunzhiPU")
    private EntityManager em;

    private ChapterPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;courseSchoolId=courseSchoolIdValue;courseId=courseIdValue;courseStartDate=courseStartDateValue;courseEndDate=courseEndDateValue;sequenceNumber=sequenceNumberValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.ChapterPK key = new entity.ChapterPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
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
        java.util.List<String> sequenceNumber = map.get("sequenceNumber");
        if (sequenceNumber != null && !sequenceNumber.isEmpty()) {
            key.setSequenceNumber(new java.lang.Integer(sequenceNumber.get(0)));
        }
        return key;
    }

    public ChapterFacadeREST() {
        super(Chapter.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Chapter entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Chapter entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.ChapterPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Chapter find(@PathParam("id") PathSegment id) {
        entity.ChapterPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Chapter> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Chapter> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
