package br.com.caelum.agiletickets.models;

public enum Periodicidade {
	
	DIARIA {
		public int getIntervalo() { return 1; }
	}, 
	SEMANAL {
		public int getIntervalo() { return 7; }
	};
	
	public abstract int getIntervalo();
	
}
