/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAO.Dao;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import tipoDados.Doacao;

/**
 * FXML Controller class
 *
 * @author cristofer
 */
public class ConsultaDoacoesController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField tFFiltro;

    @FXML
    private TableView<Doacao> tableView;

    
    String filtro = "";

    ObservableList<Doacao> doacoes;
    
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
            e.printStackTrace();
        }
    }
}
