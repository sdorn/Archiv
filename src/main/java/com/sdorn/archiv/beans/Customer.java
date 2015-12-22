/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdorn.archiv.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author siegf
 */
@ManagedBean
@SessionScoped
public class Customer {

   
    /**
     * Creates a new instance of Customer
     */
    public Customer() {
    }
    private String firstname; 
    private String lastname;
    

    /**
     * Get the value of firstname
     *
     * @return the value of firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the value of firstname
     *
     * @param firstname new value of firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

   

    /**
     * Get the value of lastname
     *
     * @return the value of lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the value of lastname
     *
     * @param lastname new value of lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    
    public String save(){
        return "/showCustomer.xhtml";
    }
        
}
