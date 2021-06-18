package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Consultation;
import metier.data.Employe;
import metier.service.Service;

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
        HttpSession session = request.getSession();
        Employe employe = (Employe) session.getAttribute("employe");
        Service service = new Service();
        Consultation consultationEnCours = service.recupererConsultationEnCours(employe);
        request.setAttribute("consultation", consultationEnCours);
    }

}
