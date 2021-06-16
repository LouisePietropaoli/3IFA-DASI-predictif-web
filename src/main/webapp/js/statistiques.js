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
                    var position = 1;
                    $.each(response.classementMediums, function(i)
                    {
                        var div1 = $('<div>', {
                            "class": "box", 
                        })
                                .appendTo(cList); 
                        
                        var li = $('<li/>')
                                .appendTo(div1);

                        var div = $("<div/>", {
                            "style": "display: flex; align-items: center", 
                        }).appendTo(li);
                        
                        var p = $("<p/>", {
                            "class": "texte",
                            "style": "width: -moz-fit-content; font-size: 1.4em"
                        }).text(position + " - " + response.classementMediums[i].name + " | " + response.classementMediums[i].type)
                                .appendTo(div);
                        
                         var img = $("<img/>", {
                            "onclick": "afficherDetailsMedium()",  //TODO : ID MEDIUM
                            "class" : "image is-16x16",
                            "src": "./img/loupe.png",
                            "style": "margin-left: 1em"
                        }).appendTo(div);
                        
                        var p = $("<p/>", {
                            "class": "texte",
                            "style": "width: -moz-fit-content"
                        }).text("Nombre de consultations : " +response.classementMediums[i].nbConsultations)
                                .appendTo(li);
                        
                        position++;
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