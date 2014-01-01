/**
 * 
 */
package net.leadware.drools.server.tools.collection;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant le helper sur les collections
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI</a>
 * @since 31 déc. 2013 - 14:36:09
 */
public class CollectionHelper {
	
	/**
	 * Methode permettant de splitter une liste en un certain nombre de sous-listes
	 * @param list	Liste originale
	 * @param subListNumber	Nombre de sous-listes souhaitees
	 * @return	Liste de sous-liste splittee
	 */
	public static <T> List<List<T>>  splitList(List<T> list, int subListNumber) {
		
		// Liste a retourner
		List<List<T>> splitted = new ArrayList<List<T>>();
		
		// Si la liste est vide
		if(list == null || list.size() == 0) return splitted;
		
		// Taille de la liste
		int listSize = list.size();
		
		// Nombre d'elements de la liste
		int subSize = Math.max(listSize / (subListNumber - 1), 1);
		
		// Parcours de la liste a splitter
		for(int i = 0; i < listSize; i += subSize) {
			
			// Ajout de la sous-liste
			splitted.add(new ArrayList<T>(list.subList(i, Math.min(listSize, i + subSize))));
		}
		
		// On retourne la liste des sous-listes
		return splitted;
	}
	
}
