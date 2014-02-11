/**
 * 
 */
package com.oci.solde.components.business.brms.impl.engine;

/*
 * #%L
 * DROOLS SERVER
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2013 - 2014 Leadware
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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import net.leadware.drools.server.engine.DroolsEngine;
import net.leadware.drools.server.engine.manager.ifaces.DroolsEngineManager;
import net.leadware.drools.server.tests.app.business.brms.ifaces.BlipEngineManagerLocal;
import net.leadware.drools.server.tests.app.business.brms.ifaces.BlipEngineManagerRemote;

import org.drools.command.impl.GenericCommand;
import org.drools.command.runtime.BatchExecutionCommandImpl;
import org.drools.runtime.ExecutionResults;

/**
 * Classe representant l'implementation du service de gestion du moteur Drools 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 11 févr. 2014 - 01:27:29
 */
@Singleton(name = DroolsEngineManager.SERVICE_NAME, mappedName = DroolsEngineManager.SERVICE_NAME)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Startup
public class DroolsEngineManagerImpl implements BlipEngineManagerLocal, BlipEngineManagerRemote {
	
	/**
	 * Chemin du fichier de configuration
	 */
	private String configurationFile = "META-INF/drools-server-configuration.xml";
	
	/**
	 * Etat de recherche du fichier de configuration dans le classpath
	 */
	private boolean inClasspath = true;
	
	/**
	 * Etat de Validation du fichier de configuration
	 */
	private boolean validateConfiguration = true;
	
	/**
	 * Moteur de regles Drools
	 */
	private DroolsEngine droolsEngine = new DroolsEngine();
	
	
	/**
	 * Methode permettant d'initialiser du serveur Drools
	 */
	@PostConstruct
	private void start() {
		
		// Positionnement du chemin du fichier de configuration
		droolsEngine.setConfigurationPath(configurationFile);
		
		// Positionnement de la recherche dans le classpath
		droolsEngine.setInClasspath(inClasspath);
		
		// Positionnement de la validation du fichier de configuration
		droolsEngine.setValidateConfiguration(validateConfiguration);
		
		// Demarrage de la creation des elements du moteur de regle
		droolsEngine.start();
	}
	
	/**
	 * Methode permettant permettant d'arreter le moteur drools
	 */
	@PreDestroy
	private void stop() {
		
		// Arret du moteur
		droolsEngine.stop();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#isSessionExists(java.lang.String)
	 */
	@Override
	public boolean isSessionExists(String sessionName) {
		
		// Delegation Moteur Drools
		return droolsEngine.isSessionExists(sessionName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#isStatelessSession(java.lang.String)
	 */
	@Override
	public boolean isStatelessSession(String sessionName) {

		// Delegation Moteur Drools
		return droolsEngine.isStatelessSession(sessionName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#isStatefulSession(java.lang.String)
	 */
	@Override
	public boolean isStatefulSession(String sessionName) {

		// Delegation Moteur Drools
		return droolsEngine.isStatefulSession(sessionName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#isBaseExists(java.lang.String)
	 */
	@Override
	public boolean isBaseExists(String baseName) {

		// Delegation Moteur Drools
		return droolsEngine.isBaseExists(baseName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#isAgentExists(java.lang.String)
	 */
	@Override
	public boolean isAgentExists(String agentName) {

		// Delegation Moteur Drools
		return droolsEngine.isAgentExists(agentName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#newStatelessSessionFromAgent(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void newStatelessSessionFromAgent(String sessionName, String agentName, boolean overwrite) {

		// Delegation Moteur Drools
		droolsEngine.newStatelessSessionFromAgent(sessionName, agentName, overwrite);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#newStatefulSessionFromAgent(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void newStatefulSessionFromAgent(String sessionName, String agentName, boolean overwrite) {

		// Delegation Moteur Drools
		droolsEngine.newStatefulSessionFromAgent(sessionName, agentName, overwrite);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#newStatelessSessionFromBase(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void newStatelessSessionFromBase(String sessionName, String baseName, boolean overwrite) {

		// Delegation Moteur Drools
		droolsEngine.newStatelessSessionFromBase(sessionName, baseName, overwrite);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#newStatefulSessionFromBase(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void newStatefulSessionFromBase(String sessionName, String baseName, boolean overwrite) {

		// Delegation Moteur Drools
		droolsEngine.newStatefulSessionFromBase(sessionName, baseName, overwrite);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#execute(org.drools.command.runtime.BatchExecutionCommandImpl)
	 */
	@Override
	public ExecutionResults execute(BatchExecutionCommandImpl command) {

		// Delegation Moteur Drools
		return droolsEngine.execute(command);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#executeOnBase(java.lang.String, org.drools.command.runtime.BatchExecutionCommandImpl)
	 */
	@Override
	public ExecutionResults executeOnBase(String baseName, BatchExecutionCommandImpl command) {

		// Delegation Moteur Drools
		return droolsEngine.executeOnBase(baseName, command);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#executeOnAgent(java.lang.String, org.drools.command.runtime.BatchExecutionCommandImpl)
	 */
	@Override
	public ExecutionResults executeOnAgent(String agentName, BatchExecutionCommandImpl command) {

		// Delegation Moteur Drools
		return droolsEngine.executeOnAgent(agentName, command);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#execute(java.lang.String, java.util.List)
	 */
	@Override
	public ExecutionResults execute(String sessionName, List<GenericCommand<?>> commands) {

		// Delegation Moteur Drools
		return droolsEngine.execute(sessionName, commands);
	}

	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#executeOnBase(java.lang.String, java.util.List)
	 */
	@Override
	public ExecutionResults executeOnBase(String baseName, List<GenericCommand<?>> commands) {

		// Delegation Moteur Drools
		return droolsEngine.executeOnBase(baseName, commands);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#executeOnAgent(java.lang.String, java.util.List)
	 */
	@Override
	public ExecutionResults executeOnAgent(String agentName, List<GenericCommand<?>> commands) {

		// Delegation Moteur Drools
		return droolsEngine.executeOnAgent(agentName, commands);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oci.solde.components.business.brms.ifaces.engine.DroolsEngineManager#disposeSession(java.lang.String)
	 */
	@Override
	public void disposeSession(String sessionName) {

		// Delegation Moteur Drools
		droolsEngine.disposeSession(sessionName);
	}
}
