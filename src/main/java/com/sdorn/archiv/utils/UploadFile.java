/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.archiv.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.NamedList;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author siegf
 */
@ManagedBean
@RequestScoped
public class UploadFile {

    private UploadedFile uploadedfile;
    private Properties props;
    private HttpSolrServer server;

    public UploadFile() {
    }

    public UploadFile(UploadedFile uploadedfile) {
        this.uploadedfile = uploadedfile;
    }

    /**
     * Get the value of uploadedfile
     *
     * @return the value of uploadedfile
     */
    public UploadedFile getUploadedfile() {
        return uploadedfile;
    }

    /**
     * Set the value of uploadedfile
     *
     * @param uploadedfile new value of uploadedfile
     */
    public void setUploadedfile(UploadedFile uploadedfile) {
        this.uploadedfile = uploadedfile;
    }

    public void load() {
        NamedList <Object> list = null;
        if (uploadedfile != null) {
            String fileName = uploadedfile.getFileName();
            ShowMeta md = new ShowMeta(uploadedfile);
            try {
                //ClassLoader cl = Thread.currentThread().getContextClassLoader();
                //props.load(cl.getResourceAsStream("global"));
                //server = new HttpSolrServer(props.getProperty("Solserver"));
                server = new HttpSolrServer("http://ubuntu2:8983/solr/gettingstarted");
                ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
                req.addFile(new File(fileName), uploadedfile.getContentType());
                req.setParam("literal.id", fileName);
                req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
                XMLResponseParser responseparser = new XMLResponseParser();
                server.setParser(responseparser);
                list = server.request(req);
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (SolrServerException ex) {
                //Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        }
        //return list;
    }
}
