$(document).ready(function () {
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
            $("#liste-mediums").html(afficherListeMediums(response.mediums));
        } else {
        }
    })
    .fail(function (error) {
    })
    .always(function () {
    });
    
    $('#bouton-deconnexion').on('click', () => deconnecter());

});