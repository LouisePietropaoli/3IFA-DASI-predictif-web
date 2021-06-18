package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Client;

public class ProfilAstralSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        Client client = (Client) request.getAttribute("client");

        if (client != null) {
            container.addProperty("signeZodiaque", client.getSigneZodiaque());
            container.addProperty("signeAstro", client.getSigneAstroChinois());
            container.addProperty("couleur", client.getCouleurPorteBonheur());
            container.addProperty("animalTotem", client.getAnimalTotem());
        } else {
            container.addProperty("erreur", true);
        }

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
