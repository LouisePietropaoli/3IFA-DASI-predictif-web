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