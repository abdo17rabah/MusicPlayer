package Donnee;

/**
 * @author RABAH Abderrafii
 */ 

public class MusiqueJoueeActuellement 
{

    private String id; // id de la musique 
    
    private String nomChansonActuelle; // titre de la chanson jouée en cours

    public String getId()  // retourne l'id de la musique
    {
        return id;
    }

    public void setId(String id) // initialise l'id de la musique
    {
        this.id = id;
    }

    public String getNomChansonActuelle() // retourne le titre de la chanson jouée en cours
    {
        return nomChansonActuelle;
    }

    public void setNomChansonActuelle(String nomChansonActuelle) // initialise le titre de la chanson jouée en cours
    {
        this.nomChansonActuelle = nomChansonActuelle;
    }
    
    
    
}
