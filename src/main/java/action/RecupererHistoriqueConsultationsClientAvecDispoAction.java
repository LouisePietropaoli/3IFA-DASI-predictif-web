package action;

import javax.servlet.http.HttpServletRequest;

/**
 * renvoie les consultations du client authentifié grâce à la session avec la
 * disponibilité du médium pour chaque consultation passée sous forme de tableau
 * associatif [ "id_consultation" => estDispo, id_consultation" => estDispo, ...
 * ]
 */
public class RecupererHistoriqueConsultationsClientAvecDispoAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {

    }

}
