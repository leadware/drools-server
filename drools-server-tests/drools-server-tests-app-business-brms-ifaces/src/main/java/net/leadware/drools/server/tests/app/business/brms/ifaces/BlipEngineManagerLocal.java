/**
 * 
 */
package net.leadware.drools.server.tests.app.business.brms.ifaces;

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

import javax.ejb.Local;

import net.leadware.drools.server.engine.manager.ifaces.DroolsEngineManager;

/**
 * Classe representant l'interface locale du gestionnaire du moteur 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 11 févr. 2014 - 01:16:48
 */
@Local
public interface BlipEngineManagerLocal extends DroolsEngineManager {}
