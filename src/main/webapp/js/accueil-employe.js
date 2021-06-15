$(document).ready(function () {
    $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'initialiser-accueil-employe'
            },
            dataType: 'json'
        })
                .done(function (response) {
                    console.log(response);
                    var consultationEnCours = response.consultationEnCours;
                    if(consultationEnCours) {
                        var statutConsultation = response.statut; 
                        
                        document.getElementById('button_prediction').disabled = false;
                        
                        document.getElementById('fc_nom_client').textContent += response.client.prenom + " " + response.client.nom;
                        document.getElementById('fc_date_naissance').textContent += response.client.date_naissance;
                        
                        document.getElementById('fc_adresse').textContent += response.client.adresse;
                        if(response.client.adresse != "" && ( response.client.ville || response.client.code_postal ) ) {
                            document.getElementById('fc_adresse').textContent += ", ";
                        }
                        if(response.client.code_postal != undefined) {
                            document.getElementById('fc_adresse').textContent += response.client.code_postal;
                            if(response.client.ville != "") {
                                document.getElementById('fc_adresse').textContent += ", ";
                            }
                        }
                        document.getElementById('fc_adresse').textContent += response.client.ville.toUpperCase();
                        
                        document.getElementById('fc_adresse_mail').textContent += response.client.mail;
                        document.getElementById('fc_telephone').textContent += response.client.telephone;
                        document.getElementById('signe_zodiaque').textContent += response.client.signe_zodiaque;
                        document.getElementById('signe_chinois').textContent += response.client.signe_chinois;
                        document.getElementById('couleur_porte_bonheur').textContent += response.client.couleur_bonheur;
                        document.getElementById('animal_totem').textContent += response.client.animal_totem;
                        
                        
                        
                        
                        if(statutConsultation === "DEMANDEE") {
                            console.log("consultation demandée");
                            document.getElementById('aucune_consultation').style.display = 'none';
                            document.getElementById('demande_consultation').style.display = 'block';
                            document.getElementById('en_consultation').style.display = 'none';
                            document.getElementById('fiche_client').style.display = 'block';
                            
                            document.getElementById('heure_demande').textContent += response.heure_demande;
                            document.getElementById('nom_client').textContent += response.client.prenom + " " + response.client.nom;
                            document.getElementById('medium').innerHTML += response.medium.designation + " | " +
                                    response.medium.type;
                        }else {
                            console.log("consultation en cours");
                            document.getElementById('aucune_consultation').style.display = 'none';
                            document.getElementById('demande_consultation').style.display = 'none';
                            document.getElementById('en_consultation').style.display = 'block';
                            document.getElementById('fiche_client').style.display = 'block';
                            
                            document.getElementById('heure_debut').textContent += response.heure_debut;
                            document.getElementById('nom_client2').textContent += response.client.prenom + " " + response.client.nom;
                            document.getElementById('medium2').textContent += response.medium.designation;
                        }
                    }else{
                        console.log("pas de consultation en cours");
                        document.getElementById('aucune_consultation').style.display = 'block';
                        document.getElementById('demande_consultation').style.display = 'none';
                        document.getElementById('en_consultation').style.display = 'none';
                        document.getElementById('fiche_client').style.display = 'none';
                        document.getElementById('button_prediction').disabled = true;
                    }
                }
                )
                .fail(function () {
                    console.log("fail");
                })
                .always(function () { 
                    console.log("always");
                });
    
    
    $('#bouton-deconnexion').on('click', function () {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'deconnecter'
            },
            dataType: 'json'
        })
                .done(function () {
                    window.location = `index.html`;
                }
                )
                .fail(function () { // Appel KO => erreur technique à gérer
                })
                .always(function () { // facultatif: appelé après le .done() ou le .fail()
                });
    });
});