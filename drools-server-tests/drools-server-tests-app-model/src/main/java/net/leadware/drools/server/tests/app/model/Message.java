package net.leadware.drools.server.tests.app.model;

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

/**
 * Classe representant un message 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 10 févr. 2014 - 19:02:51
 */
public class Message implements Serializable {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Texte
	 */
	private String text;
	
	/**
	 * Constructeur par defaut
	 */
	public Message() {
		
		// initialisation
		this.text = "";
	}
	
	public Message(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		// Renvoi de la valeur du champ
		return text;
	}

	public void setText(String text) {
		// Modification de la valeur du champ
		this.text = text;
	}
	
}	
