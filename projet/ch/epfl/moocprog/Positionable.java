package ch.epfl.moocprog;

/**
 * Représente une entité quelconque, positionné dans 
 * l'environnement torique
 * @author GB
 *
 */
public class Positionable {
	private ToricPosition position;
	
	/**
	 * Construit une entité initialement positionnée à 
	 * l'ordonné à l'origine (0, 0)
	 */
	public Positionable() {
		this.position = new ToricPosition();
	}
	
	/**
	 * Construit une entité initialement positionnée à
	 * la position passée en paramètre
	 * @param toricPosition, une position torique
	 */
	public Positionable(ToricPosition toricPosition) {
		this.position = toricPosition;
	}
	
	/**
	 * Retourne la position torique
	 * @return retourne la position torique
	 */
	public ToricPosition getPosition() {
		return this.position;
	}
	
	/**
	 * Modifie la position par une nouvelle valeur
	 * @param toricPosition, la nouvelle position
	 */
	final protected void setPosition(ToricPosition toricPosition) {
		this.position = toricPosition;
	}
	
	@Override
	public String toString() {
		return this.position.toString();
	}
}
