package it.polito.tdp.food.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento>{
	
	public enum TipoEvento{
		IN_PREPARAZIONE,
		PREPARATO
	}
	
	private TipoEvento tipo;
	private Double inizio;
	private Double cottura;
	private Food cibo;
	
	public Evento(TipoEvento tipo, Double inizio, Double cottura, Food cibo) {
		super();
		this.tipo = tipo;
		this.inizio = inizio;
		this.cottura = cottura;
		this.cibo = cibo;
	}

	public TipoEvento getTipo() {
		return tipo;
	}


	public Double getInizio() {
		return inizio;
	}

	public Double getCottura() {
		return cottura;
	}

	public Food getCibo() {
		return cibo;
	}

	@Override
	public int compareTo(Evento o) {
		return inizio.compareTo(o.getInizio());
	}

	
	
	
	
}
