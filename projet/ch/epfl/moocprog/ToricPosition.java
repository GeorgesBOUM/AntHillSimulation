package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Vec2d;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

/**
 * Cette classe permet de représenter une position dans 
 * un envitonnement torique
 * 
 * @author GB
 * 
 */
public final class ToricPosition {
	private final Vec2d position;
	/**
	 * Construit une position par défaut
	 * au point origine (0, 0)
	 */
	public ToricPosition() {
		this.position = new Vec2d(0, 0);
	}
	
	/**
	 * Construit une nouvelle position à partir de deux
	 * coordonnées x et y fournies en paramètres
	 * @param x, l'abscisse
	 * @param y, l'ordonné
	 */
	public ToricPosition(double x, double y) {
		this.position = clampedPosition(x, y);
	}
	
	/**
	 * Construit une nouvelle position à partir d'une 
	 * position (Vec2d v)
	 * @param v, un vecteur
	 */
	public ToricPosition(Vec2d v) {
		this.position = clampedPosition(v.getX(), v.getY());
	}
	
	/**
	 * Indique une position dans l'environnement torique
	 * @param x, l'abscisse
	 * @param y, l'ordonné
	 * @return la projection du vecteur (x,y) dans l'environnement torique
	 */
	private static Vec2d clampedPosition(double x, double y) {
		int w = getConfig().getInt(WORLD_WIDTH);
		int h = getConfig().getInt(WORLD_HEIGHT);
		while (x < 0 || x >= w) {
			if (x < 0) {
				x += w;
			}
			if (x >= w) {
				x -= w;
			}
		}
		while (y < 0 || y >= h) {
			if (y < 0) {
				y += h;
			}
			if (y >= h) {
				y -= h;
			}
		}
		return new Vec2d(x, y);
	}
	
	/**
	 * Construit une nouvelle position correspondant à 
	 * l'addition des instances this et that en 
	 * restant dans un environnement torique
	 * @param that, une position torique
	 * @return la nouvelle position
	 */
	public ToricPosition add(ToricPosition that) {
		return new ToricPosition(this.position.add(that.position));
	}
	
	/**
	 * Construit une nouvelle position en ajoutant à la
	 * position de l'instance this, le vecteur vec, en
	 * restant dans un environnement torique
	 * @param v, un vecteur dans le plan
	 * @return la nouvelle posiition
	 */
	public ToricPosition add(Vec2d v) {
		return new ToricPosition(this.position.add(v));
	}
	
	/**
	 * Retourne l'objet interne Vec2d (la position
	 * dans l'environnement torique)
	 * @return l'objet interne Vec2d
	 */
	public Vec2d toVec2d() {
		return this.position;
	}
	
	/**
	 * Calcule le plus petit vecteur entre les instances this et that
	 * en tenant compte des contraintes toriques
	 * @param that, une position torique
	 * @return un vecteur entre this et that
	 */
	public Vec2d toricVector(ToricPosition that) {
		int tailleListeDeVecteurs = 9;
		Vec2d listeDeVecteurs [] = new Vec2d[tailleListeDeVecteurs];
		int w = getConfig().getInt(WORLD_WIDTH);
		int h = getConfig().getInt(WORLD_HEIGHT); 
		Vec2d plusPetitVecteur;
		Vec2d plusProchePosition;
		double plusPetiteDistance;
		double distance;
		listeDeVecteurs[0] = that.position;
		listeDeVecteurs[1] = that.position.add(new Vec2d(0, h));
		listeDeVecteurs[2] = that.position.add(new Vec2d(0, -h));
		listeDeVecteurs[3] = that.position.add(new Vec2d(w, 0));
		listeDeVecteurs[4] = that.position.add(new Vec2d(-w, 0));
		listeDeVecteurs[5] = that.position.add(new Vec2d(w, h));
		listeDeVecteurs[6] = that.position.add(new Vec2d(w, -h));
		listeDeVecteurs[7] = that.position.add(new Vec2d(-w, h));
		listeDeVecteurs[8] = that.position.add(new Vec2d(-w, -h));
		plusPetiteDistance = this.position.distance(listeDeVecteurs[0]);
		plusProchePosition = listeDeVecteurs[0];
		for (int i = 1; i < tailleListeDeVecteurs; i++) {
			distance = this.position.distance(listeDeVecteurs[i]);
			if (distance < plusPetiteDistance) {
				plusPetiteDistance = distance;
				plusProchePosition = listeDeVecteurs[i];
			}
		}
		plusPetitVecteur = plusProchePosition.minus(this.position);
		return plusPetitVecteur;
	}
	
	/**
	 * Calcule la plus petite distance torique entre les instances
	 * this et that
	 * @param that, une position torique
	 * @return la plus petite distance entre this et that
	 */
	public double toricDistance(ToricPosition that) {
		return this.toricVector(that).length();
	}
	
	@Override
	public String toString() {
		return this.position.toString();
	}
}