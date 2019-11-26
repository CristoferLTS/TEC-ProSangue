/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Services.ClienteCEPWS;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.TAB;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author cristofer
 */
public class CadDoadorController implements Initializable {

    ObservableList<String> ufs = FXCollections.observableArrayList("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");

    @FXML
    private VBox CadVbox;

    @FXML
    private TextField tFNome;

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
