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
                    $('#nbClient').text(response.nbClientEmploye)
                    $('#pourcentageClient').text(response.pourcentageClientEmploye)
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
                        var aaa = $('<a/>')
                            .text(response.classementMediums[i].name + " " + response.classementMediums[i].nbConsultations)
                            .appendTo(li);
                    });
                }
                )
                .fail(function () { // Appel KO => erreur technique à gérer
                })
                .always(function () { // facultatif: appelé après le .done() ou le .fail()
                });
});