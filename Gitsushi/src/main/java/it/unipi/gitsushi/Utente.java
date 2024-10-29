/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.gitsushi;

import java.io.Serializable;


/**
 *
 * @author loren
 */
public class Utente  implements Serializable  {

 public String name;
 public String pass;

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

 public Utente(String n, String p) {
     name = n;
     pass=p;
 }
 
 }
