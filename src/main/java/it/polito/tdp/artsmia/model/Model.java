package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private Graph<Artist, DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	private Map<Integer,Artist> idMap;
	private List<Adiacenti> archi;
	private List<Artist> percorsoOttimo;
	private double costoOttimo;
	
	public Model() {
		dao = new ArtsmiaDAO();
		
	}
	
	public void creaGrafo(String ruolo) {
		idMap = new HashMap<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		archi = new ArrayList<>();
		
		dao.setVertici(idMap,ruolo);
		Graphs.addAllVertices(grafo, idMap.values());
		
		archi = dao.getArchi(idMap, ruolo);
		for(Adiacenti a : archi) {
			Graphs.addEdge(grafo, a.getA1(), a.getA2(), a.getPeso());
		}

	}
	
	public List<String> getRuoli(){
		return dao.listRoles();
	}
	
	public List<Adiacenti> getConnessioni(){
		return archi;
	}
	
	public int sizeVertex() {
		return grafo.vertexSet().size();
	}
	
	public int sizeEdges() {
		return grafo.edgeSet().size();
	}
	
	public List<Artist> getCammino(int idArtista){
		this.percorsoOttimo = new ArrayList<>();
		this.costoOttimo = 0;
		Artist partenza = idMap.get(idArtista);
		//System.out.println(idMap);
		if(partenza != null) {
			List<Artist> parziale = new ArrayList<>();
			parziale.add(partenza);
			cerca(parziale,0, partenza);
		}
		return percorsoOttimo;
	}

	private void cerca(List<Artist> parziale, double L, Artist partenza) {

		//System.out.println(Graphs.neighborListOf(grafo, partenza));
		if(parziale.size() == 1) {
			for(Artist a : Graphs.neighborListOf(grafo, partenza)) {
				DefaultWeightedEdge e = grafo.getEdge(partenza, a);
				parziale.add(a);
				cerca(parziale, grafo.getEdgeWeight(e),a);
				parziale.remove(a);
			}
		}else {
			for(Artist a : Graphs.neighborListOf(grafo, partenza)) {
				DefaultWeightedEdge e = grafo.getEdge(partenza, a);
				if(!parziale.contains(partenza) && grafo.getEdgeWeight(e) == L) {
					parziale.add(a);
					cerca(parziale, L, a);
					parziale.remove(a);
				}
			}
		}
		if(parziale.size()>percorsoOttimo.size()) {
			percorsoOttimo = new ArrayList<>(parziale);
			costoOttimo = L;
		}
		
	}
	
	public double getCostoOttimo() {
		return this.costoOttimo;
	}
}
