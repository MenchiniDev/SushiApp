package it.unipi.gitsushi;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.ibatis.jdbc.ScriptRunner;

public class PrimaryController {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PrimaryController.class.getName());
    
    @FXML private TextField user;
    @FXML private PasswordField pass;
    @FXML private Label TextError;
    @FXML private Button primaryButton;
    @FXML private Button reg;
    @FXML private Button carica;
    
    
    @FXML
    private void accedi() throws IOException, SQLException, NoSuchAlgorithmException {
        
        /*
        hashing della password
        */
        byte[] hash = PasswordHasher.getPasswordHash(pass.getText());
        String passHash = PasswordHasher.toHexString(hash);
        
      
        primaryButton.setDisable(true);
        reg.setDisable(true);
        carica.setDisable(true);
        primaryButton.setText("waiting...");
        boolean access= UtenteRegistrato(user.getText(), passHash);
      Task task = new Task<Void>() {
            @Override public Void call() {
                try
                {
                   if(access){
                       App.setRoot("menu");
                        return null;
                    }else
                {
              TextError.setText("password o utente non corretti");
              primaryButton.setDisable(false);
              reg.setDisable(false);
              carica.setDisable(false);
              throw new ClientException("password o utente non corretti");
                }
                }catch(ClientException r)
       {
          logger.info(r.getMessage() +" errore del client");
       }
                catch( Exception e )
                {
                    logger.info(e.getMessage());
                }
                return null;
            }
        };
      primaryButton.setText("accedi");
      if(access)
      {
      TextError.setText("password e utente corretti!");
      App.setRoot("menu");
      }else{
      TextError.setText("password o utente non corretti");
      }
        new Thread(task).start();
        
    }
    
    @FXML
    private void register() throws IOException
    {
         App.setRoot("register");
         logger.info("registrazione in corso");
    }
        @FXML
    private void caricaDB() throws IOException, SQLException
    {
        Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
        ScriptRunner sr = new ScriptRunner(co);
        BufferedReader reader = new BufferedReader(new FileReader(PrimaryController.class.getResource("/sql/TablesAndPopulation.sql").getPath()));
        sr.runScript(reader);
        TextError.setText("Database Caricato!");
    }    
    
    private boolean UtenteRegistrato(String user,String pass) throws IOException,SQLException   
    {
        if(user.equals("") || pass.equals(""))
            return false;
        Gson gson = new Gson();
        URL url = new URL("http://localhost:8080/Sushi/Control?user="+user+"&pass="+pass);
        /*
        mando tutti gli utenti, dal client controllo se quello immesso è tra loro.
        */
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));               
                
                Boolean ok=false;
                
                
                    if(in.readLine().equals("OK"))
                        ok=true;
                    else 
                        ok=false;
                in.close();
               return ok;
       }
    }
   