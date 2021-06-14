package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Employe;

/**
 * renvoie les statistiques d'un employé) - nombre de consultations - nombre de
 * clients - pourcentage de la clientèle totale de l'employé authentifié grâce à
 * la session
 */
public class StatistiquesEmployeSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
           
         Employe employe = (Employe) request.getAttribute("employe");
         Long nbClientEmploye = (Long) request.getAttribute("nbClientEmploye");
         double pourcentageClientEmploye = (double) request.getAttribute("pourcentageClientEmploye");
         

        if (nbClientEmploye != null && employe != null) {
            container.addProperty("nbConsultationsEmploye", employe.getNbConsultations());
            container.addProperty("nbClientEmploye", nbClientEmploye.doubleValue());
            container.addProperty("pourcentageClientEmploye", pourcentageClientEmploye);
        } else {
            container.addProperty("erreur", true);
        }
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
