/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipi.ProjectSushiApp;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author loren
 */
@Entity
 @Table(name="Utente", schema="lorenzo_menchini_615580")
public class Utente {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
//private Integer id;
 
 @Column(name="name")
 private String name;
 @Column(name="password")
 private String pass;

 

 public Utente(String n, String p) {
     name = n;
     pass=p;
 }
 
 public Utente()
 {
     name = "default_user";
     pass = "default_password";
 }
 
 public String getName()
 {
   return name;  
 }
 public String getPass()
 {
     return pass;
 }
 
 }
