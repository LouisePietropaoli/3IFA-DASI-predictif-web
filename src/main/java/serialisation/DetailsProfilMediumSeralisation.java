package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Medium;

/**
 * construit les détails sur un médium
 *      genre | designation | type | description
 *      + en fonction du type du médium, ses autres attributs (support, formation...)
 */
public class DetailsProfilMediumSeralisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

                Medium medium = (Medium) request.getAttribute("medium");

        if (medium != null) {
            JsonObject jsonClient = new JsonObject();
            jsonClient.addProperty("id", medium.getId());
            jsonClient.addProperty("nom", medium.get());
            jsonClient.addProperty("prenom", client.getPrenom());
            jsonClient.addProperty("email", client.getEmail());
            container.add("client", jsonClient);
        } else {
            container.addProperty("erreur", true);
        }
        //TODO
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
