/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Vedio;
import entity.VedioPK;
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
@Path("entity.vedio")
public class VedioFacadeREST extends AbstractFacade<Vedio> {

    @PersistenceContext(unitName = "yunzhiPU")
    private EntityManager em;

    private VedioPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;sequenceNumber=sequenceNumberValue;chapterCourseSchoolId=chapterCourseSchoolIdValue;chapterCourseId=chapterCourseIdValue;chapterCourseStartDate=chapterCourseStartDateValue;chapterCourseEndDate=chapterCourseEndDateValue;chapterSequenceNumber=chapterSequenceNumberValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.VedioPK key = new entity.VedioPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> sequenceNumber = map.get("sequenceNumber");
        if (sequenceNumber != null && !sequenceNumber.isEmpty()) {
            key.setSequenceNumber(new java.lang.Integer(sequenceNumber.get(0)));
        }
        java.util.List<String> chapterCourseSchoolId = map.get("chapterCourseSchoolId");
        if (chapterCourseSchoolId != null && !chapterCourseSchoolId.isEmpty()) {
            key.setChapterCourseSchoolId(new java.lang.Integer(chapterCourseSchoolId.get(0)));
        }
        java.util.List<String> chapterCourseId = map.get("chapterCourseId");
        if (chapterCourseId != null && !chapterCourseId.isEmpty()) {
            key.setChapterCourseId(new java.lang.Integer(chapterCourseId.get(0)));
        }
        java.util.List<String> chapterCourseStartDate = map.get("chapterCourseStartDate");
        if (chapterCourseStartDate != null && !chapterCourseStartDate.isEmpty()) {
            key.setChapterCourseStartDate(new java.util.Date(chapterCourseStartDate.get(0)));
        }
        java.util.List<String> chapterCourseEndDate = map.get("chapterCourseEndDate");
        if (chapterCourseEndDate != null && !chapterCourseEndDate.isEmpty()) {
            key.setChapterCourseEndDate(new java.util.Date(chapterCourseEndDate.get(0)));
        }
        java.util.List<String> chapterSequenceNumber = map.get("chapterSequenceNumber");
        if (chapterSequenceNumber != null && !chapterSequenceNumber.isEmpty()) {
            key.setChapterSequenceNumber(new java.lang.Integer(chapterSequenceNumber.get(0)));
        }
        return key;
    }

    public VedioFacadeREST() {
        super(Vedio.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Vedio entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Vedio entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.VedioPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vedio find(@PathParam("id") PathSegment id) {
        entity.VedioPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vedio> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vedio> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
