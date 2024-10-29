/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.gitsushi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 *
 * @author loren
 */
public class Resoconto {
    
    private static final Logger logger = Logger.getLogger(Resoconto.class.getName());
    ArrayList<Ordinazioni> Orders = new ArrayList(1);
    private int NumTotRow;
    @FXML private TableView<Ordinazioni> tabOrders=new TableView<>();
    private ObservableList<Ordinazioni> olOrdinati;
    @FXML Text SaveText;
    @FXML
    private void exit() 
    {
        try{
        URL url = new URL("http://localhost:8080/Sushi/exit");
        
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setDoOutput(true);
       
          Gson gson = new Gson();
          String obj = gson.toJson(Orders);
          String urlParameters  = "Ord="+obj;
          byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
          int postDataLength = postData.length;
          con.setInstanceFollowRedirects( false );
          con.setRequestMethod( "POST" );
          con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
          con.setRequestProperty( "charset", "utf-8");
          con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
          con.setUseCaches( false );
          try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
              wr.write( postData );
          }
         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
       
        if(!in.readLine().equals("OK"))
            throw new ClientException("errore nell'immisione dell'ordine nello storico");
                else
        {
                SaveText.setText("Ordinazione salvata!");
        }
        in.close();

        }catch(ClientException ce)
        {
            logger.info(ce.getMessage());
        }catch(IOException me)
        {
            logger.info("problema nell'exit "+me.getMessage());
        }
    }
        
    
    
     @FXML
    public void initialize() throws IOException,SQLException
    {
        Orders = new ArrayList(1);
        
        TableColumn nomeColOrd = new TableColumn("Pietanza");
        
        nomeColOrd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        TableColumn lastColOrd = new TableColumn("numero");
        
         lastColOrd.setCellValueFactory(new PropertyValueFactory<>("numero"));
         
        TableColumn quantiColOrd = new TableColumn("costo");
        
        quantiColOrd.setCellValueFactory(new PropertyValueFactory<>("costo"));
        
        TableColumn portate = new TableColumn("portate");
        portate.setCellValueFactory(new PropertyValueFactory<>("portate"));



        tabOrders.getColumns().addAll( nomeColOrd, lastColOrd, quantiColOrd, portate);
        olOrdinati = FXCollections.observableArrayList();
 
        tabOrders.setItems(olOrdinati);
        
          URL url = new URL("http://localhost:8080/Sushi/orders");
        
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
        olOrdinati.add(o);
        Orders.add(o);
        NumTotRow++;
        }
        tabOrders.setItems(olOrdinati);
       }
    
    @FXML public void gotoMenu() throws IOException
    {
        App.setRoot("menu");
    }
    
    @FXML
    public void WriteAdv() throws IOException
    {
        URL url = new URL("http://localhost:8080/Sushi/set?name="+tabOrders.getSelectionModel().getSelectedItem().getNome());
        
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("GET");
       con.setDoOutput(true);
       
       BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
       

        if( in.readLine().equals("OK")){
            App.setRoot("recensione");
         }
        else 
            return;
        in.close();
    }
    
    @FXML
    public void removeOne()
    {
        if (tabOrders.getSelectionModel().getSelectedItem() != null) {
            String nomOp = tabOrders.getSelectionModel().getSelectedItem().nome;
             Boolean UnaVolta = false;
       
             /*caso in cui bisogna rimuovere la riga*/
        if(tabOrders.getSelectionModel().getSelectedItem().portate<=1)
        {
            olOrdinati.remove(tabOrders.getSelectionModel().getSelectedItem());
            Orders.remove(tabOrders.getSelectionModel().getSelectedItem());
            return;
        }
        
        
        Orders.removeIf(Ordinazioni-> !Ordinazioni.nome.equals("null"));
        
         /*caso in cui basta decrementare le portate*/
        for(int i=0;i<olOrdinati.size();i++)
        {
            Orders.add(olOrdinati.get(i));
        }
        
        olOrdinati.removeIf(Ordinazioni-> !Ordinazioni.nome.equals("null"));
        for(int i=0;i<Orders.size();i++)
        {
            if(Orders.get(i).nome.equals(nomOp))
            {
                if(!UnaVolta){
                Orders.get(i).portate--;
                UnaVolta=true;
                }
            }
            olOrdinati.add(Orders.get(i));
            if(UnaVolta)
                tabOrders.getItems().set(i, Orders.get(i));
        }
      }
    }
        
}
