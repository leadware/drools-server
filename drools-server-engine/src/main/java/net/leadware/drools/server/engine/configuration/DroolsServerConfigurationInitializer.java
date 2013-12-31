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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.leadware.drools.server.model.configuration.DroolsServerConfiguration;
import net.leadware.drools.server.tools.env.ENVHelper;
import net.leadware.drools.server.tools.jaxb.DroolsServerConfigurationJaxbValidationEventHandler;

/**
 * Classe representant l'initialiseur de configuration du serveur
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 24 déc. 2013 - 14:26:34
 */
@SuppressWarnings("unchecked")
public class DroolsServerConfigurationInitializer {
	
	/**
	 * Localisation du schema de validation du fichier de configuration server dans le classpath
	 */
	public static final String SCHEMA_LOCATION = "xsd/drools-server-configuration.xsd";
	
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
	 * Gestionnaire des evenements de validation
	 */
	private DroolsServerConfigurationJaxbValidationEventHandler handler;
	
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

		// Contexte JAXB
		JAXBContext context = null;
		
		// Creation d'un Unmarshaller
		Unmarshaller unmarshaller = null;

		// Stream sur le fichier de configuration
		InputStream configurationStream = null;
		
		// Configuration Server Drools
		DroolsServerConfiguration serverConfiguration = null;
		
		try {
			
			// Contexte JAXB
			context = JAXBContext.newInstance(DroolsServerConfiguration.class.getPackage().getName());
			
		} catch (JAXBException e) {
			
			// On relance
			throw new RuntimeException("Erreur lors de l'initialisation du contexte JAXB", e);
		}
		
		try {
			
			// Creation d'un Unmarshaller
			unmarshaller = context.createUnmarshaller();
			
		} catch (JAXBException e) {
			
			// On relance
			throw new RuntimeException("Erreur lors de l'initialisation de l'Unmarshaller JAXB", e);
		}
		
		// Validation non requise
		unmarshaller.setSchema(null);
		
		// Si la validation est requise
		if(validateConfiguration) {
			
			// Positionnement du schema
			unmarshaller.setSchema(loadConfigurationSchema());
			
			try {
				
				// Instanciation du gestionnaire d'evenements de validation
				handler = new DroolsServerConfigurationJaxbValidationEventHandler();
				
				// Positionnement du gestionnaire d'erreur de validation
				unmarshaller.setEventHandler(handler);
				
			} catch (JAXBException e) {
				
				// On relance
				throw new RuntimeException("Erreur lors du positionnement du gestionnaire des evenements de validation", e);
			}
		}
		
		// Si le fichier est charge depuis le classpath
		if(inClasspath) {
			
			// Chargement du Stream sur le fichier de configuration
			configurationStream = getClass().getClassLoader().getResourceAsStream(configurationPath);
			
			// Si le stream est null
			if(configurationStream == null) throw new RuntimeException("Erreur lors du chargement du ficher de configuration du serveur (Fichier introuvable) : [classpath:" + configurationPath + "]"); 
			
		} else {
			
			// Un File sur le chemin resolu en ENV
			File configurationFile = new File(ENVHelper.resolveEnvironmentsParameters(configurationPath));
			
			try {
				
				// Chargement du Stream sur le fichier de configuration
				configurationStream = new FileInputStream(configurationFile);
				
			} catch (FileNotFoundException e) {
				
				// On relance
				throw new RuntimeException("Erreur lors du chargement du ficher de configuration du serveur (Fichier introuvable) : " + configurationPath);
			}
		}
		
		try {
			
			// Unmarshalling
			JAXBElement<DroolsServerConfiguration> serverConfigurationElements = (JAXBElement<DroolsServerConfiguration>) unmarshaller.unmarshal(configurationStream);
			
			// Obtention de la configuration
			serverConfiguration = serverConfigurationElements.getValue();
			
		} catch (JAXBException e) {
			
			// On relance
			throw new RuntimeException("Erreur survenue lors de la validation du fichier de configuration du serveur Drools [Fichier : " + ENVHelper.resolveEnvironmentsParameters(configurationPath) + ", ligne: " + handler.getLine() + ", colonne: " + handler.getColumn() + ", Erreur: " + handler.getMessage() + "]", handler.getLinkedException());
		}
		
		// On retourne la configuration
		return serverConfiguration;
	}
	
	/**
	 * Methode permettant de charger le schema XSD du fichier de configuration
	 * @return Schema a retourner
	 */
	private Schema loadConfigurationSchema() {
		
		// Fabrique de schema
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		// Un stream sur la localisation du schema
		InputStream stream = getClass().getClassLoader().getResourceAsStream(SCHEMA_LOCATION);
		
		// Si le stream est null
		if(stream == null) throw new RuntimeException("Erreur lors du chargement du schema de validation du ficcher de configuration du serveur: [classpath:" + SCHEMA_LOCATION + "]");
		
		try {
			
			// Construction du schema
			Schema schema = factory.newSchema(new StreamSource(stream));
			
			// On retourne le schema
			return schema;
			
		} catch (Exception e) {
			
			// On relance
			throw new RuntimeException("Erreur lors du chargement du schema de validation du ficcher de configuration du serveur: [classpath:" + SCHEMA_LOCATION + "]", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		// Buffer
		StringBuilder builder = new StringBuilder();
		
		// Construction de la chaine
		builder.append("Configuration Path : " + this.configurationPath + " --- ")
			   .append("Find In Class Path : " + this.inClasspath + " --- ")
			   .append("Validate Configuration : " + this.validateConfiguration);
		
		// On retourne la chaine
		return builder.toString();
	}
}
