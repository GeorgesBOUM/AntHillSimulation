package ch.epfl.moocprog;

/**
 * Modélise la nourriture consommable par les fourmis
 * @author GB
 *
 */
public final class Food extends Positionable{
	private double foodQuantity;
	
	/**
	 * Construit une entité Food avec des valeurs passée en paramètre
	 * @param tp, une position torique
	 * @param fq, une quantité de nourriture
	 */
	public Food(ToricPosition tp, double fq) {
		super(tp);
		if (fq < 0.0) {
			this.foodQuantity = 0.0;
		} else {
			this.foodQuantity = fq;
		}
	}
	
	/**
	 * Retourne la quantité de nourriture disponible
	 * @return la quantité de nourriture disponible
	 */
	public double getQuantity() {
		return this.foodQuantity;
	}
	
	/**
	 * Permet de préléver une quantité de nourriture
	 * @param q, la quantité de nourriture à prélever
	 * @return retourne la quantité de nourriture prélevée
	 */
	public double takeQuantity(double q) {
		double quantityTook;
		if (q < 0) {
			throw new IllegalArgumentException();
		} else {
			if (q > this.foodQuantity) {
				quantityTook = this.foodQuantity;
				this.foodQuantity = 0.0;
				return quantityTook;
			} else {
				quantityTook = q;
				this.foodQuantity -= q;
				return quantityTook;
			}
		}
	}
	
	public String toString() {
		return super.toString() + "\n" + String.format("Quantity : %.2f", getQuantity());
	}
}
