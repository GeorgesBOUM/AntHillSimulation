package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.random.NormalDistribution;
import ch.epfl.moocprog.random.UniformDistribution;
import static ch.epfl.moocprog.config.Config.FOOD_GENERATOR_DELAY;
import static ch.epfl.moocprog.config.Config.NEW_FOOD_QUANTITY_MIN;
import static ch.epfl.moocprog.config.Config.NEW_FOOD_QUANTITY_MAX;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;
import static ch.epfl.moocprog.app.Context.getConfig;

/**
 * Modélise le générateur de nourriture
 * @author GB
 *
 */
public final class FoodGenerator {
	private Time time;
	
	/**
	 * Construit par défaut un générateur de nourriture avec un temps de
	 * mise à jour à zéro.
	 */
	public FoodGenerator() {
		this.time = Time.ZERO;
	}
	
	/**
	 * Gère les mises à jour du générateur de nourriture
	 * @param env, les informations envoyée à l'environnement
	 * @param dt, période de mise à jour
	 */
	public void update(FoodGeneratorEnvironmentView env, Time dt) {
		Time delay = getConfig().getTime(FOOD_GENERATOR_DELAY);
		ToricPosition position;
		final int width = getConfig().getInt(WORLD_WIDTH);
		final int height = getConfig().getInt(WORLD_HEIGHT);
		final double foodMin = getConfig().getDouble(NEW_FOOD_QUANTITY_MIN);
		final double foodMax = getConfig().getDouble(NEW_FOOD_QUANTITY_MAX);
		double foodQuantity, x, y;
		
		this.time = this.time.plus(dt);
		while (this.time.compareTo(delay) >= 0) {
			this.time = this.time.minus(delay);
			foodQuantity = UniformDistribution.getValue(foodMin, foodMax);
			x = NormalDistribution.getValue(width / 2.0, (width * width) / 16.0);
			y = NormalDistribution.getValue(height / 2.0, (height * height) / 16.0);
			position = new ToricPosition(x, y);
			env.addFood(new Food(position, foodQuantity));
		}
	}
}
