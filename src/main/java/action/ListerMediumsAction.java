package action;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.data.Medium;
import metier.service.Service;

/**
 * récupère la liste des médiums
 */
public class ListerMediumsAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {

        Service service = new Service();
        List<Medium> mediums = service.listerMediums();

        if (mediums != null) {
            Boolean avecDispo = "true".equals(request.getParameter("avecDispo"));
            if (avecDispo) {
                HashMap<Long, Boolean> mediumsDispo = new HashMap<>();
                for (Medium m : mediums) {
                    mediumsDispo.put(m.getId(), service.getDisponibilite(m));
                    request.setAttribute("mediumsDispo", mediumsDispo);
                }
            }
            request.setAttribute("mediums", mediums);
            request.setAttribute("avecDispo", avecDispo);

        } else {
            request.setAttribute("mediums", null);
        }
    }
}
