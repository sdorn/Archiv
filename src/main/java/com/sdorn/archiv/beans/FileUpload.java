/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template uploadedFile, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.archiv.beans;

import com.sdorn.archiv.entities.md_property;
import com.sdorn.archiv.utils.ShowMeta;
import com.sdorn.archiv.utils.UploadFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.NamedList;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author siegf
 */
@ManagedBean
@SessionScoped
public class FileUpload implements Serializable {

    public FileUpload() {
        this.names = new ArrayList<>();
        this.prop = new Properties();
    }

    private UploadedFile uploadedFile;
    private String tikaout;
    private List<md_property> names;
    private Properties prop;
    private String status;

    @ManagedProperty("#{showProperties}")
    private ShowProperties showProperties;
    
   

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    

    public void setShowProperties(ShowProperties showProperties) {
        this.showProperties = showProperties;
    }

    public List<md_property> getNames() {
        return names;
    }

    public void setNames(List<md_property> names) {
        this.names = names;
    }

    /**
     * Get the value of tikaout
     *
     * @return the value of tikaout
     */
    public String getTikaout() {
        return tikaout;
    }

    /**
     * Set the value of tikaout
     *
     * @param tikaout new value of tikaout
     */
    public void setTikaout(String tikaout) {
        this.tikaout = tikaout;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    public void upload(FileUploadEvent event){
        this.uploadedFile = event.getFile();
        
    }
    
    public void load(){
        NamedList <Object> list = null;
        UploadFile upload = new UploadFile(uploadedFile);
        upload.load();
    }
    

    public void _upload(FileUploadEvent event) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            prop.load(cl.getResourceAsStream("global"));

            UploadedFile uploadedFile;
            uploadedFile = event.getFile();
            ShowMeta md = new ShowMeta(uploadedFile);
            String fileName = uploadedFile.getFileName();
            String contentType = md.getMetaData();
            HttpSolrServer server;
            server = new HttpSolrServer(prop.getProperty("Solserver"));
            XMLResponseParser responseparser = new XMLResponseParser();
            ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
            req.addFile(new File(fileName), contentType);
            req.setParam("literal.id", fileName);
            req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            req.setResponseParser(responseparser);
            this.status = server.request(req,responseparser).toString();
           
        } catch (IOException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SolrServerException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void _upload_simple() {
        if (uploadedFile != null) {
            FacesMessage message = new FacesMessage("Succesful", uploadedFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            try (InputStream input = uploadedFile.getInputstream()) {
                BodyContentHandler handler = new BodyContentHandler();
                Metadata metadata = new Metadata();
                ContentHandler xhandler = new ToXMLContentHandler();
                String contentType = uploadedFile.getContentType();
                if (contentType.equals("application/pdf")) {
                    PDFParser parser = new PDFParser();
                    ParseContext context = new ParseContext();
                    parser.parse(input, xhandler, metadata, context);

                } else {
                    AutoDetectParser parser = new AutoDetectParser();
                    parser.parse(input, xhandler, metadata);
                }

                String[] meta_names;
                meta_names = metadata.names();

                for (String meta_name : meta_names) {
                    if (!metadata.isMultiValued(meta_name)) {
                        String v = metadata.get(meta_name);
                        md_property prop = new md_property(meta_name, v);
                        names.add(prop);
                    } else {
                        String[] v = metadata.getValues(meta_name);
                        md_property prop = new md_property(meta_name, v);
                        names.add(prop);
                    }
                    this.showProperties.setProperties(this.names);
                }
                this.tikaout = handler.toString();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (SAXException | TikaException ex) {
                Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
