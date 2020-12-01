package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;

/**
 * Cette classe modélise les AntWorker, ou fourmis ouvrières
 * @author GB
 *
 */
public final class AntWorker extends Ant {
	private double foodQuantity;
		
	/**
	 * Construit une nouvelle {@code AntWorker} positionnée en
	 * tp, ayant pour fourmilière anthillId, et ayant un 
	 * {@code AntRotationProbabilityModel} passés en paramètre
	 * @param tp
	 * @param anthillId
	 * @param antWorkerProbModel
	 */
	public AntWorker(ToricPosition tp, Uid anthillId,
			AntRotationProbabilityModel antWorkerProbModel) {
		super(tp, Context.getConfig().getInt(Config.ANT_WORKER_HP), 
				Context.getConfig().getTime(Config.ANT_WORKER_LIFESPAN), 
				anthillId, antWorkerProbModel);
		this.foodQuantity = 0.0;
	}
	
	/**
	 * Construit une nouvelle {@code AntWorker} positionnée en
	 * tp, et ayant pour fourmilière anthillId, passés en paramètre
	 * @param tp
	 * @param anthillId
	 */
	public AntWorker(ToricPosition tp, Uid anthillId) {
		this(tp, anthillId, new PheromoneRotationProbabilityModel());
	}
	
	/*
	public AntWorker(ToricPosition tp, Uid anthillId) {
		super(tp, Context.getConfig().getInt(Config.ANT_WORKER_HP),
				Context.getConfig().getTime(Config.ANT_WORKER_LIFESPAN), anthillId);
		this.foodQuantity = 0.0;
	}
	*/
	
	@Override
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(Config.ANT_WORKER_MIN_STRENGTH);
	}

	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(Config.ANT_WORKER_MAX_STRENGTH);
	}

	@Override
	public Time getMaxAttackDuration() {
		return Context.getConfig().getTime(Config.ANT_WORKER_ATTACK_DURATION);
	}
	
	/**
	 * Retourne la quantité de nourriture transportée par {@code AntWorker}
	 * @return la quantité de nourriture transportée
	 */
	public double getFoodQuantity() {
		return this.foodQuantity;
	}
	
	/**
	 * Définit la valeur de l'angle de direction nécessaire pour un demi tour
	 * @return la nouvelle valeur de l'angle de direction après le demi tour
	 */
	private double turnAround() {
		double demiTour = this.getDirection() + Math.PI;
		if (demiTour > 2 * Math.PI) {
			demiTour -= 2 * Math.PI;
		}
		return demiTour;
	}
	
	/**
	 * Permet à {@code AntWorker} de chercher de la nourriture
	 * @param env
	 * @param dt
	 */
	protected void seekForFood(AntWorkerEnvironmentView env, Time dt) {
		double antMaxFood = Context.getConfig().getDouble(Config.ANT_MAX_FOOD);
		this.move(env, dt);
		if (this.getFoodQuantity() == 0) {
			Food closestFood = env.getClosestFoodForAnt(this);
			if (closestFood != null) {
				if (closestFood.getQuantity() < antMaxFood) {
					this.foodQuantity = closestFood.takeQuantity(closestFood.getQuantity());
				} else {
					this.foodQuantity = closestFood.takeQuantity(antMaxFood);
				}
				this.setDirection(this.turnAround());
			}
		}
		if (env.dropFood(this) && this.getFoodQuantity() > 0) {
			this.foodQuantity = 0;
			this.setDirection(turnAround());
		}
	}
	
	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(Config.ANT_WORKER_SPEED);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
	
	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectSpecificBehaviorDispatch(this, dt);
	}
	
	@Override
	public String toString() {
		String affichage = super.toString() + "\n";
		affichage += String.format("Quantity : %.1f", this.getFoodQuantity());
		return affichage;
	}
}
