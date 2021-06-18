package control;

import action.Action;
import action.AuthentifierAction;
import action.CommencerConsultationAction;
import action.CreerCompteClientAction;
import action.DeconnecterAction;
import action.DemanderPredictionsAction;
import action.InitialiserAccueilEmployeAction;
import action.ListerMediumsAction;
import action.RecupererClassementMedium;
import action.RecupererDetailsMediumAction;
import action.RecupererDetailsProfilClientAction;
import action.RecupererHistoriqueConsultationsClientAction;
import action.RecupererHistoriqueConsultationsClientAvecDispoAction;
import action.RecupererProfilAstral;
import action.RecupererStatistiquesEmployeAction;
import action.ReserverMediumAction;
import action.TerminerConsultationAction;
import dao.JpaUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
import serialisation.InitialisationAccueilEmployeSerialisation;
import serialisation.ListeMediumsSerialisation;
import serialisation.ListePredictionsSerialisation;
import serialisation.ProfilAstralSerialisation;
import serialisation.RetourSuccesErreurSerialisation;
import serialisation.Serialisation;
import serialisation.StatistiquesEmployeSerialisation;

@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    public static final List<String> TO_DO_SANS_AUTHENTIFICATION = Arrays.asList(
            "connecter", "lister-mediums", "recuperer-details-medium", "creer-compte-client"
    );

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
        try {
            HttpSession session = request.getSession(true);
            request.setCharacterEncoding("UTF-8");
            String todo = request.getParameter("todo");

            Action action = null;
            Serialisation serialisation = null;

            /**
             *********************************************************************
             * Vérification si le todo nécessite d'être authentifié
             * ********************************************************************
             */
            if (!TO_DO_SANS_AUTHENTIFICATION.contains(todo)) {
                //authentification nécessaire
                // Vérification de la session
                Boolean userAuthentified = (Boolean) session.getAttribute("userAuthentified");
                if (userAuthentified == null) {
                    response.sendError(403, "Accès interdit.");
                }
            }

            switch (todo) {
                /**
                 * ********************************************************************
                 * ACTIONS SANS AUTHENTIFICATION
                 * ********************************************************************
                 */
                /**
                 * @param avecDispo: boolean. Vrai si l'on doit récupérer la 
                 * disponibilité de chaque médium.
                 */
                case "lister-mediums": {
                    action = new ListerMediumsAction();
                    serialisation = new ListeMediumsSerialisation();
                }
                break;
                /**
                 * @param email
                 * @param motDePasse
                 * @param mode 'client' | 'employe'
                 */
                case "connecter": {
                    action = new AuthentifierAction();
                    serialisation = new RetourSuccesErreurSerialisation();
                }
                break;
                /**
                 * @param mediumId
                 */
                case "recuperer-details-medium": {
                    /**
                     * utilisé pour afficher les détails d'un médiums et sa
                     * disponbilité dans les modales renvoie le medium avec sa
                     * description détaillée
                     */
                    action = new RecupererDetailsMediumAction();
                    serialisation = new DetailsProfilMediumSeralisation();
                }
                break;
                case "recuperer-profil-astral": {
                    /**
                     * utilisé pour afficher le profil astral du client connecté.
                     */
                    action = new RecupererProfilAstral();
                    serialisation = new ProfilAstralSerialisation();
                }
                break;
                /**
                 * @param nom
                 * @param prenom
                 * @param date_naissance
                 * @param adresse
                 * @param ville
                 * @param code_postal
                 * @param adresse_mail
                 * @param telephone
                 * @param mdp
                 */
                case "creer-compte-client": {
                    /**
                     * utilisé pour la création d'un compte client pour ensuite
                     * afficher la modale si la création a eu lieu ou signifier
                     * une erreur sinon renvoie un attribut d'erreur à vrai si
                     * la création a eu lieu
                     */
                    action = new CreerCompteClientAction();
                    serialisation = new RetourSuccesErreurSerialisation();
                }
                break;

                /**
                 * ********************************************************************
                 * ACTIONS AVEC AUTHENTIFICATION
                 * ********************************************************************
                 *
                 */
                case "deconnecter": {
                    action = new DeconnecterAction();
                    serialisation = new RetourSuccesErreurSerialisation();
                }
                break;
                /**
                 * utilisé pour réserver un médium sur la page de listing des
                 * mediums avec dispo et pour afficher la modale de confirmation
                 * ou d'erreur de création de la consultation renvoie un
                 * attribut erreur à vrai si la consutlation n'a pas été créée
                 * @param idMedium
                 */
                case "reserver-medium": {
                    action = new ReserverMediumAction();
                    serialisation = new RetourSuccesErreurSerialisation();
                }
                break;

                /**
                 * utilisé pour l'initialisation de la page de profil client
                 * quand un client est authentifié renvoie le client authentifié
                 * avec ses détails (sans ses consultations)
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
                 * utilisé pour afficher l'historique des consultations d'un du
                 * client relié à l'employé authentifié renvoie l'historique des
                 * consultations du client sans les dispo des médiums associés
                 * et avec les éventuels commantaires sur la consultation
                 */
                case "recuperer-historique-consultations-fiche-client": {
                    action = new RecupererHistoriqueConsultationsClientAction();
                    serialisation = new HistoriqueConsultationsClientSerialisation();
                }
                break;

                /**
                 * utilisé pour l'initalisation de la page d'accueil employé
                 * authentifié renvoie la consultation reliée à ce medium avec
                 * son statut si une consultation est associée au médium,
                 * renvoie toutes les infos du client (dont profil astral) sauf
                 * son mot de passe L'historique client n'est pas renvoyé
                 */
                case "initialiser-accueil-employe": {
                    action = new InitialiserAccueilEmployeAction();
                    serialisation = new InitialisationAccueilEmployeSerialisation();
                }
                break;
                /**
                 * utilisé pour récupérer et afficher des prédictions Renvoie
                 * les trois prédictions (amour, santé, travail)
                 * @param amour 1 | 2 | 3 | 4
                 * @param sante 1 | 2 | 3 | 4
                 * @param travail 1 | 2 | 3 | 4
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
                 * consultation si elle s'est bien terminée renvoie vraie si la
                 * consultation a bien été terminée, faux sinon
                 * @param commentaire Commentaire mis par l'employé sur la 
                 * consultation.
                 */
                case "terminer-consultation": {
                    action = new TerminerConsultationAction();
                    serialisation = new RetourSuccesErreurSerialisation();
                }
                break;
                /**
                 * utilisé pour l'initalisation de la page des statistiques d'un
                 * employé authentifié renvoie le nombre de consultations, le
                 * nombre de clients et le pourcentage de la clientèle totale de
                 * l'employé
                 */
                case "recuperer-statistiques": {
                    action = new RecupererStatistiquesEmployeAction();
                    serialisation = new StatistiquesEmployeSerialisation();
                }
                break;
                /**
                 * utilisé pour l'initalisation de la page des statistiques d'un
                 * employé authentifié renvoie le nombre de consultations, le
                 * nombre de clients et le pourcentage de la clientèle totale de
                 * l'employé
                 */
                case "recuperer-classement-medium": {
                    action = new RecupererClassementMedium();
                    serialisation = new ClassementMediumSerialisation();
                }
                break;
                default:
                    break;
            }

            if (action != null && serialisation != null) {
                action.executer(request);
                serialisation.serialiser(request, response);
            }
        } catch (Exception e) {
            response.sendError(400, "Une erreur est survenue : " + e);
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
