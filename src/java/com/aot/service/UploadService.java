/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aot.service;

import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import javax.ejb.Stateless;

/**
 *
 * @author Kirellos
 */
@Stateless
public class UploadService {

    //private static final String SAVE_FOLDER = "aotmedan/medan_Files/tasks/";

    public UploadService() {
    }

    public String uploadFileOnServer(InputStream fileInputString,
            FormDataContentDisposition fileInputDetails) {

        String OS = System.getProperty("os.name").toLowerCase();
        String root;

        if (OS.contains("win")) {
            root = "C:\\tmp";
        } else {
            root = "/tmp";
        }
        
        String location = "";
        File file = new File(root);

        if (!file.exists()) {
            file.mkdirs();
        }

        String fileLocation = file + File.separator + fileInputDetails.getFileName();

        NumberFormat myFormat = NumberFormat.getInstance();

        myFormat.setGroupingUsed(
                true);

        // Save the file 
        try {
            OutputStream out = new FileOutputStream(new File(fileLocation));
            byte[] buffer = new byte[1024];
            int bytes = 0;
            long file_size = 0;
            while ((bytes = fileInputString.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
                file_size += bytes;
            }
            out.flush();
            out.close();

            location = file.getAbsolutePath() + File.separator + fileInputDetails.getFileName();

            System.out.println("************** full Location ==> " + location + "   fileSize==> " + file_size);

        } catch (IOException ex) {
            System.out.println("Unable to save file ==> " + location);
            ex.printStackTrace();
            return "{\"message\" : \"error\"}";
        }

        return "{\"message\" : \"" + pretyPath(location)
                + "\"}";
    }

    private String pretyPath(String path) {

        path = path.replaceAll("\\\\", "%5C");
        return path.replaceAll("/", "%2F");
    }
}
