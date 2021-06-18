package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.data.Medium;
import metier.service.Service;

/**
 * Récupère le classement des mediums
 */
public class RecupererClassementMedium extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();
        List<Medium> classementMedium = service.recupererClassementMedium();
        request.setAttribute("classementMedium", classementMedium);
    }
}
