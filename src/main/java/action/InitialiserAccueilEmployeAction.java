package action;

import javax.servlet.http.HttpServletRequest;

/**
 * récupère la consultation associée au médium authentifié grâce à la session
 * récupère le client associé avec son profil astral
 * Ne récupère pas l'historique de ses consultations
 * met l'attribut consultation et client à null si aucune
 * consultation en cours
 */
public class InitialiserAccueilEmployeAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {

        //TODO
    }

}
