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
	private List<Pheromone> listPheromone;
	
	/**
	 * Construit un environnement par défaut
	 */
	public Environment() {
		this.foodGenerator = new FoodGenerator();
		this.listFood = new LinkedList<Food>();
		this.listAnimal = new LinkedList<Animal>();
		this.listAnthill = new LinkedList<Anthill>();
		this.listPheromone = new LinkedList<Pheromone>();
	}
	
	@Override
	public void addPheromone(Pheromone pheromone) {
		Utils.requireNonNull(pheromone);
		this.listPheromone.add(pheromone);
	}
	
	@Override
	public void addAnt(Ant ant) {
		this.addAnimal(ant);
	}
	
	@Override
	public void addFood(Food f) {
		Utils.requireNonNull(f);
		this.listFood.add(f);
	}
	
	/**
	 * Ajoute une fourmilière dans l'environnement
	 * @param anthill
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
	 * Retourne la liste des quantités de phéromones disponibles
	 * @return la liste des quantités de phéromones disponibles
	 */
	public List<Double> getPheromonesQuantities() {
		ListIterator<Pheromone> iterator = this.listPheromone.listIterator();
		List<Double> pheromonesQuantities = new LinkedList<Double>();
		while (iterator.hasNext()) {
			pheromonesQuantities.add(iterator.next().getQuantity());
		}
		return pheromonesQuantities;
	}
	
	/**
	 * Permet la mise à jour de l'environnement à chaque dt écoulé
	 * @param dt
	 */
	public void update(Time dt) {
		
		Iterator<Anthill> anthillIterator = this.listAnthill.iterator();
		Iterator<Pheromone> pheromoneIterator = this.listPheromone.iterator();
		this.foodGenerator.update(this, dt);
		while (pheromoneIterator.hasNext()) {
			Pheromone pheromone = pheromoneIterator.next();
			if (pheromone.isNegligible()) {
				pheromoneIterator.remove();
			} else {
				pheromone.update(dt);
			}
		}
		while (anthillIterator.hasNext()) {
			Anthill anthill = anthillIterator.next();
			anthill.update(this, dt);
		}
		/*
		 * En déclarant l'itérateur d'animaux ici, cela m'évite
		 * une java.util.ConccurentModificationException.
		 * Toujours en recherche d'une explication à la nécessité de
		 * déclarer à cet endroit précis et pas à un autre
		 */
		Iterator<Animal> aniter = this.listAnimal.iterator();
		while (aniter.hasNext()) {
			Animal animal = aniter.next();
			if (animal.isDead()) {
				aniter.remove();
			} else {
				animal.update(this, dt);
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
		this.listPheromone.forEach(environmentRenderer::renderPheromone);
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

	@Override
	public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt) {
		antWorker.seekForFood(this, dt);
	}

	@Override
	public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt) {
		antSoldier.seekForEnemies(this, dt);
	}
	
	@Override
	public RotationProbability selectComputeRotationProbsDispatch(Ant ant) {
		return ant.computeRotationProbs(this);
	}
	
	@Override
	public void selectAfterMoveDispatch(Ant ant, Time dt) {
		ant.afterMoveAnt(this, dt);
	}
	
	/**
	 * Normalise un angle donné en le projettant dans l'intervalle [0, 2pi]
	 * @param angle
	 * @return l'angle normalisé (dans [0, 2pi]
	 */
	private static double normalizedAngle(double angle) {
		while (angle < 0 ) {
			angle += 2 * Math.PI;
		}
		while (angle > 2 * Math.PI) {
			angle -= 2 * Math.PI;
		}
		return angle;
	}
	
	/**
	 * Retourne l'angle le plus proche d'une cible donnée
	 * @param angle
	 * @param target
	 * @return l'angle le plus proche d'une cible donnée
	 */
	private static double closestAngleFrom(double angle, double target) {
		double diff = angle - target;
		diff = Environment.normalizedAngle(diff);
		return Math.min(diff, (2 * Math.PI - diff));
	}
	
	@Override
	public double[] getPheromoneQuantitiesPerIntervalForAnt(ToricPosition position,
			double directionAngleRad, double[] angles) {
		
		Utils.requireNonNull(position);
		Utils.requireNonNull(angles);
		Utils.requireNonNull(listPheromone);
		
		double beta; //angle between ant and pheromone in environment
		Pheromone pheromone;
		ToricPosition pheromonePosition;
		double [] pheromonesQuantities = new double[angles.length];
		int index;
		double closestAngle;
		double oneAngle;
		Iterator<Pheromone> pheromoneIterator = this.listPheromone.iterator();
		double maxSmellDistance = Context.getConfig().getDouble(Config.ANT_SMELL_MAX_DISTANCE);
		
		while (pheromoneIterator.hasNext()) {
			pheromone = pheromoneIterator.next();
			pheromonePosition = pheromone.getPosition();
			if (!pheromone.isNegligible() && position.toricDistance(pheromonePosition) <= maxSmellDistance) {
				beta = position.toricVector(pheromonePosition).angle() - directionAngleRad;
				index = 0;
				closestAngle = Environment.closestAngleFrom(angles[0], beta);
				for (int i = 1; i < angles.length; i++) {
					oneAngle = Environment.closestAngleFrom(angles[i], beta);
					if (closestAngle > oneAngle) {
						index = i;
						closestAngle = oneAngle;
					} 
				}
				pheromonesQuantities[index] += pheromone.getQuantity();
			} 
		}
		return pheromonesQuantities;
	}
}
