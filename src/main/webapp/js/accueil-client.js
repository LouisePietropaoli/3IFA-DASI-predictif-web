$(document).ready(function () {
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'initialser-accueil-client'
        },
        dataType: 'json'
    })
    .done(function (response) {
        if (!response.erreur) {
            $("#liste-mediums").html(afficherListeMediums(response.mediums, true));
        } else {
        }
    })
    .fail(function (error) {
    })
    .always(function () {
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

function afficherDetails(){
    var element = $('#modalDetails').addClass("is-active");
}

function cacheModale2(){
    var element = $('#modalDetails').removeClass("is-active");
}

function afficherReservation(){
    var element = $('#modalReservation').addClass("is-active");
}

function cacheModale3(){
    var element = $('#modalReservation').removeClass("is-active");
}