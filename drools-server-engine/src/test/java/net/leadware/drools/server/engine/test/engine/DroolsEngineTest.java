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

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.leadware.drools.server.engine.DroolsEngine;

import org.apache.log4j.Logger;
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

	/**
	 * Test method for {@link net.leadware.drools.server.engine.DroolsEngine#stop()}.
	 */
	@Test
	public void testStop() {}

}
