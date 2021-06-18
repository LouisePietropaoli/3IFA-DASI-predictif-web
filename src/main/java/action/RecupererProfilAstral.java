package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;

public class RecupererProfilAstral extends Action {

    @Override
    public void executer(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");

        if (client == null) {
            request.setAttribute("client", null);
        } else {
            request.setAttribute("client", client);
        }
    }
}
