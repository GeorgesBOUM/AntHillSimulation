package ch.epfl.moocprog;

public final class Pheromone extends Positionable {

	public Pheromone(ToricPosition toricPosition) {
		super(toricPosition);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * retourne la quantité de phéromone dans l'environnement
	 * @return la quantité de phéromone dans l'environnement
	 */
	public double getQuantity() {
		return 0.0;
	}
	
	/**
	 * retourne la quantité de nourriture dans la phéromone
	 * @return la quantité de nourriture dans la phéromone
	 */
	public double getFoodQuantity() {
		return 0.0;
	}
}
