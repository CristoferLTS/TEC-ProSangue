package Controller;

import DAO.Dao;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import tipoDados.Pergunta;

public class EditarPerguntasController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button bGravar;

    @FXML
    private Button bExcluir;

    @FXML
    private JFXComboBox<String> cBPerguntas;

    @FXML
    private TextArea textArea;

    ObservableList<Pergunta> perguntas = null;
    ObservableList<String> pgts;

    @FXML
    void excluirPergunta(ActionEvent event) {
        Dao dao = new Dao();
        Object[] dado = {perguntas.get(cBPerguntas.getSelectionModel().getSelectedIndex()).getIdPergunta()};
        try {
            dao.delete("Delete from Pergunta where ID_Pergunta = ?", dado);
            atualizaCombo();
        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não Foi Possível Realizar essa Operação");
            error.setContentText("Falha em Deletar do banco: " + ex);
            error.showAndWait();
        }
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        confirmation.setHeaderText("Sucesso");
        confirmation.setTitle("Sua Operação foi Realizada com Sucesso");
        confirmation.setContentText("Deleção realizada com Sucesso");
        textArea.clear();
        confirmation.showAndWait();
    }

    @FXML
    void gravarPergunta(ActionEvent event) {
        Dao dao = new Dao();
        Object[] dados = {textArea.getText(), perguntas.get(cBPerguntas.getSelectionModel().getSelectedIndex()).getIdPergunta()};
        try {
            dao.update("Update Pergunta set Pergunta = ? where ID_Pergunta = ?", dados);
            atualizaCombo();
        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não Foi Possível Realizar essa Operação");
            error.setContentText("Falha em Gravar no banco: " + ex);
            error.showAndWait();
        }
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        confirmation.setHeaderText("Sucesso");
        confirmation.setTitle("Sua Operação foi Realizada com Sucesso");
        confirmation.setContentText("Gravação realizada com Sucesso");
        textArea.clear();
        confirmation.showAndWait();
    }

    void atualizaCombo() {
        perguntas.clear();
        pgts.clear();
        Dao dao = new Dao();
        ResultSet result;
        try {
            result = dao.select("Select * from Pergunta");
            while (result.next()) {
                Pergunta pergunta = new Pergunta();
                pergunta.setIdPergunta(result.getInt("ID_Pergunta"));
                pergunta.setPergunta(result.getString("Pergunta"));
                perguntas.add(pergunta);
                pgts.add(Integer.toString(pergunta.getIdPergunta()) + " - " + (pergunta.getPergunta()));
            }
        } catch (SQLException ex) {
            System.out.println("Aqui na hora de atulizar que deu ruin");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não Foi Possível Realizar essa Operação");
            error.setContentText("Falha em Consultar Perguntas para atualizar: " + ex);
            error.showAndWait();
        }

    }

    @FXML
    void selecionarPergunta(ActionEvent event) {
        if (!perguntas.isEmpty()) {
            textArea.setText(perguntas.get(cBPerguntas.getSelectionModel().getSelectedIndex()).getPergunta());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Dao dao = new Dao();
        perguntas = FXCollections.observableArrayList();
        pgts = FXCollections.observableArrayList();
        try {
            ResultSet result = dao.select("Select * from Pergunta");
            while (result.next()) {
                Pergunta pergunta = new Pergunta();
                pergunta.setIdPergunta(result.getInt("ID_Pergunta"));
                pergunta.setPergunta(result.getString("Pergunta"));
                perguntas.add(pergunta);
                pgts.add(Integer.toString(pergunta.getIdPergunta()) + " - " + (pergunta.getPergunta()));
            }
        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não Foi Possível Realizar essa Operação");
            error.setContentText("Falha em Consultar Perguntas: " + ex);
            error.showAndWait();
        }
        cBPerguntas.setItems(pgts);

    }

}
