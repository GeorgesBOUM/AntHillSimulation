package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
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
	public AntSoldier(ToricPosition tp, Uid anthillId) {
		super(tp, Context.getConfig().getInt(Config.ANT_SOLDIER_HP), 
			  Context.getConfig().getTime(Config.ANT_SOLDIER_LIFESPAN), anthillId);
	}
	
	/**
	 * Permet à {@code AntSoldier} de chercher et combattre les ennemies
	 * @param env
	 * @param dt
	 */
	protected void seekForEnemies(AntEnvironmentView env, Time dt) {
		this.move(dt);
	}
	
	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(Config.ANT_SOLDIER_SPEED);
	}
	
	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectSpecificBehaviorDispatch(this, dt);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
}
