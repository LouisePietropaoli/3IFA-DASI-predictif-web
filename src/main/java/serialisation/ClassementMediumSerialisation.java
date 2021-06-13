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
import metier.data.Medium;

/**
 * renvoie le classement des mediums */

public class ClassementMediumSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

         List<Medium> classementMediums = (List<Medium>) request.getAttribute("classementMedium");
         
         JsonArray jsonListeMedium = new JsonArray();

        if (classementMediums != null) {
             for (Medium medium : classementMediums) {
                JsonObject jsonMedium = new JsonObject();
                jsonMedium.addProperty("id", medium.getId());
                jsonMedium.addProperty("name", medium.getDesignation());
                jsonMedium.addProperty("nbConsultations", medium.getNbConsultations());
                jsonListeMedium.add(jsonMedium);
            }
            container.add("classementMediums", jsonListeMedium);
        }
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
