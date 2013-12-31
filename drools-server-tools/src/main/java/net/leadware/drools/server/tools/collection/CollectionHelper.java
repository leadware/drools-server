/**
 * 
 */
package net.leadware.drools.server.tools.collection;

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
