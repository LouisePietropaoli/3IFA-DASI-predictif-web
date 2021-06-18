$(document).ready(function () {
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'initialiser-accueil-client'
        },
        dataType: 'json'
    })
            .done(function (response) {
                if (!response.erreur) {
                    $("#liste-mediums").html(afficherListeMediums(response.mediums, true));
                    afficheProfilAstral(response.client);
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

function afficheProfilAstral(client) {
    document.getElementById('signe_zodiaque').textContent += client.signeZodiaque;
    document.getElementById('img-signe').src = "./img/signes/" + client.signeZodiaque+".png";
    document.getElementById('signe_chinois').textContent += client.signeAstro;
    document.getElementById('couleur_porte_bonheur').textContent += client.couleur;
    document.getElementById('animal_totem').textContent += client.animalTotem;
}