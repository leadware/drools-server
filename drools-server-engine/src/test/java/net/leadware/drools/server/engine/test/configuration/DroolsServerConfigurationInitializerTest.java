/**
 * 
 */
package net.leadware.drools.server.engine.test.configuration;

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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import net.leadware.drools.server.engine.configuration.DroolsServerConfigurationInitializer;
import net.leadware.drools.server.model.configuration.DroolsServerConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeSessionConfiguration;
import net.leadware.drools.server.model.configuration.KnowledgeSessionTypeConfiguration;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe representant le cas de test de la classe DroolsServerConfigurationInitializer
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 25 déc. 2013 - 08:28:18
 */
public class DroolsServerConfigurationInitializerTest {
	
	/**
	 * Logger de la classe
	 */
	private Logger log = Logger.getLogger(getClass());
	
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
	 * Test method for {@link net.leadware.drools.server.engine.configuration.DroolsServerConfigurationInitializer#initConfiguration()}.
	 */
	@Test
	public void testInitConfiguration() {
		
		// Configuration serveur
		DroolsServerConfigurationInitializer initializer = new DroolsServerConfigurationInitializer();
		
		// Positionnement du chemin
		initializer.setConfigurationPath("configurations/drools-server-configuration-test-01.xml");
		
		// Positionnement de l'etat de recherche dans le classpath
		initializer.setInClasspath(true);
		
		// Positionnement de l'état de validation
		initializer.setValidateConfiguration(true);
		
		// Un log
		log.info(initializer);
		
		try {
			
			// Tentative d'initialisation
			initializer.initConfiguration();
			
			// Erreur
			fail("Echec du test : L'initialisation devrait produire une exception");
			
		} catch (Exception e) {
			
			// Trace
			log.error(e.getMessage());
		}
		
		// Positionnement du chemin
		initializer.setConfigurationPath("configurations/drools-server-configuration-test-02.xml");
		
		// Positionnement de l'etat de recherche dans le classpath
		initializer.setInClasspath(true);
		
		// Positionnement de l'état de validation
		initializer.setValidateConfiguration(true);

		// Un log
		log.info(initializer);
		
		try {
			
			// Tentative d'initialisation
			initializer.initConfiguration();
			
			// Erreur
			fail("Echec du test : L'initialisation devrait produire une exception");
			
		} catch (Exception e) {
			
			// Trace
			log.error(e.getMessage());
		}

		// Positionnement du chemin
		initializer.setConfigurationPath("configurations/drools-server-configuration-test-03.xml");
		
		// Positionnement de l'etat de recherche dans le classpath
		initializer.setInClasspath(true);
		
		// Positionnement de l'état de validation
		initializer.setValidateConfiguration(true);

		// Un log
		log.info(initializer);
		
		try {
			
			// Tentative d'initialisation
			initializer.initConfiguration();
			
			// Erreur
			fail("Echec du test : L'initialisation devrait produire une exception");
			
		} catch (Exception e) {
			
			// Trace
			log.error(e.getMessage());
		}
		
		// Positionnement du chemin
		initializer.setConfigurationPath("configurations/drools-server-configuration-test-04.xml");
		
		// Positionnement de l'etat de recherche dans le classpath
		initializer.setInClasspath(true);
		
		// Positionnement de l'état de validation
		initializer.setValidateConfiguration(true);

		// Un log
		log.info(initializer);
		
		try {
			
			// Tentative d'initialisation
			initializer.initConfiguration();
			
			// Erreur
			fail("Echec du test : L'initialisation devrait produire une exception");
			
		} catch (Exception e) {
			
			// Trace
			log.error(e.getMessage());
		}

		// Positionnement du chemin
		initializer.setConfigurationPath("configurations/drools-server-configuration-test-not-exists.xml");
		
		// Positionnement de l'etat de recherche dans le classpath
		initializer.setInClasspath(true);
		
		// Positionnement de l'état de validation
		initializer.setValidateConfiguration(true);

		// Un log
		log.info(initializer);
		
		try {
			
			// Tentative d'initialisation
			initializer.initConfiguration();
			
			// Erreur
			fail("Echec du test : L'initialisation devrait produire une exception");
			
		} catch (Exception e) {
			
			// Trace
			log.error(e.getMessage());
		}
		
		// Positionnement du chemin
		initializer.setConfigurationPath("configurations/drools-server-configuration-test-05.xml");
		
		// Positionnement de l'etat de recherche dans le classpath
		initializer.setInClasspath(true);
		
		// Positionnement de l'état de validation
		initializer.setValidateConfiguration(true);

		// Un log
		log.info(initializer);

		// Tentative d'initialisation
		DroolsServerConfiguration serverConfiguration = initializer.initConfiguration();
		
		// Verifications
		assertNotNull(serverConfiguration);
		assertNotNull(serverConfiguration.getKnowledgeSessions());
		assertEquals(2, serverConfiguration.getKnowledgeSessions().getKnowledgeSession().size());
		
		// Obtention des deux sessions
		KnowledgeSessionConfiguration session1 = serverConfiguration.getKnowledgeSessions().getKnowledgeSession().get(0);
		KnowledgeSessionConfiguration session2 = serverConfiguration.getKnowledgeSessions().getKnowledgeSession().get(1);
		
		// Verifications
		assertEquals("ksession-test-01", session1.getName());
		assertEquals("ksession-test-02", session2.getName());
		assertEquals(KnowledgeSessionTypeConfiguration.STATELESS, session1.getType());
		assertEquals(KnowledgeSessionTypeConfiguration.STATEFUL, session2.getType());
		assertNull(session1.getKnowledgeAgentRef());
		assertNotNull(session1.getKnowledgeBaseRef());
		assertNotNull(session2.getKnowledgeAgentRef());
		assertNull(session2.getKnowledgeBaseRef());
	}

}
