package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;

/**
 * Modélise de façon générale une fourmi
 * @author GB
 *
 */
public class Ant extends Animal{
	private Uid anthillId;
	private ToricPosition lastPos;
	private AntRotationProbabilityModel probModel;
	
	/**
	 * Construit une fourmi
	 * @param tp
	 * @param hitpoints
	 * @param lifespan
	 * @param anthillId
	 */
	public Ant(ToricPosition tp, int hitpoints, Time lifespan, Uid anthillId) {
		super(tp, hitpoints, lifespan);
		this.anthillId = anthillId;
		this.lastPos = tp;
		this.probModel = new PheromoneRotationProbabilityModel();
	}
	
	/**
	 * Construit une {@code Ant}
	 * @param tp
	 * @param hitpoints
	 * @param lifespan
	 * @param anthillId
	 * @param antProbModel
	 */
	public Ant(ToricPosition tp,int hitpoints, Time lifespan,
			Uid anthillId, AntRotationProbabilityModel antProbModel) {
		this(tp, hitpoints, lifespan, anthillId);
		this.probModel = antProbModel;
	}
	
	/**
	 * Retourne l'identifiant unique de la fourmilière à laquelle appartien {@code Ant}
	 * @return
	 */
	public final Uid getAnthillId() {
		return this.anthillId;
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		
	}
	
	@Override
	public double getSpeed() {
		return 0.0;
	}
	
	@Override
	public int getMinAttackStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxAttackStrength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Time getMaxAttackDuration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		
	}
	
	@Override
	protected final RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env) {
		return env.selectComputeRotationProbsDispatch(this);
	}
	
	@Override
	protected final void afterMoveDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectAfterMoveDispatch(this, dt);
	}
	
	@Override
	protected final boolean isEnemy(Animal entity) {
		return !this.isDead() && !entity.isDead() && entity.isEnemyDispatch(this);
	}

	@Override
	protected final boolean isEnemyDispatch(Termite other) {
		return true;
	}

	@Override
	protected final boolean isEnemyDispatch(Ant other) {
		if (this.getAnthillId().equals(other.getAnthillId())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Gère la diffusion de phéromone par une fourmi pendant le déplacement
	 * @param env
	 */
	private final void spreadPheromones(AntEnvironmentView env) {
		ToricPosition currentPos = this.getPosition();
		double d = this.lastPos.toricDistance(currentPos);
		double density = Context.getConfig().getDouble(Config.ANT_PHEROMONE_DENSITY);
		double energy = Context.getConfig().getDouble(Config.ANT_PHEROMONE_ENERGY);
		int numberPheromonesToCreate =(int)(d * density);
		if (numberPheromonesToCreate > 0) {
			Vec2d unitVector = lastPos.toricVector(currentPos).normalized();
			unitVector = unitVector.scalarProduct(d / numberPheromonesToCreate);
			while (numberPheromonesToCreate > 0) {
				lastPos = lastPos.add(unitVector);
				env.addPheromone(new Pheromone(lastPos, energy));
				numberPheromonesToCreate -= 1;
			}
		}
	}
	
	/**
	 * Retourne la probabilité de rotation d'une {@code Ant}
	 * @param env
	 * @return
	 */
	protected final RotationProbability computeRotationProbs(AntEnvironmentView env) {
		return this.probModel.computerRotationProbs(this.computeDefaultRotationProbs(), 
				this.getPosition(), this.getDirection(), env);
		//return this.computeDefaultRotationProbs();
	}
	
	/**
	 * Specifie le comportement propre à {@code Ant} après un mouvement
	 * @param env
	 * @param dt
	 */
	protected final void afterMoveAnt(AntEnvironmentView env, Time dt) {
		this.spreadPheromones(env);
	}
}
