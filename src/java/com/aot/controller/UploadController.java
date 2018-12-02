/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aot.controller;

import com.aot.service.UploadService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kirellos
 */
@Path("uploadFile")
public class UploadController {
    
    
    @EJB
    UploadService  uploadService;
    
    /**
     * Retrieves representation of an instance of
     * com.aot.services.UploadFileResourceWS
     *
     * @param fileInputString
     * @param fileInputDetails
     * @param path
     *
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputString,
            @FormDataParam("file") FormDataContentDisposition fileInputDetails,
            @FormDataParam("path") String path ) {        
        
        if (fileInputString != null && fileInputDetails != null && fileInputDetails.getFileName() != null) {

            String result = uploadService.uploadFileOnServer(fileInputString, fileInputDetails, path);

            if (result.equals("{\"message\" : \"error\"}")) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            return Response.ok(result, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
