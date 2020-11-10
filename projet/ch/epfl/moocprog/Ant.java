package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

/**
 * Modélise de façon générale une fourmi
 * @author GB
 *
 */
public class Ant extends Animal{
	private Uid anthillId;
	
	/**
	 * Construit une fourmi
	 * @param tp
	 */
	public Ant(ToricPosition tp, int hitpoints, Time lifespan, Uid anthillId) {
		super(tp, hitpoints, lifespan);
		this.anthillId = anthillId;
	}
	
	/**
	 * Retourne l'identifiant unique de la fourmilière à laquelle appartien {@code Ant}
	 * @return
	 */
	public final Uid getAnthillId() {
		return this.anthillId;
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		
	}
	
	@Override
	public double getSpeed() {
		return 0.0;
	}
}
