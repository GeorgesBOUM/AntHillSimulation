package ch.epfl.moocprog;

/**
 * Représente un comportement d'un modèle de calcul de la probabilité
 * de rotation sensorielle (détection de {@code Pheromone}
 * @author GB
 *
 */
public interface AntRotationProbabilityModel {
	
	/**
	 * Retourne la probabilité d'une rotation à la détection d'une {@code Pheromone}
	 * @param movementMatrix
	 * @param position
	 * @param directionAngle
	 * @param env
	 * @return la probabilité d'une rotation à la détection d'une {@code Pheromone}
	 */
	RotationProbability computerRotationProbs(
			RotationProbability movementMatrix,
			ToricPosition position,
			double directionAngle,
			AntEnvironmentView env);
}
