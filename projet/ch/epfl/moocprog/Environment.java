package ch.epfl.moocprog;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

/**
 * Modélise l'environnement dans lequel évolue les différentes entités
 * @author GB
 *
 */
public final class Environment implements 
	FoodGeneratorEnvironmentView, AnimalEnvironmentView,
	AntEnvironmentView, AntWorkerEnvironmentView, AnthillEnvironmentView {
	private FoodGenerator foodGenerator;
	private List<Food> listFood;
	private List<Animal> listAnimal;
	private List<Anthill> listAnthill;
	
	/**
	 * Construit un environnement par défaut
	 */
	public Environment() {
		this.foodGenerator = new FoodGenerator();
		this.listFood = new LinkedList<Food>();
		this.listAnimal = new LinkedList<Animal>();
		this.listAnthill = new LinkedList<Anthill>();
	}
	
	/**
	 * Ajoute une food dans la List<Food>
	 */
	@Override
	public void addFood(Food f) {
		Utils.requireNonNull(f);
		this.listFood.add(f);
	}
	
	/**
	 * Ajoute une fourmilière dans l'environnement
	 * @param a
	 */
	public void addAnthill(Anthill anthill) {
		Utils.requireNonNull(anthill);
		this.listAnthill.add(anthill);
	}
	
	/**
	 * Ajoute un animal dans l'environnement
	 * @param animal
	 */
	public void addAnimal(Animal animal) {
		Utils.requireNonNull(animal);
		this.listAnimal.add(animal);
	}
	
	/**
	 * Ajoute une fourmi à l'environnement
	 * @param ant
	 */
	@Override
	public void addAnt(Ant ant) {
		this.addAnimal(ant);
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
	 * Retourne la liste des positions de tous les animaux
	 * @return la liste des positions de tous les animaux
	 */
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
		return Context.getConfig().getInt(Config.WORLD_WIDTH);
	}
	
	/**
	 * Retourne la hauteur de l'environnement
	 * @return la hauteur de l'environnement
	 */
	public int getHeight() {
		return Context.getConfig().getInt(Config.WORLD_HEIGHT);
	}
	
	/**
	 * Retourne la {@code Food} la plus proche d'une {@code AntWorker} dans son rayon de
	 * perception. S'il n'y a rien dans ce rayon, retourne null.
	 * @param antWorker
	 * @return la nourriture la plus proche d'une AntWorker 
	 */
	@Override
	public Food getClosestFoodForAnt(AntWorker antWorker) {
		Utils.requireNonNull(antWorker);
		Utils.requireNonNull(this.listFood);
		try {
			Food closestFood = Utils.closestFromPoint(antWorker, this.listFood);
			double distanceToClosestFood = antWorker.getPosition().toricDistance(closestFood.getPosition());
			if (distanceToClosestFood > Context.getConfig().getDouble(Config.ANT_MAX_PERCEPTION_DISTANCE)) {
				return null;
			} else {
				return closestFood;
			}
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Retourne true si {@code AntWorker} peut rajouter sa nourriture à sa {@code Anthill};
	 * dans ce cas, sa fourmilière est dans son rayon de perception
	 * @param antWorker
	 * @return true si une ouvrière peut rajouter sa nourriture à sa fourmilière
	 */
	@Override
	public boolean dropFood(AntWorker antWorker) {
		Utils.requireNonNull(antWorker);
		Utils.requireNonNull(this.listAnthill);
		
		Anthill goodAnthill = null;
		Iterator<Anthill> it = this.listAnthill.iterator();
		Uid anthillId = antWorker.getAnthillId();
		boolean anthillFound = false;
		double distanceToGoodAnthill;
		
		while (it.hasNext() && !anthillFound) {
			Anthill ah = (Anthill) it.next();
			if (ah.getAnthillId().equals(anthillId)) {
				anthillFound = true;
				goodAnthill = ah;
			}
		}
		
		try {
			distanceToGoodAnthill = antWorker.getPosition().toricDistance(goodAnthill.getPosition());
			if (distanceToGoodAnthill > Context.getConfig().getDouble(Config.ANT_MAX_PERCEPTION_DISTANCE)) {
				return false;
			} else {
				goodAnthill.dropFood(antWorker.getFoodQuantity());
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}	
	}
}
