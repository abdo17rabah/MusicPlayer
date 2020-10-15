package Donnee;

import java.util.List;
import musique.Musique;

/**
 * @author RABAH Abderrafii
 */ 


public class Donnee 
{
   
    private List<Musique> musique; // une liste des chansons
    
    private List<PlayList> playlist;  // une liste des playlists
    
    private MusiqueJoueeActuellement musique_jouee_en_cours; // chanson jouée en cours
    
    private String emplacement_fichier; // l' emplacement du fichier
    
    private String nombre_musique; // Nombre des chansons existentes dans une liste de chansons
    
    public List<Musique> getMusique() // retourne une liste des chansons
    {
        return musique;
    }

    public void setMusique(List<Musique> musique) // initialiser une liste des chansons
    {
        this.musique = musique;
    }

    public List<PlayList> getPlaylist() // retourne une playlist
    {
        return playlist;
    }

    public void setPlaylist(List<PlayList> playlist) // initialiser une playlist
    {
        this.playlist = playlist;
    }

    public MusiqueJoueeActuellement getMusique_jouee_en_cours() // retourne la chanson jouée en cours
    {
        return musique_jouee_en_cours;
    }

    public void setMusique_jouee_en_cours(MusiqueJoueeActuellement musique_jouee_en_cours) // initialiser la chanson jouée en cours
    {
        this.musique_jouee_en_cours = musique_jouee_en_cours;
    }

    public String getEmplacement_fichier() // retourne l'Emplacement du fichier
    {
        return emplacement_fichier;
    }

    public void setEmplacement_fichier(String emplacement_fichier) // initialiser l'Emplacement du fichier
    {
        this.emplacement_fichier = emplacement_fichier;
    }

    public String getNombre_musique() // retourne le nombre de chansons dans une liste 
    {
        return nombre_musique;
    }

    public void setNombre_musique(String nombre_musique) // initialiser le nombre de chansons dans une liste
    {
        this.nombre_musique = nombre_musique;
    }
 
    
    
    
    
}
