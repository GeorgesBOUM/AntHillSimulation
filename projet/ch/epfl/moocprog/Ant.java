package ch.epfl.moocprog;

public class Ant extends Animal{
	
	/**
	 * Construit une fourmi
	 * @param tp
	 */
	public Ant(ToricPosition tp) {
		super(tp);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		
	}
}
