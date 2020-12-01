package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.TERMITE_HP;
import static ch.epfl.moocprog.config.Config.TERMITE_LIFESPAN;
import static ch.epfl.moocprog.config.Config.TERMITE_SPEED;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
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
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(Config.TERMITE_MIN_STRENGTH);
	}

	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(Config.TERMITE_MAX_STRENGTH);
	}

	@Override
	public Time getMaxAttackDuration() {
		return Context.getConfig().getTime(Config.TERMITE_ATTACK_DURATION);
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
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectSpecificBehaviorDispatch(this, dt);
	}

	@Override
	protected RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env) {
		return env.selectComputeRotationProbsDispatch(this);
	}

	@Override
	protected void afterMoveDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectAfterMoveDispatch(this, dt);
	}
	
	@Override
	protected boolean isEnemy(Animal entity) {
		return !this.isDead() && !entity.isDead() && entity.isEnemyDispatch(this);
	}

	@Override
	protected boolean isEnemyDispatch(Termite other) {
		return false;
	}

	@Override
	protected boolean isEnemyDispatch(Ant other) {
		return true;
	}
	
	/**
	 * Implémente le déplacement d'une {@code Termite} à la recherche d'ennemis
	 * @param env
	 * @param dt
	 */
	protected void seekForEnemies(AnimalEnvironmentView env, Time dt) {
		this.move(env, dt);
	}
	
	/**
	 * Retourne un nouveau {@code RotatiionProbability}
	 * @param env
	 * @return un nouveau {@code RotatiionProbability}
	 */
	protected RotationProbability computeRotationProbs(TermiteEnvironmentView env) {
		return this.computeDefaultRotationProbs();
	}
	
	/**
	 * Définit le comportement propre à une {@code Termite} après un mouvement
	 * @param env
	 * @param dt
	 */
	protected void afterMoveTermite(TermiteEnvironmentView env, Time dt) {
		
	}
}
