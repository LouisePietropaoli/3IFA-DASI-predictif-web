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
            if(response.historiqueConsultation.length == 0){
                var li = $('<li/>')
                            .appendTo(cList);
                var aaa = $('<div/>').text("Votre historique est vide"); 
            }
                    $.each(response.historiqueConsultation, function(i)
                    {
                        var li = $('<li/>')
                            .appendTo(cList);
                    
                        var div = $("<div/>", {
                            "class" : "box", 
                        }).appendTo(li);

                        var p = $("<p/>", {
                            "class": "texte",
                            "style": "width: -moz-fit-content"
                        }).text("Le " + response.historiqueConsultation[i].dateDemande + " : " + response.historiqueConsultation[i].heureDebut + " / " 
                                + response.historiqueConsultation[i].heureFin + " avec " + response.historiqueConsultation[i].nomMedium + " " 
                                 + " " + response.historiqueConsultation[i].typeMedium)
                                .appendTo(div);
                         var img = $("<img/>", { 
                            "onclick": "voirDetailsMedium(" + response.historiqueConsultation[i].idMedium + ");afficherDetails()",  
                            "class" : "image is-16x16",
                            "src": "./img/loupe.png",
                            "style": "margin-left: 1em"
                        }).appendTo(div);
                        
                        var button = $("<button/>", {
                            "class" : "button", 
                        }).prop("disabled", !response.historiqueConsultation[i].dispo).appendTo(div);
                        
                        var span = $("<span/>", {}).text("Réserver").appendTo(button);
                    });
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

function afficherDetails(){
    var element = $('#modalDetails').addClass("is-active");
}

function cacheModale2(){
    var element = $('#modalDetails').removeClass("is-active");
}