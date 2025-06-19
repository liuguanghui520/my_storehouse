package com.nep;

import com.nep.controller.NepaMainViewController;
import com.nep.controller.NepgLoginViewController;
import com.nep.controller.NepmLoginViewController;
import com.nep.controller.NepsLoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NepaMain extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // 加载主选择界面并设置控制器的主应用引用
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/NepaMainView.fxml"));
        Parent root = loader.load();
        NepaMainViewController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.setTitle("东软环保监督平台 - 身份选择");
        primaryStage.setScene(new Scene(root, 410, 413));
        primaryStage.show();
    }

    // 显示主选择界面
    public void showMainSelectView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainSelectView.fxml"));
        primaryStage.setTitle("东软环保监督平台 - 身份选择");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }

    // 显示管理端登录界面
    public void showManagerLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/NepmLoginView.fxml"));
        primaryStage.setTitle("东软环保公众监督平台-管理端");
        primaryStage.setScene(new Scene(root));
        NepmLoginViewController.primaryStage=primaryStage;
    }

    // 显示网格员端登录界面
    public void showGridLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/NepgLoginView.fxml"));
        primaryStage.setTitle("东软环保公众监督平台-网格员端");
        primaryStage.setScene(new Scene(root));
        NepgLoginViewController.primaryStage=primaryStage;
    }

    // 显示公众监督员端登录界面
    public void showSupervisorLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/NepsLoginView.fxml"));
        primaryStage.setTitle("东软环保公众监督平台-公众监督员端");
        primaryStage.setScene(new Scene(root));
        NepsLoginViewController.primaryStage=primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}