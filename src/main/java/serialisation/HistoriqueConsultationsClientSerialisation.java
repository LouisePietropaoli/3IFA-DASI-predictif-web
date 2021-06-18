package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Consultation;
import static util.Utility.recupererTypeMedium;

/**
 * construit l'historique des consultations du client
 * sans la dispo des médiums associés
 */
public class HistoriqueConsultationsClientSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

         List<Consultation> historiqueConsultation = (List<Consultation>) request.getAttribute("historiqueConsultation");
         
         JsonArray jsonHistoriqueConsultation = new JsonArray();

        if (historiqueConsultation != null) {
             for (Consultation consultation : historiqueConsultation) {
                JsonObject jsonConsultation = new JsonObject();
                jsonConsultation.addProperty("id", consultation.getId());
                jsonConsultation.addProperty("commentaire", consultation.getCommentaire());
                jsonConsultation.addProperty("heureDebut", consultation.getDateHeureDebut() != null ? 
                        consultation.getDateHeureDebut().getDate()+
                        "/"+consultation.getDateHeureDebut().getMonth() +
                        "/"+consultation.getDateHeureDebut().getYear() + 
                        " " + consultation.getDateHeureDebut().getHours()+
                        "h" + consultation.getDateHeureDebut().getMinutes(): null);
                jsonConsultation.addProperty("heureFin", consultation.getDateHeureFin() != null ? 
                        consultation.getDateHeureFin().getDate()+
                        "/"+consultation.getDateHeureFin().getMonth() +
                        "/"+consultation.getDateHeureFin().getYear() + 
                        " " + consultation.getDateHeureFin().getHours()+
                        "h" + consultation.getDateHeureFin().getMinutes(): null);
                jsonConsultation.addProperty("dateDemande", consultation.getDateDemande() != null ? 
                        consultation.getDateDemande().getDate()+
                        "/"+consultation.getDateDemande().getMonth() +
                        "/"+consultation.getDateDemande().getYear() + 
                        " " + consultation.getDateDemande().getHours()+
                        "h" + consultation.getDateDemande().getMinutes(): null);
                jsonConsultation.addProperty("nomEmploye", consultation.getEmploye().getNom());
                jsonConsultation.addProperty("prenomEmploye", consultation.getEmploye().getPrenom());
                jsonConsultation.addProperty("nomMedium", consultation.getMedium().getDesignation());
                jsonConsultation.addProperty("typeMedium", recupererTypeMedium(consultation.getMedium()));
                jsonConsultation.addProperty("idMedium", consultation.getMedium().getId());
                jsonHistoriqueConsultation.add(jsonConsultation);
            }
            container.add("historiqueConsultation", jsonHistoriqueConsultation);
        }else{
             container.addProperty("erreur", true);
        }
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
