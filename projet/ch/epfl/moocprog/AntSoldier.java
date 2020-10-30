package ch.epfl.moocprog;

/**
 * Cette classe modélise les AntSoldier, ou fourmis guerrières
 * @author GB
 *
 */
public final class AntSoldier extends Ant {
	
	/**
	 * Construit une nouvelle AntSoldier positionnée en
	 * tp, une position torique passée en paramètre
	 * @param tp
	 */
	public AntSoldier(ToricPosition tp) {
		super(tp);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
}
