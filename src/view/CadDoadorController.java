/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAO.Dao;
import Services.ClienteCEPWS;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.TAB;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tipoDados.Doador;

public class CadDoadorController implements Initializable {

    ObservableList<String> ufs = FXCollections.observableArrayList("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");

    private Doador selectedDoador;

    @FXML
    private VBox CadVbox;

    @FXML
    private TextField tFNome;

    @FXML
    private TextField tFRG;

    @FXML
    private Button bPesquisar;

    @FXML
    private DatePicker dpNascimento;

    @FXML
    private TextField tfNomeMae;

    @FXML
    private TextField tfNomePai;

    @FXML
    private TextField tFCEP;

    @FXML
    private TextField tFRua;

    @FXML
    private TextField tFNumero;

    @FXML
    private TextField tFBairro;

    @FXML
    private TextField tFComplemento;

    @FXML
    private TextField tFCidade;

    @FXML
    private ComboBox<String> cBUF;

    @FXML
    private HBox hboxButtons;

    @FXML
    private Button bCadastrar;

    void preencherCampos(Doador doador) {
        tFNome.setText(doador.getNome());
    }

    @FXML
    void pesquisarDoador(ActionEvent event) {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ConsultaDoador.fxml"));
        ConsultaDoadorController.retorno = "doador";
        Stage myStage = (Stage) CadVbox.getScene().getWindow();
        myStage.close();
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
    void cadastrarDoador(ActionEvent event) throws ParseException {
        String nome = tFNome.getText();
        String rua = tFRua.getText();
        Integer num = Integer.parseInt(tFNumero.getText());
        String bairro = tFBairro.getText();
        String complemento = tFComplemento.getText();
        Integer cep = Integer.parseInt(tFCEP.getText());
        String cidade = tFCidade.getText();
        String uf = cBUF.getSelectionModel().getSelectedItem();
        LocalDate localDate = dpNascimento.getValue();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        System.out.println(sqlDate);
        String nomePai = tfNomePai.getText();
        String nomeMae = tfNomeMae.getText();
        String rg = tFRG.getText();
        Object[] dados = {nome, rua, num, bairro, complemento, cep, cidade, uf, sqlDate, nomePai, nomeMae, rg};
        Dao dao = new Dao();
        try {
            dao.insert("insert into Doador (Nome, Rua, Numero, Bairro, Complemento, "
                    + "CEP, Cidade, UF, Data_Nascimento, Nome_Pai, Nome_Mae, RG) values(?,?,?,?,?,?,?,?,?,?,?,?)", dados);
        } catch (SQLException ex) {
            System.out.println("Erro em Inserir novo Doador" + ex);
        }
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        confirmation.setHeaderText("Sucesso");
        confirmation.setTitle("Cadastro Realizado com sucesso");
        confirmation.showAndWait();
    }

    @FXML
    void buscarCep(KeyEvent event) {
        if (event.getCode() == TAB) {
            String json = ClienteCEPWS.buscarCep(tFCEP.getText());
            Map<String, String> mapa = new HashMap<>();
            Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
            while (matcher.find()) {
                String[] group = matcher.group().split(":");
                mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
            }
            tFRua.setText(mapa.get("logradouro"));
            tFBairro.setText(mapa.get("bairro"));
            tFComplemento.setText("complemento");
            cBUF.getSelectionModel().select(mapa.get("uf"));
            tFCidade.setText(mapa.get("localidade"));
            System.out.println(mapa);
        }
    }

    public void editDoador() {
        Button bExcluir = new Button();
        bExcluir.setText("Excluir");
        hboxButtons.getChildren().add(bExcluir);
        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(selectedDoador.getIdDoador());
                String nome = tFNome.getText();
                String rua = tFRua.getText();
                Integer num = Integer.parseInt(tFNumero.getText());
                String bairro = tFBairro.getText();
                String complemento = tFComplemento.getText();
                Integer cep = Integer.parseInt(tFCEP.getText());
                String cidade = tFCidade.getText();
                String uf = cBUF.getSelectionModel().getSelectedItem();
                LocalDate localDate = dpNascimento.getValue();
                //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                System.out.println(sqlDate);
                String nomePai = tfNomePai.getText();
                String nomeMae = tfNomeMae.getText();
                String rg = tFRG.getText();
                Object[] dados = {nome, rua, num, bairro, complemento, cep, cidade, uf, sqlDate, nomePai, nomeMae, rg, selectedDoador.getIdDoador()};
                Dao dao = new Dao();
                try {
                    dao.update("update Doador set Nome = ?, Rua = ?, Numero = ?, Bairro = ?, Complemento = ?, CEP = ?,"
                            + "Cidade = ?, UF = ?, Data_Nascimento = ?, Nome_Pai = ?, Nome_Mae = ?, RG = ? where ID_Doador = ?", dados);
                } catch (SQLException ex) {
                    System.out.println("Deu ruin");
                }
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                confirmation.setHeaderText("Sucesso");
                confirmation.setTitle("Atualização Realizada com sucesso");
                confirmation.showAndWait();
            }
        };
        bCadastrar.setOnAction(buttonHandler);
        EventHandler<ActionEvent> editHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dao dao = new Dao();
                Object[] dados = {selectedDoador.getIdDoador()};
                try {
                    dao.delete("delete from Doador where ID_Doador = ?", dados);
                } catch (SQLException ex) {
                    Logger.getLogger(CadDoadorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        bExcluir.setOnAction(editHandler);
    }

    public void initData(Doador doador) {
        selectedDoador = doador;
        selectedDoador.setIdDoador(doador.getIdDoador());
        System.out.println(selectedDoador.getIdDoador());
        tFNome.setText(selectedDoador.getNome());
        tFRG.setText(selectedDoador.getRg());
        dpNascimento.setValue(selectedDoador.getDataNascimento().toLocalDate());
        tfNomeMae.setText(selectedDoador.getNomeMae());
        tfNomePai.setText(selectedDoador.getNomePai());
        tFCEP.setText(Integer.toString(selectedDoador.getCep()));
        tFRua.setText(selectedDoador.getRua());
        tFNumero.setText(Integer.toString(selectedDoador.getNumero()));
        tFBairro.setText(selectedDoador.getBairro());
        tFComplemento.setText(selectedDoador.getComplemento());
        tFCidade.setText(selectedDoador.getCidade());
        cBUF.getSelectionModel().select(selectedDoador.getUf());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imageDecline = new Image(getClass().getResourceAsStream("assets/icons8-pesquisar.png"));
        ImageView iBPesquisar = new ImageView(imageDecline);
        iBPesquisar.setFitHeight(15.00);
        iBPesquisar.setFitWidth(15.00);
        iBPesquisar.setPreserveRatio(true);
        bPesquisar.setGraphic(iBPesquisar);
        cBUF.setItems(ufs);
    }

}
