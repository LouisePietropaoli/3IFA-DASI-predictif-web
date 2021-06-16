package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Consultation;
import metier.data.Employe;
import metier.service.Service;

/**
 * appelle le service pour commencer la consultation Met un attribut erreur à
 * vrai si une erreur a eu lieu
 */
public class CommencerConsultationAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Service service = new Service();
        Employe employe = (Employe) session.getAttribute("employe");
        Consultation consultation = service.recupererConsultationEnCours(employe);
        if (consultation != null) {
            consultation = service.commencerConsultation(consultation);
            if (consultation == null) {
                request.setAttribute("erreur", true);
            }
        } else {
            request.setAttribute("erreur", true);
        }
    }

}
