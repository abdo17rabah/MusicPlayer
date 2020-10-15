package musique;


import Donnee.Donnee;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * @author RABAH Abderrafii
 */
public class Fichier {
    
    
    @SuppressWarnings("unused")
	private String fichier_path; // emplacement du fichier

    
    @SuppressWarnings("unused")
	private  String extension; //l'extension du fichier
    
    private Musique musique; //
    private XMLOperation xml_opration; //
    
    
    public Fichier() 
    { 
        musique=new Musique();
        xml_opration=new XMLOperation();
    }
    
    
    @SuppressWarnings("unused")
	public List<Musique> ouvrir_par_fichiers(File[] file,Donnee listedeLecture,int id_numero) //méthode d'ajouter des chansons à partir d'un fichier
    {
    	try{
    		  List<Musique> list_m=null; // créer une liste pour stocker les chansons choisis pour les ajouter 
    	        if(listedeLecture.getMusique()==null) 
    	        {
    	        	list_m=new ArrayList<>();   
    	        }else
    	        {
    	        	list_m=listedeLecture.getMusique();
    	        }
    	        
    	        for(int i=0;i<file.length;i++)
    	        {
    	        	if(verifier_chanson_exister(file[i].toString(),listedeLecture)) //verifier si la chanson n'existe pas déja dans la liste de lecture
    	        	{
    	        	Musique chanson=musique.obtenir_info_chanson(file[i].toString()); // faire appel à la fonction obtenir_info_chanson qui retourne un objet Musique avec toutes les infos sur la chanson
    	                String titreChanson= chanson.getTitre(); // initliser le titre chanson
    	                chanson.setId(""+id_numero); // initliser l'id de la chanson
    	                id_numero+=1; 
    	                list_m.add(chanson); // ajouter la chanson à la liste des chansons créée 
    	        	}
    	        } 
    	        listedeLecture.setMusique(list_m); // initaliser la liste de lecture avec la liste des chansons ajouter à partir des emplacementFichier
    	        listedeLecture.setEmplacement_fichier(emplacementRepertoire(file)); // initialiser l'emplacement de lecture par l'emplacement commun des fichiers ajouter
      	        xml_opration.ecrire_info_musique_en_xml(listedeLecture); //écrire sur le fichier xml les information sur les chansons de la liste de lecture choisie
    	         return list_m;
    	}catch (Exception e) {
    		Logger.getLogger(Fichier.class.getName()).log(Level.SEVERE, null, e);
    		JOptionPane.showMessageDialog(null, "impossible d'ajouter de la musique", "ERROR", JOptionPane.ERROR_MESSAGE); 
		}
      
        return null;
    }
    

    public boolean verifier_chanson_exister(String fichier,Donnee listedeLecture) //verifier si la chanson n'existe pas déja dans la liste de lecture
    {
    	if(listedeLecture==null)
    	{
    		return true;
    	}else{
    		List<Musique> list_musiques=listedeLecture.getMusique();
    		if(list_musiques==null)
    		{
    			return true;
    		}
    		 for(Musique musique: list_musiques)
    		 {
    			if(fichier.equals(musique.getLocalisation_chanson())) //si le fichier comparer au fichier de la liste de lecture en le même emplacement on retourne false
    			{
    				return false;
    			}else
    			{
    				continue;
    			}
    		 }
    	}
    	return true;
    }
    

    public  List<Musique>  ouvrir_par_repertoire(String repertoire,Donnee listedeLecture,int id) //méthode d'ajouter des chansons à partir d'un répertoire
    { 
    	try
    	{
            List<Musique> list_m=null;
            File file = new File(repertoire);
            File [] files = file.listFiles(); 
            
            for(int i=0; i<files.length;i++)
            {
            	String emplacementFichier=files[i].toString();
            	if(emplacementFichier.endsWith(".mp3")) // Afficher que les emplacementFichier d'extension mp3
            	{	if(verifier_chanson_exister(emplacementFichier,listedeLecture)) //verifier si la chanson n'existe pas déja dans la liste de lecture
            		{
    	        		 Musique chanson=musique.obtenir_info_chanson(emplacementFichier);
    	        		 if(chanson!=null && !"-1".equals(chanson.getId()))
    	        		 {
    	        			 chanson.setId(""+id); 
    	        			 id+=1;
    	        			 list_m=listedeLecture.getMusique();
    	        			 if(list_m==null){
    	        				 list_m=new ArrayList<>();
    	        			 }
    	        			 list_m.add(chanson); 
    	        			 listedeLecture.setMusique(list_m);
    	        		 }
            		 }
            	}else{
            		continue;
            	}
            	
            }
             
            listedeLecture.setEmplacement_fichier(repertoire);
             
            xml_opration.ecrire_info_musique_en_xml(listedeLecture);//ecrire dans le fichier xml
            return list_m;
    	}catch (Exception e) {
    		Logger.getLogger(Fichier.class.getName()).log(Level.SEVERE, null, e);
    		JOptionPane.showMessageDialog(null, "impossible d'ajouter les musiques", "ERROR", JOptionPane.ERROR_MESSAGE); 
		}
    	
         return null;
    }
   
    /**
     * trouver le chemin commun des musiques
     * @param files: les emplacementFichier des musiques
     * @return: chemin commun
     */
    public String emplacementRepertoire(File[] files) //retourner l'emplacement du répertoire 
    {
    	List<String> listRepertoires=new ArrayList<>();
       
    	if(files.length==0 || files ==null)
        {
            return "";
        }
        for(int i=0;i <files.length; i++ )
        {
            String chemin=files[i].toString();
            String os = System.getProperty("os.name");
            int longueur=0; 
            if(os.toLowerCase().startsWith("win"))
            {
            	 longueur=chemin.lastIndexOf("\\");  //sous la system windows
            }else if(os.toLowerCase().startsWith("lin"))
            {
            	 longueur=chemin.lastIndexOf("/");  //sous la system windows
            }
            String new_chemin=chemin.substring(0,longueur);//
            if(!listRepertoires.contains(new_chemin))
            {
            	listRepertoires.add(new_chemin);
            }
        }
        if(listRepertoires.size()>0)
        {
        	return listRepertoires.get(0);
        }
        return "";
    }
    
   
}
