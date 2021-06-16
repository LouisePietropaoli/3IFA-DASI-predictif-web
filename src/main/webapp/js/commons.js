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
    const htmlMediumsListe = response.mediums.reduce(
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
                        <a href="#modal-details-medium"><img onClick="voirDetailsMedium(${valeurCourante.id});afficherDetails()" class="image is-16x16" src="./img/loupe.png" 
                         style="margin-left: 1em"/></a>`;

        if (afficheReserver && valeurCourante.estDispo) {
            accumulateur += `<button class="reserver"
                        onClick="reserverMedium(${valeurCourante.id});afficherReservation()" >
                         <a href="#modal-details-medium">Réserver</a>
                       </button>`;
        } else if (afficheReserver) {
            accumulateur += `<button class="reserver" disabled >
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

function afficherListeMediums(mediums, afficheReserver = false) {
    const htmlMediumsListe = mediums.reduce(
            (accumulateur, valeurCourante) => {
        accumulateur += `<li class="carte-medium">
                ${valeurCourante.genre} | ${valeurCourante.designation} | 
                ${valeurCourante.type}<br/>
                ${valeurCourante.presentation}
                <button class="icone-loupe" onClick="voirDetailsMedium(${valeurCourante.id});afficherDetails()" >
                <a href="#modal-details-medium">Voir détails</a>
                </button>`;
        if (afficheReserver && valeurCourante.estDispo) {
            accumulateur += `<button class="reserver"
                        onClick="reserverMedium(${valeurCourante.id});afficherReservation()" >
                         <a href="#modal-details-medium">Réserver</a>
                       </button>`;
        } else if (afficheReserver) {
            accumulateur += `<button class="reserver" disabled >
                         Réserver
                       </button>`;
        }
        accumulateur += `</li><br/>`;
        return accumulateur;
    }, "");

    return htmlMediumsListe;
}

function reserverMedium(idMedium) {
    const MESSAGE_SUCCES = `Votre consultation est réservée. <br/>Votre médium 
                            vous enverra ses coordonnées 
                            pour que vous puissez le contacter.`;
    const MESAGE_ERREUR = `Le médium choisi a été réservé entre temps. <br/>
                            Votre réservation n'a paspu être prise en compte. Nous vous prions de
                             nous excuser.`;
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'reserver-medium',
            mediumId: idMedium
        },
        dataType: 'json'
    })
            .done(function (response) {
                if (!response.erreur) {
                    $('#modal-reservation-content').html(MESSAGE_SUCCES);
                } else {
                    $('#modal-reservation-content').html(MESAGE_ERREUR);
                }
            })
            .fail(function (error) {
            })
            .always(function () {
            });
}