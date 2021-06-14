package action;

import javax.servlet.http.HttpServletRequest;
import metier.data.Medium;
import metier.service.Service;

/**
 * récupère le médium dont l'id est passé dans la requête
 */
public class RecupererDetailsMediumAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("mediumId"));
        Service service = new Service();

        Medium medium;
        medium = service.rechercherMediumParId(id);
        if (medium != null) {
            request.setAttribute("medium", medium);
        } else {
            request.setAttribute("erreur", true);
        }

    }

}
