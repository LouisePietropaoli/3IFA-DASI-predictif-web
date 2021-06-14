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
import metier.data.Astrologue;
import metier.data.Cartomancien;
import metier.data.Medium;
import metier.data.Spirite;
import metier.enums.Genre;
import util.Utility;

/**
 * renvoie la liste des mediums avec leur description simple id | genre |
 * designation | type | presentation
 */
public class ListeMediumsSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JsonObject container = new JsonObject();

        List<Medium> mediums = (List<Medium>) request.getAttribute("mediums");
        if (mediums != null) {
            JsonArray jsonListeMediums = new JsonArray();
            for (Medium medium : mediums) {

                JsonObject jsonMedium = new JsonObject();
                jsonMedium.addProperty("id", medium.getId());
                jsonMedium.addProperty("genre", Utility.recupererGenreMedium(medium));
                jsonMedium.addProperty("designation", medium.getId());
                jsonMedium.addProperty("type", Utility.recupererTypeMedium(medium));
                jsonMedium.addProperty("presentation", medium.getPresentation());
                
                jsonListeMediums.add(jsonMedium);
            }
             container.add("mediums", jsonListeMediums);
        } else {
            container.addProperty("erreur", true);
        }

        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
