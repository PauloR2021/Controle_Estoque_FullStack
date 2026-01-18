package com.pauloricardo.frontend_estoque.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class DashboardController {
    @FXML
    private StackPane contentPane;


    @FXML
    private void logout(){
        System.exit(0);
    }
}
