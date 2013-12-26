/**
 * 
 */
package net.leadware.drools.server.engine.test.engine;

/*
 * #%L
 * DROOLS SERVER
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2013 Leadware
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.Serializable;
import java.util.Date;

/**
 * Classe representant un Agent de l'etat 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 26 déc. 2013 - 13:30:00
 */
public class Agent implements Serializable {

	/**
	 * ID Genere par eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Matricule de l'agent
	 */
	private String matricule;
	
	/**
	 * Nom de l'agent
	 */
	private String nom;
	
	/**
	 * Prenom de l'agent
	 */
	private String prenom;
	
	/**
	 * Date de naissance de l'agent
	 */
	private Date dateNaissance;
	
	/**
	 * Grade de l'agent
	 */
	private String grade;
	
	/**
	 * Constructeur par defaut
	 */
	public Agent() {}

	/**
	 * @param matricule	Matricule de l'agent
	 * @param nom	Nom de l'agent
	 * @param prenom Prenom de l'agent
	 * @param dateNaissance Date de naissance de l'agent
	 * @param grade	Grade de l'agent
	 */
	public Agent(String matricule, String nom, String prenom,
			Date dateNaissance, String grade) {
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.grade = grade;
	}

	public String getMatricule() {
		// Renvoi de la valeur du champ
		return matricule;
	}

	public void setMatricule(String matricule) {
		// Modification de la valeur du champ
		this.matricule = matricule;
	}

	public String getNom() {
		// Renvoi de la valeur du champ
		return nom;
	}

	public void setNom(String nom) {
		// Modification de la valeur du champ
		this.nom = nom;
	}

	public String getPrenom() {
		// Renvoi de la valeur du champ
		return prenom;
	}

	public void setPrenom(String prenom) {
		// Modification de la valeur du champ
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		// Renvoi de la valeur du champ
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		// Modification de la valeur du champ
		this.dateNaissance = dateNaissance;
	}

	public String getGrade() {
		// Renvoi de la valeur du champ
		return grade;
	}

	public void setGrade(String grade) {
		// Modification de la valeur du champ
		this.grade = grade;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object parameter) {
		
		// Si l'objet en parametre est null
		if(parameter == null) return false;
		
		// Si l'objet en parametre n'est pas de la bonne classe
		if(!(parameter instanceof Agent)) return false;
		
		// On caste
		Agent agent = (Agent) parameter;
		
		// Si l'objet en parametre n'a pas de matricule
		if(agent.matricule == null) return false;
		
		// Si l'objet courant n'a pas de matricule
		if(matricule == null) return false;
		
		// On retourne la comparaison des matricules
		return matricule.trim().equalsIgnoreCase(agent.matricule.trim());
	}
}
