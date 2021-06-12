package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeconnexionAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
    }
    
}
