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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondaryController {
    
    private static final Logger logger = Logger.getLogger(SecondaryController.class.getName());

    private ArrayList<Ordinazioni> l1;
    private ArrayList<Ordinazioni> l1Ord;
    int NumTotRow;
    int numRowOrd;
    @FXML private Button storico;
    private ObservableList<Ordinazioni> ol;
    
    @FXML private TableView<Ordinazioni> tabOrdinati = new TableView<>();
    private ObservableList<Ordinazioni> olOrdinati;
    
    @FXML private TableView<Ordinazioni> tab = new TableView<>();
    @FXML public TextField NameString;
    @FXML
    private void goLogin() throws IOException {
        App.setRoot("primary");
    }
        @FXML
    private void goStorico() throws IOException
    {
        
        App.setRoot("storico1");
    }
    
    @FXML
    private void showrecensioni() throws IOException
    {
        logger.info(NameString.getText() + " ora prendo le recensioni in savedish");
       URL url = new URL("http://localhost:8080/Sushi/savedish?name="+NameString.getText());
        
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("GET");
       con.setDoOutput(true);
       BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
       
        if(in.readLine().equals("OK"))
            App.setRoot("recensioniVista");
        else
            logger.info("problema con il salvataggio del session_dish");
        in.close();
        
    }
    
    @FXML
    private void sendToDb() throws IOException
    {
        try{
        //collegamento e invio dati tramite serializzazione Json
        
        URL url = new URL("http://localhost:8080/Sushi/store");
        
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("POST");
       con.setDoOutput(true);
       
          Gson gson = new Gson();
          String obj = gson.toJson(l1Ord);
          String urlParameters  = "OrdArray="+obj;
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
       
        if(in.readLine().equals("OK"))
            App.setRoot("resoconto1");
        else 
            throw new ClientException("errore nell'immisione dell'ordine nel databse");
        in.close();
        }catch(ClientException ce)
        {
            logger.info(ce.getMessage());
        }
         
    }
    @FXML
    public void initialize() throws IOException,SQLException
    {
        l1 = new ArrayList(1);
        l1Ord = new ArrayList(1);
        
        TableColumn nomeCol = new TableColumn("Pietanza");
        TableColumn nomeColOrd = new TableColumn("Pietanza");
        
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeColOrd.setCellValueFactory(new PropertyValueFactory<>("nome"));
 
        TableColumn lastCol = new TableColumn("numero");
        TableColumn lastColOrd = new TableColumn("n");
         lastCol.setCellValueFactory(new PropertyValueFactory<>("numero"));
         lastColOrd.setCellValueFactory(new PropertyValueFactory<>("numero"));
 
        TableColumn quantiCol = new TableColumn("costo €");
       // TableColumn quantiColOrd = new TableColumn("costo €");
        quantiCol.setCellValueFactory(new PropertyValueFactory<>("costo"));
        //quantiColOrd.setCellValueFactory(new PropertyValueFactory<>("costo"));
        
        TableColumn portate = new TableColumn("portate");
        portate.setCellValueFactory(new PropertyValueFactory<>("portate"));


        tab.getColumns().addAll( nomeCol, lastCol, quantiCol);
        tabOrdinati.getColumns().addAll( nomeColOrd, lastColOrd,  portate);
        
        ol = FXCollections.observableArrayList();
        olOrdinati = FXCollections.observableArrayList();
 
        tab.setItems(ol);
        tabOrdinati.setItems(olOrdinati);
        
          URL url = new URL("http://localhost:8080/Sushi/all");
        
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
        Ordinazioni o = new Ordinazioni(d.get("pietanza").getAsString(),d.get("numero").getAsInt(),d.get("costo").getAsInt(),0);
        ol.add(o);
        l1.add(o);
        NumTotRow++;
        
        /*condizione con cui muovo gli elementi da una tabella all'altra*/
        }
        tab.setOnMouseClicked(event -> {
            
        boolean FinisciPrima = false;
            
          tabOrdinati.getItems().clear();
         // olOrdinati.removeAll();
          
           
        if (tab.getSelectionModel().getSelectedItem() != null) {
            
             /*caso 1: la pietanza è gia stata precedentemente selezionata*/
        for(int i=0;i<numRowOrd;i++)
        {
            if(l1Ord.get(i).nome.equals(tab.getSelectionModel().getSelectedItem().nome))
            {
                 l1Ord.get(i).portate++;
                 FinisciPrima=true;
            }
              olOrdinati.add(l1Ord.get(i));
          }  
        
        tabOrdinati.setItems(olOrdinati);
        
        /*caso 2: bisogna aggiungere un nuovo piatto a quelli ordinati*/
        for(int i=0;i<NumTotRow;i++) 
              {
                   if(FinisciPrima)
                       return;
                  if(l1.get(i).nome.equals(tab.getSelectionModel().getSelectedItem().nome)){
                      l1.get(i).portate++;
                      l1Ord.add(l1.get(i));    
                      numRowOrd++;
                      olOrdinati.add(l1.get(i));
                      tabOrdinati.setItems(olOrdinati);
                  }
               }
            }
        });
    }
    
    
    
         @FXML 
         public void showFood() /*mostro il cibo relativo alla richiesta nel textField*/
         {
             String findFood = NameString.getText();
             ol.removeIf(Ordinazioni -> !Ordinazioni.nome.equals(findFood));
            
         }
         
         @FXML
         public void showAll() /*con questo bottone ri-mostro tutta la tabella*/
         {
             ol.removeIf(Ordinazioni -> !Ordinazioni.nome.equals("null"));
             for(int i=0;i<NumTotRow;i++)
                    {
                        ol.add(l1.get(i));
                    }
                  tab.setItems(ol);
         }
}