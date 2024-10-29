/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.unipi.ProjectSushiApp;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author loren
 */
public interface ProjectRepository extends CrudRepository<Utente, Integer>{

    public Utente findByName(String name);
    
}
