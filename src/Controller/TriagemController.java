/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.Dao;
import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import tipoDados.Doacao;

/**
 * FXML Controller class
 *
 * @author cristofer
 */
public class TriagemController implements Initializable {

    @FXML
    private TextField tFFiltro;

    @FXML
    private TableView<Doacao> tableView;

    @FXML
    private JFXRadioButton rbAnemiaSim;

    @FXML
    private ToggleGroup rgAnemia;

    @FXML
    private JFXRadioButton rbAnemiaNao;

    @FXML
    private Label labelAnemia;

    @FXML
    private TextField tFPeso;

    @FXML
    private TextField tFPulso;

    @FXML
    private TextField tFTemperatura;

    @FXML
    private TextField tFPressao;

    @FXML
    private Button bGravar;
    
    String filtro = "";

    ObservableList<Doacao> doacoes;

    @FXML
    void editarTriagem(ActionEvent event) {
        int codigo;
        Boolean anemia = null;
        Float peso;
        Float pulso;
        Float temperatura;
        String pressao;
        if (rbAnemiaNao.isSelected() == true) {
            anemia = false;
        } else if (rbAnemiaSim.isSelected() == true) {
            anemia = true;
        }
        peso = Float.parseFloat(tFPeso.getText());
        pulso = Float.parseFloat(tFPulso.getText());
        temperatura = Float.parseFloat(tFTemperatura.getText());
        pressao = tFPressao.getText();
        codigo = tableView.getSelectionModel().getSelectedItem().getCodigo();
        Object[] dados = {anemia, peso, pulso, temperatura, pressao, codigo};
        Object[] dado = {codigo};
        Dao dao = new Dao();
        try {
            dao.update("update Doacao set Anemia = ?, Peso = ?, Pulso = ?, Temperatura = ?, Pressao = ? where Codigo = ?", dados);
            doacoes.get(tableView.getSelectionModel().getSelectedIndex()).setAnemia(anemia);
            doacoes.get(tableView.getSelectionModel().getSelectedIndex()).setPeso(peso);
            doacoes.get(tableView.getSelectionModel().getSelectedIndex()).setPulso(pulso);
            doacoes.get(tableView.getSelectionModel().getSelectedIndex()).setTemperatura(temperatura);
            doacoes.get(tableView.getSelectionModel().getSelectedIndex()).setPressao(pressao);
        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Atualizar Triagem: " + ex);
            error.showAndWait();
        }
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        confirmation.setHeaderText("Sucesso");
        confirmation.setTitle("Sucesso");
        confirmation.setContentText("Sucesso em Registrar Triagem");
        confirmation.showAndWait();
    }

    @FXML
    void filtrarTabela(KeyEvent event) {
        filtro = "%" + tFFiltro.getText() + "%";
        doacoes.clear();
        Object[] dados = {filtro, filtro, filtro};
        Dao dao = new Dao();
        try {
            ResultSet result;
            if (filtro.equals("%%")) {
                result = dao.select("SELECT dc.*, Nome FROM Doacao dc left join Doador dd on "
                        + "dc.ID_Doador = dd.ID_Doador");
            } else {
                result = dao.select("SELECT dc.*, Nome FROM Doacao dc left join Doador dd on "
                        + "dc.ID_Doador = dd.ID_Doador"
                        + " where Nome like ? or CONVERT(Codigo, CHAR) like ? or "
                        + "CONVERT(Data, CHAR) like ?", dados);
            }
            while (result.next()) {
                Doacao doacao = new Doacao();
                doacao.setCodigo(result.getInt("Codigo"));
                doacao.setIdDoador(result.getInt("ID_Doador"));
                doacao.setNomeDoador(result.getString("Nome"));
                doacao.setData(result.getDate("Data"));
                doacao.setHora(result.getTime("Hora"));
                doacao.setAnemia(result.getBoolean("Anemia"));
                doacao.setPeso(result.getFloat("Peso"));
                doacao.setPulso(result.getFloat("Pulso"));
                doacao.setTemperatura(result.getFloat("Temperatura"));
                doacao.setPressao(result.getString("Pressao"));
                doacoes.add(doacao);
            }

        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Buscar Triagem: " + e);
            error.showAndWait();
        }

    }

    @FXML
    void selecionarLinha(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem().getAnemia() == false) {
            rbAnemiaNao.setSelected(true);
        } else if (tableView.getSelectionModel().getSelectedItem().getAnemia() == true) {
            rbAnemiaSim.setSelected(true);
        }
        tFPeso.setText(Float.toString(tableView.getSelectionModel().getSelectedItem().getPeso()));
        tFPulso.setText(Float.toString(tableView.getSelectionModel().getSelectedItem().getPulso()));
        tFTemperatura.setText(Float.toString(tableView.getSelectionModel().getSelectedItem().getTemperatura()));
        tFPressao.setText(tableView.getSelectionModel().getSelectedItem().getPressao());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Doacao, String> codigoCol = new TableColumn("Código");
        codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        TableColumn<Doacao, String> nomeCol = new TableColumn("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeDoador"));
        TableColumn<Doacao, String> dataCol = new TableColumn("Data de Doação");
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        TableColumn<Doacao, String> horaCol = new TableColumn("Horário");
        horaCol.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tableView.getColumns().addAll(codigoCol, nomeCol, dataCol, horaCol);
        doacoes = FXCollections.observableArrayList();
        Dao dao = new Dao();
        tableView.setItems(doacoes);
        try {
            ResultSet result = dao.select("SELECT dc.*, Nome FROM Doacao dc left join Doador dd on dc.ID_Doador = dd.ID_Doador");
            while (result.next()) {
                Doacao doacao = new Doacao();
                doacao.setCodigo(result.getInt("Codigo"));
                doacao.setIdDoador(result.getInt("ID_Doador"));
                doacao.setNomeDoador(result.getString("Nome"));
                doacao.setData(result.getDate("Data"));
                doacao.setHora(result.getTime("Hora"));
                doacao.setAnemia(result.getBoolean("Anemia"));
                doacao.setPeso(result.getFloat("Peso"));
                doacao.setPulso(result.getFloat("Pulso"));
                doacao.setTemperatura(result.getFloat("Temperatura"));
                doacao.setPressao(result.getString("Pressao"));
                doacoes.add(doacao);
            }

        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Buscar Triagem: " + e);
            error.showAndWait();
        }

    }

}
