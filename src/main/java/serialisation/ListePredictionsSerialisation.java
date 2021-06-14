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

/**
 * renvoie la liste des prédictions (amour, santé travail)
 */
public class ListePredictionsSerialisation extends Serialisation {

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        JsonArray array = new JsonArray();
        List<String> predictions = (List<String>)request.getAttribute("predictions");
        for(String prediction:predictions)
        {
            array.add(prediction);
        }
        
        if(predictions != null)
        {
            container.add("predictions", array);
        }
        else 
        {
            container.addProperty("erreur", true);
        }
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
