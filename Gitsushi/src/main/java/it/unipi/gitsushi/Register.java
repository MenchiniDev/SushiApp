/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.gitsushi;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author loren
 */
public class Register {
    
         private static final Logger logger = Logger.getLogger(Register.class.getName());
    
        @FXML private PasswordField pass;
        @FXML private PasswordField repeat;
        @FXML private TextField user;
        @FXML private Button primaryButton;
        @FXML private VBox st1;
        @FXML private Text textError;
        @FXML 
        private void goAccedi() throws IOException {
            Task task = new Task<Void>() {
            @Override public Void call() {
                try
                {
                    if(!repeat.getText().equals(pass.getText()))
                    {
                    throw new ClientException("le password non corrispondono");
                    }
                    Utente u;
                     byte[] hash = PasswordHasher.getPasswordHash(pass.getText());
                     String passHash = PasswordHasher.toHexString(hash);
                     u = new Utente(user.getText(), passHash);
                     URL url = new URL("http://localhost:8080/Sushi/add");
                     HttpURLConnection con = (HttpURLConnection) url.openConnection();
                     con.setRequestMethod("POST");
                    con.setDoOutput(true);
                        
                       Gson gson = new Gson();
                      String obj = gson.toJson(u);
                     String urlParameters  = "All="+obj;
                     byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                     int    postDataLength = postData.length;

                     con.setInstanceFollowRedirects( false );
                     con.setRequestMethod( "POST" );
                     con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
                     con.setRequestProperty( "charset", "utf-8");
                     con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                     con.setUseCaches( false );
                    try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
                            wr.write( postData );
                        }
                     //PrintStream out = new PrintStream( con.getOutputStream() );
                     //out.println(postData);
                     
                     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));               
                
                      Boolean ok;
                
                      if(in.readLine().equals("OK"))
                        ok=true;
                    else 
                        ok=false;
                    in.close();
                    if(ok){ 
                     App.setRoot("primary");
                    }else
                        throw new  ClientException("Utente gia registrato!");
                     }catch(ClientException r)
                     {
                         textError.setText(r.getMessage());
                           logger.info(r.getMessage());
                           return null;
                     }
                     catch( Exception e )
                     {
                         textError.setText(e.getMessage());
                        logger.info(e.getMessage());
                        return null;
                     }
                    return null;
            }
        };
        textError.setText("Registrazione avvenuta con successo!");
        new Thread(task).start();
        
    }
       @FXML 
       public void comeBack() throws IOException
       {
           App.setRoot("primary");
       }

       
}
