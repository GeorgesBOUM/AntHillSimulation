package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.TERMITE_HP;
import static ch.epfl.moocprog.config.Config.TERMITE_LIFESPAN;
import static ch.epfl.moocprog.config.Config.TERMITE_SPEED;

import ch.epfl.moocprog.utils.Time;

public final class Termite extends Animal {
	
	/**
	 * Construit une nouvelle termite positionée dans l'environnement
	 * @param tp
	 */
	public Termite(ToricPosition tp) {
		super(tp, getConfig().getInt(TERMITE_HP), getConfig().getTime(TERMITE_LIFESPAN));
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
	
	@Override
	public double getSpeed() {
		return getConfig().getDouble(TERMITE_SPEED);
	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// TODO Auto-generated method stub
	}
}
