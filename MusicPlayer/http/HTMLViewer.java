package http;


import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;


/**
 * @author RABAH Abderrafii
 */ 

public class HTMLViewer extends JFrame implements HyperlinkListener      
{
   String artiste; // nom de l'artiste cherché 
   
   public HTMLViewer(String titre) // Créer un viewer d'une page HTML
   {
      setSize(640, 480);    
      setTitle(artiste);
     // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
      JEditorPane editorPane = new JEditorPane();  // créer un vavigateur WEB avec un JEditorPane en le faisant réagir aux clics sur le hyperliens
      
      JScrollPane scrollPane = new JScrollPane(editorPane);
      editorPane.setContentType("text/html"); // le context du JEditorPane est du type text/html
      editorPane.setEditable(false);
      editorPane.addHyperlinkListener(this);// ajouter un listener 
      titre=titre.replace(" ", "_");
     //  System.out.println(titre);;
      String path = "https://fr.wikipedia.org/wiki/"+titre; //chercher des infos sur artiste su wikipedia
      try
      {
         editorPane.setPage(path);
      }
      catch (IOException e)
      {
      }
      Container container = getContentPane();
      
      container.add(scrollPane, BorderLayout.CENTER);
      
   }
   public void hyperlinkUpdate(HyperlinkEvent e)  // méthode pour réagir aux clics sur le hyperliens
   {
      if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
      {
         JEditorPane pane = (JEditorPane) e.getSource();
         if (e instanceof HTMLFrameHyperlinkEvent)
         {
            HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
            HTMLDocument doc = (HTMLDocument) pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent(evt);
         }
         else
         {
            try
            {
               pane.setPage(e.getURL());
            }
            catch (Throwable t)
            {
               t.printStackTrace();
            }
         }
      }
   }
   
 
}