package action;

import javax.servlet.http.HttpServletRequest;
import metier.data.Consultation;
import metier.service.Service;

/**
 * appelle le service pour terminer la consultation
 * Met un attribut erreur à vrai si une erreur a eu lieu
 */
public class TerminerConsultationAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
           Long id = new Long(request.getParameter("id"));
           String commentaire = request.getParameter("commentaire");
           
           Service service = new Service();
           Consultation consultation = service.rechercherConsultationParId(id);
           if (consultation != null)
           {
               consultation = service.terminerConsultation(consultation, commentaire);
               if (consultation != null)
               {
                   request.setAttribute("consultation terminée", Boolean.TRUE);
               }
               else
               {
                   request.setAttribute("consultation terminée", Boolean.FALSE );
               }
           }
           else
           {
               request.setAttribute("erreur", true);
           }
    }

}
