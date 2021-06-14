package util;

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
}
