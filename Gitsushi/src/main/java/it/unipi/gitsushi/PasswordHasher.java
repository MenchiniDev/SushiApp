/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.gitsushi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




public class PasswordHasher {

  // Metodo per ottenere il valore hash di una password
  public static byte[] getPasswordHash(String password) throws NoSuchAlgorithmException {
    // Ottieni un oggetto MessageDigest per l'algoritmo di hashing desiderato
    MessageDigest md = MessageDigest.getInstance("SHA-256");

    // Esegui l'hashing della password
    md.update(password.getBytes());
    return md.digest();
  }

  // Metodo per convertire un array di byte in una stringa esadecimale
  public static String toHexString(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(0xff & bytes[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
