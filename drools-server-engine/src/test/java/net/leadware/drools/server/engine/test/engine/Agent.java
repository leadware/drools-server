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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * Champ fictif
	 */
	private BigInteger cf1 = BigInteger.ONE;
	
	/**
	 * Champ fictif
	 */
	private BigInteger cf2 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf3 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf4 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf5 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf6 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf7 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf8 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf9 = BigInteger.ONE;

	/**
	 * Champ fictif
	 */
	private BigInteger cf10 = BigInteger.ONE;
	
	/**
	 * Champ fictif
	 */
	private List<Long> cf11 = new ArrayList<Long>(50);
	
	/**
	 * Message fictif
	 */
	private Message message1 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message2 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message3 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message4 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message5 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message6 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message7 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message8 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message9 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message10 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message11 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message12 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message13 = new Message("essage fictig de test");

	/**
	 * Message fictif
	 */
	private Message message14 = new Message("essage fictig de test");
	
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		// Builder
		StringBuilder builder = new StringBuilder();
		
		// Ajout du MAT
		builder.append("MATRICULE : " + matricule + " - ")
			   .append("NOM : " + nom + " - ")
			   .append("PRENOM : " + prenom);
		
		// On retourne la chaine
		return builder.toString();
	}

	/**
	 * Methode d'obtention du champ "cf1"
	 * @return champ "cf1"
	 */
	public BigInteger getCf1() {
		// Renvoi de la valeur du champ
		return cf1;
	}

	/**
	 * Methode de modification du champ "cf1"
	 * @param cf1 champ cf1 a modifier
	 */
	public void setCf1(BigInteger cf1) {
		// Modification de la valeur du champ
		this.cf1 = cf1;
	}

	/**
	 * Methode d'obtention du champ "cf2"
	 * @return champ "cf2"
	 */
	public BigInteger getCf2() {
		// Renvoi de la valeur du champ
		return cf2;
	}

	/**
	 * Methode de modification du champ "cf2"
	 * @param cf2 champ cf2 a modifier
	 */
	public void setCf2(BigInteger cf2) {
		// Modification de la valeur du champ
		this.cf2 = cf2;
	}

	/**
	 * Methode d'obtention du champ "cf3"
	 * @return champ "cf3"
	 */
	public BigInteger getCf3() {
		// Renvoi de la valeur du champ
		return cf3;
	}

	/**
	 * Methode de modification du champ "cf3"
	 * @param cf3 champ cf3 a modifier
	 */
	public void setCf3(BigInteger cf3) {
		// Modification de la valeur du champ
		this.cf3 = cf3;
	}

	/**
	 * Methode d'obtention du champ "cf4"
	 * @return champ "cf4"
	 */
	public BigInteger getCf4() {
		// Renvoi de la valeur du champ
		return cf4;
	}

	/**
	 * Methode de modification du champ "cf4"
	 * @param cf4 champ cf4 a modifier
	 */
	public void setCf4(BigInteger cf4) {
		// Modification de la valeur du champ
		this.cf4 = cf4;
	}

	/**
	 * Methode d'obtention du champ "cf5"
	 * @return champ "cf5"
	 */
	public BigInteger getCf5() {
		// Renvoi de la valeur du champ
		return cf5;
	}

	/**
	 * Methode de modification du champ "cf5"
	 * @param cf5 champ cf5 a modifier
	 */
	public void setCf5(BigInteger cf5) {
		// Modification de la valeur du champ
		this.cf5 = cf5;
	}

	/**
	 * Methode d'obtention du champ "cf6"
	 * @return champ "cf6"
	 */
	public BigInteger getCf6() {
		// Renvoi de la valeur du champ
		return cf6;
	}

	/**
	 * Methode de modification du champ "cf6"
	 * @param cf6 champ cf6 a modifier
	 */
	public void setCf6(BigInteger cf6) {
		// Modification de la valeur du champ
		this.cf6 = cf6;
	}

	/**
	 * Methode d'obtention du champ "cf7"
	 * @return champ "cf7"
	 */
	public BigInteger getCf7() {
		// Renvoi de la valeur du champ
		return cf7;
	}

	/**
	 * Methode de modification du champ "cf7"
	 * @param cf7 champ cf7 a modifier
	 */
	public void setCf7(BigInteger cf7) {
		// Modification de la valeur du champ
		this.cf7 = cf7;
	}

	/**
	 * Methode d'obtention du champ "cf8"
	 * @return champ "cf8"
	 */
	public BigInteger getCf8() {
		// Renvoi de la valeur du champ
		return cf8;
	}

	/**
	 * Methode de modification du champ "cf8"
	 * @param cf8 champ cf8 a modifier
	 */
	public void setCf8(BigInteger cf8) {
		// Modification de la valeur du champ
		this.cf8 = cf8;
	}

	/**
	 * Methode d'obtention du champ "cf9"
	 * @return champ "cf9"
	 */
	public BigInteger getCf9() {
		// Renvoi de la valeur du champ
		return cf9;
	}

	/**
	 * Methode de modification du champ "cf9"
	 * @param cf9 champ cf9 a modifier
	 */
	public void setCf9(BigInteger cf9) {
		// Modification de la valeur du champ
		this.cf9 = cf9;
	}

	/**
	 * Methode d'obtention du champ "cf10"
	 * @return champ "cf10"
	 */
	public BigInteger getCf10() {
		// Renvoi de la valeur du champ
		return cf10;
	}

	/**
	 * Methode de modification du champ "cf10"
	 * @param cf10 champ cf10 a modifier
	 */
	public void setCf10(BigInteger cf10) {
		// Modification de la valeur du champ
		this.cf10 = cf10;
	}

	/**
	 * Methode d'obtention du champ "cf11"
	 * @return champ "cf11"
	 */
	public List<Long> getCf11() {
		// Renvoi de la valeur du champ
		return cf11;
	}

	/**
	 * Methode de modification du champ "cf11"
	 * @param cf11 champ cf11 a modifier
	 */
	public void setCf11(List<Long> cf11) {
		// Modification de la valeur du champ
		this.cf11 = cf11;
	}

	/**
	 * Methode d'obtention du champ "message1"
	 * @return champ "message1"
	 */
	public Message getMessage1() {
		// Renvoi de la valeur du champ
		return message1;
	}

	/**
	 * Methode de modification du champ "message1"
	 * @param message1 champ message1 a modifier
	 */
	public void setMessage1(Message message1) {
		// Modification de la valeur du champ
		this.message1 = message1;
	}

	/**
	 * Methode d'obtention du champ "message2"
	 * @return champ "message2"
	 */
	public Message getMessage2() {
		// Renvoi de la valeur du champ
		return message2;
	}

	/**
	 * Methode de modification du champ "message2"
	 * @param message2 champ message2 a modifier
	 */
	public void setMessage2(Message message2) {
		// Modification de la valeur du champ
		this.message2 = message2;
	}

	/**
	 * Methode d'obtention du champ "message3"
	 * @return champ "message3"
	 */
	public Message getMessage3() {
		// Renvoi de la valeur du champ
		return message3;
	}

	/**
	 * Methode de modification du champ "message3"
	 * @param message3 champ message3 a modifier
	 */
	public void setMessage3(Message message3) {
		// Modification de la valeur du champ
		this.message3 = message3;
	}

	/**
	 * Methode d'obtention du champ "message4"
	 * @return champ "message4"
	 */
	public Message getMessage4() {
		// Renvoi de la valeur du champ
		return message4;
	}

	/**
	 * Methode de modification du champ "message4"
	 * @param message4 champ message4 a modifier
	 */
	public void setMessage4(Message message4) {
		// Modification de la valeur du champ
		this.message4 = message4;
	}

	/**
	 * Methode d'obtention du champ "message5"
	 * @return champ "message5"
	 */
	public Message getMessage5() {
		// Renvoi de la valeur du champ
		return message5;
	}

	/**
	 * Methode de modification du champ "message5"
	 * @param message5 champ message5 a modifier
	 */
	public void setMessage5(Message message5) {
		// Modification de la valeur du champ
		this.message5 = message5;
	}

	/**
	 * Methode d'obtention du champ "message6"
	 * @return champ "message6"
	 */
	public Message getMessage6() {
		// Renvoi de la valeur du champ
		return message6;
	}

	/**
	 * Methode de modification du champ "message6"
	 * @param message6 champ message6 a modifier
	 */
	public void setMessage6(Message message6) {
		// Modification de la valeur du champ
		this.message6 = message6;
	}

	/**
	 * Methode d'obtention du champ "message7"
	 * @return champ "message7"
	 */
	public Message getMessage7() {
		// Renvoi de la valeur du champ
		return message7;
	}

	/**
	 * Methode de modification du champ "message7"
	 * @param message7 champ message7 a modifier
	 */
	public void setMessage7(Message message7) {
		// Modification de la valeur du champ
		this.message7 = message7;
	}

	/**
	 * Methode d'obtention du champ "message8"
	 * @return champ "message8"
	 */
	public Message getMessage8() {
		// Renvoi de la valeur du champ
		return message8;
	}

	/**
	 * Methode de modification du champ "message8"
	 * @param message8 champ message8 a modifier
	 */
	public void setMessage8(Message message8) {
		// Modification de la valeur du champ
		this.message8 = message8;
	}

	/**
	 * Methode d'obtention du champ "message9"
	 * @return champ "message9"
	 */
	public Message getMessage9() {
		// Renvoi de la valeur du champ
		return message9;
	}

	/**
	 * Methode de modification du champ "message9"
	 * @param message9 champ message9 a modifier
	 */
	public void setMessage9(Message message9) {
		// Modification de la valeur du champ
		this.message9 = message9;
	}

	/**
	 * Methode d'obtention du champ "message10"
	 * @return champ "message10"
	 */
	public Message getMessage10() {
		// Renvoi de la valeur du champ
		return message10;
	}

	/**
	 * Methode de modification du champ "message10"
	 * @param message10 champ message10 a modifier
	 */
	public void setMessage10(Message message10) {
		// Modification de la valeur du champ
		this.message10 = message10;
	}

	/**
	 * Methode d'obtention du champ "message11"
	 * @return champ "message11"
	 */
	public Message getMessage11() {
		// Renvoi de la valeur du champ
		return message11;
	}

	/**
	 * Methode de modification du champ "message11"
	 * @param message11 champ message11 a modifier
	 */
	public void setMessage11(Message message11) {
		// Modification de la valeur du champ
		this.message11 = message11;
	}

	/**
	 * Methode d'obtention du champ "message12"
	 * @return champ "message12"
	 */
	public Message getMessage12() {
		// Renvoi de la valeur du champ
		return message12;
	}

	/**
	 * Methode de modification du champ "message12"
	 * @param message12 champ message12 a modifier
	 */
	public void setMessage12(Message message12) {
		// Modification de la valeur du champ
		this.message12 = message12;
	}

	/**
	 * Methode d'obtention du champ "message13"
	 * @return champ "message13"
	 */
	public Message getMessage13() {
		// Renvoi de la valeur du champ
		return message13;
	}

	/**
	 * Methode de modification du champ "message13"
	 * @param message13 champ message13 a modifier
	 */
	public void setMessage13(Message message13) {
		// Modification de la valeur du champ
		this.message13 = message13;
	}

	/**
	 * Methode d'obtention du champ "message14"
	 * @return champ "message14"
	 */
	public Message getMessage14() {
		// Renvoi de la valeur du champ
		return message14;
	}

	/**
	 * Methode de modification du champ "message14"
	 * @param message14 champ message14 a modifier
	 */
	public void setMessage14(Message message14) {
		// Modification de la valeur du champ
		this.message14 = message14;
	}
	
}
