package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;

/**
 * récupère le client authentifié grâce à la session
 */
public class RecupererDetailsProfilClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        Client client = (Client)session.getAttribute("client");
        
        if(client != null)
        {
            request.setAttribute("client", client);
        }
        else
        {
            request.setAttribute("erreur", true);
        }
    }

}
