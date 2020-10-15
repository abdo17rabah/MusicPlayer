package Donnee;

/**
 * @author RABAH Abderrafii
 */ 


public class PlayList 
{

    private String id; // numero de la playlist
    
    private String nomPlaylist; // nom de la playlist

    private String collection; //Pour determiner les id des chansons qui appartient à la playlist
    
    private boolean is_creer_favoris=true;
    
    public String getId() //retourne id de la playlist
    {
        return id;
    }

    public void setId(String id) //initilaliser id de la playlist
    {
        this.id = id;
    }

    public String getNomPlaylist() //retourne le nom de la playlist
    {
        return nomPlaylist;
    }

    public void setNomPlaylist(String nomPlaylist) //initilaliser le nom de la playlist
    {
        this.nomPlaylist = nomPlaylist;
    }

	public String getCollection()  //retourne id de chanson appartante à la playlist
        {
		return collection;
	}

	public void setCollection(String collection)  //initilaliser id de chanson appartante à la playlist
        {
		this.collection = collection;
	}

	public boolean isIs_creer_favoris() {
		return is_creer_favoris;
	}

	public void setIs_creer_favoris(boolean is_creer_favoris) {
		this.is_creer_favoris = is_creer_favoris;
	}

	
	 
}
