package Controller;

import DAO.Dao;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import tipoDados.Pergunta;

public class RespostasQuestionarioController implements Initializable {

    @FXML
    private Label labelPergunta;

    @FXML
    private TextArea textArea;

    @FXML
    private Button bAnterior;

    @FXML
    private Button bResponder;

    @FXML
    private Button bProximo;

    static int idDoacao;

    int controle = 0;

    @FXML
    void anterior(ActionEvent event) {
        try {
            if (controle > -1) {
                controle--;
                labelPergunta.setText(perguntas.get(controle).getPergunta());
                consultarPergunta(perguntas.get(controle).getIdPergunta());
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            controle = 0;
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            information.setHeaderText("Informação");
            information.setTitle("Não Foi Possível Realizar essa Operação");
            information.setContentText("Não existe perguntas anteriores " + ex);
            information.showAndWait();
        }
    }

    void consultarPergunta(int idPergunta) {
        Dao dao = new Dao();
        Object[] dado = {idPergunta, idDoacao};
        try {
            ResultSet result = dao.select("select Resposta from Resposta where ID_Pergunta = ? "
                    + "and ID_Doacao = ?", dado);
            if (result.next() == false) {
                textArea.clear();
            } else {
                do {
                    textArea.setText(result.getString("Resposta"));
                } while (result.next());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void proximo(ActionEvent event) {
        try {
            if (controle < perguntas.size()) {
                controle++;
                labelPergunta.setText(perguntas.get(controle).getPergunta());
                consultarPergunta(perguntas.get(controle).getIdPergunta());
            }
        } catch (IndexOutOfBoundsException ex) {
            controle = perguntas.size() - 1;
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            information.setHeaderText("Informação");
            information.setTitle("Não Foi Possível Realizar essa Operação");
            information.setContentText("Não existe perguntas posteriores " + ex);
            information.showAndWait();
        }
    }

    @FXML
    void responder(ActionEvent event) {
        Dao dao = new Dao();
        int flag = 0;
        Object[] dado = {idDoacao, perguntas.get(controle).getIdPergunta()};
        try {
            ResultSet result = dao.select("Select count(*) as qtde from Resposta where ID_Doacao = ? and "
                    + "ID_Pergunta = ?", dado);
            while (result.next()) {
                flag = result.getInt("qtde");
            }
            String resposta = textArea.getText();
            Object[] dados = {resposta, perguntas.get(controle).getIdPergunta(), idDoacao};
            if (flag == 0) {
                dao.insert("Insert into Resposta (Resposta,ID_Pergunta,ID_Doacao) values (?,?,?)", dados);
            } else if (flag == 1) {
                dao.update("update Resposta set Resposta = ? where ID_Pergunta = ? and ID_Doacao = ?", dados);
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

    ObservableList<Pergunta> perguntas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Dao dao = new Dao();
        perguntas = FXCollections.observableArrayList();
        try {
            ResultSet result = dao.select("Select * from Pergunta");
            while (result.next()) {
                Pergunta pergunta = new Pergunta();
                pergunta.setIdPergunta(result.getInt("ID_Pergunta"));
                pergunta.setPergunta(result.getString("Pergunta"));
                perguntas.add(pergunta);
            }

        } catch (SQLException ex) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.setHeaderText("Erro");
            error.setTitle("Não Foi Possível Realizar essa Operação");
            error.setContentText("Falha em Consultar Perguntas: " + ex);
            error.showAndWait();
        }
        labelPergunta.setText(perguntas.get(controle).getPergunta());
        Object[] dado = {perguntas.get(controle).getIdPergunta(), idDoacao};
        try {
            ResultSet result = dao.select("select Resposta from Resposta where ID_Pergunta = ? "
                    + "and ID_Doacao = ?", dado);
            if (result.next() == false) {
                textArea.clear();
            } else {
                do {
                    textArea.setText(result.getString("Resposta"));
                } while (result.next());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
