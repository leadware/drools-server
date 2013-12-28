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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.leadware.drools.server.engine.DroolsEngine;

import org.apache.log4j.Logger;
import org.drools.command.runtime.BatchExecutionCommandImpl;
import org.drools.command.runtime.SetGlobalCommand;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.command.runtime.rule.InsertObjectCommand;
import org.drools.runtime.StatelessKnowledgeSession;
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
	//@Test
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
		String sessionName = "ksession-test-01";
		
		// Verifications
		assertTrue(droolsEngine.isSessionExists(sessionName));
		assertTrue(droolsEngine.isStatelessSession(sessionName));
		
		// Obtention de la session
		StatelessKnowledgeSession knowledgeSession = (StatelessKnowledgeSession) droolsEngine.getKnowledgeSession(sessionName);
		
		// Agent a valider
		Agent agent = new Agent("", "YASHIRO", "NANAKAZE", dateformat.parse("28/03/1981"), "DG");
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		// Positionnement de la variable globale
		knowledgeSession.setGlobal("result", validationResult);
		
		// Execution
		knowledgeSession.execute(agent);
		
		// Verification
		assertNotNull(validationResult.getText());
		assertEquals("Le matricule de l'objet est vide", validationResult.getText());
		
		// Arret du moteur
		droolsEngine.stop();
	}
	
	//@Test
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
		
		// Nom de la session
		String sessionName = "ksession-test-01";
		
		// Obtention de la session
		final StatelessKnowledgeSession knowledgeSession = (StatelessKnowledgeSession) droolsEngine.getKnowledgeSession(sessionName);
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		// Positionnement de la variable globale
		knowledgeSession.setGlobal("result", validationResult);
		
		// Liste totale des agents a process
		final List<Agent> agents = new ArrayList<Agent>();
		
		// Pas de decoupage
		int pas = 6000;
		
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
			
			// Execution
			knowledgeSession.execute(agents.subList(i, i+pas-1));
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
	
	//@Test
	public void testMasseExecutionPasUnitaire() throws ParseException, InterruptedException {
		
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
		
		// Nom de la session
		String sessionName = "ksession-test-01";
		
		// Obtention de la session
		final StatelessKnowledgeSession knowledgeSession = (StatelessKnowledgeSession) droolsEngine.getKnowledgeSession(sessionName);
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		// Positionnement de la variable globale
		knowledgeSession.setGlobal("result", validationResult);
		
		// Liste totale des agents a process
		final List<Agent> agents = new ArrayList<Agent>();
		
		// Pas de decoupage
		int pas = 1;
		
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
			
			// Execution
			knowledgeSession.execute(agents.subList(i, i+pas-1));
		}

		// Date/Heure de demarrage de l'execution
		long endProcess = System.currentTimeMillis();
		
		// Log
		log.info("Taille de la liste de faits					:	" + size);
		log.info("Quantite des donnees inseree dans la session	:	" + pas);
		log.info("Durée d'execution des regles					: 	" + (endProcess - startProcess)/(1000) + " Secondes");
		
		// Arret du moteur
		droolsEngine.stop();
	}
	
	/**
	 * Test method for {@link net.leadware.drools.server.engine.DroolsEngine#execute()}.
	 * @throws ParseException 
	 */
	@Test
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
		
		// Nom de la session
		String sessionName = "ksession-test-01";
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		SetGlobalCommand setGlobalCommand = new SetGlobalCommand();
		setGlobalCommand.setIdentifier("result");
		setGlobalCommand.setOutIdentifier("result");
		setGlobalCommand.setObject(validationResult);
		
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
			batch.setLookup(sessionName);
			
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

}
