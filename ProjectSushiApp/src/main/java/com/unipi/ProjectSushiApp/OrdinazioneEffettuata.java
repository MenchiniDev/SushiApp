/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipi.ProjectSushiApp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author loren
 */
@Entity
 @Table(name="Ordinazioni", schema="lorenzo_menchini_615580")
 public class OrdinazioneEffettuata extends Ordinazione{
    
 @Column(name="portata")
 private int portata;


public OrdinazioneEffettuata(String s,int n,int q,int po)
{
    super(s,n,q);
    portata=po;
}

public int getPortata()
{
    return portata;
}

 public OrdinazioneEffettuata() {
     super();
    portata=0;
 }
 
 }
