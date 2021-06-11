package action;

import javax.servlet.http.HttpServletRequest;
import metier.data.Client;
import metier.service.Service;

public class AuthentifierClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Service service = new Service();
        Client client = service.authentifierClient(login, password);

        if (client != null) {
            request.setAttribute("connexion", true);
            request.setAttribute("client", client);
        } else {
            request.setAttribute("connexion", false);
        }
    }

}
