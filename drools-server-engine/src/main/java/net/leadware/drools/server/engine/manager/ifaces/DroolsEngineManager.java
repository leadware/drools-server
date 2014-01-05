/**
 * 
 */
package net.leadware.drools.server.engine.manager.ifaces;

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

import org.drools.command.impl.GenericCommand;
import org.drools.command.runtime.BatchExecutionCommandImpl;
import org.drools.runtime.ExecutionResults;

/**
 * Classe representant le gestionnaire du moteur Drools 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 4 janv. 2014 - 09:28:36
 */
public interface DroolsEngineManager {
	
	/**
	 * Nom du service
	 */
	public static final String SERVICE_NAME = "DroolsEngineManager";
	
	/**
	 * Methode permettant de tester si une session existe
	 * @param sessionName	Nom de la session
	 * @return	Etat d'existence de la session
	 */
	public boolean isSessionExists(String sessionName);
	
	/**
	 * Methode permettant de verifier qu'une session est de type Stateless
	 * @param sessionName	Nom de la session
	 * @return	Etat stateless de la session
	 */
	public boolean isStatelessSession(String sessionName);

	/**
	 * Methode permettant de verifier qu'une session est de type Stateful
	 * @param sessionName	Nom de la session
	 * @return	Etat stateful de la session
	 */
	public boolean isStatefulSession(String sessionName);
	
	/**
	 * Methode permettant de tester si une knowledgeBaseName de connaissance existe
	 * @param baseName	Nom de la knowledgeBaseName de connaissance
	 * @return	Etat d'existence de la knowledgeBaseName de connaissance
	 */
	public boolean isBaseExists(String baseName);
	
	/**
	 * Methode permettant de tester si une agent intelligent existe
	 * @param agentName	Nom de l'agent
	 * @return	Etat d'existence de l'agent
	 */
	public boolean isAgentExists(String agentName);
	
	/**
	 * Methode permettant de creer une session stateless a partir d'un agent intelligent
	 * @param sessionName	Nom de la session a creer
	 * @param agentName Nom de l'agent intelligent source
	 * @param overwrite Etat d'ecrasement d'une session existante
	 * @return	Agent intelligent
	 */
	public void newStatelessSessionFromAgent(String sessionName, String agentName, boolean overwrite);

	/**
	 * Methode permettant de creer une session stateful a partir d'un agent intelligent
	 * @param sessionName	Nom de la session a creer
	 * @param agentName Nom de l'agent intelligent source
	 * @param overwrite Etat d'ecrasement d'une session existante
	 * @return	Agent intelligent
	 */
	public void newStatefulSessionFromAgent(String sessionName, String agentName, boolean overwrite);
	
	/**
	 * Methode permettant de creer une session stateless a partir d'une knowledgeBaseName de connaissance
	 * @param sessionName	Nom de la session a creer
	 * @param baseName	Nom de la knowledgeBaseName de connaissance source
	 * @param overwrite Etat d'ecrasement d'une session existante
	 */
	public void newStatelessSessionFromBase(String sessionName, String baseName, boolean overwrite);
	
	/**
	 * Methode permettant de creer une session statefull a partir d'une knowledgeBaseName de connaissance
	 * @param sessionName	Nom de la session a creer
	 * @param baseName	Nom de la knowledgeBaseName de connaissance
	 * @param overwrite Etat d'ecrasement d'une session existante
	 */
	public void newStatefulSessionFromBase(String sessionName, String baseName, boolean overwrite);
	
	/**
	 * Methode d'execution d'une commandes batch
	 * @param command	Commandes batch a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults execute(BatchExecutionCommandImpl command);
	
	/**
	 * Methode d'execution d'une commandes batch sur une session creee sur une knowledgeBaseName de connaissace donnee
	 * @param baseName Base de connaissance source
	 * @param command	Commandes batch a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnBase(String baseName, BatchExecutionCommandImpl command);
	
	/**
	 * Methode d'execution d'une commandes batch sur une session creee sur une agent intelligent
	 * @param agentName Base de connaissance source
	 * @param command	Commandes batch a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnAgent(String agentName, BatchExecutionCommandImpl command);
	
	/**
	 * Methode d'execution d'une liste de commandes
	 * @param commands	Liste de commandes a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults execute(String sessionName, List<GenericCommand<?>> commands);
	
	/**
	 * Methode d'execution d'une liste de commandes sur une knowledgeBaseName de connaissance
	 * @param baseName Nom de la knowledgeBaseName de connaissance source
	 * @param commands	Liste de commandes a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnBase(String baseName, List<GenericCommand<?>> commands);

	/**
	 * Methode d'execution d'une liste de commandes sur un agent intelligent
	 * @param agentName Nom de l'agent intelligent source
	 * @param commands	Liste de commandes a executer
	 * @return Map des resultats de l'execution de la liste de commandes
	 */
	public ExecutionResults executeOnAgent(String agentName, List<GenericCommand<?>> commands);
	
	/**
	 * Methode permettant de liberer une session
	 * @param sessionName	Nom de la session
	 */
	public void disposeSession(String sessionName);
}
