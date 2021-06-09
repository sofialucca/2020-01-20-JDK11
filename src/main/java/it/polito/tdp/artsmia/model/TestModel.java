package it.polito.tdp.artsmia.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo("Photographer");
		//System.out.println(m.getConnessioni());
		System.out.println(m.getCammino(1112));
		System.out.println(m.getCostoOttimo());
	}

}
