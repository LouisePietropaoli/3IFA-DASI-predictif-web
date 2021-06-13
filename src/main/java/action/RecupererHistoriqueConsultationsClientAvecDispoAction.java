package action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Consultation;
import metier.data.Employe;
import metier.service.Service;

/**
 * renvoie les consultations du client associé au médium authentifié grâce à la
 * session sans la disponibilité du médium pour chaque consultation passée 
 */
public class RecupererHistoriqueConsultationsClientAvecDispoAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Long employeId = Long.parseLong(session.getAttribute("userSessionId").toString(), 10);
        Service service = new Service();
        Employe employe = service.rechercherEmployeParId(employeId);
        Consultation consultation = service.recupererConsultationEnCours(employe);
        List<Consultation> historiqueConsultation = service.recupererHistoriqueConsultationsClient(consultation.getClient());
        ArrayList<Boolean> dispos =  new ArrayList<Boolean>();
        for (Consultation consult : historiqueConsultation) {
           dispos.add(service.getDisponibilite(consult.getMedium()));
        }
        request.setAttribute("historiqueConsultation", historiqueConsultation);
        request.setAttribute("dispos", dispos);
    }

}
