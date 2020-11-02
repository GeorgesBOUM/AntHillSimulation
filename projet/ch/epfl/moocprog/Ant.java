package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

public class Ant extends Animal{
	
	/**
	 * Construit une fourmi
	 * @param tp
	 */
	public Ant(ToricPosition tp) {
		super(tp, 0, Time.ZERO);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		
	}
	
	@Override
	public double getSpeed() {
		return 0.0;
	}
}
