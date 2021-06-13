package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.service.Service;

public class AuthentifierClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Service service = new Service();
        Client client = service.authentifierClient(email, motDePasse);

        if (client != null) {
            session.setAttribute("userSessionId", client.getId());
        } else {
            request.setAttribute("erreur", true);
        }
    }

}
