package Controller;

import DAO.Dao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class CadastrarPerguntaController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button bGravar;

    @FXML
    private TextArea textArea;

    @FXML
    void gravarPergunta(ActionEvent event) {
        String text = textArea.getText();
        Dao dao = new Dao();
        Object[] dado = {text};
        try {
            try {
                dao.insert("Insert into Pergunta (Pergunta) values (?)", dado);
            } catch (SQLException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                error.setHeaderText("Erro");
                error.setTitle("Não Foi Possível Realizar essa Operação");
                error.setContentText("Falha em Gravar no banco: " + ex);
                error.showAndWait();
            }

        } finally {
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            confirmation.setHeaderText("Sucesso");
            confirmation.setTitle("Sua Operação foi Realizada com Sucesso");
            confirmation.setContentText("Gravação realizada com Sucesso");
            textArea.clear();
            confirmation.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
