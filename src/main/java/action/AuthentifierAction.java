package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Employe;
import metier.service.Service;

public class AuthentifierAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String mode = request.getParameter("mode");
        Service service = new Service();
        HttpSession session = request.getSession(false);

        if ("client".equals(mode)) {
            Client client;
            client = service.authentifierClient(email, motDePasse);
            if (client != null) {
                session.setAttribute("userSessionId", client.getId());
                request.setAttribute("connexion", true);
            } else {
                request.setAttribute("connexion", false);
            }
        } else {
            Employe employe;
            employe = service.authentifierEmploye(email, motDePasse);
            if (employe != null) {
                session.setAttribute("userSessionId", employe.getId());
                request.setAttribute("connexion", true);
            } else {
                request.setAttribute("connexion", false);
            }
        }
    }

}
