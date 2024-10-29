/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.gitsushi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author loren
 */
public class RecensioniVista {
    private static final Logger logger = Logger.getLogger(RecensioniVista.class.getName());
    @FXML private StackPane s1;
     private ObservableList<Recensione> ol;
    private ArrayList<String> l1 = new ArrayList(1);
    private int NumTotRow;
    @FXML private TableView<Recensione> tab = new TableView<>();
     @FXML
   public void initialize() throws IOException,SQLException
    {
        try{
            
            TableColumn nomeCol = new TableColumn("Recensione");
            nomeCol.setCellValueFactory(new PropertyValueFactory<>("rec"));
        
             tab.getColumns().addAll( nomeCol);
             ol = FXCollections.observableArrayList();
             
             tab.setItems(ol);
            
          URL url = new URL("http://localhost:8080/Sushi/see");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));   
                
                String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
         }
        in.close();
        Gson gson = new Gson();
 
        JsonArray json = gson.fromJson(content.toString(), JsonArray.class);
        for (int i = 0; i < json.size(); i++) {

        String o =  json.get(i).getAsString();
        ol.add(new Recensione(o));
        l1.add(o);
        NumTotRow++;
        }
            }catch(Exception e)
        {
            logger.info(e.getMessage());
        }
    } 
    @FXML
    public void comeBack() throws IOException
    {
        App.setRoot("menu");
    }
}
