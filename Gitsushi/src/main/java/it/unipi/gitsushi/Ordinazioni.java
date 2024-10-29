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
public class Ordinazioni implements Serializable{
    public Integer id;
    public String nome;
    public int numero;
    public int costo;
    public int portate;
    
    Ordinazioni(String n, int p,int q,int po)
    {
        id=0;
        numero=p; // p;
        costo = q;
        nome=n;
        portate=po;
    }

    public Integer getID() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public int getCosto() {
        return costo;
    }
    
    public int getPortate() {
        return portate;
    }

    
}
