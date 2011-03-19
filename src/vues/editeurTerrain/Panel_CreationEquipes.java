package vues.editeurTerrain;

import i18n.Langue;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import vues.GestionnaireDesPolices;
import vues.LookInterface;
import vues.commun.Panel_Table;
import models.jeu.Jeu;
import models.joueurs.EmplacementJoueur;
import models.joueurs.Equipe;
import models.terrains.Terrain;

/**
 * Fenêtre de création d'équipes.
 * 
 * TODO commenter tout le fichier
 * 
 * @author Aurelien Da Campo
 * @version 1.0 | juillet 2010
 * @since jdk1.6.0_16
 * @see Terrain
 */
public class Panel_CreationEquipes extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;
    
    private static final ImageIcon I_AJOUTER_EQUIPE = new ImageIcon("img/icones/flag_add.gif");
    private static final ImageIcon I_AJOUTER = new ImageIcon("img/icones/add.png");
    private static final ImageIcon I_SUPPRIMER = new ImageIcon("img/icones/delete.png");
    //private static final ImageIcon I_COULEURS = new ImageIcon("img/icones/color_swatch.png");
    //private static final ImageIcon I_ZONE_EDITION = new ImageIcon("img/icones/shape_square_edit.png");
    private static final ImageIcon I_PARAMETRES = new ImageIcon("img/icones/wrench.png");
    private static final ImageIcon I_SELECTION_ZONE = new ImageIcon("img/icones/shape_handles.png");
   
    private JButton bCreerEquipe = new JButton(Langue.getTexte(Langue.ID_TXT_BTN_CREER),I_AJOUTER_EQUIPE);
    private Jeu jeu;
    private int idEquipe;
    private Panel_Table pTabEquipes = new Panel_Table();   
    
    private int idEJ;
    private Panel_CreationTerrain panelCreationTerrain;
    
    public Panel_CreationEquipes(Jeu jeu,Panel_CreationTerrain panelCreationTerrain)
    {
        super(new BorderLayout());
        
        this.jeu = jeu;
        this.panelCreationTerrain = panelCreationTerrain;
        
        initIds();
 
        setOpaque(false);
        
        //add(new JLabel("Equipes!"));
        GestionnaireDesPolices.setStyle(bCreerEquipe);
        bCreerEquipe.addActionListener(this);
        
        JPanel pTmp = new JPanel();
        pTmp.setOpaque(false);
        pTmp.add(bCreerEquipe);
        add(pTmp,BorderLayout.NORTH);
        
        
        pTabEquipes.setBackground(LookInterface.COULEUR_DE_FOND_SEC);
        
        
        
        
        JScrollPane jsTest = new JScrollPane(pTabEquipes);
        jsTest.setBorder(new EmptyBorder(0,0,0,0));
        jsTest.setOpaque(false);
        
        add(jsTest,BorderLayout.CENTER);
        
        
        construirePanelEquipes();
        
        setPreferredSize(new Dimension(400,200));
    }

    private void construirePanelEquipes()
    {
        pTabEquipes.removeAll();

        int ligne=0;
        
        idEJ = 1;
        
        for(final Equipe equipe : jeu.getEquipes())
        {
            JPanel lTrait = new JPanel();
            lTrait.setBorder(new LineBorder(LookInterface.COULEUR_DE_FOND_PRI, 4));
            lTrait.setPreferredSize(new Dimension(380,8));
            //JLabel lTrait = new JLabel("------------------------------------------------------------------------------------------------------");
            pTabEquipes.add(lTrait,0,ligne++,5,1);
            
 
            // nom de l'équipe
            final JLabel lNomEquipe = new JLabel(equipe.getNom());
            lNomEquipe.setFont(GestionnaireDesPolices.POLICE_TITRE);
            lNomEquipe.setForeground(equipe.getCouleur());
            //lNomEquipe.setMinimumSize(new Dimension(80,25));
            //lNomEquipe.setPreferredSize(new Dimension(80,25));
            //GestionnaireDesPolices.setStyle(lNomEquipe);
            
            pTabEquipes.add(lNomEquipe,0,ligne);
            
            final JButton bEditerEquipe = new JButton(I_PARAMETRES);
            GestionnaireDesPolices.setStyle(bEditerEquipe);
            
            final Panel_CreationEquipes pce = this;
            
            bEditerEquipe.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    new Fenetre_EditionEquipe(pce,equipe);
                }
            });
            
            pTabEquipes.add(bEditerEquipe,1,ligne);
            
            
            // Suppression
            final JButton bSupprimerEquipe = new JButton(I_SUPPRIMER);
            GestionnaireDesPolices.setStyle(bSupprimerEquipe);
            pTabEquipes.add(bSupprimerEquipe,3,ligne);
            
            // minimum 1 equipe
            if(jeu.getEquipes().size() < 2)
                bSupprimerEquipe.setEnabled(false);
            else
            {
                bSupprimerEquipe.addActionListener(new ActionListener()
                {
                    
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        jeu.supprimerEquipe(equipe);
                        
                        construirePanelEquipes();
                    } 
                });
            }
            
            ligne++;
            
            JLabel lZonesDepart = new JLabel("Arrivee et departs");
            lZonesDepart.setFont(GestionnaireDesPolices.POLICE_SOUS_TITRE);
            pTabEquipes.add(lZonesDepart,0,ligne);
            
            JButton bAjouterZonesDepart = new JButton(I_AJOUTER);
            GestionnaireDesPolices.setStyle(bAjouterZonesDepart);
            bAjouterZonesDepart.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    equipe.ajouterZoneDepartCreatures(new Rectangle(0,0,40,40));
                    
                    construirePanelEquipes();
                }
            });
            
            pTabEquipes.add(bAjouterZonesDepart,1,ligne);
            
            ligne++;
            
            
            
            // Zone d'arrivée
            final JLabel bZoneArrive = new JLabel(Langue.getTexte(Langue.ID_TXT_BTN_ARRIVEE));
            GestionnaireDesPolices.setStyle(bZoneArrive);
            pTabEquipes.add(bZoneArrive,0,ligne);
            
            final JButton bSelectionZoneArrive = new JButton(I_SELECTION_ZONE);
            GestionnaireDesPolices.setStyle(bSelectionZoneArrive);
            pTabEquipes.add(bSelectionZoneArrive,2,ligne);
            
            bSelectionZoneArrive.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    panelCreationTerrain.setRecEnTraitement(equipe.getZoneArriveeCreatures());
                }
            });
            
            
            ligne++;
            
            // Zones de depart
            for(int noZD=0;noZD<equipe.getNbZonesDepart();noZD++)
            {
                final Rectangle z = equipe.getZoneDepartCreatures(noZD);

                // Zone de départ
                final JLabel bZoneDepart = new JLabel(Langue.getTexte(Langue.ID_TXT_BTN_DEPART) + " - " + noZD);
                GestionnaireDesPolices.setStyle(bZoneDepart);
                pTabEquipes.add(bZoneDepart,0,ligne);                
                
                final JButton bSelectionZoneDepart = new JButton(I_SELECTION_ZONE);
                GestionnaireDesPolices.setStyle(bSelectionZoneDepart);
                pTabEquipes.add(bSelectionZoneDepart,2,ligne);
                
                bSelectionZoneDepart.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        panelCreationTerrain.setRecEnTraitement(z);
                    }
                });
              
                final JButton bSupprimerZoneDepart = new JButton(I_SUPPRIMER);
                GestionnaireDesPolices.setStyle(bSupprimerZoneDepart);
                pTabEquipes.add(bSupprimerZoneDepart,3,ligne);
                
                if(equipe.getNbZonesDepart() == 1)
                    bSupprimerZoneDepart.setEnabled(false);
                
                bSupprimerZoneDepart.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if(equipe.getNbZonesDepart() > 1)
                        {
                            equipe.suppimerZoneDepart(z);
                            
                            construirePanelEquipes();
                        }     
                    }
                });
                
                ligne++;
            }      
            ligne++;  
            
            // -----------------------------
            // -- Emplacements de joueurs --
            // -----------------------------
            JLabel lEmplacementsDeJoueurs = new JLabel("Empl. Joueurs");
            lEmplacementsDeJoueurs.setFont(GestionnaireDesPolices.POLICE_SOUS_TITRE);
            pTabEquipes.add(lEmplacementsDeJoueurs,0,ligne);
            
            final JButton bNouvelEmplacement = new JButton(I_AJOUTER);
            GestionnaireDesPolices.setStyle(bNouvelEmplacement);
            pTabEquipes.add(bNouvelEmplacement,1,ligne);
            
            bNouvelEmplacement.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    equipe.ajouterEmplacementJoueur(new EmplacementJoueur(idEJ++, new Rectangle(0,0,jeu.getTerrain().getLargeur(),jeu.getTerrain().getHauteur())));
                
                    construirePanelEquipes();
                }
            });
            ligne++;

            for(final EmplacementJoueur ej : equipe.getEmplacementsJoueur())
            { 
                
                JLabel lNomEmplacement = new JLabel(Langue.getTexte(Langue.ID_TXT_ZONE_JOUEUR)+ej.getId());
                GestionnaireDesPolices.setStyle(lNomEmplacement);
                pTabEquipes.add(lNomEmplacement,0,ligne);
                
                // Couleur
                final JButton bCouleurEmplacement = new JButton(I_PARAMETRES);
                GestionnaireDesPolices.setStyle(bCouleurEmplacement);
                pTabEquipes.add(bCouleurEmplacement,1,ligne);
                bCouleurEmplacement.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        Color couleur = JColorChooser.showDialog(
                                null,"",ej.getCouleur());
                          
                        if(couleur != null)
                        {
                            ej.setCouleur(couleur);
                            bCouleurEmplacement.setBackground(couleur);
                        } 
                    } 
                });
                
                // Selection
                final JButton bSelectionnerEmplacement = new JButton(I_SELECTION_ZONE);
                GestionnaireDesPolices.setStyle(bSelectionnerEmplacement);
                pTabEquipes.add(bSelectionnerEmplacement,2,ligne);
                bSelectionnerEmplacement.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        panelCreationTerrain.setRecEnTraitement(ej.getZoneDeConstruction());
                    } 
                });
                
                if(ej.getId() >= idEJ)
                    idEJ = ej.getId() + 1;
                
                
                // Suppression
                final JButton bSupprimerEmplacement = new JButton(I_SUPPRIMER);
                GestionnaireDesPolices.setStyle(bSupprimerEmplacement);
                pTabEquipes.add(bSupprimerEmplacement,3,ligne);
                
                if(equipe.getNbEmplacements() == 1)
                    bSupprimerEmplacement.setEnabled(false);
                
                bSupprimerEmplacement.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        equipe.supprimerEmplacement(ej);
                        
                        construirePanelEquipes();
                    } 
                });
                
                ligne++;
            }
        }

        pTabEquipes.repaint();
        pTabEquipes.revalidate();
        pTabEquipes.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();
        
        if(src == bCreerEquipe)
        {
            Equipe equipe = new Equipe(idEquipe, Langue.getTexte(Langue.ID_TXT_EQUIPE)+" "+idEquipe, Color.BLACK);
            
            jeu.ajouterEquipe(equipe);
            
            equipe.ajouterZoneDepart(new Rectangle(40,40,40,40));
            equipe.setZoneArriveeCreatures(new Rectangle(140,140,40,40));
            
            equipe.ajouterEmplacementJoueur(new EmplacementJoueur(idEJ++, new Rectangle(0,0,jeu.getTerrain().getLargeur(),jeu.getTerrain().getHauteur())));

            idEquipe++;
            
            construirePanelEquipes(); 
        }
    }

    public void miseAJour()
    {
        initIds();
        construirePanelEquipes();
    }

    private void initIds() {
        
        // initialisation de l'id courant -> recupere l'id le plus grand + 1
        idEquipe = 0;
        for(Equipe e : jeu.getEquipes())
            if(e.getId() > idEquipe)
                idEquipe = e.getId();
        idEquipe++;
    }
}
