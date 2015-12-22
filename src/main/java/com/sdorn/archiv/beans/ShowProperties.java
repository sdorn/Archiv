/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.archiv.beans;

import com.sdorn.archiv.entities.md_property;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author siegf
 */
@ManagedBean
@SessionScoped
public class ShowProperties implements Serializable {

    /**
     * Creates a new instance of showProperies
     */
    public ShowProperties() {
        this.properties = new ArrayList<>();
    }

    private List<md_property> properties;

    /**
     * Get the value of properties
     *
     * @return the value of properties
     */
    public List<md_property> getProperties() {
        return properties;
    }

    /**
     * Set the value of properties
     *
     * @param properties new value of properties
     */
    public void setProperties(List<md_property> properties) {
        this.properties = properties;
    }

}
