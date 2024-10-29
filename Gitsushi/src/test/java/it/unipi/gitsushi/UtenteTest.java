/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package it.unipi.gitsushi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author loren
 */
public class UtenteTest {
    
    public UtenteTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Utente.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Utente instance = new Utente("Mario","aaa");
        String expResult = "Mario";
        String result = instance.getName();
        assertEquals(expResult, result);
        if(!expResult.equals(result)){
       fail("errore nell'UnitTest.");
        }
    }
    
}
