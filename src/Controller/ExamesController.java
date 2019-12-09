package Controller;

import DAO.Dao;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import tipoDados.Doacao;

public class ExamesController implements Initializable {

    @FXML
    private TextField tFFiltro;

    @FXML
    private TableView<Doacao> tableView;

    @FXML
    private JFXToggleButton tBHepatiteB;

    @FXML
    private JFXToggleButton tBAids;

    @FXML
    private JFXToggleButton tBSifilis;

    @FXML
    private JFXToggleButton tBHepatiteC;

    @FXML
    private JFXToggleButton tBHTVL;

    @FXML
    private JFXToggleButton tBChagas;

    @FXML
    private TextField tFTipoSanguineo;

    @FXML
    private JFXRadioButton rBPositivo;

    @FXML
    private ToggleGroup tGRh;

    @FXML
    private JFXRadioButton rbNegativo;

    @FXML
    private TextField tFAnticorpos;

    @FXML
    private Button bGravar;

    String filtro = "";

    ObservableList<Doacao> doacoes;

    @FXML
    void gravarExames(ActionEvent event) {
        Dao dao = new Dao();
        int flag = 0;
        Object[] dado = {tableView.getSelectionModel().getSelectedItem().getCodigo()};
        try {
            ResultSet result = dao.select("Select count(*) as qtde from Testes where IDDoacao = ?", dado);
            while (result.next()) {
                flag = result.getInt("qtde");
            }
            int idDoacao = tableView.getSelectionModel().getSelectedItem().getCodigo();
            Boolean hepatiteB = tBHepatiteB.isSelected();
            Boolean aids = tBAids.isSelected();
            Boolean sifilis = tBSifilis.isSelected();
            Boolean hepatiteC = tBHepatiteC.isSelected();
            Boolean htvl = tBHTVL.isSelected();
            Boolean chagas = tBChagas.isSelected();
            String tipoSanguineo = tFTipoSanguineo.getText();
            String rh = "";
            if (rBPositivo.isSelected()) {
                rh = "+";
            } else if (rbNegativo.isSelected()) {
                rh = "-";
            }
            Float anticorpos = Float.parseFloat(tFAnticorpos.getText());
            Object[] dados = {hepatiteB, aids, sifilis, hepatiteC, htvl, chagas, tipoSanguineo, rh, anticorpos, idDoacao};
            if (flag == 0) {
                dao.insert("insert into Testes (HepatiteB,AIDS,Sifilis,HepatiteC,HTVL,Chagas,Tipo_Sanguineo,"
                        + "Rh,Anticorpos,IDDoacao) values (?,?,?,?,?,?,?,?,?,?)", dados);
            } else if (flag == 1) {
                dao.update("update Testes set HepatiteB = ?, AIDS = ?, Sifilis = ?, HepatiteC = ?, HTVL = ?,"
                        + "Chagas = ?, Tipo_Sanguineo = ?, Rh = ?, Anticorpos = ? WHERE IDDOacao = ?", dados);
            }
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
        int doacao = tableView.getSelectionModel().getSelectedItem().getCodigo();
        Dao dao = new Dao();
        Object[] dado = {doacao};
        try {
            ResultSet existe = dao.select("Select count(IDDoacao) as qtde from Testes where IDDoacao = ?", dado);
            while (existe.next()) {
                if (existe.getInt("qtde") == 0) {
                    tBHepatiteB.setSelected(false);
                    tBAids.setSelected(false);
                    tBSifilis.setSelected(false);
                    tBHepatiteC.setSelected(false);
                    tBHTVL.setSelected(false);
                    tBChagas.setSelected(false);
                    tFTipoSanguineo.clear();
                    rBPositivo.setSelected(false);
                    rbNegativo.setSelected(false);
                    tFAnticorpos.clear();
                } else {
                    ResultSet result = dao.select("select * from Testes where IDDoacao = ?", dado);
                    while (result.next()) {
                        tBHepatiteB.setSelected(result.getBoolean("HepatiteB"));
                        tBAids.setSelected(result.getBoolean("AIDS"));
                        tBSifilis.setSelected(result.getBoolean("Sifilis"));
                        tBHepatiteC.setSelected(result.getBoolean("HepatiteC"));
                        tBHTVL.setSelected(result.getBoolean("HTVL"));
                        tBChagas.setSelected(result.getBoolean("Chagas"));
                        tFTipoSanguineo.setText(result.getString("Tipo_Sanguineo"));
                        String rh = result.getString("Rh");
                        if (rh.equals("+")) {
                            rBPositivo.setSelected(true);
                        } else if (rh.equals("-")) {
                            rbNegativo.setSelected(true);
                        }
                        tFAnticorpos.setText(Float.toString(result.getFloat("Anticorpos")));
                    }
                }
            }

        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não foi Possível Realizar essa operação");
            error.setContentText("Falha em Selecionar os Dados do Doador");
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
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Error");
            error.setTitle("Error");
            error.setContentText("Falha em Buscar Exames: " + e);
            error.showAndWait();
        }

    }

}
