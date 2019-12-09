package Controller;

import DAO.Dao;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import tipoDados.Doacao;

public class RemoverDoacaoController implements Initializable {

    @FXML
    private TextField tFPesquisar;

    @FXML
    private Button bPesquisar;

    @FXML
    private TextField tFDoador;

    @FXML
    private JFXDatePicker dPData;

    @FXML
    private JFXTimePicker tPHora;

    @FXML
    private JFXRadioButton rbSim;

    @FXML
    private ToggleGroup tGAnemia;

    @FXML
    private JFXRadioButton rbNao;

    @FXML
    private TextField tFPeso;

    @FXML
    private TextField tFPulso;

    @FXML
    private TextField tFTemperatura;

    @FXML
    private TextField tFPressao;

    @FXML
    private Button bExcluir;

    Doacao doacao;

    @FXML
    void excluir(ActionEvent event) {
        Dao dao = new Dao();
        try {
            Object[] dados = {doacao.getCodigo()};
            dao.delete("delete from Doacao where Codigo = ?", dados);
            dao.delete("delete from Testes where IDDoacao = ?",dados);
        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Não é Possível Realizar essa Operação " + ex);
            error.showAndWait();
        } catch (NullPointerException ex) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Operação Não Permitida");
            error.setTitle("Erro");
            error.setContentText("Você precisa Selecionar uma Doacao: " + ex);
            error.showAndWait();
        }
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        error.setHeaderText("Sucesso");
        error.setTitle("Operação Realizada Com Sucesso");
        error.setContentText("Doação Removida com Sucesso");
        error.showAndWait();
        limparCampos();
    }

    void limparCampos() {
        tFPesquisar.clear();
        tFDoador.clear();
        dPData.getEditor().clear();
        tPHora.getEditor().clear();
        rbSim.setSelected(false);
        rbNao.setSelected(false);
        tFPeso.clear();
        tFPulso.clear();
        tFTemperatura.clear();
        tFPressao.clear();
    }

    @FXML
    void pesquisar(ActionEvent event) {
        int codigo = Integer.parseInt(tFPesquisar.getText());
        Dao dao = new Dao();
        Object[] dado = {codigo};
        try {
            ResultSet result;
            result = dao.select("SELECT dc.*, Nome FROM Doacao dc left join Doador dd on "
                    + "dc.ID_Doador = dd.ID_Doador where Codigo = ?", dado);
            while (result.next()) {
                doacao = new Doacao();
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
            }
            tFDoador.setText(doacao.getNomeDoador());
            dPData.setValue(doacao.getData().toLocalDate());
            tPHora.setValue(doacao.getHora().toLocalTime());
            if (doacao.getAnemia() == true) {
                rbSim.setSelected(true);
            } else if (doacao.getAnemia() == false) {
                rbNao.setSelected(false);
            }
            tFPeso.setText(Float.toString(doacao.getPeso()));
            tFPulso.setText(Float.toString(doacao.getPulso()));
            tFTemperatura.setText(Float.toString(doacao.getTemperatura()));
            tFPressao.setText(doacao.getPressao());

        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Buscar Doacão: " + e);
            error.showAndWait();
        } catch (NullPointerException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Erro");
            error.setContentText("Esse Doador Não Existe: " + e);
            tFPesquisar.clear();
            error.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
