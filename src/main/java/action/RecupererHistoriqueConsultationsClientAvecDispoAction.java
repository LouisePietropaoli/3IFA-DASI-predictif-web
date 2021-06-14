package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Consultation;
import metier.service.Service;

/**
 * renvoie les consultations du client associé au médium authentifié grâce à la
 * session sans la disponibilité du médium pour chaque consultation passée 
 */
public class RecupererHistoriqueConsultationsClientAvecDispoAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Client client =  (Client) session.getAttribute("client");
        Service service = new Service();
        List<Consultation> historiqueConsultation = service.recupererHistoriqueConsultationsClient(client);
        Map<Long, Boolean> dispos =  new HashMap<>();
        for (Consultation consult : historiqueConsultation) {
            dispos.put(consult.getId(), service.getDisponibilite(consult.getMedium()));
        }
        request.setAttribute("historiqueConsultation", historiqueConsultation);
        request.setAttribute("dispos", dispos);
    }

}
