package ch.epfl.moocprog;

import static ch.epfl.moocprog.random.UniformDistribution.getValue;
import static ch.epfl.moocprog.utils.Time.ZERO;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANIMAL_LIFESPAN_DECREASE_FACTOR;


import ch.epfl.moocprog.utils.Time;
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
	
	/**
	 * Construit un Animal à une position passée en paramètre et 
	 * l'angle de la direction à 0.0
	 * @param tp
	 */
	public Animal(ToricPosition tp, int hitpoints, Time lifespan) {
		super(tp);
		this.direction = getValue(0.0, 2 * Math.PI);
		this.hitpoints = hitpoints;
		this.lifespan = lifespan;
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
	 * Retourne l'angle de la direction de l'animal
	 * @return l'angle de la direction de l'animal
	 */
	public final double getDirection() {
		return this.direction;
	}
	
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
	 * Retourne l'état boolean (mort ou vif) d'un animal
	 * @return l'état boolean (mort ou vif) d'un animal
	 */
	public final boolean isDead() {
		if (this.getHitpoints() <= 0 || this.getLifespan().compareTo(ZERO) <= 0) {
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
	public void update(AnimalEnvironmentView env, Time dt) {
		this.getLifespan().minus(dt.times(getConfig().getDouble(ANIMAL_LIFESPAN_DECREASE_FACTOR)));
		if (!this.isDead()) {
			this.move(dt);
		}
	}
	
	/**
	 * Définit le mouvement (rectiligne uniforme) d'un animal
	 * @param dt
	 */
	protected final void move(Time dt) {
		Vec2d v = Vec2d.fromAngle(this.getDirection()).scalarProduct(dt.toSeconds() * this.getSpeed());
		this.setPosition(this.getPosition().add(v));
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
