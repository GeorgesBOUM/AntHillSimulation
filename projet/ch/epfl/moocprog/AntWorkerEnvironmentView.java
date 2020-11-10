package ch.epfl.moocprog;

/**
 * Modélise la "vue" des fourmis ouvrières sur l'environnement
 * @author GB
 *
 */
public interface AntWorkerEnvironmentView extends AntEnvironmentView {
	/**
	 * Retourne la {@code Food} la plus proche de {@code AntWorker}
	 * @param antWorker
	 * @return la nourriture la plus proche d'une ouvrière
	 */
	Food getClosestFoodForAnt (AntWorker antWorker);
	
	/**
	 * Retourne true si {@code AntWorker} peut rajouter sa nourriture à sa {@code Anthill}
	 * @param antWorker
	 * @return true si une ouvrière peut rajouter sa nourriture à sa fourmilière
	 */
	boolean dropFood (AntWorker antWorker);
}
