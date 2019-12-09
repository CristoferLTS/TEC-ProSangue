package Controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tipoDados.Doacao;

public class QuestionarioController implements Initializable {

    
    @FXML
    private TextField tFFiltro;

    @FXML
    private Button bSelecionar;

    @FXML
    private TableView<Doacao> tableView;
    
    ObservableList<Doacao> doacoes;
    
    String filtro;

    @FXML
    void abrirQuestionario(ActionEvent event) {
        RespostasQuestionarioController.idDoacao = tableView.getSelectionModel().getSelectedItem().getCodigo();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/RespostasQuestionario.fxml"));
        try {
            Parent root = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Respostas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não foi possível realizar essa operação");
            error.setContentText("Falha em Carregar Respostas Questionário " + ex);
            error.showAndWait();
        }
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
            error.setContentText("Falha em Buscar Questionario: " + e);
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
