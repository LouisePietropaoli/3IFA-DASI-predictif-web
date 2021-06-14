package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Employe;
import metier.data.Medium;
import metier.service.Service;

/**
 * Met les attributs : - nombre de clients -
 * pourcentage de la clientèle totale de l'employé authentifié grâce à la
 * session
 */
public class RecupererStatistiquesEmployeAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Employe employe = (Employe) session.getAttribute("employe");
        Service service = new Service();
        long nbClientEmploye = service.recupererNombreClientEmploye(employe);
        double pourcentageClientEmploye = service.recupererPourcentageClientEmploye(employe);
        request.setAttribute("nbClientEmploye", nbClientEmploye);
        request.setAttribute("pourcentageClientEmploye", pourcentageClientEmploye);
        request.setAttribute("employe", employe);
    }

}
