/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.archiv.entities;

import java.io.Serializable;

/**
 *
 * @author siegf
 */

public class md_property implements Serializable{

    public md_property() {
    }

    public md_property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public md_property(String key, String[] values) {
        this.key = key;
        this.values = values;
    }
    

    private String key;
    private String value;
    private String[] values;

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the value of key
     *
     * @return the value of key
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the value of key
     *
     * @param key new value of key
     */
    public void setKey(String key) {
        this.key = key;
    }

}
