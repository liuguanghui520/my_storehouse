package com.nep;

import com.nep.controller.NepaMainViewController;
import com.nep.controller.NepmLoginViewController;
import com.nep.util.JavafxUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class NepmMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        NepmLoginViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepmMain.class,"view/NepmLoginView.fxml", primaryStage, "东软环保公众监督平台");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
