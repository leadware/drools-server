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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.leadware.drools.server.engine.DroolsEngine;
import net.leadware.drools.server.tools.collection.CollectionHelper;

import org.apache.log4j.Logger;
import org.drools.command.runtime.BatchExecutionCommandImpl;
import org.drools.command.runtime.SetGlobalCommand;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.command.runtime.rule.InsertObjectCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe representant le cas de test du moteur Drools
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 26 déc. 2013 - 13:17:58
 */
public class DroolsEngineTest {

	/**
	 *  Taille totale de la liste des agents
	 */
	private long size = 2000000;
	
	/**
	 * Logger de la classe
	 */
	private Logger log = Logger.getLogger(getClass());

	/**
	 * Formateur de date
	 */
	private static final DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * Methode permettant d'initialiser le contexte avant l'execution de chaque test
	 * @throws java.lang.Exception	Exception potentielle
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Methode permettant de liberer le contexte apres l'execution de chaque test
	 * @throws java.lang.Exception	Exception potentielle
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Test method for {@link net.leadware.drools.server.engine.DroolsEngine#start()}.
	 * @throws ParseException Exception potentielle
	 */
	@Test
	public void testStart() throws ParseException {
		
		// Moteur Drools
		DroolsEngine droolsEngine = new DroolsEngine();
		
		// Positionnement du fichier de configuration
		droolsEngine.setConfigurationPath("configurations/drools-server-configuration-test-06.xml");

		// Positionnement de l'etat de recherche dans le classpath
		droolsEngine.setInClasspath(true);
		
		// Positionnement de l'état de validation
		droolsEngine.setValidateConfiguration(true);
		
		// Un log
		log.info("Demarrage du moteur");
		
		// Demarrage du moteur
		droolsEngine.start();
		
		// Nom de la session
		String sessionName = "ksession-validation";
		
		// Verifications
		assertTrue(droolsEngine.isSessionExists(sessionName));
		assertTrue(droolsEngine.isStatelessSession((sessionName)));
		
		// Batch de commandes
		BatchExecutionCommandImpl batch = new BatchExecutionCommandImpl();
		
		// Agent a valider
		Agent agent = new Agent("", "YASHIRO", "NANAKAZE", dateformat.parse("28/03/1981"), "DG");
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		// Commande de positionnement d'une variable globale
		SetGlobalCommand setResultGlobalCommand = new SetGlobalCommand("result", validationResult);
		
		// Mise en place du nom de la variable en sortie
		setResultGlobalCommand.setOutIdentifier(setResultGlobalCommand.getIdentifier());
		
		// Positionnement de la commande d'insertion d'une variable globale
		batch.getCommands().add(setResultGlobalCommand);
		
		// Ajout de la commande d'Insertion
		batch.getCommands().add(new InsertObjectCommand(agent));
		
		// Ajout de la commande d'execution
		batch.getCommands().add(new FireAllRulesCommand());
		
		// Positionnement de la session d'exeution
		batch.setLookup(sessionName);
		
		// Execution
		droolsEngine.execute(batch);
		
		// Verification
		assertNotNull(validationResult.getText());
		assertEquals("Le matricule de l'objet est vide", validationResult.getText());
		
		// Arret du moteur
		droolsEngine.stop();
	}
	
	/**
	 * Methode permettant d'effectuer les tests d'execution des donnees en masse sans ecriture sur les flux de sortie
	 * @throws ParseException	Exception potentielle
	 * @throws InterruptedException	Exception potentielle
	 */
	@Test
	public void testMasseExecutionPas() throws ParseException, InterruptedException {
		
		// Moteur Drools
		DroolsEngine droolsEngine = new DroolsEngine();
		
		// Positionnement du fichier de configuration
		droolsEngine.setConfigurationPath("configurations/drools-server-configuration-test-07.xml");

		// Positionnement de l'etat de recherche dans le classpath
		droolsEngine.setInClasspath(true);
		
		// Positionnement de l'état de validation
		droolsEngine.setValidateConfiguration(true);
		
		// Un log
		log.info("Demarrage du moteur");
		
		// Demarrage du moteur
		droolsEngine.start();
		
		// Nom de la base
		String baseName = "printrulekbase";
		
		// Liste totale des agents a process
		final List<Agent> agents = new ArrayList<Agent>();
		
		// Pas de decoupage
		int pas = 10;
		
		// Un log
		log.info("Construction de la liste globale");
		
		// Parcours pour initialisation de la liste globale
		for(long i = 0; i <= size; i++) {
			
			// Ajout de l'agent i
			agents.add(new Agent("MAT" + i, "Nom" + i, "Prenom" + i, dateformat.parse("28/03/1981"), "GRADE" + i));
		}
		
		// Date/Heure de demarrage de l'execution
		long startProcess = System.currentTimeMillis();
		
		// Un log
		log.info("Execution de la liste de taille : " + size + ", en pas de : " + pas);
		
		// Splitting des listes
		List<List<Agent>> subLists = CollectionHelper.splitList(agents, pas);
		
		// Liste des Threads d'Execution des regles sur les sous-listes d'agents
		List<AgentExecutionThread> threads = new ArrayList<DroolsEngineTest.AgentExecutionThread>();
		
		// Parcours d'execution pas a pas
		for(List<Agent> subList : subLists) {
			
			// Instanciation d'un Thread
			AgentExecutionThread agentExecutionThread = new AgentExecutionThread();
			
			// Positionnement de la sous-liste a traiter
			agentExecutionThread.setAgents(subList);
			
			// Positionnement de la knowledgeBaseName de connaissance a utiliser
			agentExecutionThread.setKnowledgeBaseName(baseName);
			
			// Positionnement du moteur
			agentExecutionThread.setDroolsEngine(droolsEngine);
			
			// Demarrage du process
			agentExecutionThread.start();
			
			// Ajout
			threads.add(agentExecutionThread);
		}
		
		// Parcours de la liste des Thread d'execution
		for (AgentExecutionThread agentExecutionThread : threads) {
			
			// Synchronisation sur le Thread en cours
			synchronized (agentExecutionThread) {
				
				// Si le Thread est encore actif
				if(agentExecutionThread.isAlive()) agentExecutionThread.wait();
			}
		}
		
		// Date/Heure de demarrage de l'execution
		long endProcess = System.currentTimeMillis();

		// Arret du moteur
		droolsEngine.stop();
		
		// Log
		log.info("Taille de la liste de faits					:	" + size);
		log.info("Quantite des donnees inseree dans la session	:	" + pas);
		log.info("Durée d'execution des regles					:	" + (endProcess - startProcess)/(1000) + " Secondes");
	}
	
	/**
	 * Test method for {@link net.leadware.drools.server.engine.DroolsEngine#execute()}.
	 * @throws ParseException 
	 */
	//@Test
	public void testExecute() throws ParseException {
		
		// Moteur Drools
		DroolsEngine droolsEngine = new DroolsEngine();
		
		// Positionnement du fichier de configuration
		droolsEngine.setConfigurationPath("configurations/drools-server-configuration-test-07.xml");

		// Positionnement de l'etat de recherche dans le classpath
		droolsEngine.setInClasspath(true);
		
		// Positionnement de l'état de validation
		droolsEngine.setValidateConfiguration(true);
		
		// Un log
		log.info("Demarrage du moteur");
		
		// Demarrage du moteur
		droolsEngine.start();
		
		// Nom de la session a creer
		String newSessionName = "new-session-name";
		
		// Base de connaissance source
		String baseName = "printrulekbase";
		
		// Verifions que cette session n'existe pas
		assertFalse(droolsEngine.isSessionExists(newSessionName));
		
		// Verifions que la base existe
		assertTrue(droolsEngine.isBaseExists(baseName));
		
		// Creation de la session
		droolsEngine.newStatelessSessionFromBase(newSessionName, baseName, false);

		// Verifions que cette session existe
		assertTrue(droolsEngine.isSessionExists(newSessionName));
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		// Commande de positionnement de la variable globale
		SetGlobalCommand setGlobalCommand = new SetGlobalCommand("result", validationResult);
		setGlobalCommand.setOutIdentifier("result");
		
		// Liste totale des agents a process
		final List<Agent> agents = new ArrayList<Agent>();
		
		// Pas de decoupage
		int pas = 100000;
		
		// Un log
		log.info("Construction de la liste globale");
		
		// Parcours pour initialisation de la liste globale
		for(long i = 0; i <= size; i++) {
			
			// Ajout de l'agent i
			agents.add(new Agent("MAT" + i, "Nom" + i, "Prenom" + i, dateformat.parse("28/03/1981"), "GRADE" + i));
		}
		
		// Date/Heure de demarrage de l'execution
		long startProcess = System.currentTimeMillis();
		
		// Un log
		log.info("Execution de la liste de taille : " + size + ", en pas de : " + pas);
		
		// Parcours d'execution pas a pas
		for(int i = 0; i <= size - pas + 1; i+=pas) {
			
			// Sous-liste
			List<Agent> subList = agents.subList(i, i+pas-1);

			// Batch executor
			BatchExecutionCommandImpl batch = new BatchExecutionCommandImpl();
			batch.setLookup(newSessionName);
			
			// Ajout
			batch.getCommands().add(setGlobalCommand);
			
			for(int j = 0; j < subList.size(); j++) batch.getCommands().add(new InsertObjectCommand(subList.get(j)));

			// FireAllRule
			FireAllRulesCommand fireall = new FireAllRulesCommand();
			
			// Ajout du fireall
			batch.getCommands().add(fireall);
			
			// Execution
			droolsEngine.execute(batch);
		}
		
		// Date/Heure de demarrage de l'execution
		long endProcess = System.currentTimeMillis();
		
		// Log
		log.info("Taille de la liste de faits					:	" + size);
		log.info("Quantite des donnees inseree dans la session	:	" + pas);
		log.info("Durée d'execution des regles					:	" + (endProcess - startProcess)/(1000) + " Secondes");
		
		// Arret du moteur
		droolsEngine.stop();
	}
	
	/**
	 * Classe representant le worker d'execution des regles sur une liste d'agents
	 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
	 * @since 4 janv. 2014 - 10:19:34
	 */
	class AgentExecutionThread extends Thread {

		/**
		 * Moteur Drools
		 */
		private DroolsEngine droolsEngine = null;
		
		/**
		 * Liste des agents a trater
		 */
		private List<Agent> agents = new ArrayList<Agent>();
		
		/**
		 * Nom de la base de connaissance sur laquelle on executera la commande
		 */
		private String knowledgeBaseName;
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			
			synchronized (this) {
				
				// Batch de commandes
				BatchExecutionCommandImpl batch = new BatchExecutionCommandImpl();
				
				// Commande globale
				SetGlobalCommand setResultGlobalCommand = new SetGlobalCommand("result", new ValidationResult());
				
				// Nom de sortie
				setResultGlobalCommand.setOutIdentifier("result");
				
				// Ajout de la commande variable globale
				batch.getCommands().add(setResultGlobalCommand);
				
				// Parcours de la sous-liste
				for(Agent agent : agents) {
					
					// Commande d'insertion
					batch.getCommands().add(new InsertObjectCommand(agent));
				}
				
				// Commande d'execution
				batch.getCommands().add(new FireAllRulesCommand());
				
				// Excution du batch de commande sur un session construite sur la knowledgeBaseName
				droolsEngine.executeOnBase(knowledgeBaseName, batch);
				
				// Notif
				notify();
			}
		}

		/**
		 * Methode d'obtention du champ "agents"
		 * @return champ "agents"
		 */
		public List<Agent> getAgents() {
			// Renvoi de la valeur du champ
			return agents;
		}

		/**
		 * Methode de modification du champ "agents"
		 * @param agents champ agents a modifier
		 */
		public void setAgents(List<Agent> agents) {
			// Modification de la valeur du champ
			this.agents = agents;
		}

		/**
		 * Methode d'obtention du champ "knowledgeBaseName"
		 * @return champ "knowledgeBaseName"
		 */
		public String getKnowledgeBaseName() {
			// Renvoi de la valeur du champ
			return knowledgeBaseName;
		}

		/**
		 * Methode de modification du champ "knowledgeBaseName"
		 * @param knowledgeBaseName champ knowledgeBaseName a modifier
		 */
		public void setKnowledgeBaseName(String base) {
			// Modification de la valeur du champ
			this.knowledgeBaseName = base;
		}

		/**
		 * Methode d'obtention du champ "droolsEngine"
		 * @return champ "droolsEngine"
		 */
		public DroolsEngine getDroolsEngine() {
			// Renvoi de la valeur du champ
			return droolsEngine;
		}

		/**
		 * Methode de modification du champ "droolsEngine"
		 * @param droolsEngine champ droolsEngine a modifier
		 */
		public void setDroolsEngine(DroolsEngine droolsEngine) {
			// Modification de la valeur du champ
			this.droolsEngine = droolsEngine;
		}
		
	}
}
