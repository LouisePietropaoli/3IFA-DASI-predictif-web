/**
 * 
 * fonctions communes dans l'application
 */

function voirDetailsMedium(idMedium) {
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'recuperer-details-medium',
            mediumId: idMedium
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

function afficherListeMediums(mediums, afficheReserver = false) {
    const htmlMediumsListe = mediums.reduce(
            (accumulateur, valeurCourante) => {
        console.log(valeurCourante);
        accumulateur += `<div class='box'><li class="carte-medium"><div style="display: flex; align-items: center">`;
        if (valeurCourante.genre === 'Femme') {
            accumulateur += `<img class='image is-16x16' src='./img/f.png' />`;
        } else {
            accumulateur += `<img class='image is-16x16' src='./img/h.png' />`;
        }
        accumulateur += ` <p class="texte" style="width: -moz-fit-content; font-size: 1.3em;"> ${valeurCourante.designation} | 
                        ${valeurCourante.type}     </p>
                        <img onClick="voirDetailsMedium(${valeurCourante.id});afficherDetails()" class="image is-16x16" src="./img/loupe.png" 
                         style="margin-left: 1em"/></a>`;

        if (afficheReserver && valeurCourante.estDispo) {
            accumulateur += `<button id="medium-${valeurCourante.id}" class="button is-small" style="margin-left: 1em"
                        onClick="reserverMedium(${valeurCourante.id});afficherReservation()" >
                         <span>Réserver</span>
                       </button>`;
        } else if (afficheReserver) {
            accumulateur += `<button id="medium-${valeurCourante.id}" class="button is-small" style="margin-left: 1em" disabled >
                         Réserver
                       </button>`;
        }
        accumulateur += `</div>
                        <p class="texte">&#10077; ${valeurCourante.presentation} ❞</p>
                        </li></div>`;
        return accumulateur;
    }, "");

    return htmlMediumsListe;
}

function reserverMedium(idMedium) {
    const MESSAGE_SUCCES = `Votre consultation est réservée. <br/>Votre médium 
                            vous enverra ses coordonnées 
                            pour que vous puissez le contacter.`;
    const MESAGE_ERREUR = `Le médium choisi a été réservé entre temps. <br/>
                            Votre réservation n'a pas pu être prise en compte. Nous vous prions de
                             nous excuser.`;
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'reserver-medium',
            idMedium: idMedium
        },
        dataType: 'json'
    })
            .done(function (response) {
                console.log(response);

                if (!response.erreur) {
                    $('#modal-reservation-content').html(MESSAGE_SUCCES);
                } else {
                    $('#modal-reservation-content').html(MESAGE_ERREUR);
                }
            })
            .fail(function (error) {
            })
            .always(function () {
                $(`#medium-${idMedium}`).prop("disabled", true);
                //suppression de la balise <a>
                $(`#medium-${idMedium}`).html("Réserver");
            });
}


function afficherDetails() {
    var element = $('#modalDetails').addClass("is-active");
}

function cacheModale2() {
    var element = $('#modalDetails').removeClass("is-active");
}

function deconnecter() {
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
}