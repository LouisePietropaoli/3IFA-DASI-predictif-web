
import action.Action;
import action.AuthentifierAction;
import action.CommencerConsultationAction;
import action.CreerCompteClientAction;
import action.DemanderPredictionsAction;
import action.InitialiserAccueilClientAction;
import action.InitialiserAccueilEmployeAction;
import action.ListerMediumsAction;
import action.RecupererClassementMedium;
import action.RecupererDetailsMediumAction;
import action.RecupererDetailsProfilClientAction;
import action.RecupererHistoriqueConsultationsClientAction;
import action.RecupererHistoriqueConsultationsClientAvecDispoAction;
import action.RecupererStatistiquesEmployeAction;
import action.ReserverMediumAction;
import action.TerminerConsultationAction;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import serialisation.ClassementMediumSerialisation;
import serialisation.DetailsProfilClientSeralisation;
import serialisation.DetailsProfilMediumSeralisation;
import serialisation.HistoriqueConsultationsClientAvecDispoSerialisation;
import serialisation.HistoriqueConsultationsClientSerialisation;
import serialisation.InitialisationAccueilClientSerialisation;
import serialisation.InitialisationAccueilEmployeSerialisation;
import serialisation.ListeMediumsSerialisation;
import serialisation.ListePredictionsSerialisation;
import serialisation.RetourSuccesErreurSerialisation;
import serialisation.Serialisation;
import serialisation.StatistiquesEmployeSerialisation;

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
        request.getParameter("test");
        String todo = request.getParameter("todo");

        Action action = null;
        Serialisation serialisation = null;

        /**
         * utilisé pour la page d'accueil sans connexion et la liste des mediums
         * quand l'employé est connecté renvoie la liste des mediums avec leur
         * description simple (genre, designation, type,description + id)
         */
        if (todo.equals("lister-mediums")) {
            action = new ListerMediumsAction();
            serialisation = new ListeMediumsSerialisation();

        } else if (todo.equals("connecter")) {
            action = new AuthentifierAction();
            serialisation = new RetourSuccesErreurSerialisation();

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
                    /**
                     * utilisé pour l'initalisation de la page d'accueil client
                     * authentifié renvoie la liste de médiums avec dispo et le
                     * profil astral du client authentifié
                     */
                    case "initialser-accueil-client": {
                        action = new InitialiserAccueilClientAction();
                        serialisation = new InitialisationAccueilClientSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour la création d'un compte client pour ensuite
                     * afficher la modale si la création a eu lieu ou signifier
                     * une erreur sinon renvoie un attribut d'erreur à vrai si
                     * la création a eu lieu
                     */
                    case "creer-compte-client": {
                        action = new CreerCompteClientAction();
                        serialisation = new RetourSuccesErreurSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour afficher les détails d'un médiums et sa
                     * disponbilité dans les modales renvoie le medium avec sa
                     * description détaillée
                     */
                    case "recuperer-details-medium": {
                        action = new RecupererDetailsMediumAction();
                        serialisation = new DetailsProfilMediumSeralisation();
                    }
                    break;

                    /**
                     * utilisé pour réserver un médium sur la page de listing
                     * des mediums avec dispo et pour afficher la modale de
                     * confirmation ou d'erreur de création de la consultation
                     * renvoie un attribut erreur à vrai si la consutlation n'a
                     * pas été créée
                     */
                    case "reserver-medium": {
                        action = new ReserverMediumAction();
                        serialisation = new RetourSuccesErreurSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour l'initialisation de la page de profil client
                     * quand un client est authentifié renvoie le client
                     * authentifié avec ses détails (sans ses consultations)
                     */
                    case "recuperer-details-profil-client": {
                        action = new RecupererDetailsProfilClientAction();
                        serialisation = new DetailsProfilClientSeralisation();
                    }
                    break;

                    /**
                     * utilisé pour l'initalisation de la page d'historique de
                     * consultations client renvoie les consultations du client
                     * authentifié avec la disponibilité du médium pour chaque
                     * consultation passée
                     */
                    case "recuperer-historique-consultations-client-avec-dispo": {
                        action = new RecupererHistoriqueConsultationsClientAvecDispoAction();
                        serialisation = new HistoriqueConsultationsClientAvecDispoSerialisation();
                    }
                    break;
                    /**
                     * utilisé pour afficher l'historique des consultations d'un
                     * du client relié à l'employé authentifié renvoie
                     * l'historique des consultations du client sans les dispo
                     * des médiums associés
                     */
                    case "recuperer-historique-consultations-fiche-client": {
                        action = new RecupererHistoriqueConsultationsClientAction();
                        serialisation = new HistoriqueConsultationsClientSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour l'initalisation de la page d'accueil employé
                     * authentifié renvoie la consultation reliée à ce medium
                     * avec son statut si une consultation est associée au
                     * médium, renvoie toutes les infos du client (dont profil
                     * astral) sauf son mot de passe L'historique client n'est
                     * pas renvoyé
                     */
                    case "initialser-accueil-employe": {
                        action = new InitialiserAccueilEmployeAction();
                        serialisation = new InitialisationAccueilEmployeSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour récupérer et afficher des prédictions
                     * Renvoie les trois prédictions (amour, santé, travail)
                     */
                    case "demander-predictions": {
                        action = new DemanderPredictionsAction();
                        serialisation = new ListePredictionsSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour commencer la consultation de l'employé
                     * authentifié et changer le bouton en "terminer la
                     * conusltation" renvoie vraie si la consultation a bien été
                     * démarrée, faux sinon
                     */
                    case "commencer-consultation": {
                        action = new CommencerConsultationAction();
                        serialisation = new RetourSuccesErreurSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour terminer une consultation avec saisie
                     * facultative de commentaire et ne plus afficher la
                     * consultation si elle s'est bien terminée renvoie vraie si
                     * la consultation a bien été terminée, faux sinon
                     */
                    case "terminer-consultation": {
                        action = new TerminerConsultationAction();
                        serialisation = new RetourSuccesErreurSerialisation();
                    }
                    break;

                    /**
                     * utilisé pour l'initalisation de la page des statistiques
                     * d'un employé authentifié renvoie le nombre de
                     * consultations, le nombre de clients et le pourcentage de
                     * la clientèle totale de l'employé
                     */
                    case "recuperer-statistiques": {
                        action = new RecupererStatistiquesEmployeAction();
                        serialisation = new StatistiquesEmployeSerialisation();
                    }
                    break;
                    /**
                     * utilisé pour l'initalisation de la page des statistiques
                     * d'un employé authentifié renvoie le nombre de
                     * consultations, le nombre de clients et le pourcentage de
                     * la clientèle totale de l'employé
                     */
                    case "recuperer-classement-medium": {
                        action = new RecupererClassementMedium();
                        serialisation = new ClassementMediumSerialisation();
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
