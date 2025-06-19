package com.nep.util;

import com.sun.deploy.security.CertStore;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

//各种弹出框
public class JavafxUtil {

    public static void showAlert(Stage primaryStage, String title, String headText, String contentText, String alertType){
    Alert alert=null;

    switch(alertType){
        case"warn":
            alert=new Alert(Alert.AlertType.WARNING);
            break;
        case"info":
            alert=new Alert(Alert.AlertType.INFORMATION);
            break;
    }

    alert.initOwner(primaryStage);
    alert.setTitle(title);
    alert.setHeaderText(headText);
    alert.setContentText(contentText);

    alert.showAndWait();
    }

    //界面切换
    public static void showStage(Class clazz,String path,Stage primaryStage,String title){
        FXMLLoader loader = new FXMLLoader();
        URL url = clazz.getResource(path);
        loader.setLocation(url);
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    //主界面切换子界面
    public static Stage showSubStage(Class clazz,String path,Stage primaryStage,String title){
        //加载fxml文件所需
        FXMLLoader loader = new FXMLLoader();
        URL url = clazz.getResource(path);
        loader.setLocation(url);
        //创建子舞台
        Stage subStage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            subStage.setTitle(title);
            subStage.setScene(scene);
            subStage.initOwner(primaryStage);
            subStage.initModality(Modality.WINDOW_MODAL);
            subStage.showAndWait();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return subStage;
    }


    public static void hideLoading() {
    }
}
