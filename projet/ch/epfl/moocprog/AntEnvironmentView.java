package ch.epfl.moocprog;

/**
 * Modélise la "vue" des fourmis sur l'environnement
 * @author GB
 *
 */
public interface AntEnvironmentView extends AnimalEnvironmentView {
	
	/**
	 * Permet d'ajouter de la phéromone dans l'{@code Environnement}
	 * @param pheromone
	 */
	void addPheromone(Pheromone pheromone);
	
	/**
	 * Permet de repérer des phéromones autour d'une {@code Ant}
	 * @param position
	 * @param directionAngleRad
	 * @param angles
	 * @return
	 */
	double [] getPheromoneQuantitiesPerIntervalForAnt(ToricPosition position, 
								double directionAngleRad, double [] angles);
}
