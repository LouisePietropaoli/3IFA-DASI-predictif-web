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
                        <button class="icone-loupe" onClick="voirDetailsMedium(${valeurCourante.id})" >
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