$(document).ready(function () {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'recuperer-statistiques'
            },
            dataType: 'json'
        })
                .done(function (response) {
                    console.log(response);
                    document.getElementById('nbConsultations').textContent += response.nbConsultationsEmploye;
                    document.getElementById('nbClient').textContent += response.nbClientEmploye;
                    document.getElementById('pourcentageClient').textContent += response.pourcentageClientEmploye;
                }
                )
                .fail(function () { // Appel KO => erreur technique à gérer
                })
                .always(function () { // facultatif: appelé après le .done() ou le .fail()
                });
                
                 $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'recuperer-classement-medium'
            },
            dataType: 'json'
        })
                .done(function (response) {
                    console.log(response);
                    var cList = $('#mediumlist');
                    $.each(response.classementMediums, function(i)
                    {
                        var li = $('<li/>')
                                .appendTo(cList);

                        var div = $("<div/>", {
                            "style": "display: flex; align-items: center", 
                        }).appendTo(li);

                        var p = $("<p/>", {
                            "class": "texte",
                            "style": "width: -moz-fit-content"
                        }).text(response.classementMediums[i].name + " " + response.classementMediums[i].type + " " +response.classementMediums[i].nbConsultations)
                                .appendTo(div);
                         var img = $("<img/>", {
                            "onclick": "voirDetailsMedium(" + response.classementMediums[i].id + ");afficherDetails()",  
                            "class" : "image is-16x16",
                            "src": "./img/loupe.png",
                            "style": "margin-left: 1em"
                        }).appendTo(div);
                    });
                }
                )
                .fail(function () { // Appel KO => erreur technique à gérer
                })
                .always(function () { // facultatif: appelé après le .done() ou le .fail()
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