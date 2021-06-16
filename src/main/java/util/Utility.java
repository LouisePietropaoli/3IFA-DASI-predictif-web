package util;

import com.google.gson.JsonObject;
import metier.data.Astrologue;
import metier.data.Cartomancien;
import metier.data.Medium;
import metier.data.Spirite;
import metier.enums.Genre;

public class Utility {

    public static String recupererTypeMedium(Medium medium) {
        String type = "";
        if (medium instanceof Astrologue) {
            type = "Astrologue";
        } else if (medium instanceof Cartomancien) {
            type = "Cartomancien";
        } else if (medium instanceof Spirite) {
            type = "Spirite";
        }
        return type;
    }

    public static String recupererGenreMedium(Medium medium) {
        return medium.getGenre() == Genre.F ? "Femme" : "Homme";
    }

    public static JsonObject recupererJsonMediumSelonType(Medium medium) {
        JsonObject jsonMedium = new JsonObject();
        String type = recupererTypeMedium(medium);
        jsonMedium.addProperty("designation", medium.getDesignation());
        jsonMedium.addProperty("genre", recupererGenreMedium(medium));
        jsonMedium.addProperty("presentation", medium.getPresentation());
        switch (type) {
            case "Astrologue":
                Astrologue astrologue = (Astrologue)medium;
                jsonMedium.addProperty("formation", astrologue.getFormation());
                jsonMedium.addProperty("anneePromotion", astrologue.getAnneePromotion());
                break;
            case "Spirite":
                Spirite spirite = (Spirite)medium;
                jsonMedium.addProperty("support", spirite.getSupport());
                break;
            default:
                break;
        }

        return jsonMedium;
    }
}
