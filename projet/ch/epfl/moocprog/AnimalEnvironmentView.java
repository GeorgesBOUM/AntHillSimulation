package ch.epfl.moocprog;

import java.util.List;

import ch.epfl.moocprog.utils.Time;

/**
 * Modélise la "vue" des animaux sur l'environnement
 * @author GB
 *
 */
public interface AnimalEnvironmentView {
	
	/**
	 * Permet de définir le comportement spécifique d'une {@code AntWorker}
	 * @param antWorker
	 * @param dt
	 */
	void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt);
	
	/**
	 * Permet de définir le comportement spécifique d'une {@code AntSoldier}
	 * @param antSoldier
	 * @param dt
	 */
	void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt);
	
	/**
	 * Permet de définir le comportement spécifique d'une {@code Termite}
	 * @param termite
	 * @param dt
	 */
	void selectSpecificBehaviorDispatch(Termite termite, Time dt);
	
	/**
	 * Calcule la probabilité de rotation d'une {@code Ant}
	 * @param ant
	 * @return un nouveau {@code RotationProbability}
	 */
	RotationProbability selectComputeRotationProbsDispatch(Ant ant);
	
	/**
	 * Calcule la probabilité de rotation d'une {@code Termite}
	 * @param termite
	 * @return un nouveau {@code RotationProbability}
	 */
	RotationProbability selectComputeRotationProbsDispatch(Termite termite);
	
	/**
	 * Permet de spécifier une comportement spécifique à chaque
	 * {@code Ant} après chaque mouvement (appel à move)
	 * @param ant
	 * @param dt
	 */
	void selectAfterMoveDispatch(Ant ant, Time dt);
	
	/**
	 * Permet de spécifier une comportement spécifique à chaque
	 * {@code Termite} après chaque mouvement (appel à move)
	 * @param termite
	 * @param dt
	 */
	void selectAfterMoveDispatch(Termite termite, Time dt);
	
	/**
	 * Retourne la liste des ennemis d'un {@code Animal} dans un rayon donné
	 * @param from
	 * @return la liste des ennemis d'un {@code Animal} donné
	 */
	List<Animal> getVisibleEnemiesForAnimal(Animal from);
	
	/**
	 * Détermine si un {@code Animal} est visible de ses ennemis
	 * @param from
	 * @return un booléen (visible ou non)
	 */
	boolean isVisibleFromEnemies(Animal from);
}
