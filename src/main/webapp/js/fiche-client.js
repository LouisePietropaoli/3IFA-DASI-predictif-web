$(document).ready(function () {
    $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'recuperer-details-profil-client'
            },
            dataType: 'json'
                })
                .done(function (response) {
                    if (!response.erreur) {
                        document.getElementById('nom').textContent += response.client.nom;
                        document.getElementById('prenom').textContent += response.client.prenom;
                        document.getElementById('date_naissance').textContent += response.client.date_naissance;
                        
                        document.getElementById('adresse').textContent += response.client.adresse;
                        if(response.client.adresse != "" && ( response.client.ville || response.client.code_postal ) ) {
                            document.getElementById('adresse').textContent += ", ";
                        }
                        if(response.client.code_postal != undefined) {
                            document.getElementById('adresse').textContent += response.client.code_postal;
                            if(response.client.ville != "") {
                                document.getElementById('adresse').textContent += ", ";
                            }
                        }
                        document.getElementById('adresse').textContent += response.client.ville.toUpperCase();
                        
                        document.getElementById('mail').textContent += response.client.mail;
                        document.getElementById('telephone').textContent += response.client.telephone;
                }
                })
                .fail(function (response) { // Appel KO => erreur technique à gérer
                    console.log("Fail in ajax")
                })
                .always(function (response) { // facultatif: appelé après le .done() ou le .fail()
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