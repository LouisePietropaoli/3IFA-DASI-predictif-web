package action;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Medium;
import metier.service.Service;

/**
 * récupère la liste des médiums avec leur dispo et le profil astral du client
 * authentifié
 */
public class InitialiserAccueilClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        
        List<Medium> mediums = service.listerMediums();
        
        HashMap<Long, Boolean> mediumsDispo = new HashMap<>();

        if (mediums != null) {
            request.setAttribute("mediums", mediums);
            for (Medium m : mediums) {
                mediumsDispo.put(m.getId(), service.getDisponibilite(m));
                request.setAttribute("mediumsDispo", mediumsDispo);

            }
        } else {
            request.setAttribute("mediums", null);
        }
    }

}
