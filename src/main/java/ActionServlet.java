
import action.Action;
import action.AuthentifierAction;
import action.AuthentifierClientAction;
import action.DeconnexionAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.data.Client;
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
        //HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");

        String todo = request.getParameter("todo");
        Action action = null;
        Serialisation serialisation = null;

        if ("connecter".equals(todo)) {
            action = new AuthentifierAction();
            serialisation = new ConnexionSerialisation();
        } else if ("deconnecter".equals(todo)) {
            action = new DeconnexionAction();
            action.executer(request);
            PrintWriter out = response.getWriter();
                        JsonObject jsonContainer = new JsonObject();
                         Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(jsonContainer, out);
            out.close();

        } else {
            // Verification de la session
           // Long sessionUserId = (Long)session.getAttribute("sessionUserId");
            //if (sessionUserId == null) {
            if(false){
                response.sendError(403, "Accès interdit : Aucun utilisateur authentifié.");
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

        if (action != null && serialisation != null) {
            action.executer(request);
            serialisation.serialiser(request, response);
        } else {
            response.sendError(400, "Bad Request (pas d'Action ou de Serialisation pour traiter cette requete)");
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
