package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author RABAH Abderrafii
 */
public class Util  //pour la création d'un fichier xml qui va servir comme bibliothéque locale
{
   
    private Document document; 
    private String path = null;
    private DocumentBuilder builder;
    private Constante constante = null;
    private PrintWriter pw;
    private String path_local;
    
    public Util() // créer le  dossier contenant le fichier xml
    {
        this.path = Util.class.getResource("/").getPath() + "configuration/";
        this.constante = new Constante();
        this.path_local="configuration/musique.xml";
    }

    
    public void init_document() // creation d'un fichier xml
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // creation d'une instance de DocumentBuilderFactory
            builder = factory.newDocumentBuilder(); //creation d'un parseur 
        } catch (ParserConfigurationException e)
        {
            System.out.println("établir une bibliotheque a echoué" + e.getMessage());
        }

    }
 
    
    /**
     * Creer le XML
     * @param xml_donnee 
     */
    public void creer_bibliotheque_xml(Map<String, Object> xml_donnee) //creer une bibliotheque xml
    {
        init_document();
        document = builder.newDocument();
        Element racine_musique = document.createElement(constante.MUSIQUES); //racine de l'arbre
        document.appendChild(racine_musique);
        racine_musique.appendChild(creer_element(document, constante.MUSIQUE, xml_donnee)); //ajouter une fils à l'arbre
        racine_musique.appendChild(creer_element(document, constante.CHANSONS, xml_donnee));//ajouter une fils à l'arbre
        racine_musique.appendChild(creer_element(document, constante.PLAYLISTS, xml_donnee)); //ajouter une fils à l'arbre
        racine_musique.appendChild(creer_element(document, constante.JOUEACTUELLEMENT, xml_donnee)); //ajouter une fils à l'arbre
        actualiser(document); //actualiser le fichier xml
    }

    /**
     * lire le XMl
     * @return  Map<String, Object>
     */
    public Map<String, Object> lire_xlm() //lire un fichier xml
    {
        init_document(); //initialisation du document
        Map<String, Object> map = new HashMap<>();
        try
        {
           // document = builder.parse(this.path + "musique.xml");
            document = builder.parse(this.path_local);
            NodeList racine_lists = document.getChildNodes(); //récupperer la liste des noeuds 
            if (racine_lists != null)
            {
                for (int i = 0; i < racine_lists.getLength(); i++)
                {
                    Node racine = racine_lists.item(i); // récupperer la liste des fils des noeuds 
                    NodeList racine_enfant = racine.getChildNodes();//recupperer les noeuds-fils
                    if (racine_enfant != null)
                    {
                        for (int j = 0; j < racine_enfant.getLength(); j++)
                        {
                            Node enfants = racine_enfant.item(j);//recupperer les noeuds des noeuds-fils
                            NodeList enfant_list = enfants.getChildNodes();//recupperer les fils des noeuds-fils
                            String enfant_name = enfants.getNodeName();
                            switch (enfant_name) //écrire dans le fichier xml
                            {
                                case "MUSIQUE":
                                    map.put(enfant_name, produit_donnee(enfant_list, enfant_name));
                                    break;
                                case "CHANSONS":
                                    map.put(enfant_name, produit_donnee(enfant_list, enfant_name));
                                    break;
                                case "PLAYLISTS":
                                    map.put(enfant_name, produit_donnee(enfant_list, enfant_name));
                                    break;
                                case "JOUEACTUELLEMENT":
                                    map.put(enfant_name, produit_donnee(enfant_list, enfant_name));
                                    break;
                                default:
                                    break;
                            }

                        }
                    }

                }

            }
            return map;
        }catch(FileNotFoundException f)
        {
        	Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "fichier non trouvé!", f);
        	JOptionPane.showMessageDialog(null, "ajoutez de la musique,SVP", "ERROR", JOptionPane.ERROR_MESSAGE); 
        }
        catch (SAXException | IOException ex)
        {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
 
    
    public List<Map<String, Object>> produit_donnee(NodeList node_list, String nom_racine) //produire les données du fichier xml
    {
        List<Map<String, Object>> list = null;
        if (nom_racine.equals(constante.MUSIQUE))
        {
            list = new ArrayList<>();
            Map<String, Object> musique_map = new HashMap<>();
            for (int i = 0; i < node_list.getLength(); i++)
            {
                Node node_enfant = node_list.item(i);
                String nom_node = node_enfant.getNodeName(); //nom du noeuds
                String valeur_node = node_enfant.getTextContent(); //valeur du noeuds
                if (node_enfant.getNodeName().equals(constante.FICHIER_CHEMIN) || node_enfant.getNodeName().endsWith(constante.NOMBRE_CHANSONS))
                {
                    //musique_map.put(nom_racine+"%"+nom_node+"%NON", valeur_node);
                    musique_map.put(nom_node, valeur_node);

                }
            }
            list.add(musique_map);//ajout au dictionnaire 
            return list;
        }   if (nom_racine.equals(constante.JOUEACTUELLEMENT))
        {
            list = new ArrayList<>();
            Map<String, Object> actuellement_emttre = new HashMap<>();
            for (int i = 0; i < node_list.getLength(); i++)
            {
                Node node_enfant = node_list.item(i);
                String nom_node = node_enfant.getNodeName();
                String valeur_node = node_enfant.getTextContent();
                if (node_enfant.getNodeName().equals(constante.ID))
                {
                    actuellement_emttre.put(nom_node, valeur_node);
                }
            }
            list.add(actuellement_emttre);
            return list;
        }else if (nom_racine.equals(constante.CHANSONS) || nom_racine.equals(constante.PLAYLISTS)) //ajout des infos de la chanson au noeud CHANSON
        {
            list = new ArrayList<>();

            for (int i = 0; i < node_list.getLength(); i++)
            {
                Node node= node_list.item(i);
                String nom_node = node.getNodeName();
                NodeList     enfant = node.getChildNodes();
                Map<String, Object> node_map = new HashMap<>();
                for (int j = 0; j < enfant.getLength(); j++)
                {
                    Node enfant_node = enfant.item(j);
                    //String nom_node=node.getNodeName();
                    String nom_enfant_node = enfant_node.getNodeName();
                    String value_enfant_node = enfant_node.getTextContent();
                    if (nom_enfant_node.equals(constante.ID) || nom_enfant_node.equals(constante.TITRE) || nom_enfant_node.equals(constante.ARTISTE)
                            || nom_enfant_node.equals(constante.ALBUM) || nom_enfant_node.equals(constante.LONGUEUR) || nom_enfant_node.equals(constante.SORTIE_DATE)
                            || nom_enfant_node.equals(constante.JOUE_NBRFOIS) || nom_enfant_node.equals(constante.LOCATION_CHANSON) || nom_enfant_node.equals(constante.COLLECTION))
                    {
                        //node_map.put(nom_node+"%"+nom_enfant_node+"%NON", value_enfant_node);
                        node_map.put(nom_enfant_node, value_enfant_node);
                    }

                }
                if (nom_node.equals(constante.CHANSON) || nom_node.equals(constante.PLAYLIST))
                {
                    list.add(node_map);
                }
            }
            return list;

        } 

        return list;
    }

    
    
    @SuppressWarnings("unchecked")
	public Element creer_element(Document document, String nom_element, Map<String, Object> xml_donnee) //creer des noeuds
    {
        Element element = document.createElement(nom_element);
        List<Object> list = (List<Object>) xml_donnee.get(nom_element);
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                Map<String, Object> map = (Map<String, Object>) list.get(i);
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                Element pere_element=null;
                while (iterator.hasNext())
                {
                    Entry<String, Object> next = iterator.next();
                    String nom = next.getKey();
                    String valeur = (String) next.getValue();
                    String nom_split[]=nom.split("%",3);
                    Element enfant_element;
                    if(!nom_split[0].equals(nom_element))
                    {
                        if(pere_element==null)
                        {
                              pere_element=document.createElement(nom_split[0]);
                        }
                              enfant_element=document.createElement(nom_split[1]);
                              enfant_element.setTextContent(valeur); 
                              pere_element.appendChild(enfant_element);
                              element.appendChild(pere_element);
                      
                     } else
                    {
                          enfant_element=document.createElement(nom_split[1]); 
                          enfant_element.setTextContent(valeur); 
                          element.appendChild(enfant_element);
                    } 
                          
                }
            }
            
        }
        return element;
    }

   
     @SuppressWarnings("unchecked")
	public Element creer_element_par_date(Document document, String nom_element, Map<String, Object> xml_donnee) //creer un élement par date
    {
        Element element = document.createElement(nom_element);
        List<Object> list = (List<Object>) xml_donnee.get(nom_element);
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                Map<String, Object> map = (Map<String, Object>) list.get(i);
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                Element pere_element=null;
                while (iterator.hasNext())
                {
                    Entry<String, Object> next = iterator.next();
                    String nom = next.getKey();
                    String valeur = (String) next.getValue();
                    String nom_split[]=nom.split("%",3);
                    System.out.println(nom_split[0] + " : "+nom_split[1] + " : "+nom_split[2]);
                    Element enfant_element;
                    
                    if(!nom_split[0].equals(nom_element))
                    {
                        if(pere_element==null)
                        {
                              pere_element=document.createElement(nom_split[0]);
                        }
                              enfant_element=document.createElement(nom_split[1]);
                              enfant_element.setTextContent(valeur); 
                              pere_element.appendChild(enfant_element);
                              element.appendChild(pere_element);
                      
                       
                     } else
                    {
                          enfant_element=document.createElement(nom_split[1]); 
                          enfant_element.setTextContent(valeur); 
                          element.appendChild(enfant_element);
                    } 
                          
                }
            }
            
        }
        return element;
    }

    
    
    public void  ajouter_xml(String name,String[] value) //ajouter des élement au fichier xml
    {
        try{
                init_document();
                document = builder.parse(this.path + "musique.xml"); 
                Element element=null;
                if(name.equals(constante.PLAYLISTS))
                {
                    element=(Element)document.getElementsByTagName(name).item(0);
                    Element play_list_element=document.createElement(constante.PLAYLIST);
                    Element id_element=document.createElement(constante.ID);
                    Element collection_element=document.createElement(constante.COLLECTION);
                    Element titre_element=document.createElement(constante.TITRE);
                    id_element.setTextContent(value[0]);
                    collection_element.setTextContent(value[1]);
                    titre_element.setTextContent(value[2]);
                    play_list_element.appendChild(id_element);
                    play_list_element.appendChild(collection_element);
                    play_list_element.appendChild(titre_element); 
                    element.appendChild(play_list_element);
                }else if(name.equals(constante.JOUEACTUELLEMENT))
                {
                    element=(Element)document.getElementsByTagName(name).item(0);
                    if(element==null)
                    {
                         element=document.createElement(constante.JOUEACTUELLEMENT);
                         Element id_element=document.createElement(constante.ID); 
                         id_element.setTextContent(value[0]);
                         element.appendChild(id_element);
                    } 
                   
                }
               document.getDocumentElement().appendChild(element);
               actualiser(document);
        }catch(IOException | DOMException | SAXException ex)
        {
             Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void actualiser(Document docment) //actualiser le fichier xml
    {
        try
        {
            Transformer transform = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            transform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transform.setOutputProperty(OutputKeys.INDENT, "yes");
           // pw = new PrintWriter(new File(this.path + "musique.xml"));
            pw = new PrintWriter(new File(this.path_local));
            StreamResult sr = new StreamResult(pw);
            transform.transform(source, sr);
        } catch (TransformerConfigurationException ex)
        {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException | TransformerException ex)
        {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            if (pw != null)
            {
                pw.close();
            }
        }
    }
    public static void main(String[] args)
    {
       new Util();
    }
}
