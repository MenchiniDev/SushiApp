/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipi.ProjectSushiApp;

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
 @Table(name="OrdinazioniPossibili", schema="lorenzo_menchini_615580")
 public class Ordinazione {
 @Id
 @GeneratedValue(strategy=GenerationType.AUTO)
 private Integer id;
 
@Column(name="pietanza")
 private String pietanza;
@Column(name="numero")
 private int numero;
@Column(name="costo")
 private int costo;


public Ordinazione(String p,int n,int q)
{
    pietanza=p;
    numero=n;
    costo=q;
    
}

    public Integer getId() {
        return id;
    }

    public String getPietanza() {
        return pietanza;
    }

    public int getNumero() {
        return numero;
    }

    public int getCosto() {
        return costo;
    }

 public Ordinazione() {
     pietanza="null";
    numero=0;
    costo=0;
 }
 
 }
