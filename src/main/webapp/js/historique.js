$(document).ready(function () {
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'recuperer-historique-consultations-client-avec-dispo'
        },
        dataType: 'json'
    })
            .done(function (response) {
                if (!response.erreur) {
                    var cList = $('#historique');
                    if (response.historiqueConsultation.length == 0) {
                        $('#titre-historique').text('Votre historique est vide.');
                    }
                    $.each(response.historiqueConsultation, function (i)
                    {
                        var li = $('<li/>')
                                .appendTo(cList);

                        var div = $("<div/>", {
                            "class": "box",
                        }).appendTo(li);

                        var p = $("<p/>", {
                            "class": "title is-6",
                            "style": "font-size: 1.3em"
                        }).text("Le " + response.historiqueConsultation[i].dateDemande + " : " + response.historiqueConsultation[i].heureDebut + " - "
                                + response.historiqueConsultation[i].heureFin)
                                .appendTo(div);
                        
                        var div2 = $("<div/>", {
                            "style": "display: flex; align-items: center; margin-left: 7em",
                        }).appendTo(div);
                        
                        var p2 = $("<p/>", {
                            "class": "texte",
                            "style": "width: -moz-fit-content; font-size: 1.2em"
                        }).text(" avec " + response.historiqueConsultation[i].nomMedium + " "
                                + " " + response.historiqueConsultation[i].typeMedium)
                                .appendTo(div2);
                        
                        var img = $("<img/>", {
                            "onclick": "voirDetailsMedium(" + response.historiqueConsultation[i].idMedium + ");afficherDetails()",
                            "class": "image is-16x16",
                            "src": "./img/loupe.png",
                            "style": "margin-left: 1em"
                        }).appendTo(div2);

                        var button = $("<button/>", {
                            "class": "button",
                            "style": "margin-left: 1em",
                            "onclick": "declencherReserverMedium(" + response.historiqueConsultation[i].dispo + ", " + response.historiqueConsultation[i].idMedium + ")"
                        }).prop("disabled", !response.historiqueConsultation[i].dispo)
                                .appendTo(div2);

                        var span = $("<span/>", {}).text("Réserver").appendTo(button);
                    });
                } else {
                }
            })
            .fail(function (error) {
            })
            .always(function () {
            });
    $('#bouton-deconnexion').on('click', () => deconnecter());

});

function afficherDetails() {
    var element = $('#modalDetails').addClass("is-active");
}

function cacheModale2() {
    var element = $('#modalDetails').removeClass("is-active");
}

function afficherReservation() {
    var element = $('#modalReservation').addClass("is-active");
}

function cacheModale3() {
    var element = $('#modalReservation').removeClass("is-active");
}

function declencherReserverMedium(dispo, idMedium) {
    if (dispo) {
        reserverMedium(idMedium);
        afficherReservation();
    }
}