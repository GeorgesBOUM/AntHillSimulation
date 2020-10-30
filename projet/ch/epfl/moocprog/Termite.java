package ch.epfl.moocprog;

public final class Termite extends Animal {
	
	/**
	 * Construit une nouvelle termite positionée dans l'environnement
	 * @param tp
	 */
	public Termite(ToricPosition tp) {
		super(tp);
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

}
