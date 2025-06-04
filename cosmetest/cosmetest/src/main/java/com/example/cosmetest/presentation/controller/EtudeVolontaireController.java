package com.example.cosmetest.presentation.controller;

import com.example.cosmetest.business.dto.EtudeVolontaireDTO;
import com.example.cosmetest.business.service.EtudeVolontaireService;
import com.example.cosmetest.domain.model.EtudeVolontaireId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour les associations étude-volontaire
 * Fait partie de la couche de présentation et gère les requêtes HTTP
 */
@RestController
@RequestMapping("/api/etude-volontaires")
public class EtudeVolontaireController {

    private final EtudeVolontaireService etudeVolontaireService;

    @Autowired
    public EtudeVolontaireController(EtudeVolontaireService etudeVolontaireService) {
        this.etudeVolontaireService = etudeVolontaireService;
    }

    /**
     * Récupère toutes les associations étude-volontaire
     * 
     * @return Liste de toutes les associations
     */
    @GetMapping
    public ResponseEntity<List<EtudeVolontaireDTO>> getAllEtudeVolontaires() {
        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService.getAllEtudeVolontaires();
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Récupère les associations avec pagination
     * 
     * @param page Numéro de page
     * @param size Taille de la page
     * @return Page d'associations
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<EtudeVolontaireDTO>> getAllEtudeVolontairesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EtudeVolontaireDTO> etudeVolontairesPage = etudeVolontaireService
                .getAllEtudeVolontairesPaginated(pageable);

        return ResponseEntity.ok(etudeVolontairesPage);
    }

    /**
     * Récupère une association par son identifiant composite
     * 
     * @param idEtude      Identifiant de l'étude
     * @param idGroupe     Identifiant du groupe
     * @param idVolontaire Identifiant du volontaire
     * @param iv           Valeur iv
     * @param numsujet     Numéro du sujet
     * @param paye         Indicateur de paiement
     * @param statut       Statut
     * @return L'association ou 404 si non trouvée
     */
    @GetMapping("/find")
    public ResponseEntity<EtudeVolontaireDTO> getEtudeVolontaireById(
            @RequestParam int idEtude,
            @RequestParam int idGroupe,
            @RequestParam int idVolontaire,
            @RequestParam int iv,
            @RequestParam int numsujet,
            @RequestParam int paye,
            @RequestParam String statut) {

        EtudeVolontaireId id = new EtudeVolontaireId(idEtude, idGroupe, idVolontaire, iv, numsujet, paye, statut);
        Optional<EtudeVolontaireDTO> etudeVolontaire = etudeVolontaireService.getEtudeVolontaireById(id);

        return etudeVolontaire
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupère les associations par identifiant d'étude
     * 
     * @param idEtude Identifiant de l'étude
     * @return Liste des associations
     */
    @GetMapping("/etude/{idEtude}")
    public ResponseEntity<List<EtudeVolontaireDTO>> getEtudeVolontairesByEtude(
            @PathVariable int idEtude) {

        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService.getEtudeVolontairesByEtude(idEtude);
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Récupère les associations par identifiant de volontaire
     * 
     * @param idVolontaire Identifiant du volontaire
     * @return Liste des associations
     */
    @GetMapping("/volontaire/{idVolontaire}")
    public ResponseEntity<List<EtudeVolontaireDTO>> getEtudeVolontairesByVolontaire(
            @PathVariable int idVolontaire) {

        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService
                .getEtudeVolontairesByVolontaire(idVolontaire);
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Récupère les associations par identifiant de groupe
     * 
     * @param idGroupe Identifiant du groupe
     * @return Liste des associations
     */
    @GetMapping("/groupe/{idGroupe}")
    public ResponseEntity<List<EtudeVolontaireDTO>> getEtudeVolontairesByGroupe(
            @PathVariable int idGroupe) {

        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService.getEtudeVolontairesByGroupe(idGroupe);
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Récupère les associations par étude et volontaire
     * 
     * @param idEtude      Identifiant de l'étude
     * @param idVolontaire Identifiant du volontaire
     * @return Liste des associations
     */
    @GetMapping("/etude/{idEtude}/volontaire/{idVolontaire}")
    public ResponseEntity<List<EtudeVolontaireDTO>> getEtudeVolontairesByEtudeAndVolontaire(
            @PathVariable int idEtude,
            @PathVariable int idVolontaire) {

        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService
                .getEtudeVolontairesByEtudeAndVolontaire(idEtude, idVolontaire);
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Récupère les associations par étude et groupe
     * 
     * @param idEtude  Identifiant de l'étude
     * @param idGroupe Identifiant du groupe
     * @return Liste des associations
     */
    @GetMapping("/etude/{idEtude}/groupe/{idGroupe}")
    public ResponseEntity<List<EtudeVolontaireDTO>> getEtudeVolontairesByEtudeAndGroupe(
            @PathVariable int idEtude,
            @PathVariable int idGroupe) {

        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService.getEtudeVolontairesByEtudeAndGroupe(idEtude,
                idGroupe);
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Récupère les associations par statut
     * 
     * @param statut Statut recherché
     * @return Liste des associations
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<EtudeVolontaireDTO>> getEtudeVolontairesByStatut(
            @PathVariable String statut) {

        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService.getEtudeVolontairesByStatut(statut);
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Récupère les associations par indicateur de paiement
     * 
     * @param paye Indicateur de paiement
     * @return Liste des associations
     */
    @GetMapping("/paye/{paye}")
    public ResponseEntity<List<EtudeVolontaireDTO>> getEtudeVolontairesByPaye(
            @PathVariable int paye) {

        List<EtudeVolontaireDTO> etudeVolontaires = etudeVolontaireService.getEtudeVolontairesByPaye(paye);
        return ResponseEntity.ok(etudeVolontaires);
    }

    /**
     * Crée une nouvelle association étude-volontaire
     * 
     * @param etudeVolontaireDTO Données de l'association
     * @return Association créée
     */
    @PostMapping
    public ResponseEntity<EtudeVolontaireDTO> createEtudeVolontaire(
            @RequestBody EtudeVolontaireDTO etudeVolontaireDTO) {

        EtudeVolontaireDTO createdEtudeVolontaire = etudeVolontaireService.saveEtudeVolontaire(etudeVolontaireDTO);
        return new ResponseEntity<>(createdEtudeVolontaire, HttpStatus.CREATED);
    }

    /**
     * Supprime une association étude-volontaire
     * 
     * @param idEtude      Identifiant de l'étude
     * @param idGroupe     Identifiant du groupe
     * @param idVolontaire Identifiant du volontaire
     * @param iv           Valeur iv
     * @param numsujet     Numéro du sujet
     * @param paye         Indicateur de paiement
     * @param statut       Statut
     * @return Réponse vide avec statut 204
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEtudeVolontaire(
            @RequestParam int idEtude,
            @RequestParam int idGroupe,
            @RequestParam int idVolontaire,
            @RequestParam int iv,
            @RequestParam int numsujet,
            @RequestParam int paye,
            @RequestParam String statut) {

        EtudeVolontaireId id = new EtudeVolontaireId(idEtude, idGroupe, idVolontaire, iv, numsujet, paye, statut);

        etudeVolontaireService.deleteEtudeVolontaire(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Vérifie si une association existe avec les identifiants donnés
     * 
     * @param idEtude      Identifiant de l'étude
     * @param idVolontaire Identifiant du volontaire
     * @return true si l'association existe
     */
    @GetMapping("/check-existence")
    public ResponseEntity<Boolean> existsByEtudeAndVolontaire(
            @RequestParam int idEtude,
            @RequestParam int idVolontaire) {

        boolean exists = etudeVolontaireService.existsByEtudeAndVolontaire(idEtude, idVolontaire);
        return ResponseEntity.ok(exists);
    }

    /**
     * Compte le nombre de volontaires par étude
     * 
     * @param idEtude Identifiant de l'étude
     * @return Nombre de volontaires
     */
    @GetMapping("/count/volontaires/{idEtude}")
    public ResponseEntity<Long> countVolontairesByEtude(@PathVariable int idEtude) {
        Long count = etudeVolontaireService.countVolontairesByEtude(idEtude);
        return ResponseEntity.ok(count);
    }

    /**
     * Compte le nombre d'études par volontaire
     * 
     * @param idVolontaire Identifiant du volontaire
     * @return Nombre d'études
     */
    @GetMapping("/count/etudes/{idVolontaire}")
    public ResponseEntity<Long> countEtudesByVolontaire(@PathVariable int idVolontaire) {
        Long count = etudeVolontaireService.countEtudesByVolontaire(idVolontaire);
        return ResponseEntity.ok(count);
    }

    /**
     * Met à jour le statut d'une association
     * 
     * @param idEtude       Identifiant de l'étude
     * @param idGroupe      Identifiant du groupe
     * @param idVolontaire  Identifiant du volontaire
     * @param iv            Valeur iv
     * @param numsujet      Numéro du sujet
     * @param paye          Indicateur de paiement
     * @param statut        Statut actuel
     * @param nouveauStatut Nouveau statut
     * @return Association mise à jour
     */
    @PatchMapping("/update-statut")
    public ResponseEntity<EtudeVolontaireDTO> updateStatut(
            @RequestParam int idEtude,
            @RequestParam int idGroupe,
            @RequestParam int idVolontaire,
            @RequestParam int iv,
            @RequestParam int numsujet,
            @RequestParam int paye,
            @RequestParam String statut,
            @RequestParam String nouveauStatut) {

        EtudeVolontaireId id = new EtudeVolontaireId(idEtude, idGroupe, idVolontaire, iv, numsujet, paye, statut);

        EtudeVolontaireDTO updatedEtudeVolontaire = etudeVolontaireService.updateStatut(id, nouveauStatut);
        return ResponseEntity.ok(updatedEtudeVolontaire);
    }

    /**
     * Met à jour l'indicateur de paiement d'une association
     * 
     * @param idEtude      Identifiant de l'étude
     * @param idGroupe     Identifiant du groupe
     * @param idVolontaire Identifiant du volontaire
     * @param iv           Valeur iv
     * @param numsujet     Numéro du sujet
     * @param paye         Indicateur de paiement actuel
     * @param statut       Statut
     * @param nouveauPaye  Nouvel indicateur de paiement
     * @return Association mise à jour
     */
    @PatchMapping("/update-paye")
    public ResponseEntity<EtudeVolontaireDTO> updatePaye(
            @RequestParam int idEtude,
            @RequestParam int idGroupe,
            @RequestParam int idVolontaire,
            @RequestParam int iv,
            @RequestParam int numsujet,
            @RequestParam int paye,
            @RequestParam String statut,
            @RequestParam int nouveauPaye) {

        EtudeVolontaireId id = new EtudeVolontaireId(idEtude, idGroupe, idVolontaire, iv, numsujet, paye, statut);

        EtudeVolontaireDTO updatedEtudeVolontaire = etudeVolontaireService.updatePaye(id, nouveauPaye);
        return ResponseEntity.ok(updatedEtudeVolontaire);
    }

    // Ajoutez ces endpoints à la classe EtudeVolontaireController
    /**
     * Met à jour l'indemnité volontaire (IV) d'une association
     * Utilise une approche de suppression/recréation car IV fait partie de la clé
     * primaire
     */
    @PatchMapping("/update-iv")
    public ResponseEntity<?> updateIV(
            @RequestParam int idEtude,
            @RequestParam int idGroupe,
            @RequestParam int idVolontaire,
            @RequestParam int iv,
            @RequestParam int numsujet,
            @RequestParam int paye,
            @RequestParam String statut,
            @RequestParam int nouvelIV) {

        try {
            EtudeVolontaireId id = new EtudeVolontaireId(idEtude, idGroupe, idVolontaire, iv, numsujet, paye, statut);

            EtudeVolontaireDTO updatedEtudeVolontaire = etudeVolontaireService.updateIV(id, nouvelIV);
            return ResponseEntity.ok(updatedEtudeVolontaire);

        } catch (IllegalArgumentException e) {
            // Gestion des erreurs de validation et de conflit
            Map<String, Object> errorResponse = new HashMap<>();

            if (e.getMessage().contains("existe déjà")) {
                errorResponse.put("error", "CONFLICT");
                errorResponse.put("message", e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            } else if (e.getMessage().contains("n'existe pas")) {
                errorResponse.put("error", "NOT_FOUND");
                errorResponse.put("message", e.getMessage());
                return ResponseEntity.notFound().build();
            } else {
                errorResponse.put("error", "VALIDATION_ERROR");
                errorResponse.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(errorResponse);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "INTERNAL_ERROR");
            errorResponse.put("message", "Erreur interne lors de la mise à jour de l'IV");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Met à jour l'indicateur de paiement et l'IV d'une association
     */
    @PatchMapping("/update-paye-iv")
    public ResponseEntity<?> updatePayeAndIV(
            @RequestParam int idEtude,
            @RequestParam int idGroupe,
            @RequestParam int idVolontaire,
            @RequestParam int iv,
            @RequestParam int numsujet,
            @RequestParam int paye,
            @RequestParam String statut,
            @RequestParam int nouveauPaye,
            @RequestParam int nouvelIV) {

        try {
            EtudeVolontaireId id = new EtudeVolontaireId(idEtude, idGroupe, idVolontaire, iv, numsujet, paye, statut);

            EtudeVolontaireDTO updatedEtudeVolontaire = etudeVolontaireService.updatePayeAndIV(id, nouveauPaye,
                    nouvelIV);
            return ResponseEntity.ok(updatedEtudeVolontaire);

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();

            if (e.getMessage().contains("existe déjà")) {
                errorResponse.put("error", "CONFLICT");
                errorResponse.put("message", e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            } else if (e.getMessage().contains("n'existe pas")) {
                errorResponse.put("error", "NOT_FOUND");
                errorResponse.put("message", e.getMessage());
                return ResponseEntity.notFound().build();
            } else {
                errorResponse.put("error", "VALIDATION_ERROR");
                errorResponse.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(errorResponse);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "INTERNAL_ERROR");
            errorResponse.put("message", "Erreur interne lors de la mise à jour");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Récupère l'indemnité volontaire (IV) d'une association
     * 
     * @param idEtude      Identifiant de l'étude
     * @param idGroupe     Identifiant du groupe
     * @param idVolontaire Identifiant du volontaire
     * @param iv           Valeur iv
     * @param numsujet     Numéro du sujet
     * @param paye         Indicateur de paiement
     * @param statut       Statut
     * @return Valeur de l'IV
     */
    @GetMapping("/get-iv")
    public ResponseEntity<Integer> getIVById(
            @RequestParam int idEtude,
            @RequestParam int idGroupe,
            @RequestParam int idVolontaire,
            @RequestParam int iv,
            @RequestParam int numsujet,
            @RequestParam int paye,
            @RequestParam String statut) {

        EtudeVolontaireId id = new EtudeVolontaireId(idEtude, idGroupe, idVolontaire, iv, numsujet, paye, statut);

        int ivValue = etudeVolontaireService.getIVById(id);
        return ResponseEntity.ok(ivValue);
    }

}