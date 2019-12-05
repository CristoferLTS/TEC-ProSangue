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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
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
    private Button bSelecionar;

    @FXML
    private TableView<Doacao> tableView;

    @FXML
    void selecionarLinha(ActionEvent event) {

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
        ObservableList<Doacao> doacoes = FXCollections.observableArrayList();
        Dao dao = new Dao();
        tableView.setItems(doacoes);
        try {
            ResultSet result = dao.select("SELECT * FROM Doacao");
            while (result.next()) {
                Doacao doacao = new Doacao();
                doacao.setCodigo(result.getInt("Codigo"));
                doacao.setIdDoador(result.getInt("ID_Doador"));
                Object[] id = {result.getInt("ID_Doador")};
                ResultSet resultDoador = dao.select("SELECT Nome from Doador where ID_Doador = ?", id);
                while (resultDoador.next()) {
                    doacao.setNomeDoador(resultDoador.getString("Nome"));
                }
                doacao.setData(result.getDate("Data"));
                doacao.setHora(result.getTime("Hora"));
                doacao.setAnemia(result.getBoolean("Anemia"));
                doacao.setPeso(result.getFloat("Peso"));
                doacao.setPulso(result.getFloat("Pulso"));
                doacao.setTemperatura(result.getFloat("Temperatura"));
                doacao.setPressao(result.getFloat("Pressao"));
                doacoes.add(doacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
