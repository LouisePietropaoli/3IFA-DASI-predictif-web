
import action.Action;
import action.AuthentifierAction;
import action.AuthentifierClientAction;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import serialisation.ClientSerialisation;
import serialisation.ConnexionSerialisation;
import serialisation.Serialisation;

@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");

        String todo = request.getParameter("todo");
        if (todo == null) {
            todo = new String();
        }
        Action action = null;
        Serialisation serialisation = null;

        if (todo.equals("connecter")) {
            action = new AuthentifierAction();
            serialisation = new ConnexionSerialisation();
        } else {
            // Verification de la session
            Long sessionUserId = (Long) session.getAttribute("userSessionId");
            if (sessionUserId == null) {
                response.sendError(403, "Accès interdit : Aucun utilisateur authentifié.");
            } else if (todo.equals("deconnecter")) {
                // action = new DeconnexionAction();
                //action.executer(request);
               session.invalidate();
                PrintWriter out = response.getWriter();
                out.println("{}");
                out.close();
            } else {
                switch (todo) {
                    case "details-client": {
                        action = new AuthentifierClientAction();
                        serialisation = new ClientSerialisation();
                    }

                    break;

                    default:
                        break;
                }
            }
        }

        if (!(todo.equals("deconnecter"))) {
            if (action != null && serialisation != null) {
                action.executer(request);
                serialisation.serialiser(request, response);
            } else {
                response.sendError(400, "Bad Request (pas d'Action ou de Serialisation pour traiter cette requete)");
            }
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
    }
}
