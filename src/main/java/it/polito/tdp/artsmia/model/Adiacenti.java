package it.polito.tdp.artsmia.model;

public class Adiacenti implements Comparable<Adiacenti>{

	private Artist a1;
	private Artist a2;
	private Integer peso;
	
	public Adiacenti(Artist a1, Artist a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	
	public Artist getA1() {
		return a1;
	}
	
	public void setA1(Artist a1) {
		this.a1 = a1;
	}
	
	public Artist getA2() {
		return a2;
	}
	
	public void setA2(Artist a2) {
		this.a2 = a2;
	}
	
	public int getPeso() {
		return peso;
	}
	
	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenti o) {
		// TODO Auto-generated method stub
		return o.peso.compareTo(this.peso);
	}

	@Override
	public String toString() {
		return a1 + " - " + a2 + " (" + peso + ")";
	}
	
	
}
