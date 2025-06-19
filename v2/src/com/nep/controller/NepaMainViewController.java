package com.nep.controller;

import com.nep.NepaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class NepaMainViewController {

    private NepaMain mainApp;

    @FXML
    private void openManagerLogin(ActionEvent event) {
        try {
            mainApp.showManagerLogin();
        } catch (IOException e) {
            e.printStackTrace();
            // 显示错误信息
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("加载登录界面失败");
            alert.setContentText("无法打开管理端登录界面：" + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void openGridLogin(ActionEvent event) {
        try {
            mainApp.showGridLogin();
        } catch (IOException e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("加载登录界面失败");
            alert.setContentText("无法打开网格员端登录界面：" + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void openSupervisorLogin(ActionEvent event) {
        try {
            mainApp.showSupervisorLogin();
        } catch (IOException e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("加载登录界面失败");
            alert.setContentText("无法打开公众监督员端登录界面：" + e.getMessage());
            alert.showAndWait();
        }
    }

    // 由MainApplication设置引用
    public void setMainApp(NepaMain mainApp) {
        this.mainApp = mainApp;
    }
}