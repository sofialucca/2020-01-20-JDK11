package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenti;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	List<Adiacenti> result = model.getConnessioni();
    	if(result.isEmpty()) {
    		txtResult.appendText("NON CI SONO ARCHI ADIACENTI PER QUESTO GRAFO");
    		return;
    	}else {
    		txtResult.appendText("ARTISTI CONNESSI\n");
    		for(Adiacenti a : result) {
    			txtResult.appendText("\n" + a.toString());
    		}
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	if(!isValid()) {
    		return;
    	}
    	String input = this.txtArtista.getText();
    	List<Artist> result = model.getCammino(Integer.parseInt(input));
    	if(result.isEmpty()) {
    		txtResult.appendText("IDENTIFICATIVO " + input + " NON APPARTIENE AL GRAFO");
    	}else if(result.size() == 1) {
    		txtResult.appendText("ARTISTA CON IDENTIFICATIVO " + input + " è ISOLATO");
    	}else {
    		txtResult.appendText("PERCORSO A PARTIRE DA ARTISTA CON IDENTIFCATIVO " + input + ":\n");
    		result.remove(0);
    		txtResult.appendText("\nNUMERO OTTIMO DI ESPOSIZIONI: " + model.getCostoOttimo() + "\n");
    		for(Artist a: result) {
    			txtResult.appendText("\n" + a.toString());
    		}
    	}
    }

    private boolean isValid() {
		String s = this.txtArtista.getText();
		if(s == "") {
			txtResult.appendText("ERRORE: inserire un valore per trovare il percorso");
			return false;
		}
		try {
			if(Integer.parseInt(s)<0) {
				txtResult.appendText("ERRORE: identificativo dell'artista deve essere un numero intero positivo");
				return false;
			}
		}catch(NumberFormatException nfe) {
			txtResult.appendText("ERRORE: identificativo deve essere un numero");
			return false;
		}
		return true;
	}

	@FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String ruolo = this.boxRuolo.getValue();
    	if(ruolo == null) {
    		txtResult.appendText("ERRORE: scegliere un ruolo");
    		return;
    	}
    	
    	model.creaGrafo(ruolo);
    	
    	txtResult.appendText("GRAFO CREATO:\n");
    	txtResult.appendText("#VERTICI: " + model.sizeVertex());
    	txtResult.appendText("\n#ARCHI: " + model.sizeEdges());
    	
    	this.btnArtistiConnessi.setDisable(false);
    	this.btnCalcolaPercorso.setDisable(false);
    	this.txtArtista.setDisable(false);
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().setAll(model.getRuoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
