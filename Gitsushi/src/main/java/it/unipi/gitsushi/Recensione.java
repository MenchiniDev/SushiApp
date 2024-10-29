/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.gitsushi;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 *
 * @author loren
 */
public class Recensione {
    public String rec;
    private static final Logger logger = Logger.getLogger(Recensione.class.getName());
    
        
    public Recensione(String o)
    {
        rec = o;
    }
    public Recensione()
    {
        rec = "default";
    }

    public String getRec() {
        return rec;
    }
   
    @FXML private TextArea motText;
    
    @FXML private void saveAdv()
    {
        try
        {
            
       URL url = new URL("http://localhost:8080/Sushi/saveAdv");
        
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("POST");
       con.setDoOutput(true);
       
          Gson gson = new Gson();
          String obj = gson.toJson(motText.getText());
          String urlParameters  = "voto="+obj;
          byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
          int postDataLength = postData.length;
          con.setInstanceFollowRedirects( false );
          con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
          con.setRequestProperty( "charset", "utf-8");
          con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
          con.setUseCaches( false );
          try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
              wr.write( postData );
          }
       BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
       
        if(in.readLine().equals("OK"))
            App.setRoot("resoconto1");
        else 
            throw new ClientException("errore nell'immisione dell'ordine nel databse");
        in.close();
        }catch(ClientException ce)
        {
            logger.info(ce.getMessage());
        }catch(Exception e)
        {
        logger.info("problema nel salvataggio della recensione " +e.getMessage());
        }
    }
    
}
