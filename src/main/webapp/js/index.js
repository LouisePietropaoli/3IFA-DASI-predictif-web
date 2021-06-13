$(document).ready(function () {
    afficherListeMediums();
    $('.icone-loupe').on('click', function () {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: ''
            },
            dataType: 'json'
        })
                .done(function (response) {
                    if (!response.erreur) {

                    } else {
                    }
                })
                .fail(function (error) {
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