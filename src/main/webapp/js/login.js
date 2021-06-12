$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const mode = urlParams.get('mode');
    $('#connexion-titre').html(mode === 'client' ? 'Connexion' : 'Connexion employé');

    $('#bouton-connexion').on('click', function () {
        $.ajax({
            url: '../ActionServlet',
            type: 'POST',
            data: {
                todo: 'connecter',
                email: $('#champ-email').val(),
                motDePasse: $('#champ-mot-de-passe').val(),
                mode: mode
            },
            dataType: 'json'
        })
                .done(function (response) {
                    if (!response.erreur) {
                        $('#ligne-message').html('Connexion réussie');
                        setTimeout(function () { // délai avant redirection
                            const redirection = mode === "client" ? "client" : "employe";
                            window.location = `accueil-${redirection}.html`;
                        },
                                2500); // délai de 2.5s
                    } else {
                        $('#ligne-message').html('ERREUR: identifiant / mot de passe incorrects');
                    }
                })
                .fail(function (error) { // Appel KO => erreur technique à gérer
                    $('#ligne-message').html('ERREUR: ' + error.status + ' - ' + error.statusText);
                })
                .always(function () { // facultatif: appelé après le .done() ou le .fail()
                });
    });
    // Amélioration: Appui sur la touche ENTER dans un champ texte => Clic sur le bouton
    $('.formulaire-connexion').on('keyup', 'input', function (event) {
        if (event.which === 13) { // Touche ENTER
            $(this).closest('.formulaire-connexion').find('button').first().trigger('click');
        }
    });
});