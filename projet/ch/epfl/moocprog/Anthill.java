package ch.epfl.moocprog;

/**
 * Modélise une fourmilière dans l'environnement
 * @author GB
 *
 */
public final class Anthill extends Positionable {
	
	/**
	 * Construit une fourmilière positionné en tp donnée en paramètre
	 * @param toricPosition
	 */
	public Anthill(ToricPosition tp) {
		super(tp);
	}
	
	/**
	 * retourne la quantité de fourmilières dans l'environnement
	 * @return la quantité de fourmilières dans l'environnement
	 */
	public double getQuantity() {
		return 0.0;
	}
	
	/**
	 * retourne la quantité de nourriture dans fourmilière
	 * @return la quantité de nourriture dans fourmilière
	 */
	public double getFoodQuantity() {
		return 0.0;
	}
}
