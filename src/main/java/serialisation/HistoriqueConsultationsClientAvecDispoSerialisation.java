package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Consultation;
import static util.Utility.recupererTypeMedium;

/**
 * construit l'historique des consultations du client
 * avec la dispo des médiums associés
 * Pour chaque médium, ajouter un attribut "estDisponible" de type booléen
 * gràace au tableau associatif de la requête
 */
public class HistoriqueConsultationsClientAvecDispoSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        List<Consultation> historiqueConsultation = (List<Consultation>) request.getAttribute("historiqueConsultation");
        Map<Long, Boolean> dispos = (Map<Long, Boolean> ) request.getAttribute("dispos");
         JsonArray jsonHistoriqueConsultation = new JsonArray();

        if (historiqueConsultation != null && dispos != null) {
             for (Consultation consultation : historiqueConsultation) {
                JsonObject jsonConsultation = new JsonObject();
                jsonConsultation.addProperty("id", consultation.getId()); 
                jsonConsultation.addProperty("heureDebut", consultation.getDateHeureDebut() != null ? 
                        consultation.getDateHeureDebut().getDate()+
                        "/"+ (consultation.getDateHeureDebut().getMonth()+ 1) +
                        "/"+(consultation.getDateHeureDebut().getYear()+1900) + 
                        " " + consultation.getDateHeureDebut().getHours()+
                        "h" + consultation.getDateHeureDebut().getMinutes(): null);
                jsonConsultation.addProperty("heureFin", consultation.getDateHeureFin() != null ? 
                        consultation.getDateHeureFin().getDate()+
                        "/"+(consultation.getDateHeureFin().getMonth()+1) +
                        "/"+(consultation.getDateHeureFin().getYear()+1900) + 
                        " " + consultation.getDateHeureFin().getHours()+
                        "h" + consultation.getDateHeureFin().getMinutes(): null);
                jsonConsultation.addProperty("dateDemande", consultation.getDateDemande() != null ? 
                        consultation.getDateDemande().getDate()+
                        "/"+(consultation.getDateDemande().getMonth()+1) +
                        "/"+(consultation.getDateDemande().getYear()+1900) + 
                        " " + consultation.getDateDemande().getHours()+
                        "h" + consultation.getDateDemande().getMinutes(): null);                jsonConsultation.addProperty("nomMedium", consultation.getMedium().getDesignation());
                jsonConsultation.addProperty("dispo", dispos.get(consultation.getId()));
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
