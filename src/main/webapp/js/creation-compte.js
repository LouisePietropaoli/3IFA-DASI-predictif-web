function validerFormulaire() {
    console.log('test');
    if (!$('#nom').val())
    {
        alert("le champ nom est requis !");
    } else if (!$('#prenom').val())
    {
        alert("le champ prenom est requis !");
    } else if (!$('#date_naissance').val())
    {
        alert("le champ date de naissance est requis !");
    } else if (!$('#adresse_mail').val())
    {
        alert("le champ adresse email est requis !");
    } else if (!$('#telephone').val())
    {
        alert("le champ telephone est requis !");
    } else if (!$('#mdp').val())
    {
        alert("le champ mot de passe est requis !");
    } else
    {
        creerCompte();
    }
}

function creerCompte() {
    const MESSAGE_SUCCES = `Votre compte a bien été créé. Rendez-vous vite sur votre espace client
pour réserver votre première consultation ! <br/>`;
    const MESAGE_ERREUR = `La création de votre compte a échoué.<br/>
                            Nous vous prions de nous excuser.`;
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
                console.log(response);

                if (!response.erreur) {
                    $('#modal-creation-compte-content').html(MESSAGE_SUCCES);
                } else {
                    $('#modal-creation-compte-content').html(MESAGE_ERREUR);
                }
                $('#modalCreationCompte').addClass("is-active");
            })
            .fail(function (error) {
            })
            .always(function () {
            });
}

function cacheModaleCreationCompte() {
    $('#modalCreationCompte').removeClass("is-active");
}