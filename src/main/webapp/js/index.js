$(document).ready(function () {
    afficherListeMediums();
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
                        console.log("valc courante=", valeurCourante);
                return accumulateur +=
                        `<li class="carte-medium">
                        ${valeurCourante.genre} | ${valeurCourante.designation} | 
                        ${valeurCourante.type}<br/>
                        ${valeurCourante.presentation}
                        <button class="icone-loupe" onClick="voirDetailsMediums(this)" data-id="${valeurCourante.id}">Voir détails</button>
                        </li><br/>`;
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

function voirDetailsMediums(bouton) {
  $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'recuperer-details-medium',
                mediumId: bouton.dataset.id
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

