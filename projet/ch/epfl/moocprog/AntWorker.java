package ch.epfl.moocprog;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;

/**
 * Cette classe modélise les AntWorker, ou fourmis ouvrières
 * @author GB
 *
 */
public final class AntWorker extends Ant {
	private double foodQuantity;
	
	/**
	 * Construit une nouvelle AntWorker positionnée en
	 * tp, une position torique passée en paramètre
	 * @param tp
	 */
	public AntWorker(ToricPosition tp, Uid anthillId) {
		super(tp, Context.getConfig().getInt(Config.ANT_WORKER_HP),
			  Context.getConfig().getTime(Config.ANT_WORKER_LIFESPAN), anthillId);
		this.foodQuantity = 0;
	}
	
	/**
	 * Retourne la quantité de nourriture transportée par {@code AntWorker}
	 * @return la quantité de nourriture transportée
	 */
	public double getFoodQuantity() {
		return this.foodQuantity;
	}
	
	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(Config.ANT_WORKER_SPEED);
	}
	
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}
	
	@Override
	public String toString() {
		String affichage = super.toString() + "\n";
		affichage += String.format("Quantity : %.1f", this.getFoodQuantity());
		return affichage;
	}
}
