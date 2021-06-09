package it.polito.tdp.artsmia.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo(m.getRuoli().get(10));
		System.out.println(m.getConnessioni());
	}

}
