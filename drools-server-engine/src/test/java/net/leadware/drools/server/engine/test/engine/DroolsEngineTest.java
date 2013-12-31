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
import net.leadware.drools.server.tools.collection.CollectionHelper;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.runtime.BatchExecutionCommandImpl;
import org.drools.command.runtime.SetGlobalCommand;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.command.runtime.rule.InsertObjectCommand;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.rule.AgendaGroup;
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
		String sessionName = "ksession-validation";
		
		// Verifications
		assertTrue(droolsEngine.isSessionExists(sessionName));
		assertTrue(droolsEngine.isStatefulSession((sessionName)));
		
		// Obtention de la session
		StatefulKnowledgeSession knowledgeSession = (StatefulKnowledgeSession) droolsEngine.getKnowledgeSession(sessionName);
		
		// Agent a valider
		Agent agent = new Agent("", "YASHIRO", "NANAKAZE", dateformat.parse("28/03/1981"), "DG");
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		// Positionnement de la variable globale
		knowledgeSession.setGlobal("result", validationResult);
		
		// Insertion
		knowledgeSession.insert(agent);
		
		// Execution
		knowledgeSession.fireAllRules();
		
		// Verification
		assertNotNull(validationResult.getText());
		assertEquals("Le matricule de l'objet est vide", validationResult.getText());
		
		// Arret du moteur
		droolsEngine.stop();
	}
	
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
		
		// Nom de la session
		String sessionName = "printruleksession";
		
		// Obtention de la session
		final StatefulKnowledgeSession knowledgeSession = (StatefulKnowledgeSession) droolsEngine.getKnowledgeSession(sessionName);
		
		// Variable globale
		ValidationResult validationResult = new ValidationResult();
		
		// Positionnement de la variable globale
		knowledgeSession.setGlobal("result", validationResult);
		
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
		
		List<MyThread> threads = new ArrayList<DroolsEngineTest.MyThread>();
		
		// Parcours d'execution pas a pas
		for(List<Agent> subList : subLists) {
			
			// Instanciation d'un Thread
			MyThread t = new MyThread();
			
			// Positionnement de la sous-liste a traiter
			t.setAgents(subList);
			
			// Positionnement de la base de connaissance a utiliser
			t.setBase(droolsEngine.getKnowledgeBase("printrulekbase"));
			
			// Demarrage du process
			t.start();
			
			// Ajout
			threads.add(t);
		}

		for (MyThread myThread : threads) {
			
			synchronized (myThread) {

				if(myThread.isAlive()) myThread.wait();
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
		String sessionName = "printruleksession";
		
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
		
		// Nom de la session
		String sessionName = "printruleksession";
		
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
	
	public static void main(String[] args) throws ParseException {
		
		KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase();
		
		KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
		
		kb.add(ResourceFactory.newClassPathResource("drools/testrule.drl"), ResourceType.DRL);
		

		// S'il y a des erreurs
		if(kb.hasErrors()) {
			
			// On leve une exceprion
			throw new RuntimeException("Erreur lors du chargement des ressources metier : " + kb.getErrors().toString());
		}
		
		base.addKnowledgePackages(kb.getKnowledgePackages());
		
		StatefulKnowledgeSession session = base.newStatefulKnowledgeSession();
		
		AgendaGroup ag01 = session.getAgenda().getAgendaGroup("ag01");
		AgendaGroup ag02 = session.getAgenda().getAgendaGroup("ag02");
		
		Agent agent = new Agent("MAT01", "NOM01", "PRENOM01", dateformat.parse("28/02/1981"), "DG");
		
		ag01.setFocus();
		session.insert(agent);
		
		System.out.println("Agent avant la regle et l'agenda 01 : " + agent);
		session.fireAllRules();
		System.out.println("Agent apres la regle et l'agenda 01 : " + agent);
		agent.setPrenom("PRENOM02");
		System.out.println("Agent avant la regle et l'agenda 02 : " + agent);
		ag02.setFocus();
		session.fireAllRules();
		
	}
	
	class MyThread extends Thread {
		
		List<Agent> agents = new ArrayList<Agent>();
		
		KnowledgeBase base;
		
		@Override
		public void run() {
			
			synchronized (this) {
				
				// Construction de la session sans etat
				StatefulKnowledgeSession knowledgeSession = base.newStatefulKnowledgeSession();

				// Positionnement de la variable globale
				knowledgeSession.setGlobal("result", new ValidationResult());
				
				// Parcours de la sous-liste
				for(Agent agent : agents) {
					
					// Insertion
					knowledgeSession.insert(agent);
				}
				
				// Execution
				knowledgeSession.fireAllRules();

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
		 * Methode d'obtention du champ "base"
		 * @return champ "base"
		 */
		public KnowledgeBase getBase() {
			// Renvoi de la valeur du champ
			return base;
		}

		/**
		 * Methode de modification du champ "base"
		 * @param base champ base a modifier
		 */
		public void setBase(KnowledgeBase base) {
			// Modification de la valeur du champ
			this.base = base;
		}
		
	}
}
