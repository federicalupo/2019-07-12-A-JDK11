package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Evento;
import it.polito.tdp.food.model.Evento.TipoEvento;

public class Simulatore {
	
	/*
	 * per ogni k (purchè ci siano cibi) => metto un cibo in preparazione e diminuisco nStazioni
	 * EVENTO => in preparazione (aggiungo minuti) e schedulo fine 
	 * 
	 *  
	 * EVENTO => preparato
	 * tolgo da inpreparaazione metto in preparato
	 * aggiungo nSTazione
	 * tolgo nStazione perchè schedulo inpreparazione
	 * 
	 * 
	 * le stazioni cucinano in parallelo quindi non posso avere solo una variabile tempo
	 * perchè parte da 0, aggiunge la prima cottura
	 * e poi il secondo cibo verrà cucinato dopo la prima cottura
	 * 
	 * ogni stazione ha suo tempo
	 * */
	
	//mondo
	private Graph<Food, DefaultWeightedEdge> grafo;
	private List<Food> preparati;
	private List<Food> inPreparazione;
	private Integer stazioniDisponibili;
	private Model model;
	
	//input
	private Integer k; //numeroStazioni
	
	
	//coda
	private PriorityQueue<Evento> coda ;
	
	
	//output
	private Integer nCibi;
	private Double tempo;
	
	
	public void init(Graph<Food, DefaultWeightedEdge> grafo, Model model, Integer k, Food f) {
		this.grafo = grafo;
		this.model = model;
		this.k = k;
		this.preparati = new ArrayList<>();
		this.inPreparazione= new ArrayList<>();
		
		this.stazioniDisponibili=k;
		this.nCibi=0;
		this.tempo=0.0;

		this.coda = new PriorityQueue<>();
		
		List<Arco> adiacenti = model.calorieCongiunte(f);
		
		for(int i = 0; i< k && i< adiacenti.size(); i++) {
			Arco a = adiacenti.get(i);
			coda.add(new Evento(TipoEvento.IN_PREPARAZIONE, 0.0 , a.getPeso(), a.getF2()));
			this.inPreparazione.add(a.getF2());
			this.stazioniDisponibili--;
		}
	}
	//ma il primo prodotto è preparato???
	
	public void run() {
		while(!this.coda.isEmpty()) {
			processEvent(this.coda.poll());
		}
		
	}
	
	private void processEvent(Evento e) {
		switch(e.getTipo()) {
		case IN_PREPARAZIONE:
			
			this.coda.add(new Evento(TipoEvento.PREPARATO, e.getInizio()+e.getCottura(), 0.0, e.getCibo()));
			
			break;
			
		case PREPARATO:
			
			this.tempo = e.getInizio();
			this.nCibi++;
			this.stazioniDisponibili++;
			this.inPreparazione.remove(e.getCibo());
			this.preparati.add(e.getCibo());
			
			List<Arco> adiacenti = model.calorieCongiunte(e.getCibo());
			
			if(adiacenti.size()>0) {  
				Arco a = adiacenti.get(0);
				
				if(!preparati.contains(a.getF2()) && !inPreparazione.contains(a.getF2())){
					this.inPreparazione.add(a.getF2());
					this.stazioniDisponibili--;
					this.coda.add(new Evento(TipoEvento.IN_PREPARAZIONE, e.getInizio(), a.getPeso(), a.getF2()));
				}
			}
			
			break;
		}
		
	}

	public Integer getnCibi() {
		return nCibi;
	}

	public Double getTempo() {
		return tempo;
	}
	

}
