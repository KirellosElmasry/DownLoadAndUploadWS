/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aot.controller;

import com.aot.model.DownloadFileInfo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Kirellos
 */
@Path("download")
public class DownloadController {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DownLoadFileResourceWS
     */
    public DownloadController() {
    }

    /**
     * Retrieves representation of an instance of
     * com.aot.services.config.DownLoadFileResourceWS
     *
     * @param filePath
     * @return an instance of Response
     */
    @GET
    @Path("/{filePath}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("filePath") String filePath) {

        try {
            File file = new File(filePath);
            String[] paths = filePath.split("\\\\");
            
            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment; filename= " + paths[paths.length-1]);
            return response.build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
/*
    @GET
    @Path("/helloWorldZip/{filePath}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public StreamingOutput helloWorldZip(@PathParam("filePath") String filePath) {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream arg0) throws IOException, WebApplicationException {
                // TODO Auto-generated method stub

                //ByteArrayInputStream reader = (ByteArrayInputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream();     
                //byte[] input = new byte[2048];  
                java.net.URL uri = Thread.currentThread().getContextClassLoader().getResource("");
                File file = new File(filePath);

                FileInputStream fizip = new FileInputStream(file);

                try {
                    OutputStream out = new FileOutputStream(file);
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = fizip.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    out.flush();
                    out.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        };
    }

    @POST
    @Path("/getFileFromServer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFileFromServer(DownloadFileInfo info) {

        try {
            //url = "http://10.20.90.206:7003/downLoad/webresources/file/%2Fu001%2FmbFiles%2F2018_Employees%20Handbook_Ar.pdf";
            //url = "http://localhost:7101/downLoad/webresources/file/C%3A%5CAiOLog.txt";
            //url = "http://localhost:7101/downLoad/webresources/file/C:/AiOLog.txt";
            URL url2 = new URL(info.getFilePath());

            //Client client = ClientBuilder.newClient();
            // final InputStream responseStream = client.target(url).request().get(InputStream.class);
            final InputStream responseStream = url2.openStream();

            System.out.println("responseStream  avialable  " + responseStream.available());
            System.out.println("url   " + url2);

            StreamingOutput output = new StreamingOutput() {
                @Override
                public void write(OutputStream out) throws IOException, WebApplicationException {
                    int length;
                    byte[] buffer = new byte[1024];
                    while ((length = responseStream.read(buffer)) != -1) {
                        out.write(buffer, 0, length);
                    }
                    out.flush();
                    responseStream.close();
                }
            };

            return Response.ok(output).header("Content-Disposition", "attachment, filename=\"...\"").build();
        } catch (MalformedURLException ex) {
            Logger.getLogger(DownloadController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DownloadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @POST
    @Path("/redirect")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response redirectUrl(DownloadFileInfo info) {

        try {

            return Response.status(Status.MOVED_PERMANENTLY).location(new URI(info.getFilePath())).build();

        } catch (URISyntaxException ex) {
            Logger.getLogger(DownloadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("/getData")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getData() {

        return Response.ok("Welcome user").build();
    }

    @POST
    @Path("/download")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(DownloadFileInfo info) {
        try {
            URL url = new File(info.getFilePath()).toURI().toURL();
            InputStream is = url.openStream();
            //System.out.println();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = is.read(buf)) >= 0) {
                os.write(buf, 0, n);
            }
            os.close();
            is.close();

            byte[] data = os.toByteArray();
            
            String fileName = "fileName";
            FileOutputStream fileOuputStream = new FileOutputStream(fileName);
            fileOuputStream.write(data);

            
            Response.ResponseBuilder response = Response.ok((Object) fileOuputStream);
            response.header("Content-Disposition", "attachment; filename= " +fileName );
            return response.build();
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @POST
    @Path("/oldDownload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response oldDownloadFile(DownloadFileInfo info) {

        try {
            File file = new File(info.getFilePath());
            String[] paths = info.getFilePath().split("/");
            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment; filename= " + paths[paths.length - 1]);
            return response.build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
*/
}
