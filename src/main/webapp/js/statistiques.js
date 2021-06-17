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
                    var valuesStats = [];
                    
                    console.log(response);
                    var cList = $('#mediumlist');
                    var position = 1;
                    $.each(response.classementMediums, function(i)
                    {
                        valuesStats.push({label: response.classementMediums[i].name, y: response.classementMediums[i].nbConsultations });
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
                            "onclick": "voirDetailsMedium(" + response.classementMediums[i].id + ");afficherDetails()",  
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
                    
                                var options1 = {
                   animationEnabled: true,
                   title: {
                           text: "Nombre de consultations par employé"
                   },
                   data: [{
                           type: "column", 
                           
                           dataPoints: valuesStats
                           }]
           };

           $("#resizable").resizable({
                   create: function (event, ui) {
                           //Create chart.
                           $("#chartContainer1").CanvasJSChart(options1);
                   },
                   resize: function (event, ui) {
                           //Update chart size according to its container size.
                           $("#chartContainer1").CanvasJSChart().render();
                   }
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