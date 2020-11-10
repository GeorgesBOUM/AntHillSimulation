package ch.epfl.moocprog;

/**
 * Modélise la "vue" du générateur de nourriture sur l'environnement, 
 * c'est à dire, les possibilités d'interactions entre l'environnement et le
 * générateur de nourriture
 * @author GB
 *
 */
public interface FoodGeneratorEnvironmentView {
	
	/**
	 * Génère une quantité de nourriture
	 * @param food, quantité de nourriture à générer
	 */
	void addFood(Food food);
}
