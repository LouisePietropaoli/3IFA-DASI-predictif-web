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

function afficherListeMediums() {
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
                    let = "";
                    console.log(response);
                    const htmlMediumsListe = response.mediums.reduce(
                            (accumulateur, valeurCourante) => {
                        return accumulateur +=
                                `<li class="carte-medium" data-id=
                                "${valeurCourante.id}">
                                ${valeurCourante.genre} | ${valeurCourante.designation} | 
                                ${valeurCourante.type}<br/>
                                ${valeurCourante.presentation}</li><br/>`;
                    }, "");
                    $("#liste-mediums").html(htmlMediumsListe);
                } else {
                }
            })
            .fail(function (error) {
            })
            .always(function () {
            });

}