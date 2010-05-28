package serveur.enregistrement;

import java.io.IOException;
import java.util.ArrayList;

import reseau.*;


/**
 * 
 * @author lazhar
 *
 */
public class SEInscription {
   
   private static ArrayList<Enregistrement> jeuxEnregistres = new ArrayList<Enregistrement>();
   private Port port;
   private boolean avecLog;
   private CanalTCP canal;
   
   /**
    * 
    * @param port
    * @param avecLog
    */
   public SEInscription(Port port, boolean avecLog) {
      this.port = port;
      this.avecLog = avecLog;
   }
   
   /**
    * 
    */
   public void lancer() {
      try
      {
         port.reserver();
        
         while (true) {
            System.out.println("\n+ Un nouveau thread du Serveur d'enregistrement va demarrer...");
            // Fonction bloquante qui attend que quelqu'un se connecte
            creerCanal();
            (new Thread(new SEConnexion(canal))).start();
         }
      } 
      catch (IOException e)
      {
         System.err.println("Serveur déjà lancé !");
      } 
   }
   
   /**
    * 
    */
   private void creerCanal() {
      try {
         canal = new CanalTCP(port, avecLog);
      } catch (CanalException ce) {
         System.out.println("\tProbleme de connexion : " + ce.getMessage());
      }
   }
   
   /**
    * 
    * @param e
    * @return
    */
   public static synchronized boolean ajouterEnregistrement(Enregistrement e) {
      // TODO : surcharger les contains() pour pas que ce soit les réf. qui sont comparées
      if (!jeuxEnregistres.contains(e)) {
         jeuxEnregistres.add(e);
         System.out.println("Nb d'enreg. : " + jeuxEnregistres.size());
         return true;
      }
      return false;
   }
   
   /**
    * 
    * @param e
    */
   public static synchronized void enleverEnregistrement(Enregistrement e) {
      jeuxEnregistres.remove(e);
   }
   
   /**
    * 
    * @return
    */
   public static synchronized int getNombreEnregistrements() {
      return jeuxEnregistres.size();
   }
   
   /**
    * 
    * @return
    */
   public static synchronized ArrayList<Enregistrement> getJeuxEnregistres() {
      return jeuxEnregistres;
   }
}
