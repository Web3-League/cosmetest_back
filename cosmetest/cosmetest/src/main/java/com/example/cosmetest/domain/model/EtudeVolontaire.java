package com.example.cosmetest.domain.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.*;

@Entity // Indique que cette classe est une entité JPA
@Table(name = "etude_volontaire") // Nom de la table dans la base de données
public class EtudeVolontaire {

	@EmbeddedId
	private EtudeVolontaireId id;

	public EtudeVolontaire() {
	}

	public EtudeVolontaire(EtudeVolontaireId id) {
		this.id = id;
	}

	public EtudeVolontaireId getId() {
		return this.id;
	}

	public void setId(EtudeVolontaireId id) {
		this.id = id;
	}

}
