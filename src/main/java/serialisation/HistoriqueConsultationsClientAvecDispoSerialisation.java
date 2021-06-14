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
                jsonConsultation.addProperty("dateDemande", consultation.getDateDemande().toString());
                jsonConsultation.addProperty("heureDebut", consultation.getDateHeureDebut().toString());
                jsonConsultation.addProperty("heureFin", consultation.getDateHeureFin().toString());
                jsonConsultation.addProperty("nomMedium", consultation.getMedium().getDesignation());
                jsonConsultation.addProperty("dispo", dispos.get(consultation.getId()));
                jsonConsultation.addProperty("type", recupererTypeMedium(consultation.getMedium()));
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
