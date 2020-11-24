package ch.epfl.moocprog;

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
	 * @param antWorker
	 * @param dt
	 */
	void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt);
}
