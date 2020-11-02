package ch.epfl.moocprog;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;

import static ch.epfl.moocprog.utils.Utils.requireNonNull;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;
import static ch.epfl.moocprog.app.Context.getConfig;

/**
 * Modélise l'environnement dans lequel évolue les différentes entités
 * @author GB
 *
 */
public final class Environment implements 
	FoodGeneratorEnvironmentView, AnimalEnvironmentView{
	private FoodGenerator foodGenerator;
	private List<Food> listFood;
	private List<Animal> listAnimal;
	
	/**
	 * Construit un environnement par défaut
	 */
	public Environment() {
		this.foodGenerator = new FoodGenerator();
		this.listFood = new LinkedList<Food>();
		this.listAnimal = new LinkedList<Animal>();
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
	
	/**
	 * Permet la mise à jour de l'environnement à chaque dt écoulé
	 * @param dt
	 */
	public void update(Time dt) {
		Iterator<Animal> aniter = this.listAnimal.iterator();
		this.foodGenerator.update(this, dt);
		while (aniter.hasNext()) {
			Animal animal = (Animal) aniter.next();
			if (animal.isDead()) {
				aniter.remove();
			} else {
				animal.update(null, dt);
			}
		}
		this.listFood.removeIf(food -> food.getQuantity() <= 0);
	}
	
	/**
	 * Permet le rendu graphique des éléments de l'environnement
	 * @param environmentRenderer
	 */
	public void renderEntities(EnvironmentRenderer environmentRenderer) {
		this.listFood.forEach(environmentRenderer::renderFood);
		this.listAnimal.forEach(environmentRenderer::renderAnimal);
	}
	
	/**
	 * Ajoute une fourmilière dans l'environnement
	 * @param a
	 */
	public void addAnthill(Anthill anthill) {
		
	}
	
	/**
	 * Ajoute un animal dans l'environnement
	 * @param animal
	 */
	public void addAnimal(Animal animal) {
		requireNonNull(animal);
		this.listAnimal.add(animal);
	}
	
	public List<ToricPosition> getAnimalsPosition() {
		LinkedList<ToricPosition> animalsPosition = new LinkedList<ToricPosition>();
		Iterator<Animal> aniter = this.listAnimal.iterator();
		while (aniter.hasNext()) {
			animalsPosition.add(aniter.next().getPosition());			
		}
		return animalsPosition;
	}
	
	/**
	 * Retourne la largeur de l'environnement
	 * @return la largeur de l'environnement
	 */
	public int getWidth() {
		return getConfig().getInt(WORLD_WIDTH);
	}
	
	/**
	 * Retourne la hauteur de l'environnement
	 * @return la hauteur de l'environnement
	 */
	public int getHeight() {
		return getConfig().getInt(WORLD_HEIGHT);
	}
}
