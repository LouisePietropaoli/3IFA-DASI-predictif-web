package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Consultation;
import metier.data.Employe;
import metier.service.Service;

/**
 * appelle le service pour créer demander des prédictions
 */
public class DemanderPredictionsAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Service service = new Service(); 
        Employe employe = (Employe)session.getAttribute("employe");
        Consultation consultation = service.recupererConsultationEnCours(employe);
        Client client = consultation.getClient();
        String couleur = "";
        String animal = "";
        if(client != null)
        {
            couleur = client.getCouleurPorteBonheur();
            animal = client.getAnimalTotem();
        }
        else
        {
            request.setAttribute("erreur", true);
        }
        
        int amour = Integer.parseInt(request.getParameter("amour"));
        int sante = Integer.parseInt(request.getParameter("sante"));
        int travail = Integer.parseInt(request.getParameter("travail"));
        List<String> predictions = service.recupererPredictions(couleur, animal, amour, sante, travail);
        
        if(predictions != null)
        {
            request.setAttribute("amour", predictions.get(0));
            request.setAttribute("sante", predictions.get(1));
            request.setAttribute("travail", predictions.get(2));
        }
        else
        {
            request.setAttribute("erreur", true);
        }
    
    }

}
