/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.archiv.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author siegf
 */
@ManagedBean
@RequestScoped

public class ShowMeta {

    private UploadedFile file;
    private String MetaData;

    public ShowMeta() {
    }

    public ShowMeta(UploadedFile file) {
        this.file = file;
    }

    /**
     * Get the value of file
     *
     * @return the value of file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * Set the value of file
     *
     * @param file new value of file
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    /**
     * Get the value of MetaData
     *
     * @return the value of MetaData
     */
    public String getMetaData() {
        this.MetaData = "application/text";
        this.MetaData = this.file.getContentType();
        return MetaData;
    }

    /**
     * Set the value of MetaData
     *
     * @param MetaData new value of MetaData
     */
    public void setMetaData(String MetaData) {
        this.MetaData = MetaData;
    }

}
