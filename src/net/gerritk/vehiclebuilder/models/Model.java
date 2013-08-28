package net.gerritk.vehiclebuilder.models;

import java.util.Observable;

public abstract class Model extends Observable {
	public Model() {
		setChanged();
	}
	
	public void notifyObservers(boolean changed) {
		if(changed) {
			setChanged();
		}
		
		notifyObservers();
	}
	
	public void notifyObservers(Object arg, boolean changed) {
		if(changed) {
			setChanged();
		}
		
		notifyObservers(arg);
	}
}
