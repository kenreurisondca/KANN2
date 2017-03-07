/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.main;

import br.com.ufrn.kann2.implement.Graph;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kenreurison
 */
public class KANN2 extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/ufrn/kann2/components/MainFXML.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Rede Neural");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        Graph.main(args);
    }

}
