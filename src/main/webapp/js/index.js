$(document).ready(function () {
    //afficherListeMediums();
    $('.icone-loupe').on('click', function () {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'connecter'            },
            dataType: 'json'
        })
                .done(function (response) {
                    if (!response.erreur) {

                    } else {
                    }
                })
                .fail(function (error) {
                    $('#ligne-message').html('ERREUR: ' + error.status + ' - ' + error.statusText);
                })
                .always(function () {
                });
    });
});

function afficherListeMediums() {
                        console.log("response");
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'lister-mediums'
        },
        dataType: 'json'
    })
            .done(function (response) {
                if (!response.erreur) {
                    console.log(response);
                } else {
                }
            })
            .fail(function (error) {
            })
            .always(function () {
            });

}