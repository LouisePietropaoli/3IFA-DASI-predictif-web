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
                    accumulateur += `<div class='box'><li class="carte-medium"><div style="display: flex; align-items: center">`;
                    if(valeurCourante.genre === 'Femme') {
                        accumulateur += `<img class='image is-16x16' src='./img/f.png' />`;
                    }else{
                        accumulateur += `<img class='image is-16x16' src='./img/h.png' />`;
                    }
                    accumulateur += ` <p class="texte" style="width: -moz-fit-content; font-size: 1.3em;"> ${valeurCourante.designation} | 
                        ${valeurCourante.type}     </p>
                        <a href="#modal-details-medium" rel="modal:open"><img onClick="voirDetailsMedium(${valeurCourante.id})" class="image is-16x16" src="./img/loupe.png" 
                         style="margin-left: 1em"/></a></div>
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
}