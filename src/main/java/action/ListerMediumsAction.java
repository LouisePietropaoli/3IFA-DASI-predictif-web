package action;

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
            request.setAttribute("mediums", mediums);
        } else {
            request.setAttribute("mediums", null);
        }
    }
}
