package models.terrains;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import models.creatures.*;
import models.outils.Musique;

/**
 * Classe de gestion du fameux terrain Element TD repris de chez Blizzard.
 * 
 * Cette classe hérite de la classe Terrain de base.
 * 
 * @author Pierre-Dominique Putallaz
 * @author Aurelien Da Campo
 * @author Lazhar Farjallah
 * @version 1.0 | 13 decembre 2009
 * @since jdk1.6.0_16
 * @see Terrain
 */
public class ElementTD extends Terrain
{
    public final static Image IMAGE_DE_FOND;
    public final static Image IMAGE_MENU;
    public final static Musique MUSIQUE_DE_FOND;
    
    public static final VagueDeCreatures[] vagues = 
    {
    	new VagueDeCreatures(5, new Creature1(100,4,10),"Creatures terrestres faibles"),
    	new VagueDeCreatures(10, new Creature1(100,4,10),"Creature terrestres faibles"),
    	new VagueDeCreatures(5, new CarapaceKoopa(300,10,10),"Creature terrestres moyennes"),
    	new VagueDeCreatures(10, new Nuage(100,6,10),"Creature volantes"),
    	new VagueDeCreatures(20, new Creature1(100,4,30),"Creature terrestres rapides"),
        new VagueDeCreatures(10, new Creature1(600,20,20),"Creature terrestres resistantes"),
        new VagueDeCreatures(1, new CarapaceKoopa(10000,40,10),"Boss : Très résistant")
    };
    
    static
    {
        MUSIQUE_DE_FOND = new Musique("snd/blizzard/Human_I_(Fanfare).mp3");
        
        IMAGE_MENU    = Toolkit.getDefaultToolkit().getImage(
                                          "img/cartes/menu_principal/elementTD.png");
    	IMAGE_DE_FOND = Toolkit.getDefaultToolkit().getImage(
    	                                                 "img/cartes/elementTD.png");
    }
	
    /**
     * Constructeur d'un terrain ElementTD selon la célèbre map de Blizzard.
     */
    public ElementTD () {
        super(480, 500, 100, 
              0, 0, 540, 500, IMAGE_DE_FOND, "ElementTD", 
              new Rectangle(110, 0, 80, 20),
              new Rectangle(230, 0, 80, 20)
        );
    
        setMusiqueDeFond(MUSIQUE_DE_FOND);
        demarrerMusiqueDeFond();
        
        /*
         * Définition des murs du labyrinthe.
         */
        ajouterMur(new Rectangle(20, 0, 80, 20));
        ajouterMur(new Rectangle(0, 0, 20, 500));
        ajouterMur(new Rectangle(20, 480, 440, 20));
        ajouterMur(new Rectangle(460, 0, 20, 500));
        ajouterMur(new Rectangle(320, 0, 140, 20));
        ajouterMur(new Rectangle(200, 0, 20, 100));
        ajouterMur(new Rectangle(120, 100, 240, 20));
        ajouterMur(new Rectangle(120, 120, 20, 20));
        ajouterMur(new Rectangle(340, 120, 20, 260));
        ajouterMur(new Rectangle(120, 360, 220, 20));
        ajouterMur(new Rectangle(20, 240, 220, 20));
        ajouterMur(new Rectangle(220, 220, 20, 20));
  
    }

    
    /**
     * Permet de recuperer la vague suivante
     * @return la vague suivante
     */
    VagueDeCreatures getVagueSuivante()
	{
    	if (indiceVagueCourante < 6)
    		return vagues[indiceVagueCourante];
    	else
    		return new VagueDeCreatures(10, 
    			new Creature1(indiceVagueCourante*100,indiceVagueCourante,indiceVagueCourante));
	}
    
}
