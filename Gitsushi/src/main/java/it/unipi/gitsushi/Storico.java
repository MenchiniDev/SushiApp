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

/**
 *
 * @author loren
 */
public class Storico {

    private static final Logger logger = Logger.getLogger(Storico.class.getName());
    
    
    private ObservableList<Ordinazioni> ol;
    private ArrayList<Ordinazioni> l1 = new ArrayList(1);
    private int NumTotRow;
    @FXML private TableView<Ordinazioni> tab = new TableView<>();
    @FXML
    private void goToLogin() throws IOException
    {
        App.setRoot("primary");
    }
    
    @FXML
   public void initialize() throws IOException,SQLException
    {
        try{
            
            TableColumn nomeCol = new TableColumn("Pietanza");
            nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
            TableColumn lastCol = new TableColumn("numero");
            lastCol.setCellValueFactory(new PropertyValueFactory<>("numero"));
            TableColumn quantiCol = new TableColumn("costo €");
            quantiCol.setCellValueFactory(new PropertyValueFactory<>("costo"));
            TableColumn portate = new TableColumn("portate");
            portate.setCellValueFactory(new PropertyValueFactory<>("portate"));
        
             tab.getColumns().addAll( nomeCol, lastCol, quantiCol, portate);
             ol = FXCollections.observableArrayList();
             
             tab.setItems(ol);
            
        URL url = new URL("http://localhost:8080/Sushi/storicoload");
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
        JsonObject d = json.get(i).getAsJsonObject();
        Ordinazioni o = new Ordinazioni(d.get("pietanza").getAsString(),d.get("numero").getAsInt(),d.get("costo").getAsInt(),d.get("portata").getAsInt());
        ol.add(o);
        l1.add(o);
        NumTotRow++;
        }
            }catch(Exception e)
        {
            logger.info(e.getMessage());
        }
    }
    
    @FXML private void goBack() throws IOException
    {
        App.setRoot("menu");
    }
}
