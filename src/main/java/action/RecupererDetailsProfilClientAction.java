package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.service.Service;

/**
 *
 * @author rdesouza
 */
public class RecupererDetailsProfilClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        Long id = new Long(session.getAttribute("userSessionId"));
        Service service = new Service();
        Client client = service.rechercherClientParId(id);
        
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
