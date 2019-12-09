package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuController implements Initializable {

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
    private MenuItem mIDoacoesExcluir;

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
    private MenuItem mlDoacoesTriagem;
    
    @FXML
    private MenuItem mIPerguntasQuestionario;
    
    
    @FXML
    void abrirQuestionario(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Questionario.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Questionario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não foi possível realizar essa operação");
            error.setContentText("Falha em Carregar Questionário " + ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirTriagem(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Triagem.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Realizar Triagem");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Carregar Triagem: " + ex);
            error.showAndWait();
        }

    }

    @FXML
    void abrirExcluirDoacao(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("RemoverDoacao.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Remover Doação");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em abrir Exclusão de Doação: " + ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirCadDoacoes(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("CadDoacao.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Doação");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Carregar Cadastro de Doação: " + ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirCadDoador(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("CadDoador.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Doador");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Carregar Cadastro de Doadores: " + ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirCadPerguntas(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("CadastrarPergunta.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Pergunta");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não foi Possível realizar essa operação");
            error.setContentText("Falha em Carregar Cadastro de pergunta: "+ ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirConPerguntas(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("EditarPerguntas.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Consulta de Perguntas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não foi Possível realizar essa operação");
            error.setContentText("Falha em Carregar Consulta de pergunta: "+ ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirConDoador(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ConsultaDoador.fxml"));
        ConsultaDoadorController.retorno = "doador";
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Doador");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Carregar Consulta: " + ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirConExames(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Exames.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Exames");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não foi Possível abrir Exames");
            error.setContentText("Falha em Carregar Exames: " + ex);
            error.showAndWait();
        }
    }

    @FXML
    void abrirConsDoacoes(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ConsultaDoacoes.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Doação");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Carregar Consulta: " + ex);
            error.showAndWait();
        }
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
        Image image = new Image(getClass().getResourceAsStream("assets/Pro-Sangue.jpg"));
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        borderMenu.setBackground(new Background(background));
    }

}
