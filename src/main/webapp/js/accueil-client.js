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
                .done(function () {
                    setTimeout(function () {
                        window.location = `../index.html`;
                    },
                            2500);
                }
                )
                .fail(function () { // Appel KO => erreur technique à gérer
                })
                .always(function () { // facultatif: appelé après le .done() ou le .fail()
                });
    });
});