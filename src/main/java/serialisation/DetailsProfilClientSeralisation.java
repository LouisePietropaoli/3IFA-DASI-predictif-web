package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Client;

/**
 * renvoie les détails d'un client sans son mot de passe ni son 
 * profil astral, ni ses consultations. Ne renvoie pas son id
 */
public class DetailsProfilClientSeralisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        Client client = (Client) request.getAttribute("client");

        if (client != null) {
            JsonObject jsonClient = new JsonObject();
            jsonClient.addProperty("id", client.getId());
            jsonClient.addProperty("nom", client.getNom());
            jsonClient.addProperty("prenom", client.getPrenom());
            Date dateNaissance = client.getDateNaissance();
            jsonClient.addProperty("date_naissance", dateNaissance.getDate()+"/"+(dateNaissance.getMonth()+1)+"/"+(dateNaissance.getYear()+1900));
            jsonClient.addProperty("adresse", client.getAdresse());
            jsonClient.addProperty("ville", client.getVille());
            jsonClient.addProperty("code_postal", client.getCodePostal());
            jsonClient.addProperty("mail", client.getEmail());
            jsonClient.addProperty("telephone", client.getTelephone());
            container.add("client", jsonClient);
        } else {
            container.addProperty("erreur", true);
        }

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
