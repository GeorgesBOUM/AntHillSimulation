package ch.epfl.moocprog;

/**
 * Modélise la "vue" des fourmis ouvrières sur l'environnement
 * @author GB
 *
 */
public interface AntWorkerEnvironmentView extends AntEnvironmentView {
	/**
	 * Retourne la {@code Food} la plus proche d'une {@code AntWorker} dans son rayon de
	 * perception. S'il n'y a rien dans ce rayon, retourne null.
	 * @param antWorker
	 * @return la nourriture la plus proche d'une AntWorker 
	 */
	Food getClosestFoodForAnt (AntWorker antWorker);
	
	/**
	 * Retourne true si {@code AntWorker} peut rajouter sa nourriture à sa {@code Anthill};
	 * dans ce cas, sa fourmilière est dans son rayon de perception
	 * @param antWorker
	 * @return true si une ouvrière peut rajouter sa nourriture à sa fourmilière
	 */
	boolean dropFood (AntWorker antWorker);
}
