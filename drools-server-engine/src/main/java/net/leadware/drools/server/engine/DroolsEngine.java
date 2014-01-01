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
import net.leadware.drools.server.model.configuration.KnowledgeAgentsConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeBaseConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeBaseConfigurationRef;
import net.leadware.drools.server.model.configuration.KnowledgeBasesConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeSessionConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeSessionTypeConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeSessionsConfiguration;
import net.leadware.drools.server.model.configuration.ResourceConfiguration;
import net.leadware.drools.server.model.configuration.ResourceConfigurationRef;
import net.leadware.drools.server.model.configuration.ResourceTypeConfiguration;
import net.leadware.drools.server.model.configuration.ResourcesConfiguration;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.RuntimeDroolsException;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.builder.ResultSeverity;
import org.drools.builder.conf.KBuilderSeverityOption;
import org.drools.command.CommandFactory;
import org.drools.command.impl.GenericCommand;
import org.drools.command.runtime.BatchExecutionCommandImpl;
import org.drools.conf.MaxThreadsOption;
import org.drools.conf.MultithreadEvaluationOption;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.ExecutionResults;
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
	 * MAP des ressources
	 */
	private Map<String, ResourceConfiguration> resourcesConfiguration = new HashMap<String, ResourceConfiguration>();
	
	/**
	 * MAP Des bases de connaissances
	 */
	private Map<String, KnowledgeBase> knowledgeBases = new HashMap<String, KnowledgeBase>();
	
	/**
	 * Map des agents intelligents par session
	 */
	private Map<String, KnowledgeAgent> knowledgeAgents = new HashMap<String, KnowledgeAgent>();

	/**
	 * Map des sessions
	 */
	private Map<String, Object> knowledgeSessions = new HashMap<String, Object>();
	
	/**
	 * Etat de demarrage des services de moditoring des changement d'etat des ressources
	 */
	private boolean startResourceChangeService = false;
	
	/**
	 * Methode permettant d'initialiser la MAP des Resources par nom
	 * @param resourcesConfiguration	Configuration des ressources
	 */
	private void buildResources(ResourcesConfiguration resourcesConfiguration) {
		
		// Parcours
		for (ResourceConfiguration resourceConfiguration : resourcesConfiguration.getResource()) {
			
			// Ajout dans la MAP des configuration de ressources
			this.resourcesConfiguration.put(resourceConfiguration.getName().trim(), resourceConfiguration);
		}
	}
	
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
		
		// Parcours de la liste des references de Resources de la configuration
		for (ResourceConfigurationRef resourceConfigurationref : knowledgeBaseConfiguration.getResourceRef()) {
			
			// Obtention de la configuration de ressources
			ResourceConfiguration resourceConfiguration = this.resourcesConfiguration.get(resourceConfigurationref.getResourceName().trim());
			
			// Si la ressource n'existe pas
			if(resourceConfiguration == null) 
				throw new RuntimeException("Erreur lors de la construction de la base de connaissance : la ressource referencee par '" + resourceConfigurationref.getResourceName() + "' n'existe pas.");
			
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
	 * Methode permettant de construire la liste des bases de connaissance a partir de la base de connaissance configuree
	 * @param knowledgeBasesConfiguration	Configuration de la liste des bases de connaissance	
	 */
	private void buildKnowledgeBases(KnowledgeBasesConfiguration knowledgeBasesConfiguration) {
		
		// Parcours de la liste des configuration des bases de connaissances
		for (KnowledgeBaseConfiguration knowledgeBaseConfiguration : knowledgeBasesConfiguration.getKnowledgeBase()) {
			
			// Obtention de la base de connaissance
			KnowledgeBase knowledgeBase = buildKnowledgeBase(knowledgeBaseConfiguration);
			
			// Ajout de la base de connaissance dans la MAP des bases de connaissance
			knowledgeBases.put(knowledgeBaseConfiguration.getName().trim(), knowledgeBase);
		}
	}
	
	/**
	 * Methode permettant de construire un agent intelligent configuree
	 * @param knowledgeAgentConfiguration	Configuration de l'agent intelligent
	 * @return	Agent intelligent Drools
	 */
	private KnowledgeAgent buildKnowledgeAgent(KnowledgeAgentConfiguration knowledgeAgentConfiguration) {

		// Base de connaissance drools a construire
		KnowledgeBase knowledgeBase = null;
		
		// Obtention de la reference sur la base de connaissance
		KnowledgeBaseConfigurationRef knowledgeBaseConfigurationRef = knowledgeAgentConfiguration.getKnowledgeBaseRef();

		// Recherche de la base de connaissance dans la MAP des bases de connaissances
		knowledgeBase = this.knowledgeBases.get(knowledgeBaseConfigurationRef.getKnowledgeBase().trim());
		
		// Si la base de connaissance est nulle
		if(knowledgeBase == null) 
			throw new RuntimeException("Erreur lors de la construction de l'agent intelligent : la base de connaissance referencee par '" + knowledgeBaseConfigurationRef.getKnowledgeBase() + "' n'existe pas.");
		
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
		return knowledgeAgent;
	}
	
	/**
	 * Methode permettant de construire la liste des agent intelligent configuree a partir de la configuration des agents
	 * @param knowledgeAgentsConfiguration	Configuration des agents intelligent
	 */
	private void buildKnowledgeAgents(KnowledgeAgentsConfiguration knowledgeAgentsConfiguration) {
		
		// Parcours de la liste des configuration d'agents
		for (KnowledgeAgentConfiguration knowledgeAgentConfiguration : knowledgeAgentsConfiguration.getKnowledgeAgent()) {
			
			// Construction de l'agent intelligent
			KnowledgeAgent knowledgeAgent = buildKnowledgeAgent(knowledgeAgentConfiguration);
			
			// Ajout de l'agent dans la MAP des agents
			this.knowledgeAgents.put(knowledgeAgentConfiguration.getName().trim(), knowledgeAgent);
		}
	}
	
	/**
	 * Methode permettant de construire une session intelligente sans etat
	 * @param knowledgeSessionConfiguration	Configuration de la session intelligente sans etat
	 * @return	Session intelligente sans etat
	 */
	private StatelessKnowledgeSession buildStatelessKnowledgeSession(KnowledgeSessionConfiguration knowledgeSessionConfiguration) {
		
		// Base de connaissance
		KnowledgeBase knowledgeBase = null;
		
		// Agent intelligent
		KnowledgeAgent knowledgeAgent = null;
		
		// Si la reference a la base de connaissance est non nulle
		if(knowledgeSessionConfiguration.getKnowledgeBaseRef() != null) {
			
			// Obtention de la base de connaissance depuis la MAP des bases de connaissances
			knowledgeBase = this.knowledgeBases.get(knowledgeSessionConfiguration.getKnowledgeBaseRef().getKnowledgeBase().trim());
			
			// Si la base de connaissance est nulle
			if(knowledgeBase == null) 
				throw new RuntimeException("Erreur lors de la construction de la session intelligente (stateless) : la base de connaissance referencee par '" + knowledgeSessionConfiguration.getKnowledgeBaseRef().getKnowledgeBase() + "' n'existe pas.");
			
		} else {
			
			// Obtention de l'agent intelligent depuis la MAP des aggents intelligents
			knowledgeAgent = this.knowledgeAgents.get(knowledgeSessionConfiguration.getKnowledgeAgentRef().getKnowledgeAgent().trim());
			
			// Si l'agent est null
			if(knowledgeAgent == null)
				throw new RuntimeException("Erreur lors de la construction de la session intelligente (stateless) : l'agent intelligent referencee par '" + knowledgeSessionConfiguration.getKnowledgeAgentRef().getKnowledgeAgent() + "' n'existe pas.");
			
			// Construction de la base de connaissance
			knowledgeBase = knowledgeAgent.getKnowledgeBase();
			
		}
		
		// Construction de la session sans etat
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		
		// Si la session est a debogguer
		if(knowledgeSessionConfiguration.isDebug()) {

			// Ajout du listener de debug des evenements sur la memoire de travail
			statelessKnowledgeSession.addEventListener(new DebugWorkingMemoryEventListener(System.out));
			
			// Ajout du listener de debug des evenements sur l'agenda
			statelessKnowledgeSession.addEventListener(new DebugAgendaEventListener(System.out));
		}
		
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
		
		// Agent intelligent
		KnowledgeAgent knowledgeAgent = null;
		
		// Si la reference a la base de connaissance est non nulle
		if(knowledgeSessionConfiguration.getKnowledgeBaseRef() != null) {
			
			// Obtention de la base de connaissance depuis la MAP des bases de connaissances
			knowledgeBase = this.knowledgeBases.get(knowledgeSessionConfiguration.getKnowledgeBaseRef().getKnowledgeBase().trim());
			
			// Si la base de connaissance est nulle
			if(knowledgeBase == null) 
				throw new RuntimeException("Erreur lors de la construction de la session intelligente (stateful) : la base de connaissance referencee par '" + knowledgeSessionConfiguration.getKnowledgeBaseRef().getKnowledgeBase() + "' n'existe pas.");
			
		} else {
			
			// Obtention de l'agent intelligent depuis la MAP des aggents intelligents
			knowledgeAgent = this.knowledgeAgents.get(knowledgeSessionConfiguration.getKnowledgeAgentRef().getKnowledgeAgent().trim());
			
			// Si l'agent est null
			if(knowledgeAgent == null)
				throw new RuntimeException("Erreur lors de la construction de la session intelligente (stateful) : l'agent intelligent referencee par '" + knowledgeSessionConfiguration.getKnowledgeAgentRef().getKnowledgeAgent() + "' n'existe pas.");
			
			// Construction de la base de connaissance
			knowledgeBase = knowledgeAgent.getKnowledgeBase();
			
		}
		
		// Construction de la session sans etat
		StatefulKnowledgeSession statefulKnowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
		
		// Si la session est a debogguer
		if(knowledgeSessionConfiguration.isDebug()) {

			// Ajout du listener de debug des evenements sur la memoire de travail
			statefulKnowledgeSession.addEventListener(new DebugWorkingMemoryEventListener(System.out));
			
			// Ajout du listener de debug des evenements sur l'agenda
			statefulKnowledgeSession.addEventListener(new DebugAgendaEventListener(System.out));
		}
		
		// On retourne la session sans etat
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
		
		// Initialisation de la MAP des resources
		buildResources(droolsServerConfiguration.getResources());
		
		// Initialisation de la MAP des Bases de connaissances
		buildKnowledgeBases(droolsServerConfiguration.getKnowledgeBases());
		
		// Obtention de la configuration des agents intelligents
		KnowledgeAgentsConfiguration knowledgeAgentsConfiguration = droolsServerConfiguration.getKnowledgeAgents();
		
		// Si la configuration est non nulle (Initialisation de la MAP des Agents intelligents)
		if(knowledgeAgentsConfiguration != null) buildKnowledgeAgents(knowledgeAgentsConfiguration);
		
		// Obtention de la configuration de la liste des sessions
		KnowledgeSessionsConfiguration knowledgeSessionsConfiguration = droolsServerConfiguration.getKnowledgeSessions();
		
		// Si la configuration est non nulle
		if(knowledgeSessionsConfiguration != null) {

			// Obtention de la liste des configurations des Sessions
			List<KnowledgeSessionConfiguration> knowledgeSessionConfigurations = knowledgeSessionsConfiguration.getKnowledgeSession();

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
		return (sessionName != null) && (!sessionName.trim().isEmpty()) && (knowledgeSessions.get(sessionName.trim()) != null);
	}
	
	/**
	 * Methode permettant de verifier qu'une session est de type Stateless
	 * @param sessionName	Nom de la session
	 * @return	Etat stateless de la session
	 */
	public boolean isStatelessSession(String sessionName) {
		
		// Si la session n'existe pas
		if(!isSessionExists(sessionName)) return false;
		
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

		// Si la session n'existe pas
		if(!isSessionExists(sessionName)) return false;
		
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
		
		// Si la session n'existe pas
		if(!isSessionExists(sessionName)) return null;
		
		// On retourne la session
		return knowledgeSessions.get(sessionName.trim());
	}
	
	/**
	 * Methode permettant d'obtenir une base de connaissance
	 * @param baseName	Nom de la base de connaissance
	 * @return	Base de connaissance
	 */
	public KnowledgeBase getKnowledgeBase(String baseName) {
		
		// Si la base n'existe pas
		if(!isBaseExists(baseName)) return null;
		
		// On retourne la base
		return knowledgeBases.get(baseName.trim());
	}
	
	/**
	 * Methode permettant de tester si une base de connaissance existe
	 * @param baseName	Nom de la base de connaissance
	 * @return	Etat d'existence de la base de connaissance
	 */
	public boolean isBaseExists(String baseName) {
		
		// on retourne l'etat d'existence
		return (baseName != null) && (!baseName.trim().isEmpty()) && (knowledgeBases.get(baseName.trim()) != null);
	}

	/**
	 * Methode permettant d'obtenir un agent intelligent
	 * @param sessionName	Nom de l'agent
	 * @return	Agent intelligent
	 */
	public KnowledgeAgent getKnowledgeAgent(String agentName) {
		
		// Si l'agent n'existe pas
		if(!isAgentExists(agentName)) return null;
		
		// On retourne l'agent
		return knowledgeAgents.get(agentName.trim());
	}
	
	/**
	 * Methode permettant de tester si une agent intelligent existe
	 * @param agentName	Nom de l'agent
	 * @return	Etat d'existence de l'agent
	 */
	public boolean isAgentExists(String agentName) {
		
		// on retourne l'etat d'existence
		return (agentName != null) && (!agentName.trim().isEmpty()) && (knowledgeAgents.get(agentName.trim()) != null);
	}
	
	/**
	 * Methode permettant de creer une session stateless a partir d'un agent intelligent
	 * @param sessionName	Nom de la session a creer
	 * @param agentName Nom de l'agent intelligent source
	 * @param overwrite Etat d'ecrasement d'une session existante
	 * @return	Agent intelligent
	 */
	public void newStatelessSessionFromAgent(String sessionName, String agentName, boolean overwrite) {

		// Si le nom de la session est vide
		if(sessionName == null || sessionName.trim().isEmpty()) throw new RuntimeException("net.leadware.drools.engine.create.sessionfromagent.sessionnameempty");
		
		// Si l'agent n'existe pas
		if(!isAgentExists(agentName)) throw new RuntimeException("net.leadware.drools.engine.create.sessionfromagent.agentnotfound");
		
		// Si la session existe et qu'on ne doit pas ecraser
		if(isSessionExists(sessionName) && !overwrite)  throw new RuntimeException("net.leadware.drools.engine.create.sessionfromagent.sessionexists.nooverwrite");
		
		// Obtention de l'agent
		KnowledgeAgent knowledgeAgent = knowledgeAgents.get(agentName);
		
		// Obtention de la base de connaissance
		KnowledgeBase knowledgeBase = knowledgeAgent.getKnowledgeBase();
		
		// Ajout de la session
		knowledgeSessions.put(sessionName, knowledgeBase.newStatelessKnowledgeSession());
	}

	/**
	 * Methode permettant de creer une session stateful a partir d'un agent intelligent
	 * @param sessionName	Nom de la session a creer
	 * @param agentName Nom de l'agent intelligent source
	 * @param overwrite Etat d'ecrasement d'une session existante
	 * @return	Agent intelligent
	 */
	public void newStatefulSessionFromAgent(String sessionName, String agentName, boolean overwrite) {
		
		// Si le nom de la session est vide
		if(sessionName == null || sessionName.trim().isEmpty()) throw new RuntimeException("net.leadware.drools.engine.create.sessionfromagent.sessionnameempty");
		
		// Si l'agent n'existe pas
		if(!isAgentExists(agentName)) throw new RuntimeException("net.leadware.drools.engine.create.sessionfromagent.agentnotfound");
		
		// Si la session existe et qu'on ne doit pas ecraser
		if(isSessionExists(sessionName) && !overwrite)  throw new RuntimeException("net.leadware.drools.engine.create.sessionfromagent.sessionexists.nooverwrite");
		
		// Obtention de l'agent
		KnowledgeAgent knowledgeAgent = knowledgeAgents.get(agentName);
		
		// Obtention de la base de connaissance
		KnowledgeBase knowledgeBase = knowledgeAgent.getKnowledgeBase();
		
		// Ajout de la session
		knowledgeSessions.put(sessionName.trim(), knowledgeBase.newStatefulKnowledgeSession());
	}
	
	/**
	 * Methode permettant de creer une session stateless a partir d'une base de connaissance
	 * @param sessionName	Nom de la session a creer
	 * @param baseName	Nom de la base de connaissance source
	 * @param overwrite Etat d'ecrasement d'une session existante
	 */
	public void newStatelessSessionFromBase(String sessionName, String baseName, boolean overwrite) {
		
		// Si le nom de la session est vide
		if(sessionName == null || sessionName.trim().isEmpty()) throw new RuntimeException("net.leadware.drools.engine.create.sessionfrombase.sessionnameempty");
		
		// Si l'agent n'existe pas
		if(!isBaseExists(baseName)) throw new RuntimeException("net.leadware.drools.engine.create.sessionfrombase.basenotfound");
		
		// Si la session existe et qu'on ne doit pas ecraser
		if(isSessionExists(sessionName) && !overwrite)  throw new RuntimeException("net.leadware.drools.engine.create.sessionfrombase.sessionexists.nooverwrite");
		
		// Obtention de la base de connaissance
		KnowledgeBase knowledgeBase = knowledgeBases.get(baseName);
		
		// Ajout de la session
		knowledgeSessions.put(sessionName.trim(), knowledgeBase.newStatelessKnowledgeSession());
	}
	
	/**
	 * Methode permettant de creer une session statefull a partir d'une base de connaissance
	 * @param sessionName	Nom de la session a creer
	 * @param baseName	Nom de la base de connaissance
	 * @param overwrite Etat d'ecrasement d'une session existante
	 */
	public void newStatefulSessionFromBase(String sessionName, String baseName, boolean overwrite) {
		
		// Si le nom de la session est vide
		if(sessionName == null || sessionName.trim().isEmpty()) throw new RuntimeException("net.leadware.drools.engine.create.sessionfrombase.sessionnameempty");
		
		// Si l'agent n'existe pas
		if(!isBaseExists(baseName)) throw new RuntimeException("net.leadware.drools.engine.create.sessionfrombase.basenotfound");
		
		// Si la session existe et qu'on ne doit pas ecraser
		if(isSessionExists(sessionName) && !overwrite)  throw new RuntimeException("net.leadware.drools.engine.create.sessionfrombase.sessionexists.nooverwrite");
		
		// Obtention de la base de connaissance
		KnowledgeBase knowledgeBase = knowledgeBases.get(baseName);
		
		// Ajout de la session
		knowledgeSessions.put(sessionName.trim(), knowledgeBase.newStatefulKnowledgeSession());
	}
	
	/**
	 * Methode d'execution d'une commandes batch
	 * @param command	Commandes batch a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults execute(BatchExecutionCommandImpl command) {
		
		// Nom de la session
		String sessionName = command.getLookup();
		
		// Si le lookup n'est pas present
		if(sessionName == null || sessionName.trim().isEmpty()) throw new RuntimeException("net.leadware.drools.engine.execute.commands.nosessionspecified");
		
		// Obtention de la session objet 
		Object oSession = getKnowledgeSession(sessionName);
		
		// Si la session n'existe pas
		if(oSession == null) throw new RuntimeException("net.leadware.drools.engine.execute.commands.sessionnotfound");
		
		// Resultat de l'execution
		ExecutionResults result = null;
		
		// Si on est en Stateless
		if(isStatelessSession(sessionName)) {
			
			// On caste
			StatelessKnowledgeSession knowledgeSession = (StatelessKnowledgeSession) oSession;
			
			// Execution
			result = knowledgeSession.execute(command);
			
		} else {
			
			// On caste
			StatefulKnowledgeSession knowledgeSession = (StatefulKnowledgeSession) oSession;
			
			// Execution
			result = knowledgeSession.execute(command);
		}
		
		// On retourne le resultat
		return result;
	}
	
	/**
	 * Methode d'execution d'une commandes batch sur une session creee sur une base de connaissace donnee
	 * @param baseName Base de connaissance source
	 * @param command	Commandes batch a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnBase(String baseName, BatchExecutionCommandImpl command) {
		
		// Si la base n'existe pas
		if(!isBaseExists(baseName)) throw new RuntimeException("net.leadware.drools.engine.execute.commands.onbase.basenotfound");
		
		// Obtention de la base
		KnowledgeBase knowledgeBase = knowledgeBases.get(baseName.trim());
		
		// Creation de la session stateless
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		
		// Execution de la commande
		ExecutionResults result = statelessKnowledgeSession.execute(command);
		
		// On retourne le resultat
		return result;
	}
	
	/**
	 * Methode d'execution d'une commandes batch sur une session creee sur une agent intelligent
	 * @param agentName Base de connaissance source
	 * @param command	Commandes batch a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnAgent(String agentName, BatchExecutionCommandImpl command) {
		
		// Si la base n'existe pas
		if(!isAgentExists(agentName)) throw new RuntimeException("net.leadware.drools.engine.execute.commands.onagent.agentnotfound");
		
		// Obtention de la base
		KnowledgeBase knowledgeBase = knowledgeAgents.get(agentName.trim()).getKnowledgeBase();
		
		// Creation de la session stateless
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		
		// Execution de la commande
		ExecutionResults result = statelessKnowledgeSession.execute(command);
		
		// On retourne le resultat
		return result;
	}

	/**
	 * Methode d'execution d'une liste de commandes
	 * @param commands	Liste de commandes a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults execute(String sessionName, List<GenericCommand<?>> commands) {
		
		// Obtention de la session objet 
		Object oSession = getKnowledgeSession(sessionName);
		
		// Si la session n'existe pas
		if(oSession == null) throw new RuntimeException("net.leadware.drools.engine.execute.commands.sessionnotfound");
		
		// Si la liste de commandes est vide
		if(commands == null || commands.isEmpty()) throw new RuntimeDroolsException("net.leadware.drools.engine.execute.commands.emptycommandslist");
		
		// Resultat de l'execution
		ExecutionResults result = null;
		
		// Si on est en Stateless
		if(isStatelessSession(sessionName)) {
			
			// On caste
			StatelessKnowledgeSession knowledgeSession = (StatelessKnowledgeSession) oSession;
			
			// Execution
			result = knowledgeSession.execute(CommandFactory.newBatchExecution(commands));
			
		} else {
			
			// On caste
			StatefulKnowledgeSession knowledgeSession = (StatefulKnowledgeSession) oSession;
			
			// Execution
			result = knowledgeSession.execute(CommandFactory.newBatchExecution(commands));
		}
		
		// On retourne le resultat
		return result;
	}

	/**
	 * Methode d'execution d'une liste de commandes sur une base de connaissance
	 * @param baseName Nom de la base de connaissance source
	 * @param commands	Liste de commandes a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnBase(String baseName, List<GenericCommand<?>> commands) {
		
		// Si la base n'existe pas
		if(!isBaseExists(baseName)) throw new RuntimeException("net.leadware.drools.engine.execute.commands.onbase.basenotfound");
		
		// Obtention de la base
		KnowledgeBase knowledgeBase = knowledgeBases.get(baseName.trim());
		
		// Creation de la session stateless
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		
		// Execution de la commande
		ExecutionResults result = statelessKnowledgeSession.execute(CommandFactory.newBatchExecution(commands));
		
		// On retourne le resultat
		return result;
	}

	/**
	 * Methode d'execution d'une liste de commandes sur un agent intelligent
	 * @param agentName Nom de l'agent intelligent source
	 * @param commands	Liste de commandes a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnAgent(String agentName, List<GenericCommand<?>> commands) {
		
		// Si la base n'existe pas
		if(!isAgentExists(agentName)) throw new RuntimeException("net.leadware.drools.engine.execute.commands.onagent.agentnotfound");
		
		// Obtention de la base
		KnowledgeBase knowledgeBase = knowledgeAgents.get(agentName.trim()).getKnowledgeBase();
		
		// Creation de la session stateless
		StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();
		
		// Execution de la commande
		ExecutionResults result = statelessKnowledgeSession.execute(CommandFactory.newBatchExecution(commands));
		
		// On retourne le resultat
		return result;
	}
}
