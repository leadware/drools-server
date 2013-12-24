package net.leadware.drools.server.engine.configuration;

/*
 * #%L
 * DROOLS SERVER :: Engine
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

import net.leadware.drools.server.model.configuration.DroolsServerConfiguration;

/**
 * Classe representant l'initialiseur de configuration du serveur
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 24 déc. 2013 - 14:26:34
 */
public class DroolsServerConfigurationInitializer {
	
	/**
	 * Chemin du fichier de configuration
	 */
	private String configurationPath = "META-INF/drools-server-configuration.xml";
	
	/**
	 * Recherche du fichier dans le Classpath
	 */
	private boolean inClasspath = true;
	
	/**
	 * Etat de validation de configuration
	 */
	private boolean validateConfiguration = true;
	
	/**
	 * Constructeur par defaut
	 */
	public DroolsServerConfigurationInitializer() {}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param configurationPath	Chemin du fichier de configuration
	 * @param inClasspath	Etat de recherche du fichier dans le Classpath
	 * @param validateConfiguration	Etat de validation du fichier de configuration
	 */
	public DroolsServerConfigurationInitializer(String configurationPath,
			boolean inClasspath, boolean validateConfiguration) {
		this.configurationPath = configurationPath;
		this.inClasspath = inClasspath;
		this.validateConfiguration = validateConfiguration;
		
		// Si le chemin est null
		if(this.configurationPath == null) this.configurationPath = "META-INF/drools-server-configuration.xml";
	}

	/**
	 * Methode d'obtention du champ "configurationPath"
	 * @return champ "configurationPath"
	 */
	public String getConfigurationPath() {
		
		// Renvoi de la valeur du champ
		return configurationPath;
	}

	/**
	 * Methode de modification du champ "configurationPath"
	 * @param configurationPath champ configurationPath a modifier
	 */
	public void setConfigurationPath(String configurationPath) {
		
		// Modification de la valeur du champ
		this.configurationPath = configurationPath;

		// Si le chemin est null
		if(this.configurationPath == null) this.configurationPath = "META-INF/drools-server-configuration.xml";
	}

	/**
	 * Methode d'obtention du champ "inClasspath"
	 * @return champ "inClasspath"
	 */
	public boolean isInClasspath() {
		
		// Renvoi de la valeur du champ
		return inClasspath;
	}

	/**
	 * Methode de modification du champ "inClasspath"
	 * @param inClasspath champ inClasspath a modifier
	 */
	public void setInClasspath(boolean inClasspath) {
		// Modification de la valeur du champ
		this.inClasspath = inClasspath;
	}

	/**
	 * Methode d'obtention du champ "validateConfiguration"
	 * @return champ "validateConfiguration"
	 */
	public boolean isValidateConfiguration() {
		
		// Renvoi de la valeur du champ
		return validateConfiguration;
	}

	/**
	 * Methode de modification du champ "validateConfiguration"
	 * @param validateConfiguration champ validateConfiguration a modifier
	 */
	public void setValidateConfiguration(boolean validateConfiguration) {
		
		// Modification de la valeur du champ
		this.validateConfiguration = validateConfiguration;
	}
	
	/**
	 * Methode permettant de traiter le fichier de configuration 
	 * @return	Objet de configuration initialise
	 */
	public DroolsServerConfiguration initConfiguration() {
		
		return null;
	}
}
