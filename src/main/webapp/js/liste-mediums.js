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

/*function afficherListeMediums() {
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
                    console.log(valeurCourante);
                    accumulateur += `<div class='box'><li class="carte-medium"><div style="display: flex; align-items: center">`;
                    if(valeurCourante.genre === 'Femme') {
                        accumulateur += `<img class='image is-16x16' src='./img/f.png' />`;
                    }else{
                        accumulateur += `<img class='image is-16x16' src='./img/h.png' />`;
                    }
                    accumulateur += ` <p class="texte" style="width: -moz-fit-content; font-size: 1.3em;"> ${valeurCourante.designation} | 
                        ${valeurCourante.type}     </p>
                        <img  class="image is-16x16" onClick="voirDetailsMedium(${valeurCourante.id});afficherDetails()" src="./img/loupe.png" 
                         style="margin-left: 1em"/></div>
                        <p class="texte">&#10077; ${valeurCourante.presentation} ❞</p>
                        </li></div>`;
                return accumulateur;
                    }, "");
                    $("#liste-mediums").html(htmlMediumsListe);
                } else {
                }
            })
            .fail(function (error) {
            })
            .always(function () {
            });

}*/