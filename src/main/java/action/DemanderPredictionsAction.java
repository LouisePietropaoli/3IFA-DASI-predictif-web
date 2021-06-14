package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.data.Client;
import metier.service.Service;

/**
 * appelle le service pour créer demander des prédictions
 */
public class DemanderPredictionsAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        String couleur = request.getParameter("couleur");
        String animal = request.getParameter("animal");
        int amour = Integer.parseInt(request.getParameter("amour"));
        int sante = Integer.parseInt(request.getParameter("sante"));
        int travail = Integer.parseInt(request.getParameter("travail"));
        
        
        Service service = new Service();
        List<String> predictions = service.recupererPredictions(couleur, animal, amour, sante, travail);
        
        if(predictions != null)
        {
            request.setAttribute("predictions", predictions);
        }
        else
        {
            request.setAttribute("erreur", true);
        }
    
    }

}
