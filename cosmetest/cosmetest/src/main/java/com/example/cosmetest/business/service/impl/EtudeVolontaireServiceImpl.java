package com.example.cosmetest.business.service.impl;

import com.example.cosmetest.business.dto.EtudeVolontaireDTO;
import com.example.cosmetest.business.mapper.EtudeVolontaireMapper;
import com.example.cosmetest.business.service.EtudeVolontaireService;
import com.example.cosmetest.data.repository.EtudeVolontaireRepository;
import com.example.cosmetest.domain.model.EtudeVolontaire;
import com.example.cosmetest.domain.model.EtudeVolontaireId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation de la couche business (BLL) pour les associations
 * étude-volontaire
 * Contient toute la logique métier liée aux associations étude-volontaire
 */
@Service
public class EtudeVolontaireServiceImpl implements EtudeVolontaireService {

    private final EtudeVolontaireRepository etudeVolontaireRepository;
    private final EtudeVolontaireMapper etudeVolontaireMapper;

    @Autowired
    public EtudeVolontaireServiceImpl(EtudeVolontaireRepository etudeVolontaireRepository,
            EtudeVolontaireMapper etudeVolontaireMapper) {
        this.etudeVolontaireRepository = etudeVolontaireRepository;
        this.etudeVolontaireMapper = etudeVolontaireMapper;
    }

    @Override
    public List<EtudeVolontaireDTO> getAllEtudeVolontaires() {
        return etudeVolontaireRepository.findAll().stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EtudeVolontaireDTO> getAllEtudeVolontairesPaginated(Pageable pageable) {
        return etudeVolontaireRepository.findAll(pageable)
                .map(etudeVolontaireMapper::toDto);
    }

    @Override
    public Optional<EtudeVolontaireDTO> getEtudeVolontaireById(EtudeVolontaireId id) {
        return etudeVolontaireRepository.findById(id)
                .map(etudeVolontaireMapper::toDto);
    }

    @Override
    public List<EtudeVolontaireDTO> getEtudeVolontairesByEtude(int idEtude) {
        return etudeVolontaireRepository.findByIdEtude(idEtude).stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudeVolontaireDTO> getEtudeVolontairesByVolontaire(int idVolontaire) {
        return etudeVolontaireRepository.findByIdVolontaire(idVolontaire).stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudeVolontaireDTO> getEtudeVolontairesByGroupe(int idGroupe) {
        return etudeVolontaireRepository.findByIdGroupe(idGroupe).stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudeVolontaireDTO> getEtudeVolontairesByEtudeAndVolontaire(int idEtude, int idVolontaire) {
        return etudeVolontaireRepository.findByIdEtudeAndIdVolontaire(idEtude, idVolontaire).stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudeVolontaireDTO> getEtudeVolontairesByEtudeAndGroupe(int idEtude, int idGroupe) {
        return etudeVolontaireRepository.findByIdEtudeAndIdGroupe(idEtude, idGroupe).stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudeVolontaireDTO> getEtudeVolontairesByStatut(String statut) {
        if (statut == null || statut.trim().isEmpty()) {
            throw new IllegalArgumentException("Le statut ne peut pas être vide");
        }

        return etudeVolontaireRepository.findByStatut(statut).stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EtudeVolontaireDTO> getEtudeVolontairesByPaye(int paye) {
        // Validation de la valeur de paye
        if (paye != 0 && paye != 1) {
            throw new IllegalArgumentException("La valeur de paye doit être 0 ou 1");
        }

        return etudeVolontaireRepository.findByPaye(paye).stream()
                .map(etudeVolontaireMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EtudeVolontaireDTO saveEtudeVolontaire(EtudeVolontaireDTO etudeVolontaireDTO) {
        // Validation des données
        validateEtudeVolontaireData(etudeVolontaireDTO);

        // Conversion en entité
        EtudeVolontaire etudeVolontaire = etudeVolontaireMapper.toEntity(etudeVolontaireDTO);

        // Sauvegarde
        EtudeVolontaire savedEtudeVolontaire = etudeVolontaireRepository.save(etudeVolontaire);

        // Conversion en DTO pour retour
        return etudeVolontaireMapper.toDto(savedEtudeVolontaire);
    }

    @Override
    @Transactional
    public void deleteEtudeVolontaire(EtudeVolontaireId id) {
        if (!etudeVolontaireRepository.existsById(id)) {
            throw new IllegalArgumentException("L'association étude-volontaire avec l'ID spécifié n'existe pas");
        }

        etudeVolontaireRepository.deleteById(id);
    }

    @Override
    public boolean existsByEtudeAndVolontaire(int idEtude, int idVolontaire) {
        return etudeVolontaireRepository.existsByIdEtudeAndIdVolontaire(idEtude, idVolontaire);
    }

    @Override
    public Long countVolontairesByEtude(int idEtude) {
        return etudeVolontaireRepository.countVolontairesByEtude(idEtude);
    }

    @Override
    public Long countEtudesByVolontaire(int idVolontaire) {
        return etudeVolontaireRepository.countEtudesByVolontaire(idVolontaire);
    }

    @Override
    @Transactional
    public EtudeVolontaireDTO updateStatut(EtudeVolontaireId id, String nouveauStatut) {
        // Validation du statut
        if (nouveauStatut == null || nouveauStatut.trim().isEmpty()) {
            throw new IllegalArgumentException("Le statut ne peut pas être vide");
        }

        // Vérification de l'existence de l'association
        EtudeVolontaire etudeVolontaire = etudeVolontaireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "L'association étude-volontaire avec l'ID spécifié n'existe pas"));

        // Mise à jour du statut
        etudeVolontaire.getId().setStatut(nouveauStatut);

        // Sauvegarde
        EtudeVolontaire updatedEtudeVolontaire = etudeVolontaireRepository.save(etudeVolontaire);

        // Conversion en DTO pour retour
        return etudeVolontaireMapper.toDto(updatedEtudeVolontaire);
    }

    @Override
    @Transactional
    public EtudeVolontaireDTO updatePaye(EtudeVolontaireId id, int paye) {
        // Validation de la valeur de paye
        if (paye != 0 && paye != 1) {
            throw new IllegalArgumentException("La valeur de paye doit être 0 ou 1");
        }

        // Vérification de l'existence de l'association
        EtudeVolontaire etudeVolontaire = etudeVolontaireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "L'association étude-volontaire avec l'ID spécifié n'existe pas"));

        // Mise à jour du paye
        etudeVolontaire.getId().setPaye(paye);

        // Sauvegarde
        EtudeVolontaire updatedEtudeVolontaire = etudeVolontaireRepository.save(etudeVolontaire);

        // Conversion en DTO pour retour
        return etudeVolontaireMapper.toDto(updatedEtudeVolontaire);
    }

    // Méthodes privées pour la logique métier

    /**
     * Valide les données d'une association étude-volontaire avant sauvegarde
     * 
     * @param dto DTO à valider
     */
    private void validateEtudeVolontaireData(EtudeVolontaireDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException(
                    "Les données de l'association étude-volontaire ne peuvent pas être nulles");
        }

        // Validation des identifiants
        if (dto.getIdEtude() <= 0) {
            throw new IllegalArgumentException("L'identifiant de l'étude doit être un nombre positif");
        }

        if (dto.getIdVolontaire() <= 0) {
            throw new IllegalArgumentException("L'identifiant du volontaire doit être un nombre positif");
        }

        if (dto.getIdGroupe() < 0) {
            throw new IllegalArgumentException("L'identifiant du groupe doit être un nombre positif ou zéro");
        }

        // Validation du statut
        if (dto.getStatut() == null || dto.getStatut().trim().isEmpty()) {
            throw new IllegalArgumentException("Le statut ne peut pas être vide");
        }

        // Liste des statuts valides (à adapter selon vos besoins)
        List<String> statutsValides = List.of("INSCRIT", "CONFIRME", "ANNULE", "TERMINE", "RESERVE");
        if (!statutsValides.contains(dto.getStatut().toUpperCase())) {
            throw new IllegalArgumentException("Statut non valide: " + dto.getStatut() +
                    ". Les statuts valides sont: " + String.join(", ", statutsValides));
        }

        // Validation de paye
        if (dto.getPaye() != 0 && dto.getPaye() != 1) {
            throw new IllegalArgumentException("La valeur de paye doit être 0 ou 1");
        }
    }

    @Override
    public int getIVById(EtudeVolontaireId id) {
        // Vérification de l'existence de l'association
        EtudeVolontaire etudeVolontaire = etudeVolontaireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "L'association étude-volontaire avec l'ID spécifié n'existe pas"));

        // Retour de la valeur IV
        return etudeVolontaire.getId().getIv();
    }

    // Dans EtudeVolontaireServiceImpl.java - Méthodes updateIV corrigées

    @Override
    @Transactional
    public EtudeVolontaireDTO updateIV(EtudeVolontaireId id, int nouvelIV) {
        // Validation de la valeur de IV
        if (nouvelIV < 0) {
            throw new IllegalArgumentException("La valeur de l'indemnité volontaire doit être positive ou nulle");
        }

        // Vérification de l'existence de l'association
        EtudeVolontaire etudeVolontaire = etudeVolontaireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "L'association étude-volontaire avec l'ID spécifié n'existe pas"));

        // Si l'IV ne change pas, retourner l'entité actuelle
        if (id.getIv() == nouvelIV) {
            return etudeVolontaireMapper.toDto(etudeVolontaire);
        }

        // Vérifier si une association avec la nouvelle IV existe déjà
        EtudeVolontaireId nouveauId = new EtudeVolontaireId(
                id.getIdEtude(),
                id.getIdGroupe(),
                id.getIdVolontaire(),
                nouvelIV, // Nouvelle IV
                id.getNumsujet(),
                id.getPaye(),
                id.getStatut());

        if (etudeVolontaireRepository.existsById(nouveauId)) {
            throw new IllegalArgumentException(
                    "Une association avec l'IV " + nouvelIV + " existe déjà pour ce volontaire dans cette étude");
        }

        // *** MÉTHODE SUPPRIMER/RECRÉER ***

        // 1. Supprimer l'ancienne association
        etudeVolontaireRepository.deleteById(id);

        // 2. Créer une nouvelle association avec la nouvelle IV
        EtudeVolontaire nouvelleAssociation = new EtudeVolontaire(nouveauId);

        // 3. Sauvegarder la nouvelle association
        EtudeVolontaire savedEtudeVolontaire = etudeVolontaireRepository.save(nouvelleAssociation);

        return etudeVolontaireMapper.toDto(savedEtudeVolontaire);
    }

    @Override
    @Transactional
    public EtudeVolontaireDTO updatePayeAndIV(EtudeVolontaireId id, int paye, int nouvelIV) {
        // Validation de la valeur de paye
        if (paye != 0 && paye != 1) {
            throw new IllegalArgumentException("La valeur de paye doit être 0 ou 1");
        }

        // Validation de la valeur de IV
        if (nouvelIV < 0) {
            throw new IllegalArgumentException("La valeur de l'indemnité volontaire doit être positive ou nulle");
        }

        // Vérification de l'existence de l'association
        EtudeVolontaire etudeVolontaire = etudeVolontaireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "L'association étude-volontaire avec l'ID spécifié n'existe pas"));

        // Si rien ne change, retourner l'entité actuelle
        if (id.getIv() == nouvelIV && id.getPaye() == paye) {
            return etudeVolontaireMapper.toDto(etudeVolontaire);
        }

        // Créer le nouvel ID avec les nouvelles valeurs
        EtudeVolontaireId nouveauId = new EtudeVolontaireId(
                id.getIdEtude(),
                id.getIdGroupe(),
                id.getIdVolontaire(),
                nouvelIV, // Nouvelle IV
                id.getNumsujet(),
                paye, // Nouveau paye
                id.getStatut());

        // Vérifier si une association avec les nouvelles valeurs existe déjà
        if (!nouveauId.equals(id) && etudeVolontaireRepository.existsById(nouveauId)) {
            throw new IllegalArgumentException(
                    "Une association avec l'IV " + nouvelIV + " et paye " + paye +
                            " existe déjà pour ce volontaire dans cette étude");
        }

        // *** MÉTHODE SUPPRIMER/RECRÉER ***

        // 1. Supprimer l'ancienne association
        etudeVolontaireRepository.deleteById(id);

        // 2. Créer une nouvelle association avec les nouvelles valeurs
        EtudeVolontaire nouvelleAssociation = new EtudeVolontaire(nouveauId);

        // 3. Sauvegarder la nouvelle association
        EtudeVolontaire savedEtudeVolontaire = etudeVolontaireRepository.save(nouvelleAssociation);

        return etudeVolontaireMapper.toDto(savedEtudeVolontaire);
    }
}