$(document).ready(function () {

    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'recuperer-historique-consultations-fiche-client'
        },
        dataType: 'json'
    })
            .done(function (response) {
                if (!response.erreur) {
                    var cList = $('#historique');
                    if (response.historiqueConsultation.length == 0) {
                        var li = $('<li/>')
                                .appendTo(cList);
                        var aaa = $('<div/>').text("L'historique est vide");
                    }
                    $.each(response.historiqueConsultation, function (i)
                    {
                        var li = $('<li/>')
                                .appendTo(cList);

                        var div = $("<div/>", {
                            "class": "box",
                        }).appendTo(li);

                        var p = $("<p/>", {
                            "class": "texte",
                            "style": "width: -moz-fit-content"
                        }).text("Le " + response.historiqueConsultation[i].dateDemande + " : " + response.historiqueConsultation[i].heureDebut + " / "
                                + response.historiqueConsultation[i].heureFin + " avec " + response.historiqueConsultation[i].nomMedium + " "
                                + " " + response.historiqueConsultation[i].typeMedium + " Commentaire : " + response.historiqueConsultation[i].commentaire)
                                .appendTo(div);
                        var img = $("<img/>", {
                            "onclick": "voirDetailsMedium(" + response.historiqueConsultation[i].idMedium + ");afficherDetails()",
                            "class": "image is-16x16",
                            "src": "./img/loupe.png",
                            "style": "margin-left: 1em"
                        }).appendTo(div);
                    });
                } else {
                }
            })
            .fail(function (error) {
            })
            .always(function () {
            });

    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'initialiser-accueil-employe'
        },
        dataType: 'json'
    })
            .done(function (response) {
                var consultationEnCours = response.consultationEnCours;
                if (consultationEnCours) {
                    var statutConsultation = response.statut;

                    document.getElementById('demande_prediction').disabled = false;

                    document.getElementById('fc_nom_client').textContent += response.client.prenom + " " + response.client.nom;
                    document.getElementById('fc_date_naissance').textContent += response.client.date_naissance;

                    document.getElementById('fc_adresse').textContent += response.client.adresse;
                    if (response.client.adresse != "" && (response.client.ville || response.client.code_postal)) {
                        document.getElementById('fc_adresse').textContent += ", ";
                    }
                    if (response.client.code_postal != undefined) {
                        document.getElementById('fc_adresse').textContent += response.client.code_postal;
                        if (response.client.ville != "") {
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




                    if (statutConsultation === "DEMANDEE") {
                        document.getElementById('aucune_consultation').style.display = 'none';
                        document.getElementById('demande_consultation').style.display = 'block';
                        document.getElementById('en_consultation').style.display = 'none';
                        document.getElementById('fiche_client').style.display = 'block';

                        document.getElementById('heure_demande').textContent += response.heure_demande;
                        document.getElementById('nom_client').textContent += response.client.prenom + " " + response.client.nom;
                        document.getElementById('medium').innerHTML += response.medium.designation + " | " +
                                response.medium.type;
                        $("<img/>", {
                            "onclick": "voirDetailsMedium(" + response.medium.id + ");afficherDetails()",
                            "class": "image is-16x16",
                            "src": "./img/loupe.png",
                            "style": "margin-left: 1em"
                        }).appendTo(document.getElementById('medium-div'));

                    } else {
                        document.getElementById('aucune_consultation').style.display = 'none';
                        document.getElementById('demande_consultation').style.display = 'none';
                        document.getElementById('en_consultation').style.display = 'block';
                        document.getElementById('fiche_client').style.display = 'block';

                        document.getElementById('heure_debut').textContent += response.heure_debut;
                        document.getElementById('nom_client2').textContent += response.client.prenom + " " + response.client.nom;
                        document.getElementById('medium2').textContent += response.medium.designation;
                        $("<img/>", {
                            "onclick": "voirDetailsMedium(" + response.medium.id + ");afficherDetails()",
                            "class": "image is-16x16",
                            "src": "./img/loupe.png",
                            "style": "margin-left: 1em"
                        }).appendTo(document.getElementById('medium-2-div'));                    }
                } else {
                    document.getElementById('aucune_consultation').style.display = 'block';
                    document.getElementById('demande_consultation').style.display = 'none';
                    document.getElementById('en_consultation').style.display = 'none';
                    document.getElementById('fiche_client').style.display = 'none';
                    document.getElementById('demande_prediction').disabled = true;
                }
            }
            )
            .fail(function () {
            })
            .always(function () {
            });


    $('#bouton-deconnexion').on('click', () => deconnecter());

    $('#commencer_consultation').on('click', function () {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'commencer-consultation'
            },
            dataType: 'json'
        })
                .done(function () {
                    window.location.reload();
                }
                )
                .fail(function () {
                })
                .always(function () {
                });
    });

    $('#terminer_consultation').on('click', function () {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'terminer-consultation',
                commentaire: $('#commentaire').val()
            },
            dataType: 'json'
        })
                .done(function () {
                    window.location.reload();
                }
                )
                .fail(function () {
                })
                .always(function () {
                });
    });

    $('#demande_prediction').on('click', function () {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'demander-predictions',
                amour: $('#amour').val(),
                sante: $('#sante').val(),
                travail: $('#travail').val()
            },
            dataType: 'json'
        })
                .done(function (response) {
                    document.getElementById('amour_res').textContent = '"' + response.amour + '"';
                    document.getElementById('sante_res').textContent = '"' + response.sante + '"';
                    document.getElementById('travail_res').textContent = '"' + response.travail + '"';
                }
                )
                .fail(function () {
                })
                .always(function () {
                });
    });

});


function afficherHistoriqueConsultation() {
    var element = $('#modalHisto').addClass("is-active");
}

function cacheModale() {
    var element = $('#modalHisto').removeClass("is-active");
}

function afficherDetails() {
    var element = $('#modalDetails').addClass("is-active");
}

function cacheModale2() {
    var element = $('#modalDetails').removeClass("is-active");
}