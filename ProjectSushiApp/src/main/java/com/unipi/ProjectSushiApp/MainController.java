/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipi.ProjectSushiApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author loren
 */
@Controller
 @RequestMapping(path="/Sushi")
 public class MainController {
 @Autowired
 private ProjectRepository projectrepo;
 
 /*
 variabili di 'sessione', necessarie per scoprire il resoconto degli ordini del cliente o le recensioni di una eventuale pietanza.
 non vengono salvate nel db e rimangono attive fintanto che lo è la sessione
 */
 
  private String SESSION_USER; //necessarie per eseguire query particolari, vedesi le ultime funzioni di questo file:
  private String SESSION_DISH; //salvandosi questi valori non sarà necessario chiederli per ottenere i valori attesi

 @GetMapping(path="/all")
 public @ResponseBody ArrayList<Ordinazione> getAllFood() { //riempo la tabella di menu
     try{
         ArrayList<Ordinazione> l1 = new ArrayList(1);
         Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
         Statement st = co.createStatement(); 
         ResultSet rs = st.executeQuery("SELECT * FROM OrdinazioniPossibili");
         int i=0;
         while(rs.next())
         {
           Ordinazione o1=new Ordinazione(rs.getString(1),rs.getInt(2),rs.getInt(3));
           l1.add(o1);
            i++;
         }
         return l1;
         //return .findAll();
     }
     catch(Exception sq)
     {
         System.out.println(sq.getMessage());
          return null;
     }
 }
 
 /*controllo accesso: se l'utente è o meno presente, in caso salvo il SESSION_USER*/
  @GetMapping(path="/Control")
 public @ResponseBody String Control(@RequestParam String user, @RequestParam String pass) {
     System.out.println("sto confrontando l'utente mandato con tutti gli utenti");
     try{
      boolean b=false;
                        Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
                        Statement st = co.createStatement(); 
                        ResultSet rs = st.executeQuery("SELECT count(*) FROM login where NomeUtente = " +"\"" + user +"\""+ " and password =" + "\"" + pass +"\"" );
        
                        while(rs.next())
                        {
                        if(rs.getInt(1)>0)
                        b = true;
                        }
                     
        if(b){
        System.out.println("utente valido");
        SESSION_USER=user;
            return "OK";
        }
        else{
            System.out.println("utente non valido!");
         return "NO";
        }
     }catch(SQLException se)
     {
         System.out.println("problema sql " +se.getMessage());
     }
     return "NO";
 }
 
 @PostMapping(path="/add")
     public @ResponseBody String addNewUser (@RequestParam String All) throws SQLException {
         
         
         System.out.println("verifico l'utente, in caso aggiungo o lancio errore");
         Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580", "root","root");
            
            Statement st = co.createStatement();
            Gson gson = new Gson(); 
            Utente u = gson.fromJson(All, Utente.class);
            System.out.println(u.getName() + " " +u.getPass());
            boolean b=false;
            ResultSet rs = st.executeQuery("SELECT count(*) FROM login where NomeUtente = " +"\"" + u.getName() +"\"" );
            while(rs.next())
            {
                if(rs.getInt(1)>0){
                    b = true;
                    System.out.println("utente presente! non può registrarsi");
                }
            }        
        if(b)
         return "NO";
        else
        {
            String SQLString = "insert into Login values(?,?);";
            PreparedStatement pstmt = co.prepareStatement(SQLString);
            pstmt.setString(1,u.getName());
            pstmt.setString(2,u.getPass());
            pstmt.executeUpdate();
            return "OK";
        }
    }
     
     @GetMapping(path="/utente")
    public @ResponseBody Utente getUserByName(@RequestParam String name) throws SQLException {
        System.out.println(name+ " " + " mi è arrivato questo");
    return projectrepo.findByName(name);
     }
    
     @PostMapping(path="/store") 
     public @ResponseBody String SendToDatabase(@RequestParam String OrdArray ) throws SQLException
     {  
         Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
         Statement st = co.createStatement();
         try{
               System.out.println(OrdArray);
               
                String deleteSql = "delete from Ordinazioni where nomeUtente ="+ "\"" + SESSION_USER +"\"";
               PreparedStatement ps1 = co.prepareStatement(deleteSql);
               ps1.executeUpdate();
               System.out.println("STORE: ORDARRAY= "  +OrdArray );
               
                Gson gson = new Gson(); 
                //ArrayList<OrdinazioneEffettuata> l1 = gson.fromJson(OrdArray, ArrayList.class);
                
                 JsonArray json = gson.fromJson(OrdArray, JsonArray.class);
                 for (int i = 0; i < json.size(); i++) {
                 JsonObject d = json.get(i).getAsJsonObject();
                 OrdinazioneEffettuata o = new OrdinazioneEffettuata(d.get("nome").getAsString(),d.get("numero").getAsInt(),d.get("costo").getAsInt(),d.get("portate").getAsInt());
                //OrdinazioneEffettuata e  = new OrdinazioneEffettuata(d.get("pietanza").getAsString(),d.get("numero").getAsInt(),d.get("quantita").getAsInt(),d.get("portata").getAsInt());
                
                String SQLString = "insert into Ordinazioni values(?,?,?,?,?);";
                PreparedStatement pstmt = co.prepareStatement(SQLString);
                pstmt.setString(1,SESSION_USER);
                pstmt.setString(2,o.getPietanza()); 
                pstmt.setInt(3,o.getNumero());
                pstmt.setInt(4,o.getCosto());
                pstmt.setInt(5,o.getPortata());
                pstmt.executeUpdate();
                }
     }catch(SQLException se)
     {
         System.out.println("problema sql " +se.getMessage());
     }
          return "OK";
     }
     
     @GetMapping(path="/orders")
     public @ResponseBody ArrayList<OrdinazioneEffettuata> sendOrders()
     {
          try{
         ArrayList<OrdinazioneEffettuata> l1 = new ArrayList(1);
         Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
         Statement st = co.createStatement(); 
         ResultSet rs = st.executeQuery("SELECT * FROM Ordinazioni" ); 
         int i=0;
         while(rs.next())
         {
           OrdinazioneEffettuata o1=new OrdinazioneEffettuata(rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
           l1.add(o1);
           System.out.println(o1.getPietanza()+" "+o1.getPortata());
         }
         System.out.println("ORDERS: ordini inviati");
         return l1;
         //return ordinRepo.findAll();
     }
     catch(Exception sq)
     {
         System.out.println(sq.getMessage());
          return null;
     }
     }
     
     @PostMapping(path="/exit")
     public @ResponseBody String StoreToStorico(@RequestParam String Ord ) throws SQLException
     {  
         Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
         Statement st = co.createStatement();
         try{
               System.out.println(Ord);
               
               String deleteSql = "delete from Ordinazioni where nomeUtente ="+ "\"" + SESSION_USER +"\"";
               PreparedStatement ps1 = co.prepareStatement(deleteSql);
               ps1.executeUpdate();
               
               System.out.println("EXIT: ORD= " + Ord);
               
               Gson gson = new Gson(); 
                
                 JsonArray json = gson.fromJson(Ord, JsonArray.class);
                 for (int i = 0; i < json.size(); i++) {
                 JsonObject d = json.get(i).getAsJsonObject();
                 OrdinazioneEffettuata o = new OrdinazioneEffettuata(d.get("nome").getAsString(),d.get("numero").getAsInt(),d.get("costo").getAsInt(),d.get("portate").getAsInt());
                
                String SQLString = "insert into OrdinazioniStorico values(?,?,?,?,?);";
                PreparedStatement pstmt = co.prepareStatement(SQLString);
                pstmt.setString(1,SESSION_USER);
                pstmt.setString(2,o.getPietanza());
                pstmt.setInt(3,o.getNumero());
                pstmt.setInt(4,o.getCosto());
                pstmt.setInt(5,o.getPortata());
                pstmt.executeUpdate();
                }
     }catch(SQLException se)
     {
         System.out.println("problema sql " +se.getMessage());
         return "NO";
     }
                  System.out.println("ordinazione salvata!");
          return "OK";
     }
     @GetMapping(path="/set")
     public @ResponseBody String SetDish(@RequestParam String name)
     {
         SESSION_DISH=name;
         System.out.println("piatto per la recensione salvato");
         return "OK";
     }
     
     @PostMapping(path="saveAdv")
     public @ResponseBody String SavePiatto(@RequestParam String voto)
     {
          try{
               Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
               Statement st = co.createStatement();
               
               Gson gson = new Gson(); 
                
                 String json = gson.fromJson(voto, String.class);
                 System.out.println(json);
                 
                String SQLString = "insert into recensioni values(?,?,?);";
                PreparedStatement pstmt = co.prepareStatement(SQLString);
                pstmt.setString(1,SESSION_USER);
                pstmt.setString(2,SESSION_DISH);
                pstmt.setString(3,json);
                pstmt.executeUpdate();
                }catch(SQLException se)
                {
         System.out.println("problema sql " +se.getMessage());
         return "NO";
                }
                  System.out.println("recensione salvata!");
                return "OK";
     }
     
     @GetMapping(path="/see")
     public @ResponseBody ArrayList<String> recensioneVista()
     {
         try{
             ArrayList<String> r1 = new ArrayList(1);
               Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
               Statement st = co.createStatement();
               System.out.println("utente: "+SESSION_USER + " piatto: "+SESSION_DISH);
               int i=0;
                ResultSet rs = st.executeQuery( "select voto from recensioni where ordinazione=" +"\"" + SESSION_DISH +"\"" +"and NomeUtente = "+"\""+SESSION_USER+"\"");
                while(rs.next()){
                    r1.add(rs.getString(1));
                     i++;
                }
                System.out.println("tutte le recensioni sono state mandate");
                return r1;
                
                }catch(SQLException se)
                {
                 System.out.println("problema sql " +se.getMessage());
                 ArrayList<String> r = new ArrayList(1);
                 r.add("problema sql " +se.getMessage());
                 return r;
                }
     }
     
     @GetMapping(path="/savedish")
     public @ResponseBody String saveDish(@RequestParam String name)
     {
         SESSION_DISH = name;
         System.out.println("SAVEDISH: piatto salvato= "+SESSION_DISH);
         return "OK";
     }
     
     @GetMapping(path="/storicoload")
     public @ResponseBody  ArrayList<OrdinazioneEffettuata> LoadStorico()
     {
         try{
         ArrayList<OrdinazioneEffettuata> a = new ArrayList(1);
         Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/lorenzo_menchini_615580","root","root");
         Statement st = co.createStatement();
         System.out.println("utente: "+SESSION_USER + " piatto: "+SESSION_DISH);
         int i=0;
         ResultSet rs = st.executeQuery( "select * from ordinazioniStorico where NomeUtente = "+"\""+SESSION_USER+"\"");
         while(rs.next()){
             a.add(new OrdinazioneEffettuata(rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
                }
                System.out.println("tutte le ordinazioni passate sono state mandate");
                return a;
                
                }catch(SQLException se)
                {
                 System.out.println("problema sql " +se.getMessage());
                 return null;
                }
     }

}
