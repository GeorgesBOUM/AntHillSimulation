package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_HP;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_LIFESPAN;

import ch.epfl.moocprog.utils.Time;

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
		//super(tp, getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN));
		super(tp);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
}
