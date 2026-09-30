# Notifications métier

Les notifications sont personnelles : le demandeur, le bénéficiaire ou le responsable
du traitement reçoit le message dans SIGRH et à son adresse e-mail enregistrée.
La création d'une notification dans SIGRH partage la transaction métier. L'e-mail
est envoyé de façon asynchrone après le commit. Aucun e-mail n'est envoyé en cas de rollback.

## Opérations raccordées

| Module | Événements | Destinataires |
| --- | --- | --- |
| Dossier agent | Création, y compris dossier vide | Agent propriétaire |
| Imputation / bulletin de visite | Création | Agent bénéficiaire |
| Mutation | Dépôt, transmission, décision, validation finale | Demandeur et responsables du traitement selon le circuit existant |
| Permutation | Dépôt, acceptation, validation, rejet, retour pour modification, transmission | Les deux agents pour le suivi ; responsables pour le traitement |
| Actes | Dépôt, transmission, retour pour modification, rejet, validation | Agent concerné et responsables du circuit |
| Prise en charge | Dépôt, retour pour modification, validation, rejet | Demandeur |
| Besoin en personnel | Soumission | Demandeur |
| Fiche d'établissement | Création | Chef d'établissement concerné |
| Courrier | Enregistrement, affectation prévue par le circuit, traitement | Créateur et responsables concernés |
| Expression de besoin | Soumission, traitement | Demandeur et responsables concernés |
| Campagne | Création, ouverture, clôture manuelles | Responsable ayant créé la campagne |
| Plan de formation | Création, changement de statut | Créateur du plan |
| Thème de formation | Affectation initiale du suivi | Responsable de suivi |
| Formation | Création, changement de statut, convocation, session, planning, rapport, procès-verbal d'examen | Créateur à la création ; responsable de suivi, créateur du plan et participants liés |
| Participation à une formation | Inscription, inscription définitive, changement du résultat d'admission | Participant identifié par compte ou matricule |
| Offre technique et financière | Création, changement de statut | Chef d'établissement porteur de l'offre |
| Stage | Enregistrement, autorisation, refus, affectation, attestation disponible | Demandeur ; responsables pour l'affectation |
| PTA | Création du plan, action, sous-action, dépôt d'un rapport de réalisation | Créateur et responsable de la sous-action |

Les écrans de consultation, statistiques et référentiels ne produisent pas de
notification. Les modifications ordinaires et suppressions ne sont pas incluses
dans la règle « création, affectation, validation ou rejet ». Un événement passé
n'est pas renotifié rétroactivement.

## Cas particuliers

- Un compte sans e-mail reçoit toujours sa notification dans SIGRH.
- Un demandeur de stage sans compte SIGRH reçoit uniquement l'e-mail : aucune
  notification publique n'est créée pour compenser l'absence de compte.
- Les listes de participants et de responsables d'une formation sont dédupliquées
  par identifiant utilisateur. Le service commun déduplique également un même
  destinataire, objet et message au sein d'une transaction.
- Les décisions inchangées des plans/formations/offres, stages et courriers ne
  génèrent pas une seconde notification.
- L'échec SMTP est traité par le service de messagerie existant (table FailedMail).
  Une vérification de livraison réelle reste nécessaire sur l'environnement de test.

## Vérification fonctionnelle

1. Redémarrer le backend avec les paramètres SMTP de l'environnement.
2. Utiliser deux comptes distincts : le gestionnaire et le bénéficiaire.
3. Déclencher les événements du tableau et vérifier les notifications du bénéficiaire,
   puis son e-mail (y compris les indésirables).
4. Vérifier qu'un tiers non concerné ne voit pas ces notifications.
5. Vérifier un rejet, une validation, un compte sans e-mail et une création en échec.
6. Pour les formations, utiliser un participant également responsable de suivi :
   une seule notification doit être créée pour un même événement.

Les tests automatisés utilisent des doubles du service SMTP et n'envoient aucun e-mail réel.
