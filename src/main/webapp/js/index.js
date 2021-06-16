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
});
    
 function afficherDetails(){
    var element = $('#modalDetails').addClass("is-active");
}

function cacheModale2(){
    var element = $('#modalDetails').removeClass("is-active");
}
