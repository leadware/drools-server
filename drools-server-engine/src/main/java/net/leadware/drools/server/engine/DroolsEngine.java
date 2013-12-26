/**
 * 
 */
package net.leadware.drools.server.engine;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.leadware.drools.server.engine.configuration.DroolsServerConfigurationInitializer;
import net.leadware.drools.server.model.configuration.DroolsServerConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeAgentConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeBaseConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeSessionConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeSessionTypeConfiguration;
import net.leadware.drools.server.model.configuration.ResourceConfiguration;
import net.leadware.drools.server.model.configuration.ResourceTypeConfiguration;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.builder.ResultSeverity;
import org.drools.builder.conf.KBuilderSeverityOption;
import org.drools.conf.MaxThreadsOption;
import org.drools.conf.MultithreadEvaluationOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

/**
 * Classe representant le server drools
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 25 déc. 2013 - 13:01:12
 */
@SuppressWarnings("deprecation")
public class DroolsEngine {
	
	/**
	 * Initialiseur de configuration serveur
	 */
	private DroolsServerConfigurationInitializer serverConfigurationInitializer = new DroolsServerConfigurationInitializer();
	
	/**
	 * Map des sessions
	 */
	private Map<String, Object> knowledgeSessions = new HashMap<String, Object>();
	
	/**
	 * Etat de demarrage des services de moditoring des changement d'etat des ressources
	 */
	private boolean startResourceChangeService = false;
	
	/**
	 * Methode permettant de construire une base de connaissance a partir de la base de connaissance configuree
	 * @param knowledgeBaseConfiguration	Configuration de base de connaissance	
	 * @return	Base de connaissance Drools
	 */
	private KnowledgeBase buildKnowledgeBase(KnowledgeBaseConfiguration knowledgeBaseConfiguration) {

		// Configuration du constructeur de connaissance
		KnowledgeBuilderConfiguration droolsKnowledgeBuilderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
		
		// Traitement des regles multiples
		switch (knowledgeBaseConfiguration.getOnDuplicateRule()) {
			
			// en cas d'INFO
			case INFO:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateRule", ResultSeverity.INFO));
				 		break;

			// en cas de WARN
			case WARN:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateRule", ResultSeverity.WARNING));
				 		break;

			// en cas d'ERROR
			case ERROR:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateRule", ResultSeverity.ERROR));
						break;
										 		
			default:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateRule", ResultSeverity.INFO));
						break;
		}

		// Traitement des fonctions multiples
		switch (knowledgeBaseConfiguration.getOnDuplicateFunction()) {
			
			// en cas d'INFO
			case INFO:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateFunction", ResultSeverity.INFO));
				 		break;

			// en cas de WARN
			case WARN:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateFunction", ResultSeverity.WARNING));
				 		break;

			// en cas d'ERROR
			case ERROR:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateFunction", ResultSeverity.ERROR));
						break;
			
			// Cas par defaut			
			default:	droolsKnowledgeBuilderConfiguration.setOption(KBuilderSeverityOption.get("drools.kbuilder.severity.duplicateFunction", ResultSeverity.INFO));
						break;
		}
		
		// Constructeur de connaissances
		KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(droolsKnowledgeBuilderConfiguration);
		
		// Parcours de la liste des Resources de la configuration
		for (ResourceConfiguration resourceConfiguration : knowledgeBaseConfiguration.getResource()) {
			
			// Si la ressource est dans le classpath
			if(resourceConfiguration.isInClassPath()) {
				
				// Ajout de la resource
				knowledgeBuilder.add(ResourceFactory.newClassPathResource(resourceConfiguration.getPath(), getClass()), buildResourceType(resourceConfiguration.getType()));
				
			} else {
				
				// Ajout de la ressource
				knowledgeBuilder.add(ResourceFactory.newFileResource(resourceConfiguration.getPath()), buildResourceType(resourceConfiguration.getType()));
			}
		}
		
		// S'il y a des erreurs
		if(knowledgeBuilder.hasErrors()) {
			
			// On leve une exceprion
			throw new RuntimeException("Erreur lors du chargement des ressources metier : " + knowledgeBuilder.getErrors().toString());
		}
		
		// Configuration de la base de connaissance
		org.drools.KnowledgeBaseConfiguration droolsKnowledgeBaseConfiguration = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		
		// Si on est en evaluation multithread
		if(knowledgeBaseConfiguration.isMultiThreadEvaluation()) {
			
			// On positionne l'etat de multithread
			droolsKnowledgeBaseConfiguration.setOption(MultithreadEvaluationOption.YES);
			
			// Positionnement du nombre de thread
			droolsKnowledgeBaseConfiguration.setOption(MaxThreadsOption.get(knowledgeBaseConfiguration.getMaxEvaluationThreads()));
		}
		
		// Instanciation de la base de connaissance
		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(droolsKnowledgeBaseConfiguration);
		
		// Ajout des packages de connaissances dans la base
		knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
		
		// On retourne la base de connaissances
		return knowledgeBase;
	}
		
	/**
	 * Methode permettant de construire une base de connaissance a partir de l'agent intelligent configuree
	 * @param knowledgeAgentConfiguration	Configuration de l'agent intelligent
	 * @return	Base de connaissance Drools
	 */
	private KnowledgeBase buildKnowledgeBase(KnowledgeAgentConfiguration knowledgeAgentConfiguration) {
		
		// Obtention de la configuration de base de connaissance
		KnowledgeBaseConfiguration knowledgeBaseConfiguration = knowledgeAgentConfiguration.getKnowledgeBase();
		
		// Construction de la base de connaissance Drools
		KnowledgeBase knowledgeBase = buildKnowledgeBase(knowledgeBaseConfiguration);
		
		// Configuration de l'agent intelligent
		org.drools.agent.KnowledgeAgentConfiguration droolsKnowledgeAgentConfiguration = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		
		// Si on doit scanner les ressources
		if(knowledgeAgentConfiguration.isScanDirectories() || knowledgeAgentConfiguration.isScanResources()) this.startResourceChangeService = true;
		
		// Positionnement du scan des repertoires
		droolsKnowledgeAgentConfiguration.setProperty("drools.agent.scanDirectories", Boolean.toString(knowledgeAgentConfiguration.isScanDirectories()));
		
		// Positionnement du scan des ressources
		droolsKnowledgeAgentConfiguration.setProperty("drools.agent.scanResources", Boolean.toString(knowledgeAgentConfiguration.isScanResources()));
		
		// Positionnement de la periode de scan
		droolsKnowledgeAgentConfiguration.setProperty("drools.agent.scanPeriod", Integer.toString(knowledgeAgentConfiguration.getScanPeriod()));
		
		// Positionnement de l'etat d'utilisation du classloader de la base de connaissance
		droolsKnowledgeAgentConfiguration.setProperty("drools.agent.useKBaseClassLoaderForCompiling", Integer.toString(knowledgeAgentConfiguration.getScanPeriod()));
		
		// Construction de l'agent
		KnowledgeAgent knowledgeAgent = KnowledgeAgentFactory.newKnowledgeAgent(knowledgeAgentConfiguration.getName(), knowledgeBase, droolsKnowledgeAgentConfiguration);
		
		// On retourne la base de connaissance issue de l'agent
		return knowledgeAgent.getKnowledgeBase();
	}
	
	/**
	 * Methode permettant de construire une session intelligente sans etat
	 * @param knowledgeSessionConfiguration	Configuration de la session intelligente sans etat
	 * @return	Session intelligente sans etat
	 */
	private StatelessKnowledgeSession buildStatelessKnowledgeSession(KnowledgeSessionConfiguration knowledgeSessionConfiguration) {
		
		// Base de connaissance
		KnowledgeBase knowledgeBase = null;
		
		// Si la base de connaissance est non nulle
		if(knowledgeSessionConfiguration.getKnowledgeBase() != null) {
			
			// Construction de la base de connaissance
			knowledgeBase = buildKnowledgeBase(knowledgeSessionConfiguration.getKnowledgeBase());
			
		} else {
			
			// Construction de la base de connaissance
			knowledgeBase = buildKnowledgeBase(knowledgeSessionConfiguration.getKnowledgeAgent());
		}
		
		// Construction de la session sans etat
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		
		// On retourne la session sans etat
		return statelessKnowledgeSession;
	}

	/**
	 * Methode permettant de construire une session intelligente avec etat
	 * @param knowledgeSessionConfiguration	Configuration de la session intelligente sans etat
	 * @return	Session intelligente sans etat
	 */
	private StatefulKnowledgeSession buildStatefulKnowledgeSession(KnowledgeSessionConfiguration knowledgeSessionConfiguration) {
		
		// Base de connaissance
		KnowledgeBase knowledgeBase = null;
		
		// Si la base de connaissance est non nulle
		if(knowledgeSessionConfiguration.getKnowledgeBase() != null) {
			
			// Construction de la base de connaissance
			knowledgeBase = buildKnowledgeBase(knowledgeSessionConfiguration.getKnowledgeBase());
			
		} else {
			
			// Construction de la base de connaissance
			knowledgeBase = buildKnowledgeBase(knowledgeSessionConfiguration.getKnowledgeAgent());
		}
		
		// Construction de la session avec etat
		StatefulKnowledgeSession statefulKnowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
		
		// On retourne la session avec etat
		return statefulKnowledgeSession;
	}
	
	/**
	 * Methode permettant d'obtenir le type de la ressource 
	 * @param resourceTypeConfiguration	Configuration du type
	 * @return	Type Drools
	 */
	private ResourceType buildResourceType(ResourceTypeConfiguration resourceTypeConfiguration) {
		
		// Choix
		switch (resourceTypeConfiguration) {
			
			// En cas de BPMN
			case BPMN: return ResourceType.BPMN2;

			// En cas de CHANGE_SET
			case CHANGE_SET: return ResourceType.CHANGE_SET;

			// En cas de BRL
			case BRL: return ResourceType.BRL;

			// En cas de DESCR
			case DESCR: return ResourceType.DESCR;

			// En cas de DRF
			case DRF: return ResourceType.DRF;

			// En cas de DRL
			case DRL: return ResourceType.DRL;

			// En cas de DSL
			case DSL: return ResourceType.DSL;

			// En cas de DSLR
			case DSLR: return ResourceType.DSLR;

			// En cas de DTABLE
			case DTABLE: return ResourceType.DTABLE;

			// En cas de PKG
			case PKG: return ResourceType.PKG;

			// En cas de PMML
			case PMML: return ResourceType.PMML;

			// En cas de XDRL
			case XDRL: return ResourceType.XDRL;

			// En cas de XSD
			case XSD: return ResourceType.XSD;
			
			// Valeur par defaut
			default: return ResourceType.DRL;
		}
	}

	/**
	 * Methode d'obtention du champ "configurationPath"
	 * @return champ "configurationPath"
	 */
	public String getConfigurationPath() {
		
		// Renvoi de la valeur du champ
		return serverConfigurationInitializer.getConfigurationPath();
	}
	
	/**
	 * Methode de modification du champ "configurationPath"
	 * @param configurationPath champ configurationPath a modifier
	 */
	public void setConfigurationPath(String configurationPath) {
		
		// Modification de la valeur du champ
		serverConfigurationInitializer.setConfigurationPath(configurationPath);
	}

	/**
	 * Methode d'obtention du champ "inClasspath"
	 * @return champ "inClasspath"
	 */
	public boolean isInClasspath() {
		
		// Renvoi de la valeur du champ
		return serverConfigurationInitializer.isInClasspath();
	}

	/**
	 * Methode de modification du champ "inClasspath"
	 * @param inClasspath champ inClasspath a modifier
	 */
	public void setInClasspath(boolean inClasspath) {
		
		// Modification de la valeur du champ
		serverConfigurationInitializer.setInClasspath(inClasspath);
	}

	/**
	 * Methode d'obtention du champ "validateConfiguration"
	 * @return champ "validateConfiguration"
	 */
	public boolean isValidateConfiguration() {
		
		// Renvoi de la valeur du champ
		return serverConfigurationInitializer.isValidateConfiguration();
	}

	/**
	 * Methode de modification du champ "validateConfiguration"
	 * @param validateConfiguration champ validateConfiguration a modifier
	 */
	public void setValidateConfiguration(boolean validateConfiguration) {
		
		// Modification de la valeur du champ
		serverConfigurationInitializer.setValidateConfiguration(validateConfiguration);
	}
	
	/**
	 * Methode de demarrage du serveur
	 */
	public void start() {
		
		// Construction de la configuration
		DroolsServerConfiguration droolsServerConfiguration = this.serverConfigurationInitializer.initConfiguration();
		
		// Obtention de la liste des configurations des Sessions
		List<KnowledgeSessionConfiguration> knowledgeSessionConfigurations = droolsServerConfiguration.getKnowledgeSession();
		
		// Parcours
		for (KnowledgeSessionConfiguration knowledgeSessionConfiguration : knowledgeSessionConfigurations) {
			
			// Si c'est une session stateless
			if(knowledgeSessionConfiguration.getType().equals(KnowledgeSessionTypeConfiguration.STATELESS)) {
				
				// Creation de la session
				StatelessKnowledgeSession statelessKnowledgeSession = buildStatelessKnowledgeSession(knowledgeSessionConfiguration);
				
				// Ajout de la session dans la MAP
				knowledgeSessions.put(knowledgeSessionConfiguration.getName(), statelessKnowledgeSession);
				
			} else {
				
				// Creation de la session
				StatefulKnowledgeSession statefulKnowledgeSession = buildStatefulKnowledgeSession(knowledgeSessionConfiguration);
				
				// Ajout de la session dans la MAP
				knowledgeSessions.put(knowledgeSessionConfiguration.getName(), statefulKnowledgeSession);
			}
			
		}
		
		// Si le service de monitoring des changements doit etre demarre
		if(startResourceChangeService) {
			
			// Demarage du service de scanning des ressources
			ResourceFactory.getResourceChangeScannerService().start();
			
			// Demarrage du service de notification
			ResourceFactory.getResourceChangeNotifierService().start();
		}
	}
	
	/**
	 * Methode d'arret du serveur
	 */
	public void stop() {

		// Si le service de monitoring des changements doit est demarre
		if(startResourceChangeService) {
			
			try {

				// Arret du service de scanning des ressources
				ResourceFactory.getResourceChangeScannerService().stop();
				
			} catch (Exception e) {
				
				// Trace
				e.printStackTrace();
			}
			
			try {

				// Arret du service de notification
				ResourceFactory.getResourceChangeNotifierService().start();
				
			} catch (Exception e) {
				
				// Trace
				e.printStackTrace();
			}
		}
		
		// Parcours de la Map des sessions
		for (String sessionName : knowledgeSessions.keySet()) {
			
			try {
				
				// Si c'est une session Statefull
				if(isStatefulSession(sessionName)) {
					
					// Cast
					StatefulKnowledgeSession statefulKnowledgeSession = (StatefulKnowledgeSession) knowledgeSessions.get(sessionName);

					// Arret des travaux
					statefulKnowledgeSession.halt();
					
					// tentative de liberation des ressources
					statefulKnowledgeSession.dispose();
				}
				
			} catch (Exception e) {
				
				// Trace
				e.printStackTrace();
			}
		}
		
		// On vide la map
		knowledgeSessions.clear();
	}
	
	/**
	 * Methode permettant de tester si une session existe
	 * @param sessionName	Nom de la session
	 * @return	Etat d'existence de la session
	 */
	public boolean isSessionExists(String sessionName) {
		
		// on retourne l'etat d'existence
		return (knowledgeSessions.get(sessionName.trim()) != null);
	}
	
	/**
	 * Methode permettant de verifier qu'une session est de type Stateless
	 * @param sessionName	Nom de la session
	 * @return	Etat stateless de la session
	 */
	public boolean isStatelessSession(String sessionName) {
		
		// Objet session
		Object knowledgeSession = knowledgeSessions.get(sessionName.trim());
		
		// On retourne l'etat
		return (knowledgeSession != null) && (knowledgeSession instanceof StatelessKnowledgeSession);
	}
	
	/**
	 * Methode permettant de verifier qu'une session est de type Stateful
	 * @param sessionName	Nom de la session
	 * @return	Etat stateful de la session
	 */
	public boolean isStatefulSession(String sessionName) {
		
		// Objet session
		Object knowledgeSession = knowledgeSessions.get(sessionName.trim());
		
		// On retourne l'etat
		return (knowledgeSession != null) && (knowledgeSession instanceof StatefulKnowledgeSession);
	}
	
	/**
	 * Methode permettant d'obtenir un session intelligente
	 * @param sessionName	Nom de la session
	 * @return	Session intelligente
	 */
	public Object getKnowledgeSession(String sessionName) {
		
		// On retourne la session
		return knowledgeSessions.get(sessionName.trim());
	}
}
