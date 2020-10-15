package musique;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Donnee.Donnee;
import Donnee.MusiqueJoueeActuellement;
import Donnee.PlayList;
import http.HTMLViewer;
import util.Constante;

/**
 * @author RABAH Abderrafii
 */
@SuppressWarnings("serial")
public class LecteurMusiqueGUI extends JFrame implements ActionListener, MouseListener {

	private JButton precedent; // le button precedent
	private JButton jouer; // le button jouer
	private JButton suivant; // le button suivant
	private JButton repeter; // le button repeter
	private JButton chercher; // le bouton chercher
	private JTextField chercher_jtexfeilde; // zone de saisie
	private Label album_label; // album
	private Label artiste_label; // artiste
	private Label chanson_label; // chanson
	private Label joueRecemment_label; // playing
	private JButton button_atiste; // bouton atiste
	private JButton button_album; // bouton album
	private JButton button_chanson;// bouton chanson
	private JButton button_joueRecemment;// bouton playing
	private JPanel gauche_jpanel; // jpanel à gauche
	private JPanel droite_jpanel; // jpanel à drote
	private JPanel gauche_en_haut_jpanel; // jpanel à gauche en
											// haut(artiste,album,chanson,playing)
	private JPanel gauche_au_bas_jpanel; // jpanel à gauche au bas(PLAYLISTE)
	private JPanel droite_en_haut_jpanel; // jpanel à droite en haut(afficher
											// les
											// chanson,albums,artistes....ect)
	private JPanel droite_au_bas_jpanel; // jpanel à droite au bas(tous les
											// boutons suivant,jouer....ect)
	private JPanel chercher_jpanel; // jpanel en haut
	private JProgressBar jSlider_progress; // mettre la durée de temps da la
											// chanson
	private JScrollPane jScrollPane; // la barre de défilement
	private JScrollPane jSplitPane_artist;// la barre de défilement
	private JScrollPane jSplitPane_album; // la barre de défilement
	private JSeparator ligne_separe_gauche; // la ligne de separation
	private JSeparator ligne_separe_droite; // la ligne de separation
	private JSplitPane jSplitPane; // séparer la gauche et la droite
	private JTable jTable;// mettre toutes les chanson
	private JPopupMenu m_popupMenu; // pop menu
	private Fichier fichier; // la calss fichier
	private JMenuBar menu_bar; // la menu de ouvrir la fichier et répertoire
	private String path_image = LecteurMusiqueGUI.class.getResource("/").getPath(); // le
																				// chemin
																				// de
																				// bibliothèque(chanson.xml)
	private XMLOperation operation; // operation à écrire dans le fichier xml
									
	private List<Musique> musiques = null; // liste de chansons
	private Musique chanson;// un objet de classe Musique
	private int row; // rang 
	private int nombreChansons; // nombre de chansons d'une liste de chansons
	private int longeur_time; // la durée le temp de la chanson
	private boolean flag = true; // pour eliminer la valeur de
	private boolean is_repeter = false;
	private Donnee donnee; // toutes les informations sur la liste de lecture
	private Timer timer; // un timer pour la durée des chansons
	private int id_numero = 0; // ID de la chanson
	@SuppressWarnings("unused")
	private JPanel artiste_jpanel;
	Thread t = null; //  un Thread pour calculer la durée de temps de la chanson
						
	private Donnee listedeLecture_nouvelle = new Donnee();
	private List<Musique> list_musique_nouvelle = new ArrayList<>();
	@SuppressWarnings("unused")
	private List<PlayList> playlist = new ArrayList<>();
	private MusiqueJoueeActuellement musique_Jouee_enCours; // la chanson jouée en cours
	@SuppressWarnings("unused")
	private HTMLViewer html; // une page web pour afficher les informations de l'artiste sur wikipedia
	private String selection_jpanel =""; 
	private Constante constante;
	public LecteurMusiqueGUI() {
		constante=new Constante();
		operation = new XMLOperation(); // Création d'un objet XMLOperation
		chanson = new Musique();// Création d'un objet Musique
		Map<String, Object> map = operation.lire_ficher(); // lire le fichier xml
		if (map != null) {
			donnee = operation.encapsuler_infos(map); //obtenir toutes les informations des chansons du fichier xml lu
			musiques = donnee.getMusique(); // obtenir toutes les chansons
			if (musiques != null && musiques.size() > 0) {
				chanson = musiques.get(row); 
			}
		} else {
			donnee = new Donnee();
		}
		// implementation du graphe
		initComponents();
	}

	@SuppressWarnings("unused")
	private void initComponents() {
		selection_jpanel = constante.ARTISTE;
		jSplitPane = new JSplitPane(); //créer un objet JSplitPane
		gauche_jpanel = new JPanel(); // créer un objet JPanel
		gauche_en_haut_jpanel = new JPanel(); //créer un objet JPanel
		artiste_label = new Label(); // créer un objet Label pour artiste
		album_label = new Label(); // créer un objet Label pour album
		chanson_label = new Label(); // créer un objet Label pour chanson
		joueRecemment_label = new Label(); // créer un objet Label pour joué Recemment
		button_atiste = new JButton(); // créer un button atiste 
		button_album = new JButton(); // créer un button album 
		button_chanson = new JButton(); // créer un button chanson
		button_joueRecemment = new JButton(); // créer un button joueRecemment
		gauche_au_bas_jpanel = new JPanel(); // créer un objet JPanel
		ligne_separe_droite = new JSeparator(); // créer un objet JSeparator 
		droite_jpanel = new JPanel(); // créer un objet JPanel
		droite_en_haut_jpanel = new JPanel(); // créer un objet JPanel
		jScrollPane = new JScrollPane(); // créer un objet JScrollPane
		droite_au_bas_jpanel = new JPanel(); // créer un objet JPanel
		precedent = new JButton(); // créer un button precedent
		jouer = new JButton(); // créer un button jouer
		suivant = new JButton(); // créer un button repeter
		repeter = new JButton(); // créer un button repeter
		jSlider_progress = new JProgressBar(); // créer un objet JProgressBar
		ligne_separe_gauche = new JSeparator(); // créer un objet JSeparator
		chercher_jpanel = new JPanel(); // créer un objet JPanel
		chercher = new JButton(); // créer un button album  
		chercher.setToolTipText("Chercher"); //indication sur le role du boutton chercher
		chercher.setBackground(new Color(238, 238, 238));
		chercher.setBorder(null);
		setIcon("chercher.png", chercher); // initialiser l'icone de recherche
		chercher.addActionListener(this);
		chercher_jtexfeilde = new JTextField(); // créer un objet JPanel
		menu_bar = new JMenuBar();

		album_label.setText("Album");
		artiste_label.setText("Artiste");
		chanson_label.setText("Chanson");
		joueRecemment_label.setText("Jouées récemment");

		button_atiste.setToolTipText("Artiste");
		button_atiste.addActionListener(this);
		button_atiste.setBorder(null);
		button_atiste.setBackground(new Color(238, 238, 238));
		setIcon("artist.png", button_atiste);

		button_album.setToolTipText("Album");
		button_album.addActionListener(this);
		button_album.setBorder(null);
		button_album.setBackground(new Color(238, 238, 238));
		setIcon("pist.png", button_album);

		button_chanson.setToolTipText("Chanson");
		button_chanson.addActionListener(this);
		button_chanson.setBorder(null);
		button_chanson.setBackground(new Color(238, 238, 238));
		setIcon("song.png", button_chanson);

		button_joueRecemment.setToolTipText("Jouees recemment");
		button_joueRecemment.addActionListener(this);
		button_joueRecemment.setBorder(null);
		button_joueRecemment.setBackground(new Color(238, 238, 238));
		setIcon("emettre_chancon.png", button_joueRecemment);

		GroupLayout gauche_en_haut_group = new GroupLayout(gauche_en_haut_jpanel);
		gauche_en_haut_jpanel.setLayout(gauche_en_haut_group);
		gauche_en_haut_group.setHorizontalGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(gauche_en_haut_group.createSequentialGroup().addContainerGap()
						.addGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(artiste_label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(album_label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(chanson_label, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(joueRecemment_label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(button_atiste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(button_album, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(button_chanson, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(button_joueRecemment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(5, 5)));
		gauche_en_haut_group.setVerticalGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(gauche_en_haut_group.createSequentialGroup().addGap(20, 20, 20)
						.addGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(button_atiste, GroupLayout.PREFERRED_SIZE, 0, 20)
								.addComponent(artiste_label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 20))
						.addGap(20, 20, 20)
						.addGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(album_label, GroupLayout.PREFERRED_SIZE, 0, 20).addComponent(button_album,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 20))
						.addGap(20, 20, 20)
						.addGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(button_chanson, GroupLayout.PREFERRED_SIZE, 0, 20).addComponent(
										chanson_label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 20))
						.addGap(20, 20, 20)
						.addGroup(gauche_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(joueRecemment_label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												20)
										.addComponent(button_joueRecemment, GroupLayout.PREFERRED_SIZE, 0, 20))
						.addContainerGap(171, 171)));
		// nouveau_play_list.setText("Nouveau Playlist");
		GroupLayout gauche_au_bas_group = new GroupLayout(gauche_au_bas_jpanel);
		creer_playlist_jpenl();
		// ******************
		GroupLayout gauche_group = new GroupLayout(gauche_jpanel);
		gauche_jpanel.setLayout(gauche_group);
		gauche_group.setHorizontalGroup(gauche_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.LEADING, gauche_group.createSequentialGroup().addGap(5, 5, 5)
						.addGroup(gauche_group.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(gauche_group.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(gauche_en_haut_jpanel, GroupLayout.Alignment.TRAILING,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(gauche_au_bas_jpanel, GroupLayout.Alignment.CENTER,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gauche_group.createSequentialGroup().addGap(8, 8, 8)
										.addGroup(gauche_group.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(ligne_separe_droite))
										.addContainerGap()))));
		gauche_group.setVerticalGroup(gauche_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(gauche_group.createSequentialGroup()
						.addComponent(gauche_en_haut_jpanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(ligne_separe_droite, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(gauche_au_bas_jpanel,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));

		jSplitPane.setLeftComponent(gauche_jpanel);
		artiste_jpanel(donnee.getMusique());
		precedent.setToolTipText("precedent");
		precedent.addActionListener(this);
		precedent.setBorder(null);
		precedent.setBackground(new Color(238, 238, 238));
		setIcon("precedent.png", precedent);

		jouer.setToolTipText("jouer"); // jouer
		// jouer.setText("jouer");
		jouer.addActionListener(this);
		jouer.setBorder(null);
		jouer.setBackground(new Color(238, 238, 238));
		setIcon("emettre.png", jouer);

		suivant.setToolTipText("suivant");
		suivant.addActionListener(this);
		suivant.setBorder(null);
		suivant.setBackground(new Color(238, 238, 238));
		setIcon("suivant.png", suivant);

		repeter.setToolTipText("repeter");
		repeter.addActionListener(this);
		repeter.setBorder(null);
		repeter.setBackground(new Color(238, 238, 238));
		setIcon("repeter.png", repeter);
		GroupLayout droite_au_bas_group = new GroupLayout(droite_au_bas_jpanel);
		droite_au_bas_jpanel.setLayout(droite_au_bas_group);
		droite_au_bas_group.setHorizontalGroup(droite_au_bas_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(droite_au_bas_group.createSequentialGroup().addGap(5, 5, 5).addGroup(droite_au_bas_group
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jSlider_progress, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
						.addGroup(droite_au_bas_group.createSequentialGroup().addComponent(precedent).addGap(10, 10, 10)
								.addComponent(jouer).addGap(10, 10, 10).addComponent(suivant).addGap(10, 10, 10)
								.addComponent(repeter).addGap(10, 10, 10)))
						.addContainerGap(10, Short.MAX_VALUE)));
		droite_au_bas_group.setVerticalGroup(droite_au_bas_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(droite_au_bas_group.createSequentialGroup()
						.addComponent(jSlider_progress, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
						.addGap(10, 10, 10)
						.addGroup(droite_au_bas_group.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(precedent).addGap(10, 10, 10).addComponent(jouer).addGap(10, 10, 10)
								.addComponent(suivant).addGap(10, 10, 10).addComponent(repeter).addGap(10, 10, 10))
						.addContainerGap(10, Short.MAX_VALUE)));

		GroupLayout jPanel2Layout = new GroupLayout(droite_jpanel);
		droite_jpanel.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(jPanel2Layout.createSequentialGroup().addGap(5, 5, 5)
										.addComponent(droite_au_bas_jpanel, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(10, Short.MAX_VALUE))
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(droite_en_haut_jpanel, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(ligne_separe_gauche))
										.addContainerGap()))));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(droite_en_haut_jpanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(5, 5, 5)
						.addComponent(ligne_separe_gauche, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(droite_au_bas_jpanel,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(10, Short.MAX_VALUE)));

		jSplitPane.setRightComponent(droite_jpanel);

		chercher.setText("Chercher");

		GroupLayout chercher_group = new GroupLayout(chercher_jpanel);
		chercher_jpanel.setLayout(chercher_group);
		chercher_group.setHorizontalGroup(chercher_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(chercher_group.createSequentialGroup().addComponent(chercher)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(chercher_jtexfeilde, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));
		chercher_group.setVerticalGroup(chercher_group.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				chercher_group.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(chercher_group.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chercher).addComponent(chercher_jtexfeilde, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));

		// jToolBar2.setRollover(true);
		// JMenuBar menu_bar=new JMenuBar();
		JMenu menu = new JMenu();
		menu.setToolTipText("Ouvrir");
		setIcon("ouvrir.png", menu);
		JMenuItem fichier_menu_item = new JMenuItem("fichier");
		setIcon("fichier.png", fichier_menu_item);
		fichier_menu_item.addActionListener(this);
		JMenuItem repertoire_menu_item = new JMenuItem("repertoire");
		setIcon("repertoire.png", repertoire_menu_item);
		repertoire_menu_item.addActionListener(this);
		JMenuItem quitter_menu_item = new JMenuItem("quitter");
		setIcon("quitter.png", quitter_menu_item);
		quitter_menu_item.addActionListener(this);

		menu.add(fichier_menu_item);
		menu.add(repertoire_menu_item);
		menu.add(quitter_menu_item);
		// menu_bar.add(menu);
		menu_bar.add(menu);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(20, 20, 20)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(menu_bar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(chercher_jpanel, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jSplitPane)))
						.addContainerGap(100, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(10, 10, 10)
						.addComponent(menu_bar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(chercher_jpanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jSplitPane,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(100, Short.MAX_VALUE)));

		this.setSize(1080, 800);
		this.setLocation(300, 100);
		this.setVisible(true);
		this.setBackground(Color.GREEN);
		this.setIconImage(set_image_icon(path_image + "images/music-icon.png").getImage());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	
	public void artiste_jpanel(List<Musique> musiques_) //methode pour afficher un jpanel contenant les artistes des chansons de la liste de lecture
        {
		droite_en_haut_jpanel.removeAll(); // effacer le contenu précedent du jpanel pour le remplacer avec le jpanel des artistes
		selection_jpanel = constante.ARTISTE; 
		JPanel artiste_jpanel = new JPanel();
		jSplitPane_artist = new JScrollPane(artiste_jpanel); // fournit un view scrolable 
		// musiques = donnee.getMusique();
		List<String> list = new ArrayList<>(); // créer une liste 
		// int rows = 0;
		
                /**
                 creer pour chaque chanson de la liste de musique un label et un bouton pour l'artiste de la chanson  
                 **/
                if (musiques_ != null && musiques_.size() > 0) 
                {
			// rows = musiques.size();// / rangs + 1;
			Font font = new Font("Times New Roman", Font.ITALIC, 20);
			JLabel jb = null;
			JButton jbutton = null;
			for (int i = 0; i < musiques_.size(); i++) {
				jb = new JLabel();
				String artiste = musiques_.get(i).getArtiste();
				if ("inconnu".equals(artiste)) {
					jb.setToolTipText(musiques_.get(i).getArtiste());
					jb.setIcon(musiques_.get(i).getImg());
					artiste_jpanel.add(jb);
					jb.addMouseListener(new MouseListener() {
						@Override
						public void mouseReleased(MouseEvent e) {}
						@Override
						public void mousePressed(MouseEvent e) {}
						@Override
						public void mouseExited(MouseEvent e) {}
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseClicked(MouseEvent e) {
							JLabel obj = (JLabel) e.getSource();
							String titre = obj.getToolTipText();
							Donnee ablbum_donnee = operation.chercher_donnes(constante.ARTISTE, titre, donnee);
							if (ablbum_donnee != null) {
								chanson_jpanel(ablbum_donnee.getMusique());
							}
						}
					});
					jbutton = new JButton(musiques_.get(i).getArtiste());
					jbutton.setToolTipText("Consulter");
					jbutton.setBackground(new Color(238, 238, 238));
					jbutton.setFont(font);
					jbutton.setBorder(null);
					jbutton.addActionListener(this);
					artiste_jpanel.add(jbutton);
					list.add(artiste);
				} else {
					if (list == null || !list.contains(artiste)) {
						jb.setToolTipText(musiques_.get(i).getArtiste());
						jb.setIcon(musiques_.get(i).getImg());
						jb.addMouseListener(new MouseListener() {
							@Override
							public void mouseReleased(MouseEvent e) {
							}

							@Override
							public void mousePressed(MouseEvent e) {
							}

							@Override
							public void mouseExited(MouseEvent e) {
							}

							@Override
							public void mouseEntered(MouseEvent e) {
							}

							@Override
							public void mouseClicked(MouseEvent e) {
								JLabel obj = (JLabel) e.getSource();
								String titre = obj.getToolTipText();
								Donnee ablbum_donnee = operation.chercher_donnes(constante.ARTISTE, titre, donnee);
								if (ablbum_donnee != null) {
									chanson_jpanel(ablbum_donnee.getMusique());
								}
							}
						});
						artiste_jpanel.add(jb);
						jbutton = new JButton(musiques_.get(i).getArtiste());
						jbutton.setToolTipText("Consulter");
						jbutton.setBackground(new Color(238, 238, 238));
						jbutton.setFont(font);
						jbutton.setBorder(null);
						jbutton.addActionListener(this);
						artiste_jpanel.add(jbutton);
						list.add(artiste);
					}
				}
			}
		}

		int rows = (list.size() == 0) ? 0 : list.size();
		artiste_jpanel.setLayout(new GridLayout(rows, 2, 20, 3));
		droite_en_haut_jpanel.setLayout(new GridLayout(rows, 2, 20, 3));
		droite_en_haut_jpanel.add(jSplitPane_artist);
		creer_playlist_jpenl(); 
	}

	
	public void album_jpanel(List<Musique> musiques_) //méthode pour afficher un jpanel contenant les albums des chansons de la liste de lecture
        {
		droite_en_haut_jpanel.removeAll(); // effacer le contenu précedent du jpanel pour le remplacer avec le jpanel des albums
		selection_jpanel = constante.ALBUM; 
		JPanel album_jpanel = new JPanel();
		jSplitPane_album = new JScrollPane(album_jpanel);
		// musiques = donnee.getMusique();
		List<String> list = new ArrayList<>();
		int rows = 0; // nombre de lignes pour affichage des albums
		int rangs = 10; // // nombre de colonnes pour affichage des albums
                
		if (musiques_ != null && musiques_.size() > 0)  
                {
			JLabel jb = null;
			for (int i = 0; i < musiques_.size(); i++) //Parcourir la liste des musiques
                        {
				String album = musiques_.get(i).getAlbum();
				if (list == null || !list.contains(album)) {
					jb = new JLabel();
					jb.setToolTipText(musiques_.get(i).getAlbum()); // initialiser les labels avec les noms des albums
					jb.setIcon(musiques_.get(i).getImg()); //récupperer les images des albums
					album_jpanel.add(jb);
					jb.addMouseListener(new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
						}

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseExited(MouseEvent e) {
						}

						@Override
						public void mouseEntered(MouseEvent e) {
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							JLabel obj = (JLabel) e.getSource();
							String titre = obj.getToolTipText();
							Donnee ablbum_donnee = operation.chercher_donnes(constante.ALBUM, titre, donnee);
							if (ablbum_donnee != null) {
								chanson_jpanel(ablbum_donnee.getMusique());
							}

						}
					});
					list.add(album);
				}

			}
		}
		rows = list.size() / rangs + 1;
		album_jpanel.setLayout(new GridLayout(rows, rangs, 1, 5));
		droite_en_haut_jpanel.setLayout(new GridLayout(rows, rangs, 20, 3));
		droite_en_haut_jpanel.add(jSplitPane_album);
	 	creer_playlist_jpenl();
	}

	public void chanson_jpanel(List<Musique> musiques_) //Affichages des chansons
        {
		droite_en_haut_jpanel.removeAll();
		selection_jpanel =constante.CHANSON;
		createPopupMenu();
		String[] dimention_table = new String[] { "nom", "album", "artiste", "longueur", "nombre d'écoute", "emplacement",
				"id" };
		if (donnee != null) {
			musiques = donnee.getMusique(); //recuperer la liste des chansons
		}
		Object[][] object = null;
		if (chanson != null) {
			object = chanson.init_chansons(musiques_, dimention_table.length);  // récupperer toutes les chansons avec tous leur infos; pour chaque chnason son titre,artiste,album,emplacement....
		} else {
			object = new Object[1][1];
		}

		if (musiques_ != null) {
			nombreChansons = musiques_.size(); //nombres de chansons de la liste des musiques
		}
		DefaultTableModel jtable_model = new DefaultTableModel(object, dimention_table)  {
			// interdir d'éditer la table
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		 
		jTable = new JTable(jtable_model);
		hiddenColumn(5, jTable);
		hiddenColumn(6, jTable);
		// jTable1.setEnabled(false);
		jTable.addMouseListener(this);
		jScrollPane.setViewportView(jTable);

		GroupLayout droite_en_haut_group = new GroupLayout(droite_en_haut_jpanel);
		droite_en_haut_jpanel.setLayout(droite_en_haut_group);
		droite_en_haut_group.setHorizontalGroup(
				droite_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(droite_en_haut_group
						.createSequentialGroup().addContainerGap().addComponent(jScrollPane).addContainerGap()));
		droite_en_haut_group.setVerticalGroup(droite_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(droite_en_haut_group.createSequentialGroup()
						.addGap(29, 29, 29).addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(35, Short.MAX_VALUE)));
		 creer_playlist_jpenl();
	}

	public void playlist_jpanel() 
        {
		selection_jpanel = "Playlists";
		String[] dimention_table = new String[] { "nom", "album", "artiste", "longueur", "nombre d'écoute", "emplacement",
				"id" };
		Object[][] object = chanson.init_playlist(list_musique_nouvelle, dimention_table.length); //récupperer toutes les chansons avec tous leur infos; pour chaque chnason son titre,artiste,album,emplacement....
		DefaultTableModel jtable_model = new DefaultTableModel(object, dimention_table) {
			// interdir d'éditer la table
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jTable = new JTable(jtable_model);
		hiddenColumn(5, jTable);
		hiddenColumn(6, jTable);
		//jTable.setEnabled(false);
		jTable.addMouseListener(this);
		jScrollPane.setViewportView(jTable);

		GroupLayout droite_en_haut_group = new GroupLayout(droite_en_haut_jpanel);
		droite_en_haut_jpanel.setLayout(droite_en_haut_group);
		droite_en_haut_group.setHorizontalGroup(
				droite_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(droite_en_haut_group
						.createSequentialGroup().addContainerGap().addComponent(jScrollPane).addContainerGap()));
		droite_en_haut_group.setVerticalGroup(droite_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(droite_en_haut_group.createSequentialGroup()
						.addGap(29, 29, 29).addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(35, Short.MAX_VALUE)));
		 creer_playlist_jpenl();
	}

	public void _chanson_playlist_jpanel(PlayList _crerlist_) // pour afficher les chansons d'une playlist
        {
		// createPopupMenu();
		List<Musique> collection_paylist_ = new ArrayList<>();
		String[] dimention_table = new String[] { "nom", "album", "artiste", "longueur", "nombre d'écoute", "emplacement",
				"id" };
		String collection = _crerlist_.getCollection();
		String[] collections = collection.split(":");
		for (int i = 0; i < collections.length; i++) { // ajouter les chansons choisis à la playlist
			if (!"".equals(collections[i])) {
				collection_paylist_.add(musiques.get(Integer.valueOf(collections[i]))); //
			}
		}
		Object[][] object = chanson.init_chansons(collection_paylist_, dimention_table.length); // récupperer les chansons ajoutées à la playlist avec tous leur infos; pour chaque chnason son titre,artiste,album,emplacement....
		if (musiques != null) {
			nombreChansons = musiques.size();
		}
		DefaultTableModel jtable_model = new DefaultTableModel(object, dimention_table) {
			// interdi d'editer la table
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jTable = new JTable(jtable_model);
		hiddenColumn(5, jTable);
		hiddenColumn(6, jTable);
		jTable.addMouseListener(this);
		jScrollPane.setViewportView(jTable);

		GroupLayout droite_en_haut_group = new GroupLayout(droite_en_haut_jpanel);
		droite_en_haut_jpanel.setLayout(droite_en_haut_group);
		droite_en_haut_group.setHorizontalGroup(
				droite_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(droite_en_haut_group
						.createSequentialGroup().addContainerGap().addComponent(jScrollPane).addContainerGap()));
		droite_en_haut_group.setVerticalGroup(droite_en_haut_group.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(droite_en_haut_group.createSequentialGroup()
						.addGap(29, 29, 29).addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(35, Short.MAX_VALUE)));
		 creer_playlist_jpenl();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		new LecteurMusiqueGUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
        {
		Object action_object = e.getSource(); //recupperer l'objet sur lequel l'action a été faite
		String action = null;
		if (action_object instanceof JButton) { //l'objet est un bouton
			JButton jbutton = (JButton) action_object; 
			action = jbutton.getToolTipText();
			if ("Artiste".equals(action)) { //le bouton artiste
				artiste_jpanel(musiques); // recupperer les chansons selon le nom de l'artiste
				this.validate();
			} else if ("Album".equals(action)) {//le bouton album
				album_jpanel(musiques);// recupperer les chansons selon le nom de l'artiste
				this.validate();
			} else if ("Chanson".equals(action)) {//le bouton chanson
				chanson_jpanel(musiques);// recupperer les chansons selon le nom de l'artiste
				jTable.setRowSelectionInterval(row, row);
				this.validate();
			} else if ("Jouees recemment".trim().equals(action.trim())) {//le bouton chanson Joue recemment
				playlist_jpanel(); //afficher les chansons jouées 
				this.validate();

			} else if ("precedent".equals(action)) {
				if (timer == null) {
					timer = new Timer(); // créer un timer 
				} else {
					timer.cancel();
					timer = new Timer(); // créer un timer
				}
				effacer_progress(); // effacer l'indicateur du progrés de la chanson
				if (chanson != null) {
					chanson.stop();//arreter le thread
					row = row - 1; //reculer vers id de la chanson precedente
					if (row < 0) {
						row = row + 1;
					}
					try {
						chanson = chanson.obtenir_musique_par_id(row, musiques);//recupperer la chanson de l'in row à partire de la liste musiques
						int longure_time = Integer.valueOf(chanson.getLongueur());//obtenir la durée de la chanson
						String titre = chanson.getTitre();//obtenir le titre de la chanson
						musique_Jouee_enCours = new MusiqueJoueeActuellement();//mise à jour de la chanson jouée actuellement 
						musique_Jouee_enCours.setId("" + row);//mise à jour de la chanson jouée actuellement 
						musique_Jouee_enCours.setNomChansonActuelle(titre);//mise à jour de la chanson jouée actuellement 
						list_musique_nouvelle.add(chanson);//mise à jour de liste de lecture
						listedeLecture_nouvelle.setMusique(list_musique_nouvelle);//mise à jour de liste de lecture
						listedeLecture_nouvelle.setMusique_jouee_en_cours(musique_Jouee_enCours);//mise à jour de liste de lecture
						operation.update_xlm(donnee, listedeLecture_nouvelle);//mise à jour du fichier xml
						chanson.start();//commencer le thread de lecture de la chanson
						progess(longure_time);// commencer l'indicateur de la barre du progrés de la chanson
						timer.schedule(new Task(longure_time, row, nombreChansons), 0, 1000);
						jTable.setRowSelectionInterval(row, row);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Pas de musique trouvé!", constante.ERROR,
								JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
					}
				}
			} else if ("jouer".equals(action)) //jouer une chanson
             {
                    if (is_repeter) { //option de répetition
					if (timer != null) {
						timer.cancel();
					}
					effacer_progress();// effacer l'indicateur de la barre du progrés de la chanson
					chanson = chanson.obtenir_musique_par_id(row, musiques);
					longeur_time = Integer.valueOf(chanson.getLongueur());
					jouer.setToolTipText("arretre");
					setIcon("arretre.png", jouer);
					musique_Jouee_enCours = new MusiqueJoueeActuellement();
					musique_Jouee_enCours.setId(chanson.getId());
					musique_Jouee_enCours.setNomChansonActuelle(chanson.getTitre());
					listedeLecture_nouvelle.setMusique(list_musique_nouvelle);
					listedeLecture_nouvelle.setMusique_jouee_en_cours(musique_Jouee_enCours);
					operation.update_xlm(donnee, listedeLecture_nouvelle);
					list_musique_nouvelle.add(chanson);
					chanson.start();
					progess(longeur_time);
				} else {

					if (timer == null) {
						timer = new Timer();
					} else {
						timer.cancel();
						timer = new Timer();
					}
					if (chanson != null) {
						chanson.stop();
						chanson = chanson.obtenir_musique_par_id(row, musiques);
						try {
							String titre = chanson.getTitre();
							longeur_time = Integer.valueOf(chanson.getLongueur());
							list_musique_nouvelle.add(chanson);
							jouer.setToolTipText("arretre"); //remplacer le bouton play par le bouton arreter aprés le clic
							setIcon("arretre.png", jouer);//remplacer l'icon du bouton play par l'icon du bouton arreter aprés le clic
							musique_Jouee_enCours = new MusiqueJoueeActuellement();
							musique_Jouee_enCours.setId("" + row);
							musique_Jouee_enCours.setNomChansonActuelle(titre);
							listedeLecture_nouvelle.setMusique(list_musique_nouvelle);
							listedeLecture_nouvelle.setMusique_jouee_en_cours(musique_Jouee_enCours);
							operation.update_xlm(donnee, listedeLecture_nouvelle);
							chanson.start();
							progess(longeur_time);
							timer.schedule(new Task(longeur_time, row, nombreChansons), 0, 1000);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "pas de musique trouvé!", constante.ERROR,
									JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
						}
					}
				}

			} else if ("arretre".equals(action)) {//arreter la chanson
				if (timer != null) {
					timer.cancel();
				}
				effacer_progress();
				jouer.setToolTipText("jouer");//remplacer le bouton play par le bouton arreter aprés le clic
				setIcon("emettre.png", jouer);//remplacer l'icon du bouton play par l'icon du bouton arreter aprés le clic
				chanson.stop();
			} else if ("suivant".equals(action)) { // lire la chanson suivante
				if (timer == null) {
					timer = new Timer();
				} else {
					timer.cancel();
					timer = new Timer();
				}
				if (chanson != null) {
					chanson.stop();
					effacer_progress();
					row = row + 1;
					if (row > nombreChansons) {
						row = row - 1;
					}
					try {
						chanson = chanson.obtenir_musique_par_id(row, musiques);
						int longure_time = Integer.valueOf(chanson.getLongueur());
						String titre = chanson.getTitre();
						musique_Jouee_enCours = new MusiqueJoueeActuellement();
						musique_Jouee_enCours.setId("" + row);
						musique_Jouee_enCours.setNomChansonActuelle(titre);
						list_musique_nouvelle.add(chanson);
						listedeLecture_nouvelle.setMusique(list_musique_nouvelle);
						listedeLecture_nouvelle.setMusique_jouee_en_cours(musique_Jouee_enCours);
						operation.update_xlm(donnee, listedeLecture_nouvelle);
						chanson.start();
						progess(longure_time);
						timer.schedule(new Task(longure_time, row, nombreChansons), 0, 1000);
						jTable.setRowSelectionInterval(row, row);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "pas de musique trouvé!", constante.ERROR,
								JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
					}
				}
			} else if ("repeter".equals(action)) { // repeter les chansons
				if (timer != null) {
					timer.cancel();
				}
				repeter.setToolTipText("annuler");
				setIcon("ordre.png", repeter);
				is_repeter = true;
			} else if ("annuler".equals(action)) { // annuler la repetition des chansons
				if (timer != null) {
					timer.cancel();
				}
				repeter.setToolTipText("repeter");
				setIcon("repeter.png", repeter);
				is_repeter = false;
				timer = new Timer();
				int longure_time = Integer.valueOf(chanson.getLongueur());
				timer.schedule(new Task(longure_time, row, nombreChansons), 0, 1000);
			} else if ("Consulter".equals(action)) { //chercher un artiste sur wikipedia
				String titre = e.getActionCommand();
				html = new HTMLViewer(titre);
			} else if ("Chercher".equals(action)) { //chercher une chanson dans la liste de lecture
				String text = chercher_jtexfeilde.getText();
				if (constante.ARTISTE.equals(selection_jpanel)) { //chercher la chanson dans le panel artiste
					droite_en_haut_jpanel.removeAll();
					Donnee donne_chercher = operation.chercher_donnes(constante.ARTISTE, text, donnee); // (JButton) action_object.getToolTipText()="Album"
					artiste_jpanel(donne_chercher.getMusique());
					this.validate();
				}  else if (constante.CHANSON.equals(selection_jpanel)) { //chercher la chanson dans le panel chanson
					droite_en_haut_jpanel.removeAll();
					Donnee donne_chercher = operation.chercher_donnes(constante.CHANSON, text, donnee);
					chanson_jpanel(donne_chercher.getMusique());
					this.validate();
				} else if (constante.ALBUM.equals(selection_jpanel)) { //chercher la chanson dans le panel album
					droite_en_haut_jpanel.removeAll();
					Donnee donne_chercher = operation.chercher_donnes(constante.ALBUM, text, donnee);
					album_jpanel(donne_chercher.getMusique());
					this.validate();
				}

			}
		} else if (action_object instanceof JMenuItem) {
			JMenuItem jmenu_bar = (JMenuItem) action_object;
			action = jmenu_bar.getText();
			if ("fichier".equals(action)) { // ajouter des fichiers à lire
				System.out.println("fichier : " + action);
				JFileChooser jfc = new JFileChooser(); //creation d'un objet JFileChooser 
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//selectionner que les fichiers
				jfc.setMultiSelectionEnabled(true);
				jfc.showDialog(new JLabel(), "choisir");//afficher la boite de dialogue
				File[] files = jfc.getSelectedFiles(); // recupperer les fichiers selectinnés 
				fichier = new Fichier(); // créer un objet de type Fichier
				if (musiques != null && musiques.size() > 0) { //verfier s'il y a déja des chansons dans la liste de lecture
					id_numero = musiques.size();  //recupperer les nombre des chansons
				} 
				if (files != null && files.length > 0) {
					 musiques = fichier.ouvrir_par_fichiers(files, donnee, id_numero); // recuperer le nouveau nombre des chansons
					 id_numero=musiques.size();
				} 
				droite_en_haut_jpanel.removeAll();
				artiste_jpanel(musiques);//afficher les chansons
				this.validate();
			} else if ("repertoire".equals(action)) { //ajouter un repertoir à lire
				JFileChooser jfc = new JFileChooser();//creation d'un objet JFileChooser 
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //selectionner que les repertoires
				jfc.showDialog(new JLabel(), "choisir");//afficher la boite de dialogue
				File file = jfc.getSelectedFile(); // recupperer le repertoire selectinné
				fichier = new Fichier();// créer un objet de type Fichier
				if (musiques != null && musiques.size() > 0) { //verfier s'il y a déja des chansons dans la liste de lecture
					id_numero = musiques.size(); //recupperer les nombre des chansons
				} 
				if (file.isDirectory()) {
					String chemin = file.getAbsolutePath(); //recupperer l'mplacement absolu du repertoire
					String os = System.getProperty("os.name");
					String nouvelle_repertoirs="";
					if(os.toLowerCase().startsWith("win"))
					  {
					    nouvelle_repertoirs = chemin.replace("\\", "\\\\");  //sous la system windows
					  }else if(os.toLowerCase().startsWith("lin"))
					  {
					    	 nouvelle_repertoirs =chemin;  //sous la system windows
					  }

					musiques = fichier.ouvrir_par_repertoire(nouvelle_repertoirs, donnee, id_numero); // recuperer le nouveau nombre des chansons
					 id_numero=musiques.size();
				} else if (file.isFile()) {
					
				}
				// System.out.println(jfc.getSelectedFile().getName());
				droite_en_haut_jpanel.removeAll();
				artiste_jpanel(musiques); // ajouter les chansons dans le panel artiste
				this.validate();
			} else if ("supprimer".equals(action)) { //supprimer une chanson
				int _selection_=JOptionPane.showConfirmDialog(this,"vous êtes sûr de la supprimer ?","comfirmer",JOptionPane.YES_NO_OPTION); 
				// supprimer la chanson
				if(_selection_==JOptionPane.YES_OPTION)
				{
					row = jTable.getSelectedRow();
					Musique supprimer_musique = musiques.get(row); //recupperer l'indice de la musique dans la liste musiques
					if (list_musique_nouvelle == null || !list_musique_nouvelle.contains(supprimer_musique)) {
						list_musique_nouvelle.add(supprimer_musique);//ajouter la chanson à supprimer dans une liste temporaire
					}
					listedeLecture_nouvelle.setMusique(list_musique_nouvelle);
					donnee = operation.update_xml(donnee, listedeLecture_nouvelle); //supprimer la chanson de donnee
					musiques = donnee.getMusique();//recuppere la nouvelle liste de lecture
					list_musique_nouvelle.remove(supprimer_musique); //supprimer la chanson de la liste temporaire
					droite_en_haut_jpanel.removeAll();//effacer le contenu précedent
					chanson_jpanel(donnee.getMusique());//afficher la nouvelle liste de lecture
					if (row > musiques.size()) {
						row = musiques.size();
					}
					try {
						jTable.setRowSelectionInterval(row, row);
						this.validate();
					} catch (Exception e2) {
						this.validate();
					}

				}else if(_selection_==JOptionPane.NO_OPTION)
				{
					return;
				}
				
			} else if ("quitter".equals(action)) { //arreter l'applicatiob
				System.exit(0);
			} else {
                                //ajouter une chanson à une playlist
				List<PlayList> _crer_list_ = donnee.getPlaylist(); //recupperer la liste des playlists
				row = jTable.getSelectedRow();
				for (PlayList _crer_ : _crer_list_) {
					String titre = _crer_.getNomPlaylist();//recupper les noms des playlists
					if (titre.equals(action)) {
						// String value = (String) jTable1.getValueAt(row, 4);
						String collection = _crer_.getCollection() + ":" + row; // collection contient les ids des chansons appartenant à une playlist donnée
						_crer_.setCollection(collection);
					}
				}
				operation.ecrire_info_musique_en_xml(donnee); //mise à jour du fichier xml
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int clickTimes = e.getClickCount();
		//row = jTable.getSelectedRow();
		String id=jTable.getValueAt(jTable.getSelectedRow(), 6).toString();
		row=Integer.valueOf(id);
		System.out.println(id+"--------------");
		if (clickTimes == 2) {
			chanson.stop(); //arreter la chanson jouée
			effacer_progress(); // effacer l'indicateur du progrés de la barre du progrés de durée de la chanson
			if (timer == null) {
				timer = new Timer(); // creer un timer
			} else {
				timer.cancel();
				timer = new Timer();
			}
			jouer.setToolTipText("arretre"); //changer le bouton de jouer avec le bouton arreter
			setIcon("arretre.png", jouer);//changer l'icon du bouton de jouer avec l'icon du bouton arreter
			jTable.setSelectionBackground(Color.lightGray);
			chanson = chanson.obtenir_musique_par_id(row, musiques);//indiquer quel chanson à lire
			musique_Jouee_enCours = new MusiqueJoueeActuellement();//mise à jour de la chanson jouée en cours
			musique_Jouee_enCours.setId("" + row);//mise à jour de la chanson jouée en cours
			musique_Jouee_enCours.setNomChansonActuelle(chanson.getTitre());//mise à jour de la chanson jouée en cours
			listedeLecture_nouvelle.setMusique(list_musique_nouvelle);//mise à jour de la liste de lecture
			listedeLecture_nouvelle.setMusique_jouee_en_cours(musique_Jouee_enCours);//mise à jour de la  liste de lecture
			operation.update_xlm(donnee, listedeLecture_nouvelle);// mise à jour du fichier xml
			longeur_time = Integer.valueOf(chanson.getLongueur());//obtenir la durée de la chanson
			list_musique_nouvelle.add(chanson);//ajouter la chanson à la liste de lecture
			chanson.start();//jouer la chanson
			progess(longeur_time);// démarrer l'indicateur du progrés de la barre du progrés de durée de la chanson
			timer.schedule(new Task(longeur_time, row, donnee.getMusique().size()), 0, 1000);
			chanson_jpanel(musiques);
			this.validate();
		} else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			int focusedRowIndex = jTable.rowAtPoint(e.getPoint());
			if (focusedRowIndex == -1) {
				return;
			}
			jTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
			m_popupMenu.show(jTable, e.getX(), e.getY());
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * cacher un colum
	 *
	 * @param columnIndex
	 * @param table
	 */
	public void hiddenColumn(int columnIndex, JTable table) { //chacher une colone d'un objet JTable
		TableColumnModel tcm = table.getColumnModel();
		TableColumn tc = tcm.getColumn(columnIndex);
		tc.setWidth(0);
		tc.setPreferredWidth(0);
		tc.setMaxWidth(0);
		tc.setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(columnIndex).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(columnIndex).setMinWidth(0);
	}

	/**
	 * set immage
	 */
	public ImageIcon set_image_icon(String nom_image) { //intialiser l'image d'une chanson
		return new ImageIcon(nom_image);
	}

	/**
	 * modifer le taille de l'image du button
	 *
	 * @param file
	 * @param iconButton
	 */
	@SuppressWarnings("static-access")
	public void setIcon(String file, Object object) {
		ImageIcon icon = new ImageIcon(path_image + "images/" + file);
		Image temp = icon.getImage().getScaledInstance(30, 30, icon.getImage().SCALE_DEFAULT);
		icon = new ImageIcon(temp);
		if (object instanceof JButton) {
			((JButton) object).setIcon(icon);
		} else if (object instanceof JMenu) {
			((JMenu) object).setIcon(icon);
		} else if (object instanceof JMenuItem) {
			((JMenuItem) object).setIcon(icon);
		}
	}

	/**
	 * lancer le progrés
	 * 
	 * @param longeur_time
	 */
	public void progess(int longeur_time) {
		flag = false;
		t = new Thread() {
			public void run() {
				while (!flag) {
					for (int i = 0; i <= longeur_time; i++) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						jSlider_progress.setValue(i);
					}
					flag = true;
					jSlider_progress.setValue(0);
				}

			}

		};
		t.start();
	}

	/**
	 * arreter le progrés
	 * 
	 * @param longeur_time
	 */
	@SuppressWarnings("deprecation")
	public void effacer_progress() {
		flag = true;
		if (t != null) {
			t.stop();
		}
	}

	/**
	 * lire la chanson suivante automatiquement
	 * 
	 * @param longeur_time
	 */
	public void suvant_automatique() {
		flag = false;
		Thread t = new Thread() {
			public void run() {
				while (!flag) {
					for (int i = 0; i <= longeur_time; i++) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(i);
						jSlider_progress.setValue(i);
					}
					flag = true;
					jSlider_progress.setValue(0);
				}

			}

		};
		t.start();
	}

	public void creer_playlist_jpenl() { // créer un jpanel des playlist
		gauche_au_bas_jpanel.removeAll(); // effacer le contenu précedent de panel
		List<PlayList> list = donnee.getPlaylist()==null ? new ArrayList<>() : donnee.getPlaylist() ;// recupperer les playlists
		Font font = new Font("Times New Roman", Font.ITALIC, 15);
		int rows = 1;
		int colos = 2;
		// la nombre des chanson qui ont emis
		List<Musique> list_mettre_plus_fois = chanson.les_plus_jouees(musiques);//recupperer le chansons les plus jouées
		if (list_mettre_plus_fois != null && list_mettre_plus_fois.size() > 0) {
			PlayList play_list_ = new PlayList(); //créer une nouvelle playlist
			String favori_ = "";
			for (Musique musique_favori : list_mettre_plus_fois) {
				favori_ = favori_ + ":" + musique_favori.getId(); //recupperer les ids des chansons les plus jouées
			}
			if (list == null || list.size()==0) { //au cas où y a pas encore de playlist créer
				play_list_.setCollection(favori_);//initialiser la collections de la playlist par des ids des chansons les plus jouées
            	play_list_.setNomPlaylist(constante.MES_FAVORIS);//initialiser le nom de la playlist des chansons les plus jouées
				play_list_.setId(list.size() + "");//initialiser l'id de la playlist 
				list.add(play_list_);//ajouter la playlist à la liste des playlists
				operation.ecrire_info_musique_en_xml(donnee);//mise à jours du fichier xml
			}else if (list.size() > 0) {
				ArrayList<String> list_titre_ = new ArrayList<>();
				for (PlayList _play_list_ : list) { 
					String titre = _play_list_.getNomPlaylist();//obtenir le nom de la playlist
					if (constante.MES_FAVORIS.equals(titre)) { 
						list_titre_.add(titre);
						// mettre à jour la playlist des favoris
						_play_list_.setCollection(favori_);
						operation.ecrire_info_musique_en_xml(donnee);//mise à jours du fichier xml
					}

				}
				if (list_titre_.isEmpty()) {
					play_list_.setCollection(favori_);
					play_list_.setNomPlaylist(constante.MES_FAVORIS);
					play_list_.setId(list.size() + "");
					list.add(play_list_);
					operation.ecrire_info_musique_en_xml(donnee);//mise à jours du fichier xml
				}

			}
			rows+=1;
		} else {
			if (list != null && !list.isEmpty()) {
				ArrayList<String> list_titre_ = new ArrayList<>();
				for (PlayList _play_list_ : list) {
					String titre = _play_list_.getNomPlaylist();
					if (constante.MES_FAVORIS.equals(titre)) {
						list_titre_.add(titre);
						list.remove(_play_list_);
                        // mettre à jour la playlist des favoris
						operation.ecrire_info_musique_en_xml(donnee);
						break;
					}

				}
			}

		}
                //creation des labels pour les playlists
		if (list != null && list.size() > 0) {
			JLabel nouveau_play_list = new JLabel();
			JButton creer_playlist = new JButton();//bouton de création d'une playlist
			nouveau_play_list.setText("Playlist");
			nouveau_play_list.setFont(font);
			creer_playlist.setToolTipText("plus");
			creer_playlist.setBorder(null);
			creer_playlist.setBackground(new Color(238, 238, 238));
			setIcon("creer.png", creer_playlist);//icon de création d'une playlist
			creer_playlist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String str = JOptionPane.showInputDialog(null, "nouvelle playlist");//boite de dialogue d'ajout du nom de la playlist
					if (!"".equals(str) && str!= null && "" != str) {
						try {
							if (exister_play_(list, str)) { //verifier la disponibilité du nom de la playlist
								int id = list.size();//obtenir le nombre des playlists de liste
								id += 1;//incrementer le nombre de playlists
								PlayList play_list_ = new PlayList();
								play_list_.setId("" + id);
								play_list_.setNomPlaylist(str);
								play_list_.setCollection("");
								donnee.getPlaylist().add(play_list_);//ajouter la nouvelle playlist à la liste des playlists
								operation.ecrire_info_musique_en_xml(donnee);//mise à jour du fichier xml
								gauche_au_bas_jpanel.removeAll();//supprimer le panel précedent
								creer_playlist_jpenl();//création du panel
								gauche_au_bas_jpanel.validate();
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "nom de la playlist ne peut pas être vide!", constante.ERROR,
									JOptionPane.ERROR_MESSAGE);
							return;
						}

					} else {

						JOptionPane.showMessageDialog(null, "nom de la playlist ne peut pas être vide!", constante.ERROR,
								JOptionPane.ERROR_MESSAGE);
						return;
					}

				}
			});
			gauche_au_bas_jpanel.add(nouveau_play_list);//mise à jour du panel
			gauche_au_bas_jpanel.add(creer_playlist);

			for (int i = 0; i < list.size(); i++) { //créer des labels pour les playlists
				PlayList _crerlist_ = list.get(i);
				JLabel _play_list_ = new JLabel();
				JButton _plaliste_ = new JButton();
				_play_list_.setText(_crerlist_.getNomPlaylist());
				_plaliste_.setBorder(null);
				_plaliste_.setToolTipText(_crerlist_.getId());
				_plaliste_.setBackground(new Color(238, 238, 238));
				_plaliste_.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton jb = (JButton) e.getSource();
						String id = jb.getToolTipText();
						int _selection_=JOptionPane.showConfirmDialog(gauche_au_bas_jpanel,"vous êtes sûr de la supprimer ?","comfirmer",JOptionPane.YES_NO_OPTION); 
						if(_selection_==JOptionPane.YES_OPTION)
						{
							try {
								 if(constante.MES_FAVORIS.equals(_crerlist_.getNomPlaylist())){
									 constante.IS_CREER_FAVORIS=false;
								 }
								 list.remove(list.get(Integer.valueOf(id)));
								 operation.ecrire_info_musique_en_xml(donnee);
							     gauche_au_bas_jpanel.removeAll();
							     creer_playlist_jpenl();
							     gauche_au_bas_jpanel.validate();
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, "échouer à supprimer!", constante.ERROR,
										JOptionPane.ERROR_MESSAGE);
								e2.printStackTrace();
							}
						}else if(_selection_==JOptionPane.NO_OPTION)
						{
							return;
						}
						
					}
				});
				_play_list_.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseClicked(MouseEvent e) {
						droite_en_haut_jpanel.removeAll();
						_chanson_playlist_jpanel(_crerlist_);
						gauche_au_bas_jpanel.removeAll();
						creer_playlist_jpenl();
						gauche_au_bas_jpanel.validate();
					}
				});
				setIcon("supprimer.png", _plaliste_);
				gauche_au_bas_jpanel.add(_play_list_);
				gauche_au_bas_jpanel.add(_plaliste_);
				rows += 1;
			}
			gauche_au_bas_jpanel.setLayout(new GridLayout(rows, colos, 15, 1));
		} else {
			JLabel nouveau_play_list = new JLabel();
			JButton creer_playlist = new JButton();
			nouveau_play_list.setText("Playlist");
			nouveau_play_list.setFont(font);
			creer_playlist.setToolTipText("plus");
			creer_playlist.setBorder(null);
			creer_playlist.setBackground(new Color(238, 238, 238));
			setIcon("creer.png", creer_playlist);
			creer_playlist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String str = JOptionPane.showInputDialog(null, "nouvelle playlist");
					if ("".equals(str.trim())) {
						JOptionPane.showMessageDialog(null, "nom de la playlist ne peut pas être vide!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					PlayList play_list_ = new PlayList();
					play_list_.setId("" + 0);
					play_list_.setCollection("");
					play_list_.setNomPlaylist(str);
					if (donnee.getPlaylist() == null) {
						List<PlayList> list_crer = new ArrayList<>();
						list_crer.add(play_list_);
						donnee.setPlaylist(list_crer);
					} else {
						donnee.getPlaylist().add(play_list_);
					}

					operation.ecrire_info_musique_en_xml(donnee);
					gauche_au_bas_jpanel.removeAll();
				    creer_playlist_jpenl();
				    gauche_au_bas_jpanel.validate();
				}
			});
			gauche_au_bas_jpanel.add(nouveau_play_list);
			gauche_au_bas_jpanel.add(creer_playlist);
			gauche_au_bas_jpanel.setLayout(new GridLayout(rows, colos, 15, 1));
		}
		gauche_au_bas_jpanel.repaint();
	}

	/**
	 * afficher un menu lors d'un clic droit 
	 */
	private void createPopupMenu() {
		m_popupMenu = new JPopupMenu(); //création du Popmenu
		JMenu supprimer_menu = new JMenu();//ajouter un menu
		supprimer_menu.setText("supprimer");
		JMenuItem supprimer = new JMenuItem();//ajouter un option au menu
		supprimer.setText("supprimer");
		JMenu ajouter_men = new JMenu();//ajouter un menu
		ajouter_men.setText("ajouter");
		List<PlayList> crer_list_ = donnee.getPlaylist();
		if (crer_list_ != null && crer_list_.size() > 0) { //ajouter les noms des playlists au option du menu
			for (PlayList crer_ : crer_list_) {
				JMenuItem ajouter_ = new JMenuItem();
				ajouter_.setText(crer_.getNomPlaylist());
				ajouter_men.add(ajouter_);
				ajouter_.addActionListener(this);
			}
		}
		supprimer_menu.add(supprimer);
		supprimer.addActionListener(this);
		m_popupMenu.add(supprimer_menu);//ajouter un option au Popmenu
		m_popupMenu.add(ajouter_men);//ajouter un option au Popmenu
	}

	class Task extends java.util.TimerTask {
		int row;
		int duree;
		int max_row;
		int i;

		Task(int longueur, int row, int max_row) { 
			this.row = row;
			this.duree = longueur;
			this.max_row = max_row;
		}

		@Override
		public void run() {
			if (i < longeur_time) {
				i += 1;
				System.out.println(i);
			} else {
				i = 0;
				effacer_progress();
				if (row + 1 > max_row) {
					row = 1;
					chanson.stop();
					chanson = chanson.obtenir_musique_par_id(row, musiques);
					longeur_time = Integer.valueOf(chanson.getLongueur());
					jTable.setRowSelectionInterval(row, row);
					jouer.setToolTipText("arretre");
					setIcon("arretre.png", jouer);
					musique_Jouee_enCours = new MusiqueJoueeActuellement();
					musique_Jouee_enCours.setId(chanson.getId());
					musique_Jouee_enCours.setNomChansonActuelle(chanson.getTitre());
					chanson.start();
					progess(longeur_time);
				} else {
					row += 1;
					chanson.stop();
					if (row > musiques.size()) {
						row = 0;
					}
					chanson = chanson.obtenir_musique_par_id(row, musiques);
					musique_Jouee_enCours = new MusiqueJoueeActuellement();
					musique_Jouee_enCours.setId(chanson.getId());
					musique_Jouee_enCours.setNomChansonActuelle(chanson.getTitre());
					longeur_time = Integer.valueOf(chanson.getLongueur());
					jTable.setRowSelectionInterval(row, row);
					jouer.setToolTipText("arretre");
					setIcon("arretre.png", jouer);
					chanson.start();
					progess(longeur_time);
				}
				listedeLecture_nouvelle.setMusique_jouee_en_cours(musique_Jouee_enCours);
				operation.update_xlm(donnee, listedeLecture_nouvelle);
			}
		}
	}

	/**
	 * verifier si le nom de la chanson existe déja 
	 * 
	 * @param crer_list
	 * @param _titre
	 * @return
	 * @throws Exception 
	 */
	public boolean exister_play_(List<PlayList> crer_list, String _titre) throws Exception {
		if("".equals(_titre.trim()))
		{
			 throw new Exception("nom de la playlist ne peut pas être vide!");
		}
		for (PlayList crerlist : crer_list) {
			String titre = crerlist.getNomPlaylist();
			if (_titre.equals(titre)) {
				JOptionPane.showMessageDialog(null, "le titre est déjà exsité", constante.ERROR, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
}
