

$(document).ready(function () {
    $('#bouton-deconnexion').on('click', function () {
        $.ajax({
            url: '../ActionServlet',
            type: 'POST',
            data: {
                todo: 'deconnecter'
            },
            dataType: 'json'
        })
                .done(function (response) {
                    window.location = `../index.html`;
                }
                )
                .fail(function (error) { // Appel KO => erreur technique à gérer
                    $('#ligne-message').html('ERREUR: ' + error.status + ' - ' + error.statusText);
                })
                .always(function () { // facultatif: appelé après le .done() ou le .fail()
                });
    });
});