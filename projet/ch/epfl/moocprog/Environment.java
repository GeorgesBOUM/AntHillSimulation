package ch.epfl.moocprog;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import ch.epfl.moocprog.utils.Time;

import static ch.epfl.moocprog.utils.Utils.requireNonNull;

/**
 * Modélise l'environnement dans lequel évolue les différentes entités
 * @author GB
 *
 */
public final class Environment implements FoodGeneratorEnvironmentView{
	private FoodGenerator foodGenerator;
	private List<Food> listFood;
	
	/**
	 * Construit un environnement par défaut
	 */
	public Environment() {
		this.foodGenerator = new FoodGenerator();
		this.listFood = new LinkedList<Food>();
	}
	
	/**
	 * Ajoute une food dans la List<Food>
	 */
	public void addFood(Food f) {
		requireNonNull(f);
		this.listFood.add(f);
	}
	
	/**
	 * Retourne la liste des quantités de chaque Food disponible
	 * @return la liste des quantités de chaque Food disponible
	 */
	public List<Double> getFoodQuantities() {
		ListIterator<Food> it = this.listFood.listIterator();
		List<Double> foodQuantities = new LinkedList<Double>();
		while (it.hasNext()) {
			foodQuantities.add(it.next().getQuantity());
		}
		return foodQuantities;
	}
	
	public void update(Time dt) {
		this.foodGenerator.update(this, dt);
		this.listFood.removeIf(food -> food.getQuantity() <= 0);
	}
}
