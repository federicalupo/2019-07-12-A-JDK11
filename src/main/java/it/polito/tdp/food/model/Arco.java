package it.polito.tdp.food.model;

public class Arco implements Comparable<Arco>{
 
	
	private Food f1;
	private Food f2;
	private Double peso;
	
	public Arco(Food f1, Food f2, double peso) {
		this.f1 = f1;
		this.f2 = f2;
		this.peso= peso;
	}

	public Food getF1() {
		return f1;
	}

	public Food getF2() {
		return f2;
	}

	public Double getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return  f1 + "  " + f2 + "  " + peso;
	}

	@Override
	public int compareTo(Arco o) {
		
		return -this.peso.compareTo(o.getPeso());
	}
	
	
}
