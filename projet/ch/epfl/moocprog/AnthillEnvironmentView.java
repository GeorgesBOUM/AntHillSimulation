package ch.epfl.moocprog;

/**
 * Modélise la "vue" des fourmilières sur l'environnement
 * @author GB
 *
 */
public interface AnthillEnvironmentView {
	
	/**
	 * Ajoute une fourmi à l'environnement
	 * @param ant
	 */
	void addAnt(Ant ant);
}
