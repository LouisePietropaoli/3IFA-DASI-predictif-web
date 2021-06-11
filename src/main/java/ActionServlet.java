
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
            JsonObject jsonContainer = new JsonObject();

            switch (todo) {
                case "connecter":
                    Client client = service.authentifierClient(request.getParameter("login"), request.getParameter("password"));

                    if (client != null) {
                        jsonContainer.addProperty("connexion", true);
                        JsonObject jsonClient = new JsonObject();
                        Long id = client.getId();
                        String nom = client.getNom();
                        String prenom = client.getPrenom();
                        String email = client.getEmail();
                        jsonClient.addProperty("id", id);
                        jsonClient.addProperty("nom", nom);
                        jsonClient.addProperty("prenom", prenom);
                        jsonClient.addProperty("email", email);
                        jsonContainer.add("client", jsonClient);
                    } else {
                        jsonContainer.addProperty("connexion", false);

                    }
                    break;
                default:
                    break;
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(jsonContainer, out);
            out.close();

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
