/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.service.Service;

/**
 *
 * @author talegre
 */
public class RecupererDetailsProfilClientAvecProfilAstralAction extends Action {
    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        Long id = new Long((String)session.getAttribute("userSessionId"));
        
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
