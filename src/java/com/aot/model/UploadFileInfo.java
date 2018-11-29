/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aot.model;

/**
 *
 * @author kirellos
 */
public class UploadFileInfo {

    private String title;
    private String fullPath;
    private boolean mainFile; //this is for cms file

    public UploadFileInfo(String name, String fullPath) {
        title = name;
        mainFile = false;
        this.fullPath = fullPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setMainFile(boolean mainFile) {
        this.mainFile = mainFile;
    }

    public boolean isMainFile() {
        return mainFile;
    }
    
}
