package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Utils;
/**
 * Modélise une fourmilière dans l'environnement
 * @author GB
 *
 */
public final class Anthill extends Positionable {
	private Uid anthillID;
	private double foodQuantity;
	private double antWorkerProbability;
	
	/**
	 * Construit une fourmilière positionné en tp donnée en paramètre
	 * @param toricPosition
	 */
	public Anthill(ToricPosition tp) {
		super(tp);
		this.anthillID = Uid.createUid();
		this.foodQuantity = 0.0;
		this.antWorkerProbability = Context.getConfig().getDouble(Config.ANTHILL_WORKER_PROB_DEFAULT);
	}
	
	/**
	 * Construit une fourmilière positionné en tp donnée en paramètre
	 * @param toricPosition
	 */
	public Anthill(ToricPosition tp, double antWorkerProbability) {
		super(tp);
		this.anthillID = Uid.createUid();
		this.foodQuantity = 0.0;
		this.antWorkerProbability = antWorkerProbability;
	}
	
	/**
	 * Retourne la probabilité de sortie d'une fourmi
	 * @return la probabilité de sortie d'une fourmi
	 */
	public double getAntWorkerProbability() {
		return this.antWorkerProbability;
	}
	
	/**
	 * retourne la quantité de nourriture dans fourmilière
	 * @return la quantité de nourriture dans fourmilière
	 */
	public double getFoodQuantity() {
		return this.foodQuantity;
	}
	
	/**
	 * Retourne l'identifiant unique de la fourmilière
	 * @return l'identifiant unique de la fourmilière
	 */
	public final Uid getAnthillId() {
		return this.anthillID;
	}
	
	/**
	 * Détermine la quantité de nourriture ramenée par {@code AntWorker}
	 * @param toDrop
	 */
	public void dropFood(double toDrop) {
		Utils.require(toDrop >= 0);
		this.foodQuantity += toDrop;
	}
	
	@Override
	public String toString() {
		String affichage = super.toString();
		affichage += String.format("Quantity : %.1f", this.getFoodQuantity());
		return affichage;
	}
}
