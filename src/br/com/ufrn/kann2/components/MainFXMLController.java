/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.components;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kenreurison
 */
public class MainFXMLController implements Initializable {

    @FXML
    private TreeView<?> tvwRegraInicial;
    @FXML
    private TreeView<?> tvwRegraFinal;
    @FXML
    private TextField txfErro;
    @FXML
    private TextField txfEpocas;
    @FXML
    private ComboBox<?> cmbMomento;
    @FXML
    private TextField txfEta;
    @FXML
    private Button btnTopGen;
    @FXML
    private TextField txfErroValidacao;
    @FXML
    private TextField txfErroTreinamneto;
    @FXML
    private TextField txfnumEpocas;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private CheckBox cbxCirculo;
    @FXML
    private CheckBox cbxNivel;
    @FXML
    private VBox vbxLegenda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
