package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.random.UniformDistribution;
import  ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;


/**
 * Cette classe modélise tout animal présent dans l'environnement
 * l'angle indique la direction dans laquelle l'animal regarde.
 * @author GB
 *
 */
public abstract class Animal extends Positionable {
	private double direction;
	private int hitpoints;
	private Time lifespan;
	private Time rotationDelay;
	
	/**
	 * Construit un Animal à une position passée en paramètre et 
	 * l'angle de la direction à 0.0
	 * @param tp
	 */
	public Animal(ToricPosition tp, int hitpoints, Time lifespan) {
		super(tp);
		this.direction = UniformDistribution.getValue(0.0, 2 * Math.PI);
		this.hitpoints = hitpoints;
		this.lifespan = lifespan;
		this.rotationDelay = Time.ZERO;
	}
	
	/**
	 * Retourne la vitesse d'un animal donné
	 * @return la vitesse d'un animal donné
	 */
	public abstract double getSpeed();
	
	/**
	 * Permet le rendu graphique des animaux dans l'environnement
	 * @param visitor
	 * @param s
	 */
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);
	
	/**
	 * Permet de définir le comportement spécifique de chaque animal
	 * @param env
	 * @param dt
	 */
	protected abstract void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt);
	
	/**
	 * Calcule la probabilité de rotation d'un animal
	 * (comportement spécifique à un animal donné, dans un environnement donné)
	 * @param env
	 * @return un nouveau {@code RotationProbability}
	 */
	protected abstract RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env);
	
	/**
	 * Permet de spécifier une comportement spécifique à chaque
	 * {@code Animal} après chaque mouvement (appel à move)
	 * @param env
	 * @param dt
	 */
	protected abstract void afterMoveDispatch(AnimalEnvironmentView env, Time dt);
	
	/**
	 * Retourne l'angle de la direction de l'animal
	 * @return l'angle de la direction de l'animal
	 */
	public final double getDirection() {
		return this.direction;
	}
	
	/**
	 * Définit la nouvelle direction
	 * @param direction
	 */
	public final void setDirection(double direction) {
		this.direction = direction;
	}
	
	/**
	 * Retourne la durée de vie maximale d'un animal
	 * @return la durée de vie maximale d'un animal
	 */
	public final Time getLifespan() {
		return this.lifespan;
	}
	
	/**
	 * Retourne le nombre de points de vie
	 * @return le nombre de points de vie
	 */
	public final int getHitpoints() {
		return this.hitpoints;
	}
	
	/**
	 * Retourne le temps écoulé depuis la précédente rotation
	 * @return le temps écoulé depuis la précédente rotation
	 */
	public Time getRotationDelay() {
		return this.rotationDelay;
	}
	
	/**
	 * Définit le temps écoulé depuis la dernière rotation
	 * @param rotationDelay
	 */
	public void setRotationDelay(Time rotationDelay) {
		this.rotationDelay = rotationDelay;
	}
	
	/**
	 * Retourne l'état boolean (mort ou vif) d'un animal
	 * @return l'état boolean (mort ou vif) d'un animal
	 */
	public final boolean isDead() {
		if (this.getHitpoints() <= 0 || this.getLifespan().compareTo(Time.ZERO) <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Permet de simuler l'évolution d'un animal dans l'environnement
	 * à chaque dt écoulé
	 * @param env
	 * @param dt
	 */
	public final void update(AnimalEnvironmentView env, Time dt) {
		this.getLifespan().minus(dt.times(Context.getConfig().getDouble(Config.ANIMAL_LIFESPAN_DECREASE_FACTOR)));
		if (!this.isDead()) {
			specificBehaviorDispatch(env, dt);
		}
	}
	
	/**
	 * Retourne l'angle de rotation le plus probable
	 * @return l'angle de rotation le plus probable
	 */
	private void rotate(AnimalEnvironmentView env) {
		// RotationProbability rt = this.computeRotationProbs();
		RotationProbability rt = this.computeRotationProbsDispatch(env);
		double nouvelleDirection = Utils.pickValue(rt.getAngles(), rt.getProbabilities());
		nouvelleDirection += this.getDirection();
		this.setDirection(nouvelleDirection);
	}
	
	/**
	 * Définit le mouvement (rectiligne uniforme) d'un animal
	 * @param dt
	 */
	protected final void move(AnimalEnvironmentView env, Time dt) {
		Time animalNextRotationDelay = Context.getConfig().getTime(Config.ANIMAL_NEXT_ROTATION_DELAY);
		Vec2d v;
		this.setRotationDelay(this.getRotationDelay().plus(dt));
		while (this.getRotationDelay().compareTo(animalNextRotationDelay) >= 0) {
			this.setRotationDelay(this.getRotationDelay().minus(animalNextRotationDelay));
			this.rotate(env);
		}
		v = Vec2d.fromAngle(this.getDirection()).scalarProduct(dt.toSeconds() * this.getSpeed());
		this.setPosition(this.getPosition().add(v));
	}
	
	/**
	 * Retourne un nouveau {@code RotationProbability} avec ses tableaux
	 * de directiion et d'angle (comportement par défaut d'un {@code Animal}
	 * @return un nouveau {@code RotationProbability}
	 */
	protected final RotationProbability computeDefaultRotationProbs() {
		double [] angles = {-180, -100, -55, -25, -10, 0, 10, 25, 55, 100, 180};
		double [] probabilities = {0.0000, 0.0000, 0.0005, 0.0010, 0.0050,
								   0.9870, 0.0050, 0.0010, 0.0005, 0.0000, 0.0000};
		for (int i = 0; i < angles.length; i++) {
			angles[i] = Math.toRadians(angles[i]);
		}
		return new RotationProbability(angles, probabilities);
	}
	
	@Override
	public String toString() {
		String affichage = super.toString() + "\n";
		affichage += String.format("Speed : %.1f", this.getSpeed()) + "\n";
		affichage += String.format("Hitpoints : %.1f", this.getHitpoints()) + "\n";
		affichage += String.format("Lifespan : %.1f", this.getLifespan());
		return affichage;
	}
	
}
