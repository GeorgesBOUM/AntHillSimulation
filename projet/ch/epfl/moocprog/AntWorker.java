package ch.epfl.moocprog;

/**
 * Cette classe modélise les AntWorker, ou fourmis ouvrières
 * @author GB
 *
 */
public final class AntWorker extends Ant {
	
	/**
	 * Construit une nouvelle AntWorker positionnée en
	 * tp, une position torique passée en paramètre
	 * @param tp
	 */
	public AntWorker(ToricPosition tp) {
		super(tp);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
}
