package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Client;
import metier.data.Consultation;
import metier.data.Medium;
import metier.enums.Statut;
import util.Utility;

/**
 * renvoie la consultation en cours avec son statut et toutes les infos du
 * client (dont profil astral) sauf son mot de passe L'historique client n'est
 * pas renvoyé. Renvoie un attribut consultation et client à null si aucune
 * consultation en cours
 */
public class InitialisationAccueilEmployeSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        Consultation consultationEnCours = (Consultation) request.getAttribute("consultation");
        if(consultationEnCours != null) {
            container.addProperty("consultationEnCours",true);
            container.addProperty("statut",consultationEnCours.getStatut().toString());
            
            Medium medium = consultationEnCours.getMedium(); 
            JsonObject jsonMedium = new JsonObject();
            jsonMedium.addProperty("id", medium.getId());
            jsonMedium.addProperty("designation", medium.getDesignation());
            jsonMedium.addProperty("type", Utility.recupererTypeMedium(medium));
            container.add("medium",jsonMedium);            

            Client client = consultationEnCours.getClient();
            JsonObject jsonClient = new JsonObject();
            jsonClient.addProperty("id", client.getId());
            jsonClient.addProperty("nom", client.getNom());
            jsonClient.addProperty("prenom", client.getPrenom());
            Date dateNaissance = client.getDateNaissance();
            jsonClient.addProperty("date_naissance", dateNaissance.getDate()+"/"+(dateNaissance.getMonth()+1)+"/"+dateNaissance.getYear());
            jsonClient.addProperty("adresse", client.getAdresse());
            jsonClient.addProperty("ville", client.getVille());
            jsonClient.addProperty("code_postal", client.getCodePostal());
            jsonClient.addProperty("mail", client.getEmail());
            jsonClient.addProperty("telephone", client.getTelephone());
            jsonClient.addProperty("signe_zodiaque", client.getSigneZodiaque());
            jsonClient.addProperty("signe_chinois", client.getSigneAstroChinois());
            jsonClient.addProperty("couleur_bonheur", client.getCouleurPorteBonheur());
            jsonClient.addProperty("animal_totem", client.getAnimalTotem());
            container.add("client",jsonClient);           
            
            String pattern = "dd-MM-yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                
            if(consultationEnCours.getStatut()==Statut.DEMANDEE) {
                String dateDemande = simpleDateFormat.format(consultationEnCours.getDateDemande());
                container.addProperty("heure_demande", dateDemande);
            }else if(consultationEnCours.getStatut()==Statut.EN_COURS) {
                String dateDebut = simpleDateFormat.format(consultationEnCours.getDateHeureDebut());
                container.addProperty("heure_debut", dateDebut);
            }
        }else{
            container.addProperty("consultationEnCours",false);
        }
         
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
