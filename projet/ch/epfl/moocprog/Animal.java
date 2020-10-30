package ch.epfl.moocprog;

/**
 * Cette classe modélise tout animal présent dans l'environnement
 * l'angle indique la direction dans laquelle l'animal regarde.
 * @author GB
 *
 */
public abstract class Animal extends Positionable {
	double direction;
	
	/**
	 * Construit un Animal à une position passée en paramètre et 
	 * l'angle de la direction à 0.0
	 * @param tp
	 */
	public Animal(ToricPosition tp) {
		super(tp);
		this.direction = 0.0;
	}
	
	/**
	 * Retourne l'angle de la direction de l'animal
	 * @return l'angle de la direction de l'animal
	 */
	public final double getDirection() {
		return this.direction;
	}
	
	/**
	 * Permet le rendu graphique des animaux dans l'environnement
	 * @param visitor
	 * @param s
	 */
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);
	
}
