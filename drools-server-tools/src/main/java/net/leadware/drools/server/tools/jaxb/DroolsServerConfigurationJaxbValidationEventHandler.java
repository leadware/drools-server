/**
 * 
 */
package net.leadware.drools.server.tools.jaxb;

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

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 * Classe representant le gestionnaire des evenement d'erreur de validation du fichier de configuration du serveur Drools
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 25 déc. 2013 - 11:09:11
 */
public class DroolsServerConfigurationJaxbValidationEventHandler implements
		ValidationEventHandler {
	
	/**
	 * Message d'erreur
	 */
	private String message = null;
	
	/**
	 * Cause de l'erreur
	 */
	private Throwable linkedException = null;
	
	/**
	 * Ligne de l'erreur
	 */
	private int line = 0;
	
	/**
	 * Colonne de l'erreur
	 */
	private int column = 0;
	
	/* (non-Javadoc)
	 * @see javax.xml.bind.ValidationEventHandler#handleEvent(javax.xml.bind.ValidationEvent)
	 */
	@Override
	public boolean handleEvent(ValidationEvent event) {
		
		// Si la severite de l'evenement est erreur
		if(event.getSeverity() == ValidationEvent.ERROR || event.getSeverity() == ValidationEvent.FATAL_ERROR) {
			
			// Localisation de l'evenement
			ValidationEventLocator locator = event.getLocator();
			
			// La ligne
			line = locator.getLineNumber();
			
			// La colone
			column = locator.getColumnNumber();
			
			// Message
			message = event.getMessage();
			
			// Obtention de l'exception liee
			linkedException = event.getLinkedException();
			
			// On retourne false
			return false;
		}
		
		// On retourne true
		return true;
	}

	/**
	 * Methode d'obtention du champ "message"
	 * @return champ "message"
	 */
	public String getMessage() {
		// Renvoi de la valeur du champ
		return message;
	}

	/**
	 * Methode de modification du champ "message"
	 * @param message champ message a modifier
	 */
	public void setMessage(String message) {
		// Modification de la valeur du champ
		this.message = message;
	}

	/**
	 * Methode d'obtention du champ "linkedException"
	 * @return champ "linkedException"
	 */
	public Throwable getLinkedException() {
		// Renvoi de la valeur du champ
		return linkedException;
	}

	/**
	 * Methode de modification du champ "linkedException"
	 * @param linkedException champ linkedException a modifier
	 */
	public void setLinkedException(Throwable linkedException) {
		// Modification de la valeur du champ
		this.linkedException = linkedException;
	}

	/**
	 * Methode d'obtention du champ "line"
	 * @return champ "line"
	 */
	public int getLine() {
		// Renvoi de la valeur du champ
		return line;
	}

	/**
	 * Methode de modification du champ "line"
	 * @param line champ line a modifier
	 */
	public void setLine(int line) {
		// Modification de la valeur du champ
		this.line = line;
	}

	/**
	 * Methode d'obtention du champ "column"
	 * @return champ "column"
	 */
	public int getColumn() {
		// Renvoi de la valeur du champ
		return column;
	}

	/**
	 * Methode de modification du champ "column"
	 * @param column champ column a modifier
	 */
	public void setColumn(int column) {
		// Modification de la valeur du champ
		this.column = column;
	}
}
