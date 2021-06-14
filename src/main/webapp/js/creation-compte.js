function validerFormulaire() {
        $.ajax({
            url: './ActionServlet',
            type: 'POST',
            data: {
                todo: 'creer-compte-client',
                nom: $('#nom').val(),
                prenom: $('#prenom').val(),
                date_naissance: $('#date_naissance').val(),
                adresse: $('#adresse').val(),
                ville: $('#ville').val(),
                code_postal: $('#code_postal').val(),
                adresse_mail: $('#adresse_mail').val(),
                telephone: $('#telephone').val(),
                mdp: $('#mdp').val()           
            },
            dataType: 'json'
        })
                .done(function (response) {
                    if (!response.erreur) {
                        document.getElementById('modal_success').style.visibility= "visible";
                    } else {
                        document.getElementById('moda_error').style.visibility= "visible";
                    }
                })
                .fail(function (error) {
                        console.log("fail");
                })
                .always(function () {
                        console.log(" always");
                });
    ;
}