package ch.epfl.moocprog;

import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.app.Context;

/**
 * Modélise les traces de phéromones
 * @author GB
 *
 */
public final class Pheromone extends Positionable {
	private double quantity;
	
	/**
	 * Construit une phéromone
	 * @param toricPosition
	 */
	public Pheromone(ToricPosition toricPosition, double quantitiy) {
		super(toricPosition);
		this.quantity = quantitiy;
	}
	
	/**
	 * retourne la quantité de phéromone dans l'environnement
	 * @return la quantité de phéromone dans l'environnement
	 */
	public double getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Détermine si la quantité de phéromone est négligeable ou non
	 * @return la quantité de phéromone est négligeable (true) ou non (false)
	 */
	public boolean isNegligible() {
		if (this.getQuantity() < Context.getConfig().getDouble(Config.PHEROMONE_THRESHOLD)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Définit l'évolution de la quantité de phéromone au cours du temps
	 * @param dt
	 */
	public void update(Time dt) {
		if (!this.isNegligible()) {
			this.quantity -= dt.toSeconds() * Context.getConfig().getDouble(Config.PHEROMONE_EVAPORATION_RATE);
			if (this.quantity < 0) {
				this.quantity = 0;
			}
		}
	}
}
