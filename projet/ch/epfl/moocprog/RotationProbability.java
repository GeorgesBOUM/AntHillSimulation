package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Utils;

/**
 * Cette classe modélise les changements de directions opérés
 * qui peuvent être opérés par un animal.
 * @author GB
 *
 */
public class RotationProbability {
	private double [] angles;
	private double [] probabilities;
	
	/**
	 * Construit un changement de direction d'un angle {@code angles}
	 * et selon un probabilité de {@code probabilities}
	 * @param angles, le tableau des angles de rotation possibles
	 * @param probabilities, le tableau des probabilités correspondant à chacun des angles. 
	 */
	public RotationProbability(double [] angles, double [] probabilities) {
		Utils.requireNonNull(angles);
		Utils.requireNonNull(probabilities);
		Utils.require(angles.length == probabilities.length);
		this.angles = angles.clone();
		this.probabilities = probabilities.clone();
	}
	
	/**
	 * Retourne le tableau des directions possibles (en radians)
	 * @return le tableau des directions possibles (en radians)
	 */
	public double [] getAngles() {
		return this.angles.clone();
	}
	
	/**
	 * Retourne le tableau des probabilités de chaque direction possible
	 * @return le tableau des probabilités de chaque direction possible
	 */
	public double [] getProbabilities() {
		return this.probabilities.clone();
	}
}
