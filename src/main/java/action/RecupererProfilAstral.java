package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.service.Service;

public class RecupererProfilAstral extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        Long clientId;
        try {
            clientId = Long.valueOf(request.getParameter("clientId"));
        } catch (Exception e) {
            clientId = null;
        }
        Client client;

        if (clientId == null) {

            HttpSession session = request.getSession();
            client = (Client) session.getAttribute("client");
        } else {
            Service service = new Service();

            client = service.rechercherClientParId(clientId);
        }
        if (client == null) {
            request.setAttribute("client", null);
        } else {
            request.setAttribute("client", client);
        }
    }
}
