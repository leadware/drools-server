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
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

import net.leadware.drools.server.engine.configuration.DroolsServerConfigurationInitializer;
import net.leadware.drools.server.model.configuration.KnowledgeBaseConfiguration;

/**
 * Classe representant le server drools
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 25 déc. 2013 - 13:01:12
 */
public class DroolsServer {
	
	/**
	 * Initialiseur de configuration serveur
	 */
	private DroolsServerConfigurationInitializer serverConfigurationInitializer = null;
	
	/**
	 * Map des sessions avec etat
	 */
	private Map<String, StatefulKnowledgeSession> statefullSessions = new HashMap<String, StatefulKnowledgeSession>();
	
	/**
	 * Map des sessions stateless
	 */
	private Map<String, StatelessKnowledgeSession> statelessSessions = new HashMap<String, StatelessKnowledgeSession>();
	
	/**
	 * Methode permettant de construire une base de connaissance a partir de la base de connaissance configuree
	 * @param configurationKnowledgeBase	
	 * @return
	 */
	private KnowledgeBase buildKnowledgeBase(KnowledgeBaseConfiguration configurationKnowledgeBaseConfiguration) {
		return null;
	}
}
