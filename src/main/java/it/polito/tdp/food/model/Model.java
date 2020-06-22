package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private FoodDao dao;
	private Graph<Food, DefaultWeightedEdge> grafo;
	private Map<Integer, Food> idMap; //metto solo vertici
	private List<Food> vertici;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(Integer n) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap = new HashMap<>();
		this.vertici = new ArrayList<>();
		
		this.vertici = dao.listaVertici(n, idMap);
				
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(Arco a : dao.listaArchi(idMap)) {
			Graphs.addEdge(this.grafo, a.getF1(), a.getF2(), a.getPeso());
		}
		
	}
	
	public List<Arco> calorieCongiunte(Food f) {
		
		List<Food> adiacenti = Graphs.neighborListOf(this.grafo, f);
		List<Arco> calorieCongiunte = new ArrayList<>();
		
		for(Food food : adiacenti) {
			calorieCongiunte.add(new Arco(f, food, this.grafo.getEdgeWeight(this.grafo.getEdge(f, food))));
		}
		
		Collections.sort(calorieCongiunte);;
		return calorieCongiunte;
		
	}
	
	public List<Food> vertici(){
		return vertici;
	}
	
	public Integer nArchi() {
		return grafo.edgeSet().size();
	}
	
	
	
	

}
