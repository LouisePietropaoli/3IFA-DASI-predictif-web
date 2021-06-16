package action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Consultation;
import metier.data.Employe;
import metier.service.Service;

/**
 * renvoie les consultations du client associé au médium authentifié grâce à la
 * session sans la disponibilité du médium pour chaque consultation passée
 */
public class RecupererHistoriqueConsultationsClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Employe employe = (Employe) session.getAttribute("employe");
        Service service = new Service();
        Consultation consultation = service.recupererConsultationEnCours(employe);
        if (consultation != null) {
            List<Consultation> historiqueConsultation = service.recupererHistoriqueConsultationsClient(consultation.getClient());
            request.setAttribute("historiqueConsultation", historiqueConsultation);
        } else {
            request.setAttribute("historiqueConsultation", new ArrayList<Consultation>());
        }
    }
}
