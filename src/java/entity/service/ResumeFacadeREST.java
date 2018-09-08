/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Resume;
import entity.ResumePK;
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
@Path("entity.resume")
public class ResumeFacadeREST extends AbstractFacade<Resume> {

    @PersistenceContext(unitName = "yunzhiPU")
    private EntityManager em;

    private ResumePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;startDate=startDateValue;endDate=endDateValue;userId=userIdValue;schoolId=schoolIdValue;identityName=identityNameValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.ResumePK key = new entity.ResumePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> startDate = map.get("startDate");
        if (startDate != null && !startDate.isEmpty()) {
            key.setStartDate(new java.util.Date(startDate.get(0)));
        }
        java.util.List<String> endDate = map.get("endDate");
        if (endDate != null && !endDate.isEmpty()) {
            key.setEndDate(new java.util.Date(endDate.get(0)));
        }
        java.util.List<String> userId = map.get("userId");
        if (userId != null && !userId.isEmpty()) {
            key.setUserId(userId.get(0));
        }
        java.util.List<String> schoolId = map.get("schoolId");
        if (schoolId != null && !schoolId.isEmpty()) {
            key.setSchoolId(new java.lang.Integer(schoolId.get(0)));
        }
        java.util.List<String> identityName = map.get("identityName");
        if (identityName != null && !identityName.isEmpty()) {
            key.setIdentityName(identityName.get(0));
        }
        return key;
    }

    public ResumeFacadeREST() {
        super(Resume.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Resume entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Resume entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.ResumePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Resume find(@PathParam("id") PathSegment id) {
        entity.ResumePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Resume> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Resume> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
