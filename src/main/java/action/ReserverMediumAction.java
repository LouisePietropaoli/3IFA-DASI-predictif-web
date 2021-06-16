package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Consultation;
import metier.data.Medium;
import metier.service.Service;
import metier.service.exceptions.AucunEmployeDisponibleException;

/**
 * appelle le service pour créer une consultation met un attribut {"erreur" :
 * true} si la consultation n'a pas pu être créée
 */
public class ReserverMediumAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Service service = new Service();

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        Long mediumId = (Long) request.getAttribute("mediumId");
        Medium medium = service.rechercherMediumParId(mediumId);

        if (medium != null) {
            try {
                Consultation consultation = service.reserverMediumParClient(client, medium);
            } catch (AucunEmployeDisponibleException e) {
                request.setAttribute("erreur", true);
            }
        } else {
            request.setAttribute("erreur", true);
        }

    }

}
