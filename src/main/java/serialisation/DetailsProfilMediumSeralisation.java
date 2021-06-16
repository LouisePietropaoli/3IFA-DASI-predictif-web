package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Medium;
import util.Utility;
import static util.Utility.recupererGenreMedium;

/**
 * construit les détails sur un médium genre | designation | type | description
 * + en fonction du type du médium, ses autres attributs (support, formation...)
 */
public class DetailsProfilMediumSeralisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        Medium medium = (Medium) request.getAttribute("medium");

        if (medium != null) {
            JsonObject jsonMedium = Utility.recupererJsonMediumSelonType(medium);
            jsonMedium.addProperty("designation", medium.getDesignation());
            jsonMedium.addProperty("genre", Utility.recupererGenreMedium(medium));
            jsonMedium.addProperty("presentation", medium.getPresentation());
            container.add("medium", jsonMedium);
        } else {
            container.addProperty("erreur", true);
        }

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
