
import action.Action;
import action.AuthentifierClientAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.data.Client;
import metier.service.Service;
import serialisation.ClientSerialisation;
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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Service service = new Service();

            String todo = request.getParameter("todo");
            Action action = null;
            Serialisation serialisation = null;
            JsonObject jsonContainer = new JsonObject();

            switch (todo) {
                case "connecter":
                    action = new AuthentifierClientAction();
                    serialisation = new ClientSerialisation();
                    break;
                case "se-connecter":
                    Client client = service.authentifierClient(request.getParameter("login"), request.getParameter("password"));

                    if (client != null) {
                        jsonContainer.addProperty("erreur", false);
                        JsonObject jsonClient = new JsonObject();
                        jsonClient.addProperty("id", client.getId());
                        jsonClient.addProperty("nom", client.getNom());
                        jsonClient.addProperty("prenom", client.getPrenom());
                        jsonClient.addProperty("email", client.getEmail());
                    } else {
                        jsonContainer.addProperty("erreur", true);

                    }
                    break;
                default:
                    break;
            }

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
