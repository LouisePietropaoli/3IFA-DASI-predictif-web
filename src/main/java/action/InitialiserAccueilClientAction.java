package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * récupère la liste des médiums avec leur dispo
 * et le profil astral du client authentifié
 */
public class InitialiserAccueilClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
                HttpSession session = request.getSession();
                session.getAttribute("client");
        //TODO
        //récupérer le client authentifié grâce à la session
        //récupérer la liste des médiums
        //pour chaque médium, récupérer sa dispo
    }

}
