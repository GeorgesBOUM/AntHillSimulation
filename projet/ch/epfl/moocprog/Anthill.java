package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;
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
	private Time spawnDelay;
	
	/**
	 * Construit une fourmilière positionné en tp donnée en paramètre
	 * @param toricPosition
	 */
	public Anthill(ToricPosition tp) {
		super(tp);
		this.anthillID = Uid.createUid();
		this.foodQuantity = 0.0;
		this.antWorkerProbability = Context.getConfig().getDouble(Config.ANTHILL_WORKER_PROB_DEFAULT);
		this.spawnDelay = Time.ZERO;
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
		this.spawnDelay = Time.ZERO;
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
	 * Détermine le temps écoulé depuis la dernière création de {@code Ant}
	 * @param delay
	 */
	public void setSpawnDelay(Time delay) {
		this.spawnDelay = delay;
	}
	
	/**
	 * Retourne le temps écoulé depuis la dernière création de {@code Ant}
	 * @return e temps écoulé depuis la dernière création de fourmi
	 */
	public Time getSpawnDelay() {
		return this.spawnDelay;
	}
	
	/**
	 * Détermine la quantité de nourriture ramenée par {@code AntWorker}
	 * @param toDrop
	 */
	public void dropFood(double toDrop) {
		Utils.require(toDrop >= 0);
		this.foodQuantity += toDrop;
	}
	
	/**
	 * Génère des {@code Ant} et met à jour la {@code Anthill}
	 * @param dt
	 * @param env
	 */
	public void update(AnthillEnvironmentView env, Time dt) {
		Time antSpawnDelay = Context.getConfig().getTime(Config.ANTHILL_SPAWN_DELAY);
		double probability;
		this.setSpawnDelay(this.getSpawnDelay().plus(dt));
		while (this.getSpawnDelay().compareTo(antSpawnDelay) >= 0) {
			this.setSpawnDelay(this.getSpawnDelay().minus(antSpawnDelay));
			probability = UniformDistribution.getValue(0, 1);
			if (probability <= this.antWorkerProbability) {
				env.addAnt(new AntWorker(this.getPosition(), this.getAnthillId()));
			} else {
				env.addAnt(new AntSoldier(this.getPosition(), this.getAnthillId()));
			}
		}
	}
	
	@Override
	public String toString() {
		String affichage = super.toString();
		affichage += String.format("Quantity : %.1f", this.getFoodQuantity());
		return affichage;
	}
}
