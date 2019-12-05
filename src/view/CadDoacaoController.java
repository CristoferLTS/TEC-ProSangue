/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAO.Dao;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tipoDados.Doador;

/**
 * FXML Controller class
 *
 * @author cristofer
 */
public class CadDoacaoController implements Initializable {

    private Doador selectedDoador;

    @FXML
    private VBox cadVbox;

    @FXML
    private TextField tFDoador;

    @FXML
    private Button bPesquisar;

    @FXML
    private JFXDatePicker dpData;

    @FXML
    private JFXTimePicker tpHora;

    @FXML
    private Button bRegistrar;

    @FXML
    void pesquisarDoador(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ConsultaDoador.fxml"));
        ConsultaDoadorController.retorno = "doacao";
        Stage myStage = (Stage) cadVbox.getScene().getWindow();
        myStage.close();
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Doador");
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

    public void initData(Doador doador) {
        selectedDoador = doador;
        tFDoador.setText(Integer.toString(selectedDoador.getIdDoador()));
    }

    @FXML
    void registrarDoacao(ActionEvent event) {
        Integer idDoador = Integer.parseInt(tFDoador.getText());
        LocalDate localDate = dpData.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        LocalTime localTime = tpHora.getValue();
        java.sql.Time sqlTime = java.sql.Time.valueOf(localTime);
        Object[] dados = {idDoador, sqlDate, sqlTime};
        Dao dao = new Dao();
        try {
            dao.insert("insert into Doacao (ID_Doador, Data, Hora) values (?,?,?)", dados);
        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não foi Possível Realizar o Cadastro");
            error.showAndWait();
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        confirmation.setHeaderText("Sucesso");
        confirmation.setTitle("Cadastro Realizado com sucesso");
        confirmation.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tpHora.set24HourView(true);
    }

}
