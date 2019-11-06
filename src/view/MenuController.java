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
    private MenuItem mIDoadorAlterar;

    @FXML
    private MenuItem mIDoadorExcluir;

    @FXML
    private MenuItem mIDoadorConsultar;

    @FXML
    private Menu menuDoacoes;

    @FXML
    private MenuItem mIDoacoesCadastrar;

    @FXML
    private MenuItem mIDoacoesAlterar;

    @FXML
    private MenuItem mIDoacoesExcluir;

    @FXML
    private MenuItem mIDoacoesConsultar;

    @FXML
    private MenuItem mIDoacoesExames;

    @FXML
    private MenuItem mIDoacoesTriagem;

    @FXML
    private MenuItem mIDoacoesEntrevista;

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
    void abreAltDoador(ActionEvent event) {

    }

    @FXML
    void abreCadDoador(ActionEvent event) {

    }

    @FXML
    void abreConDoador(ActionEvent event) {

    }

    @FXML
    void abreExcDoador(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }


}
