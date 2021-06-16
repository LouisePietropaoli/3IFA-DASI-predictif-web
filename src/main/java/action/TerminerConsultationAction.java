package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Consultation;
import metier.data.Employe;
import metier.service.Service;

/**
 * appelle le service pour terminer la consultation
 * Met un attribut erreur à vrai si une erreur a eu lieu
 */
public class TerminerConsultationAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        String commentaire = request.getParameter("commentaire");
           
        HttpSession session = request.getSession(false);
        Service service = new Service();
        Employe employe = (Employe)session.getAttribute("employe");
        Consultation consultation = service.recupererConsultationEnCours(employe);
                    
        if (consultation != null)
            {
                consultation = service.terminerConsultation(consultation, commentaire);
                if (consultation != null)
                {
                    request.setAttribute("erreur", "Pas d'erreur");
                }
                else
                {
                    request.setAttribute("erreur", "Erreur lors du démarrage");
                }
            }
            else
            {
                request.setAttribute("erreur", "Erreur lors de la récupération");
            }
    }

}
