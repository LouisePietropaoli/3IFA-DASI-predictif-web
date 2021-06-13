package action;

import javax.servlet.http.HttpServletRequest;

/**
 * Met les attributs : - nombre de consultations - nombre de clients -
 * pourcentage de la clientèle totale de l'employé authentifié grâce à la
 * session
 */
public class RecupererStatistiquesEmployeAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        //TODO
        //recuperer l'employe dans la session
        //appel aux services de stats

    }

}
