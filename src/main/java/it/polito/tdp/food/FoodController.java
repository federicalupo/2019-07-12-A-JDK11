/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
    		Integer n = Integer.valueOf(this.txtPorzioni.getText());
    		this.model.creaGrafo(n);
    		
    		this.txtResult.appendText("Creato grafo!\n");
    		this.txtResult.appendText("#vertici: "+this.model.vertici().size()+"\n#archi: "+model.nArchi());
    		
    		this.boxFood.getItems().clear(); //!!! pulire
    		this.boxFood.getItems().addAll(model.vertici());
    		this.boxFood.setValue(model.vertici().get(0));
    		
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserisci valore corretto");
    	}
    	
    	
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	
    	Food f= this.boxFood.getValue();
    	List<Arco> calorieC = model.calorieCongiunte(f);
    	
    	if(calorieC.size()>0) {
    	
	    	this.txtResult.appendText("Elenco 5 cibi adiacenti a - "+f+ " -  aventi calorie congiunte max:\n");
	    	
	    	
	    	for(int i=0; i<5 && i<calorieC.size(); i++) { //non è detto ce ne siano 5
	    		Arco a = calorieC.get(i);
	    		this.txtResult.appendText(a.getF2()+"   "+a.getPeso()+"\n");
	   
	    	}
    	}else {
    		this.txtResult.appendText("Nessun cibo adiacente trovato");
    		
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
