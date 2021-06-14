$(document).ready(function () {
    $.ajax({
        url: './ActionServlet',
        type: 'POST',
        data: {
            todo: 'recuperer-historique-consultations-client-avec-dispo'
        },
        dataType: 'json'
    })
            .done(function (response) {
                if (!response.erreur) {
                    console.log(response);
            var cList = $('#historique');
                    $.each(response.historiqueConsultation, function(i)
                    {
                        var li = $('<li/>')
                            .appendTo(cList);
                        var aaa = $('<a/>')
                            .text(response.historiqueConsultation[i].dateDemande + " " + response.historiqueConsultation[i].heureDebut + " " + response.historiqueConsultation[i].heureFin + " " + response.historiqueConsultation[i].nomMedium + " " + response.historiqueConsultation[i].dispo + " " + response.historiqueConsultation[i].typeMedium + " " + response.historiqueConsultation[i].idMedium)
                            .appendTo(li);
                    });
                } else {
                }
            })
            .fail(function (error) {
            })
            .always(function () {
            });
});