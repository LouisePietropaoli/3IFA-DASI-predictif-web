package action;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.service.Service;

/**
 * crée le client avec les infos passées dans la requête
 * renvoie une erreur à vrai si une erreur est survenue
 */
public class CreerCompteClientAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date_naissance = new Date();
        try {
            date_naissance = simpleDateFormat.parse(request.getParameter("date_naissance"));
        } catch (ParseException ex) {
            Logger.getLogger(CreerCompteClientAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        String adresse = request.getParameter("adresse");
        String ville = request.getParameter("ville");
        Integer code_postal = null; 
        if(request.getParameter("code_postal") != "") {
            code_postal = Integer.parseInt(request.getParameter("code_postal"));
        }
        String adresse_mail = request.getParameter("adresse_mail");
        BigInteger telephone = new BigInteger(request.getParameter("telephone"));
        String mdp = request.getParameter("mdp");
        
        Service service = new Service();
        Client client = new Client(adresse_mail,telephone,mdp,prenom,nom,date_naissance,adresse,ville,code_postal);
        Client clientRetour = service.creerClient(client);

        if (clientRetour == null) {
            request.setAttribute("erreur", true);
        }
        System.out.println(request.getAttribute("erreur"));
    }

}
