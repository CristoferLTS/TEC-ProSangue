package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MenuController implements Initializable{

    @FXML
    private BorderPane borderMenu;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuDoador;

    @FXML
    private MenuItem mIDoadorCadastrar;

    @FXML
    private MenuItem mIDoadorConsultar;

    @FXML
    private Menu menuDoacoes;

    @FXML
    private MenuItem mIDoacoesCadastrar;

    @FXML
    private MenuItem mIDoacoesConsultar;

    @FXML
    private MenuItem mIDoacoesExames;

    @FXML
    private Menu mPerguntas;

    @FXML
    private MenuItem mIPerguntasCadastrar;

    @FXML
    private MenuItem mIPerguntasConsultar;

    @FXML
    private Menu menuRelatorios;

    @FXML
    private MenuItem mIRelatoriosDoadores;

    @FXML
    private MenuItem mIRelatorioDoacoes;

    @FXML
    private Menu menuSair;

    @FXML
    private StackPane sPMenu;

    @FXML
    void abrirCadDoacoes(ActionEvent event) {

    }

    @FXML
    void abrirCadDoador(ActionEvent event) {

    }

    @FXML
    void abrirCadPerguntas(ActionEvent event) {

    }

    @FXML
    void abrirConConsultar(ActionEvent event) {

    }

    @FXML
    void abrirConDoador(ActionEvent event) {

    }

    @FXML
    void abrirConExames(ActionEvent event) {

    }

    @FXML
    void abrirConsDoacoes(ActionEvent event) {

    }

    @FXML
    void abrirRelDoacoes(ActionEvent event) {

    }

    @FXML
    void abrirRelDoadores(ActionEvent event) {

    }

    @FXML
    void fechar(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
