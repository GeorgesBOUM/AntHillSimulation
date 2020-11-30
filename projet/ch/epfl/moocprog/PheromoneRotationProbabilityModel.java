package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;

/**
 * Représente le modèle de calcul de la probabilité
 * de rotation sensorielle (si détection de {@code Pheromone}
 * @author GB
 *
 */
public class PheromoneRotationProbabilityModel implements AntRotationProbabilityModel {
	
	/**
	 * Retourne le degré de perception par une {@code Ant} 
	 * d'une quantité x de {@code Pheromone}
	 * @param x
	 * @return le degré de perception
	 */
	private double detection(double x) {
		double beta = Context.getConfig().getDouble(Config.BETA_D);
		double qZero = Context.getConfig().getDouble(Config.Q_ZERO);
		return 1.0 / (1.0 + Math.exp(-beta * (x - qZero)));
	}
	
	@Override
	public RotationProbability computerRotationProbs(RotationProbability movementMatrix, 
			ToricPosition position, double directionAngle, AntEnvironmentView env) {
		double [] angles = movementMatrix.getAngles();
		double [] proba = movementMatrix.getProbabilities();
		int taille = angles.length;
		double [] probaPrime = new double [taille];
		double [] numerateur = new double [taille];
		double [] quantitiesDetected = env.getPheromoneQuantitiesPerIntervalForAnt(position, 
				directionAngle, angles);
		double somme = 0;
		int alpha = Context.getConfig().getInt(Config.ALPHA);
		
		for (int j = 0; j < taille; j++) {
			numerateur[j] = proba[j] * Math.pow(detection(quantitiesDetected[j]), alpha);
			somme += numerateur[j];
		}
		for (int j = 0; j < taille; j++) {
			probaPrime[j] = numerateur[j] / somme;
		}
		return new RotationProbability(angles, probaPrime);
	}
}
