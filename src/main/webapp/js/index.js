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
            console.log(response);
            const htmlMediumsListe = response.mediums.reduce(
                    (accumulateur, valeurCourante) => {
                                    console.log(valeurCourante);
                return accumulateur +=
                        `<li class="carte-medium">
                        ${valeurCourante.genre} | ${valeurCourante.designation} | 
                        ${valeurCourante.type}<br/>
                        ${valeurCourante.presentation}
                        <button class="icone-loupe" onClick="voirDetailsMediums(this)" data-id="${valeurCourante.id}">
                        <a href="#modal-details-medium" rel="modal:open">Voir détails</a>
                        </button>
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
          $('#modal-details-medium-content').html(detailsMediumsHtml(response.medium));
            } else {
            }
        })
        .fail(function (error) {
        })
        .always(function () {
        });              
    }

function detailsMediumsHtml(medium) {
    console.log("m", medium);
    html = `Désignation: ${medium.designation} <br/> 
            Genre: ${medium.genre} <br/>
            Type: ${medium.type}<br/>`;
    if (medium.type === "Astrologue") {
        html += `Formation: ${medium.formation}<br/>
                 Année de promotion: ${medium.anneePromotion}<br/>`;
    } else if (medium.type === "Spirite") {
        html += `Support: ${medium.support}<br/>`;
    }
    html += `Présentation: ${medium.presentation}`;
    
    return html;
}