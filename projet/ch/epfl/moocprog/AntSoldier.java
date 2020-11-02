package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_HP;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_LIFESPAN;

import ch.epfl.moocprog.utils.Time;

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
		//super(tp, getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN));
		super(tp);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
}
