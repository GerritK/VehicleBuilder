package net.gerritk.vehiclebuilder.interfaces;

public interface ILogger {	
	public void log(String msg, Type type);
	
	enum Type {
		INFO, WARNING, ERROR;
	}
}
