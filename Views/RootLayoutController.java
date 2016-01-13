/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persaccounting.Views;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import persaccounting.Auxiliaries.AlertManagement;
import persaccounting.Configs;
import persaccounting.ViewNavigation;

/**
 *
 * @author Olena
 */
public class RootLayoutController  {
    
    @FXML private StackPane viewHolder;
   
    public void setView(Node node) {
        viewHolder.getChildren().setAll(node);
    }

    @FXML
    private void showMainPage(ActionEvent event) {
        ViewNavigation.loadView(Configs.MAIN_PAGE);
    }

    @FXML
    private void launchCalculator(ActionEvent event) {
        ViewNavigation.loadView(Configs.CALCULATOR);        
    }

    @FXML
    private void launchGraph(ActionEvent event) {
        ViewNavigation.loadView(Configs.GRAPH_DISPLAY);
    }
    
    @FXML
    private void showAbout() {
        AlertManagement.displayAuthorAlert();
    }

    @FXML
    private void exit() {
        System.exit(0);  // Platform.exit();
    }

    @FXML
    private void showAuthor(ActionEvent event) {
        ViewNavigation.loadView(Configs.AUTHOR_INFO);
    }

    @FXML
    private void launchAccountingApp(ActionEvent event) {
        ViewNavigation.loadView(Configs.ACC_MAIN);
    }

    @FXML
    private void showViewSettings(ActionEvent event) {
        ViewNavigation.loadView(Configs.VIEW_SETTINGS);
    }

    @FXML
    private void setDarkStyle(ActionEvent event) {
    }

    @FXML
    private void setBlueStyle(ActionEvent event) {
    }
}
