package view;

import DAO.Dao;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tipoDados.Doacao;
import tipoDados.Doador;

public class ConsultaDoadorController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField tFFiltro;

    @FXML
    private Button bSelecionar;

    @FXML
    private TableView<Doador> tableView;

    static String retorno;
    
    String filtro = "";
    
    ObservableList<Doador> doadores;
    
    @FXML
    void filtrarTabela(KeyEvent event) {
        filtro = "%" + tFFiltro.getText() + "%";
        doadores.clear();
        Object[] dados = {filtro, filtro, filtro, filtro, filtro};
        Dao dao = new Dao();
        try {
            ResultSet result;
            if (filtro.equals("%%")) {
                result = dao.select("SELECT * FROM Doador");
            } else {
                result = dao.select("SELECT * FROM Doador dd where Nome like ? or "
                        + "CONVERT(Data_Nascimento, CHAR) like ? or "
                        + "Nome_Pai like ? or "
                        + "Nome_Mae like ? or "
                        + "RG like ?", dados);
            }
            while (result.next()) {
                Doador doador = new Doador();
                doador.setIdDoador(result.getInt("ID_Doador"));
                doador.setNome(result.getString("Nome"));
                doador.setRua(result.getString("Rua"));
                doador.setNumero(result.getInt("Numero"));
                doador.setBairro(result.getString("Bairro"));
                doador.setComplemento(result.getString("Complemento"));
                doador.setCep(result.getInt("Cep"));
                doador.setCidade(result.getString("Cidade"));
                doador.setUf(result.getString("UF"));
                doador.setDataNascimento(result.getDate("Data_Nascimento"));
                doador.setNomePai(result.getString("Nome_Pai"));
                doador.setNomeMae(result.getString("Nome_Mae"));
                doador.setRg(result.getString("RG"));
                doadores.add(doador);
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
    void selecionarLinha(ActionEvent event) throws IOException {
        if ("doador".equals(retorno)) {
            Doador doadorSelecionado = tableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("CadDoador.fxml"));
            try {
                Parent root = (Parent) fxmlloader.load();
                Stage stage = new Stage();
                CadDoadorController controller = fxmlloader.getController();
                controller.initData(doadorSelecionado);
                controller.editDoador();
                stage.setTitle("Menu");
                stage.setScene(new Scene(root));

                stage.show();
            } catch (IOException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                error.setHeaderText("Error");
                error.setTitle("Error");
                error.setContentText("Falha em Carregar Menu: " + ex);
                error.showAndWait();
            }
        } else if ("doador".equals(retorno)) {
            Doador doadorSelecionado = tableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("CadDoacao.fxml"));
            try {
                Parent root = (Parent) fxmlloader.load();
                Stage stage = new Stage();
                CadDoacaoController controller = fxmlloader.getController();
                controller.initData(doadorSelecionado);
                stage.setTitle("Menu");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                error.setHeaderText("Error");
                error.setTitle("Error");
                error.setContentText("Falha em Carregar Menu: " + ex);
                error.showAndWait();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Doador, String> nameCol = new TableColumn("Nome");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Doador, String> rgCol = new TableColumn("RG");
        rgCol.setCellValueFactory(new PropertyValueFactory<>("rg"));
        TableColumn<Doador, String> dataCol = new TableColumn("Data de Nascimento");
        dataCol.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        TableColumn<Doador, String> maeCol = new TableColumn("Mãe");
        maeCol.setCellValueFactory(new PropertyValueFactory<>("nomeMae"));
        TableColumn<Doador, String> paiCol = new TableColumn("Pai");
        paiCol.setCellValueFactory(new PropertyValueFactory<>("nomePai"));
        tableView.getColumns().addAll(nameCol, rgCol, dataCol, maeCol, paiCol);
        doadores = FXCollections.observableArrayList();
        Dao dao = new Dao();
        tableView.setItems(doadores);
        try {
            ResultSet result = dao.select("SELECT * FROM Doador");
            while (result.next()) {
                Doador doador = new Doador();
                doador.setIdDoador(result.getInt("ID_Doador"));
                doador.setNome(result.getString("Nome"));
                doador.setRua(result.getString("Rua"));
                doador.setNumero(result.getInt("Numero"));
                doador.setBairro(result.getString("Bairro"));
                doador.setComplemento(result.getString("Complemento"));
                doador.setCep(result.getInt("Cep"));
                doador.setCidade(result.getString("Cidade"));
                doador.setUf(result.getString("UF"));
                doador.setDataNascimento(result.getDate("Data_Nascimento"));
                doador.setNomePai(result.getString("Nome_Pai"));
                doador.setNomeMae(result.getString("Nome_Mae"));
                doador.setRg(result.getString("RG"));
                doadores.add(doador);
            }

        } catch (SQLException e) {

        }
    }

}
