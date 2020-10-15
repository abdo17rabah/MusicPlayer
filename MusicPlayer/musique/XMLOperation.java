package musique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import Donnee.PlayList;
import Donnee.Donnee;
import Donnee.MusiqueJoueeActuellement;
import util.Constante;
import util.Util;

/**
 * @author RABAH Abderrafii
 */
public class XMLOperation
{

    private Util util;

    private Constante constante;

    @SuppressWarnings("unused")
	private String emplacement_fichier;

    @SuppressWarnings("unused")
	private int nombre_chansons;
 
    public XMLOperation()
    {
        util = new Util();
        constante = new Constante();
    }

    public Map<String, Object> lire_ficher() //lire le fichier xml
    {
        Map<String, Object> map = util.lire_xlm();
        if (map == null || map.size() == 0)
        {
            return null;
        }

        return map;
    }

    public List<Donnee> get_info_musique(Map<String, Object> map) //obtenir les infos des chansons
    {

        Map<String, Object> maps = util.lire_xlm();

        System.out.println("musique.XMLOperation.get_info_musique()" + maps);

        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean ecrire_info_musique_en_xml(Donnee listedeLecture) //introduire les infos des chansons dans le fichier xml
    {
        Map<String, Object> xml_infos = new HashMap<>();

        if (listedeLecture != null)
        {
            List<Musique> musiques = listedeLecture.getMusique();

            if (musiques != null)
            {
                List info_musique = conversion_infos(constante.MUSIQUE, listedeLecture.getEmplacement_fichier(),musiques);
                List list_object = conversion_infos(constante.CHANSON, listedeLecture.getEmplacement_fichier(),musiques);
                xml_infos.put(constante.MUSIQUE, info_musique);
                xml_infos.put(constante.CHANSONS, list_object);
            } else
            {

                xml_infos.put(constante.MUSIQUE, null);
                xml_infos.put(constante.CHANSONS, null);
            }

            List<PlayList> playlist = listedeLecture.getPlaylist();
            if (playlist != null)
            {
                List list_object = conversion_infos(constante.PLAYLIST,listedeLecture.getEmplacement_fichier(),  playlist);
                xml_infos.put(constante.PLAYLISTS, list_object);

            } else
            {
                xml_infos.put(constante.PLAYLISTS, null);
            }
            MusiqueJoueeActuellement musique_actuelle = listedeLecture.getMusique_jouee_en_cours();
            List list = new ArrayList<Object>();
            list.add(musique_actuelle);
            if (musique_actuelle != null)
            {
                List list_object = conversion_infos(constante.JOUEACTUELLEMENT,listedeLecture.getEmplacement_fichier(),list);
                xml_infos.put(constante.JOUEACTUELLEMENT, list_object);

            } else
            {
                xml_infos.put(constante.JOUEACTUELLEMENT, null);
            }

        }

        util.creer_bibliotheque_xml(xml_infos);
        return true;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List conversion_infos(String name, String fichier_chemin, List list) //conversion des infos des chansons pour les introduire dans le fichier xml
    {
        List list_object = null;
        Map<String,Object> map_object = null;
        if (name.equals(constante.MUSIQUE))
        {
            list_object = new ArrayList();
            map_object = new HashMap();
            map_object.put(constante.MUSIQUE + "%" + constante.FICHIER_CHEMIN + "%NON", fichier_chemin);
            map_object.put(constante.MUSIQUE + "%" + constante.NOMBRE_CHANSONS + "%NON", String.valueOf(list.size()));
            list_object.add(map_object);
        } else if (name.equals(constante.CHANSON))
        {
            list_object = new ArrayList();
            for (int i = 0; i < list.size(); i++)
            {
                map_object = new HashMap();
                Musique musique = (Musique) list.get(i);
                map_object.put(constante.CHANSON + "%" + constante.JOUE_NBRFOIS + "%NON", musique.getJoue_nbrfois());
                map_object.put(constante.CHANSON + "%" + constante.ID + "%NON", musique.getId());
                map_object.put(constante.CHANSON + "%" + constante.ARTISTE + "%NON", musique.getArtiste());
                map_object.put(constante.CHANSON + "%" + constante.ALBUM + "%NON", musique.getAlbum());
                map_object.put(constante.CHANSON + "%" + constante.LONGUEUR + "%NON", musique.getLongueur());
                map_object.put(constante.CHANSON + "%" + constante.LOCATION_CHANSON + "%NON", musique.getLocalisation_chanson());
                map_object.put(constante.CHANSON + "%" + constante.TITRE + "%NON", musique.getTitre());
                map_object.put(constante.CHANSON + "%" + constante.SORTIE_DATE + "%NON", musique.getDate_sortie());
                list_object.add(map_object);
            }
        } else if (name.equals(constante.PLAYLIST))
        {
            list_object = new ArrayList();
            for (int i = 0; i < list.size(); i++)
            {
                map_object = new HashMap();
                PlayList crerlist = (PlayList) list.get(i);
                map_object.put(constante.PLAYLIST + "%" + constante.ID + "%NON", i+"");
                ((PlayList)list.get(i)).setId(""+i);
                map_object.put(constante.PLAYLIST + "%" + constante.COLLECTION + "%NON", crerlist.getCollection());
                map_object.put(constante.PLAYLIST + "%" + constante.TITRE + "%NON", crerlist.getNomPlaylist());
                list_object.add(map_object);
            }
        } else if (name.equals(constante.JOUEACTUELLEMENT))
        {
            list_object = new ArrayList();
            for (int i = 0; i < list.size(); i++)
            {
                map_object = new HashMap();
                MusiqueJoueeActuellement musique_actuelle = (MusiqueJoueeActuellement) list.get(i);
                map_object.put(constante.JOUEACTUELLEMENT + "%" + constante.ID + "%NON", musique_actuelle.getId());
                map_object.put(constante.JOUEACTUELLEMENT+"%"+constante.TITRE+"%NON",musique_actuelle.getNomChansonActuelle());
                list_object.add(map_object);
            }
        }
        return list_object;
    }

    /**
     * encapsuler les informations en object 
     * @param maps les donées dans xml
     * @return :
     * 		
     */
    @SuppressWarnings({ "rawtypes", "null" })
	public Donnee encapsuler_infos(Map<String, Object> maps)
    {
        Donnee listedeLecture = new Donnee();
        List<Musique> list_musique = new ArrayList<>();
        List<Musique> list_chanson = new ArrayList<>();
        List<PlayList> list_playlist = new ArrayList<>();
        MusiqueJoueeActuellement musique_actuelle;
        if (maps == null && maps.size() == 0)
        {
            list_musique = new ArrayList<>();
            list_playlist = new ArrayList<>();
            Musique musique = new Musique();
            list_musique.add(musique);
            PlayList playlist = new PlayList();
            list_playlist.add(playlist);
            musique_actuelle = new MusiqueJoueeActuellement();

            listedeLecture.setEmplacement_fichier(null);
            listedeLecture.setNombre_musique("0");
            listedeLecture.setMusique_jouee_en_cours(musique_actuelle);
            listedeLecture.setMusique(list_musique);
            listedeLecture.setPlaylist(list_playlist);
            return listedeLecture;
        }

        List list_m = (List) maps.get(constante.MUSIQUE);
        System.out.println("length : " + list_m.size());
        if (list_m != null && list_m.size() > 0)
        {
            for (int i = 0; i < list_m.size(); i++)
            {
                Map map = (Map) list_m.get(i);
                Iterator it = map.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    if (key.equals(constante.FICHIER_CHEMIN))
                    {
                        listedeLecture.setEmplacement_fichier(value);
                    }

                    if (key.equals(constante.NOMBRE_CHANSONS))
                    {
                        listedeLecture.setNombre_musique(value);
                    }
                }
            }
        } else
        {
            listedeLecture.setNombre_musique("0");
            listedeLecture.setEmplacement_fichier(null);
        }

        List list_c = (List) maps.get(constante.CHANSONS); 
        if (list_c != null && list_c.size() > 0)
        {
            for (int i = 0; i < list_c.size(); i++)
            {
                Map map = (Map) list_c.get(i);
                Iterator it = map.entrySet().iterator();
                Musique musique = new Musique();
                while (it.hasNext())
                {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                   
                    if (constante.ID.equals(key))
                    {
                        musique.setId(value);
                    }
                    else if (constante.ALBUM.equals(key))
                    {
                        musique.setAlbum(value);
                    }
                    else if (constante.ARTISTE.equals(key))
                    {
                        musique.setArtiste(value);
                    }
                    else if (constante.LONGUEUR.equals(key))
                    {
                        musique.setLongueur(value);
                    }
                    else if (constante.LOCATION_CHANSON.equals(key))
                    {
                        musique.setLocalisation_chanson(value);
                        musique.setImg(musique.obtrenir_information_image(value));
                    }
                    else if (constante.TITRE.equals(key))
                    {
                        musique.setTitre(value);
                    }
                    else if (constante.SORTIE_DATE.equals(key))
                    {
                        musique.setDate_sortie(value);
                    }
                    else if (constante.JOUE_NBRFOIS.equals(key))
                    {
                        musique.setJoue_nbrfois(value);
                    }
                }
                list_chanson.add(musique);
            }
               listedeLecture.setMusique(list_chanson);
        } else
        {
            listedeLecture.setMusique(list_chanson);
        }
        
         List list_p = (List) maps.get(constante.PLAYLISTS);
        if (list_p != null && list_p.size() > 0)
        {
            for (int i = 0; i < list_p.size(); i++)
            {
                Map map = (Map) list_p.get(i);
                Iterator it = map.entrySet().iterator();
                PlayList play=new PlayList();
                while (it.hasNext())
                {
                    Map.Entry entry = (Map.Entry) it.next();
                     String key = (String) entry.getKey();
                     System.out.println("length : " +key);
                    String value = (String) entry.getValue();
                    if (key.equals(constante.ID))
                    {
                        play.setId(value);
                    }

                    if(key.equals(constante.COLLECTION))
                    {
                    	play.setCollection(value);
                    }
                    if (key.equals(constante.TITRE))
                    {
                        play.setNomPlaylist(value);
                    }
                    
                    
                }
                list_playlist.add(play);
            }
            listedeLecture.setPlaylist(list_playlist);
        } else
        {
            listedeLecture.setPlaylist(list_playlist);
        }
        
          List list_a = (List) maps.get(constante.JOUEACTUELLEMENT);
        if (list_a != null && list_a.size() > 0)
        {
            for (int i = 0; i < list_a.size(); i++)
            {
                Map map = (Map) list_a.get(i);
                Iterator it = map.entrySet().iterator();
                MusiqueJoueeActuellement musique_c=new MusiqueJoueeActuellement();
                while (it.hasNext())
                {
                    Map.Entry entry = (Map.Entry) it.next();
                     String key = (String) entry.getKey();
                     System.out.println("length : " +key);
                    String value = (String) entry.getValue();
                    if (key.equals(constante.ID))
                    {
                        musique_c.setId(value);
                    }

                    if (key.equals(constante.TITRE))
                    {
                        musique_c.setNomChansonActuelle(value);
                    }
                } 
                listedeLecture.setMusique_jouee_en_cours(musique_c);
            }
            listedeLecture.setPlaylist(list_playlist);
        } else
        {
               listedeLecture.setMusique_jouee_en_cours(null);
        }
        return listedeLecture;
    }

    /***/
    @SuppressWarnings("unused")
	public void update_xlm(Donnee info_acienne,Donnee info_nouvelle) //mise à jour du fichier xml 
    {
    	Donnee donne_xml=new Donnee();
    	List<Musique> list_m=new ArrayList<>();
    	List<PlayList> list_c=new ArrayList<>(); 
    	if(info_acienne!=null)
    	{
    		info_acienne.setMusique_jouee_en_cours(info_nouvelle.getMusique_jouee_en_cours());
    		ecrire_info_musique_en_xml(info_acienne);
    	}
    }
    
    
    
     
    public Donnee update_xml(Donnee donne,Donnee donne_supprimer) //mise à jour du fichier xml
    {   
    	List<Musique> list_musique_ancien=donne.getMusique(); //recupperer la liste des chansons 
    	List<Musique> list_musique_supprrimer=donne_supprimer.getMusique(); //recupperer la liste des chansons 
    	String [] chanson_ids_supprimer= new String [list_musique_supprrimer.size()]; //créer un tableau de String 
    	if(list_musique_ancien!=null && list_musique_ancien.size()>0)
    	{
    	    for(int i=0;i<list_musique_ancien.size();i++)
    	    {
    	    	Musique musique_ancien=list_musique_ancien.get(i);
    	    	int id_ancien=Integer.valueOf(musique_ancien.getId());
    	    	if(list_musique_supprrimer!=null && list_musique_supprrimer.size()>0){
    	    		for(int j=0;j<list_musique_supprrimer.size();j++){
    	    			Musique musique_supprrimer=list_musique_supprrimer.get(j);
    	    			int id_supprimer=Integer.valueOf(musique_supprrimer.getId());
    	    			if(id_ancien==id_supprimer){
    	    				chanson_ids_supprimer[j]=musique_supprrimer.getId();
    	    				list_musique_ancien.remove(musique_ancien);
    	    			}else{
    	    				continue;
    	    			}
    	    		}
    	    	}
    	    }
    	}
    	supprimer_chanson_dans_playlist(donne.getPlaylist(), chanson_ids_supprimer);
    	ordonner_donne_par_id(donne);
    	ecrire_info_musique_en_xml(donne);
    	return donne;
    }
    
    
    /**
     * tier le donne en ordre
     * @param donne
     */
    public void ordonner_donne_par_id(Donnee donne){ //classer les chansons par leur id
    	List<Musique> list_musique_ancien=donne.getMusique();
    	if(list_musique_ancien!=null && list_musique_ancien.size()>0){
    		for(int i=0;i<list_musique_ancien.size();i++){
    			Musique musique=list_musique_ancien.get(i);
    			int id=Integer.valueOf(musique.getId());
    			if(id==i){
    				continue;
    			}else{
    				musique.setId(""+i);
    			}
    		}
    		donne.setNombre_musique(String.valueOf(list_musique_ancien.size()));
    	}
    }
    /**
     *
     * @param args
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args)
    {
        XMLOperation x = new XMLOperation();
        Util util = new Util();

        Map map = util.lire_xlm();
        x.encapsuler_infos(map);
    }

    public void supprimer_chanson_dans_playlist(List<PlayList> _crer_list_,String[] chanson_ids_supprimer) //supprimer des chansons des playlists
    {
    	for(PlayList _crer:_crer_list_)
    	{
    		String collection=_crer.getCollection();
    		String [] collections = collection.split(":");
    		_crer.setCollection(comparer_chanson_id(collections,chanson_ids_supprimer));
    	}
    }
    
    public String comparer_chanson_id(String [] collections,String [] chanson_ids_supprimer) //comparer deux chansons
    {
    	String nouveaux_collections="";
    	for(int i=0;i<collections.length;i++)
    	{
    		String chanson_id_conllection=collections[i];
    		for(int j=0;j<chanson_ids_supprimer.length;j++)
    		{
    			String chanson_id_supprimer=chanson_ids_supprimer[j];
    			if(!"".equals(chanson_id_conllection))
    			{
    				if(!chanson_id_conllection.equals(chanson_id_supprimer))
        			{
        				nouveaux_collections=nouveaux_collections+":"+chanson_id_conllection;
        			}
    			}
    			
    		}
    	}
    	return nouveaux_collections;
    }
   
    @SuppressWarnings("unused")
	public Donnee chercher_donnes(String nom_jpanel,String chercher_contexte,Donnee object) //fournir des infos 
    {
    	Donnee chercher_donne=new Donnee();
    	List<Musique> list_musique_chercher=new ArrayList<>();
    	List<PlayList> list_crer_chercher=new ArrayList<>();
    	List<Musique> list_musique_=object.getMusique();
    	List<PlayList> list_crer_=object.getPlaylist();
    	if(constante.ARTISTE.equals(nom_jpanel))
		{
    		if(list_musique_!=null && list_musique_.size()>0){
    			for(Musique musique_:list_musique_){
    				String artiste=musique_.getArtiste();
    				if(artiste.toUpperCase().trim().contains(chercher_contexte.toUpperCase().trim()))
    				{
    					list_musique_chercher.add(musique_);
    				}
    			}
    		} 
		}else if(constante.CHANSON.equals(nom_jpanel) || constante.PLAYLIST.equals(nom_jpanel))
		{
			if(list_musique_!=null && list_musique_.size()>0){
    			for(Musique musique_:list_musique_){
    				String titre=musique_.getTitre();
    				if(titre.toUpperCase().trim().contains(chercher_contexte.toUpperCase().trim()))
    				{
    					list_musique_chercher.add(musique_);
    				}
    			}
    		}
			 
		}else if(constante.ALBUM.equals(nom_jpanel))
		{
			if(list_musique_!=null && list_musique_.size()>0){
    			for(Musique musique_:list_musique_){
    				String album=musique_.getAlbum();
    				if(album.toUpperCase().trim().contains(chercher_contexte.toUpperCase().trim()))
    				{
    					list_musique_chercher.add(musique_);
    				}
    			}
    		}
		}
    	chercher_donne.setMusique(list_musique_chercher);
    	return chercher_donne;
    }
}
