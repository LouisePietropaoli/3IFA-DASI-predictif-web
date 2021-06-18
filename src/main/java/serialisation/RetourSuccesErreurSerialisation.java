package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Employe;
import metier.enums.Genre;

public class RetourSuccesErreurSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        container.addProperty("erreur", (Boolean) request.getAttribute("erreur"));

        /**
         * temporaire - uniquement pour vérifier qu'une demande de consultation
         * par un client permet bien de prendre un employé du même sexe.
         */
        Employe employeResa = (Employe) request.getAttribute("employeResa");
        if (employeResa != null) {
            JsonObject jsonEmployeResa = new JsonObject();
            jsonEmployeResa.addProperty("id", employeResa.getId());
            jsonEmployeResa.addProperty("genre", employeResa.getGenre() == Genre.F ? "Femme" : "Homme");
            jsonEmployeResa.addProperty("nom", employeResa.getNom());
            jsonEmployeResa.addProperty("prenom", employeResa.getPrenom());
            container.add("employeResa", jsonEmployeResa);
        }

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
