package musique;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import javazoom.jl.player.Player;
import util.Constante;
 

/**
 * @author RABAH Abderrafii
 */
public class Musique implements Runnable
{
    //id de la musique
    private String id;
    //nom de la chanson
    private String titre;
    //artiste de la musique
    private String artiste;
    //album de la musique
    private String album;
    //la duree de la musique
    private String longueur;
    //date de sortie
    private String date_sortie;
    //nombre de lecture
    private String joue_nbrfois;
    //chemin de la chanson
    private String localisation_chanson;
    //icones des boutons
    private ImageIcon img;
    //gestion la thead
    volatile boolean stop=false;
    //les piste en cours de lecture
    private  List<Musique>  piste_list=new ArrayList<>();
    //chemin d'image
    private String path_image = LecteurMusiqueGUI.class.getResource("/").getPath();
    //construire un flux de donées à partir d'un fhichier
    FileInputStream  fs =null;
  //construire un flux de donées à partir d'un fhichier
    BufferedInputStream  bis =null;
    //classe d'émettre la musique
    Player play =null;
    
    private  File[] fichiers={};
    private Constante constante =new Constante();
    //constructeur
    public Musique()
    { 
        
    }
    /**
     * constructeur
     * @param localisation_chanson : le chemin de la chanson
     */
    public Musique(String localisation_chanson)
    {
    
        this.localisation_chanson=localisation_chanson;
    }

    public String getTitre()
    {
        return titre;
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public String getArtiste()
    {
        return artiste;
    }

    public void setArtiste(String artiste)
    {
        this.artiste = artiste;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getLongueur()
    {
        return longueur;
    }

    public void setLongueur(String longueur)
    {
        this.longueur = longueur;
    }

    public String getDate_sortie()
    {
        return date_sortie;
    }

    public void setDate_sortie(String date_sortie)
    {
        this.date_sortie = date_sortie;
    }

    public String getJoue_nbrfois()
    {
        return joue_nbrfois;
    }

    public void setJoue_nbrfois(String joue_nbrfois)
    {
        this.joue_nbrfois = joue_nbrfois;
    }


    public String getLocalisation_chanson() {
		return localisation_chanson;
	}

	public void setLocalisation_chanson(String localisation_chanson) {
		this.localisation_chanson = localisation_chanson;
	}

	public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
 

    public List<Musique> getPiste_list() {
		return piste_list;
	}

	public void setPiste_list(List<Musique> piste_list) {
		this.piste_list = piste_list;
	}

    public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}
	 /**
	  * 
	  * @param musiques
	  * @param dimention_table
	  * @return
	  */
	public Object[][] init_chansons(List<Musique> musiques, int dimention_table) //initialiser chaque chanson avec ses propre infos
    {
        Object[][] chancons = null;
        if (musiques != null)
        {
            chancons = new Object[musiques.size()][dimention_table];
            int i = 0;
            for (Musique musique : musiques)
            {
                
                chancons[i][0] = musique.getTitre();
                chancons[i][1] = musique.getAlbum();
                chancons[i][2] = musique.getArtiste();
                chancons[i][3] = musique.getLongueur();
                chancons[i][4] = musique.getJoue_nbrfois();
                chancons[i][5] = musique.getLocalisation_chanson();  
                chancons[i][6] = musique.getId();  
                piste_list.add(musique);
                i++;
            }
        } else
        {
            chancons = new Object[1][dimention_table];
        }

        return chancons;
    }
	
	/**
	 * 
	 * @param musiques
	 * @param longeur_titre
	 * @return
	 */
	public Object[][] init_playlist(List<Musique> musiques, int longeur_titre) //initaliser les chansons de chaques playlist avec ses propres infos
    {
       
        List<Musique> nouveaux_musiques=new ArrayList<>();
        List<String> list_id=new ArrayList<>();
        if (musiques != null)
        {
            for (Musique musique : musiques)
            {
            	String id=musique.getId();
            	if(!list_id.contains(id)){
	                piste_list.add(musique);
	                list_id.add(id);
	                nouveaux_musiques.add(musique); 
            	}
            }
        }
        Object[][] chancons = new  Object[nouveaux_musiques.size()][longeur_titre];
        if(nouveaux_musiques.size()>0){
        	int i=0;
        	 for (Musique musique : nouveaux_musiques)
             { 
 	            	chancons[i][0] = musique.getTitre();
 	                chancons[i][1] = musique.getAlbum();
 	                chancons[i][2] = musique.getArtiste();
 	                chancons[i][3] = musique.getLongueur();
 	                chancons[i][4] = musique.getJoue_nbrfois();
 	                chancons[i][5] = musique.getLocalisation_chanson();  
 	                chancons[i][6] = musique.getId(); 
 	                piste_list.add(musique); 
 	                i+=1;
             	}
             }else
        {
            chancons = new Object[1][longeur_titre];
        }

        return chancons;
    }
	
	
	 
    /**
     * 
     * @param musiques
     * @return
     */
    @SuppressWarnings("unused")
	public  List<Musique> order(List<Musique> musiques) //lire les chansons en ordre
    {
            List<Musique> list_musiques=new ArrayList<>();
            List<String> list_key=new ArrayList<>();
            int i=0;
            for (Musique musique : musiques)
            { 
                String id= musique.getId();    
                 if(i==Integer.valueOf(id))
                 {
                        list_musiques.add(musique);
                 }else
                 {
                     continue;
                 }
                 i++;
             }
            
            
            return list_musiques;
     }
    
    @Override
    public void run() //jouer la chanson
    { 
        
        while(!stop)
        {
            try
            {
                  fs = new FileInputStream(this.localisation_chanson);
                  bis = new BufferedInputStream(fs); 
                  play = new Player(bis);      
                  play.play();  
            } catch (Exception ex)
            {
                Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } 
    }
    
   
   public void start() //demarrer un thread
   { 
	  
          Thread thread = new Thread(this); 
          stop=false;
          thread.start(); 
    
   }
    
    public void stop() //arreter la chanson
   { 
    	this.stop=true;
    	if(play!=null)
    	{
    		play.close();
    	}
   }
     
    @SuppressWarnings("unused")
	public void suivant(String id) // lire la chanson suivante
    { 
     for(int i=0;i<piste_list.size();i++)
     {
         String musique_id=piste_list.get(i).getId(); 
         if(musique_id.equals(id))
         { 
             try
             { 
                 this.localisation_chanson=piste_list.get(i+1).getLocalisation_chanson();
                 start();
             } catch (Exception e)
             {
                   Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, "Fin de la liste de lecture", e);
               
             } 
         }
                 break;
     }
    
    }
    
    
    @SuppressWarnings("unused")
	public ImageIcon obtrenir_information_image(String chemain_fichier) //obtenir l'image de la chanson
    {
    	ImageIcon icon =null;
        MP3File mp3File;
		try {
			mp3File = (MP3File) AudioFileIO.read(new File(chemain_fichier));
			  AbstractID3v2Tag v2tag = mp3File.getID3v2Tag(); 
			  MP3AudioHeader header = mp3File.getMP3AudioHeader(); 
        	  AbstractID3v2Frame frame = (AbstractID3v2Frame) v2tag.getFrame("APIC");  
        	  if(frame!=null)
        	  {
        		  FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();  
                  byte[] imageData = body.getImageData();  
                  Image img=Toolkit.getDefaultToolkit().createImage(imageData, 0,imageData.length);  
                  img = img.getScaledInstance(150, 150, Image.SCALE_DEFAULT);  
                  icon = new ImageIcon(img);  
                 // Image img = image.getImage();  
                 //
        	  }
        	  else{
        		  Image img=Toolkit.getDefaultToolkit().createImage(path_image + "images/artist_sans_figure.png");
        		  img = img.getScaledInstance(150, 150, Image.SCALE_DEFAULT);  
        		  icon=new ImageIcon(img);
        	  }
		} catch (CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        return icon;
    }
    
    @SuppressWarnings("unused")
	public  Musique obtenir_info_chanson(String chemain_fichier) //obtenir les info de la chanson
    {
        Musique musique=new Musique();
        try
        {
            //"D://muis//english//May It Be.mp3"
            MP3File mp3File = (MP3File) AudioFileIO.read(new File(chemain_fichier));
            AbstractID3v2Tag v2tag = mp3File.getID3v2Tag();
            if(v2tag!=null)
            {
            	  MP3AudioHeader header = mp3File.getMP3AudioHeader(); //la infomation de la tete de
            	  AbstractID3v2Frame frame = (AbstractID3v2Frame) v2tag.getFrame("APIC");  
                  //String artist = v2tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
                  //String album = v2tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
                 String artist = v2tag.getFirst(FieldKey.ARTIST); //obtenir l'artiste de la chanson
                 String album = v2tag.getFirst(FieldKey.ALBUM);//obtenir l'album de la chanson
                 String songName = v2tag.getFirst(FieldKey.TITLE);//obtenir le tite de la chanson
                 String date = v2tag.getFirst(FieldKey.YEAR);//obtenir la date de sortie  de la chanson
                 String length = String.valueOf(header.getTrackLength());  //durée de la chancon
                 if("".equals(artist) || null==artist) //au cas où aucune info sur l'artiste n'a été pas trouvé
                 {
                       musique.setArtiste("inconnu");
                 }else
                 {
                      musique.setArtiste(artist);
                 }
                  if("".equals(album) || null==album) //au cas où aucune info sur l'album n'a été pas trouvé
                 {
                      musique.setAlbum("inconnu");
                 }else
                 {
                      musique.setAlbum(album);
                 }
                   if("".equals(songName) || null==songName) //au cas où aucune info sur le titre n'a été pas trouvé
                 {
                      musique.setTitre("inconnu");
                 }else
                 {
                     musique.setTitre(songName);
                 }
                    if("".equals(date) || null==date) //au cas où aucune info sur la date de sortie n'a été pas trouvé
                 {
                         musique.setDate_sortie("inconnu");
                 }else
                 {
                     musique.setDate_sortie(date);
                 }
                     if("".equals(String.valueOf(length)) || "0".equals(length)) //au cas où aucune info sur la durée n'a été pas trouvé
                 {
                      musique.setLongueur("0");
                 }else
                 {
                    musique.setLongueur(length);
                 } 
                 musique.setJoue_nbrfois("0"); //initialiser le nombre d'écoute de la chanson
                 musique.setLocalisation_chanson(chemain_fichier);//initialiser l'emplacement de la chanson
                 musique.setImg(obtrenir_information_image(chemain_fichier)); //initialiser l'image de la chanson
            }else{
            	musique.setId("-1");
            }
          
        } catch (CannotReadException ex)
        {
            Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagException ex)
        {
            Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReadOnlyFileException ex)
        {
            Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAudioFrameException ex)
        {
            Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return musique;
    }
    /**
     *obtenir les informations de la chanson par son id
     * @param id
     * @param musqiues
     * @return
     */
    public Musique obtenir_musique_par_id(int indice,List<Musique> musqiues)
    {
    	Musique musique=null; 
    	
    	if(musqiues!=null)
    	{
    		try {
    			musique=musqiues.get(indice);
    			int emttre_fois=Integer.valueOf(musique.getJoue_nbrfois());
    			emttre_fois+=1;
    			musique.setJoue_nbrfois(""+emttre_fois+"");
			} catch (Exception e) {
				musique=new Musique();
			}
    	
    	}
    	return musique;
    	  
    }
     
    /**
     * determiner l'extension d'un fichier audio(methode non utilisée)
     * @param files
     * @return:
     */
    public String getMusique(File[] files)
    {

        for (int i = 0; i < files.length; i++)
        {
            File ficher = files[i];
            String nom_fichier = ficher.getName();
            System.out.println(nom_fichier);
            if (nom_fichier.endsWith(".au") || nom_fichier.endsWith(".rmf")
                    || nom_fichier.endsWith(".mid") || nom_fichier.endsWith(".wav")
                    || nom_fichier.endsWith(".aif") || nom_fichier.endsWith(".aiff") || nom_fichier.endsWith(".mp3"))
            {

                fichiers[i] = ficher;
            }
        }

        return null;
    }
    
    /**
     * 
     * @param musiques
     * @return
     */ 
	public  List<Musique> les_plus_jouees(List<Musique> musiques)
    {
            List<Musique> list_musiques=new ArrayList<>();
            if(constante.IS_CREER_FAVORIS){
            	 if(musiques!=null && musiques.size()>0)
                 {
                 	 for (Musique musique : musiques)
                      { 
                 		String  nombre_fois=musique.getJoue_nbrfois();
                 		 if(" "!=nombre_fois && !" ".equals(nombre_fois) && nombre_fois!=null)
                 		 {
                 			 int emettre_fois=Integer.valueOf(nombre_fois);
                 			 if(emettre_fois >= 5)
                           	{
                           		list_musiques.add(musique);
                           	}
                 		 }
                       }
                 }
            }
            return list_musiques;
     }
}
