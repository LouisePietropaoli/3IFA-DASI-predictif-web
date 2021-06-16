/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialisation;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Client;
import metier.data.Consultation;
import metier.data.Employe;
import metier.data.Medium;

/**
 *
 * @author rdesouza
 */
public class ConsultationSerialisation extends Serialisation{
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        JsonObject container = new JsonObject();
        
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        
        if (consultation != null)
        {
            JsonObject consultationJson = new JsonObject();
            consultationJson.addProperty("id", consultation.getId());
            consultationJson.addProperty("dateDemande", dateFormat.format(consultation.getDateDemande()));
            consultationJson.addProperty("dateHeureDebut", dateFormat.format(consultation.getDateHeureDebut()));
            consultationJson.addProperty("dateHeureFin", dateFormat.format(consultation.getDateHeureFin()));
            consultationJson.addProperty("statut", dateFormat.format(consultation.getStatut()));
            consultationJson.addProperty("commentaire", dateFormat.format(consultation.getCommentaire()));
            
            /* récupération des trois parties prenantes */
            Client client = consultation.getClient();
            Employe employe = consultation.getEmploye();
            Medium medium = consultation.getMedium();
            // Affichage provisoire
            consultationJson.addProperty("client", client.toString());
            consultationJson.addProperty("employe", employe.toString());
            consultationJson.addProperty("medium", medium.toString());
            container.add("consultation", consultationJson);
        }
        else
        {
            container.addProperty("erreur", true);
        }
    }
}
