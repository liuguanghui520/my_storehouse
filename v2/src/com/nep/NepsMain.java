package com.nep;

import com.nep.controller.NepsLoginViewController;
import com.nep.util.JavafxUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//继承应用程序Application的父类
public class NepsMain extends Application {
    //重写启动方法

    public void start(Stage primaryStage)throws Exception{
       NepsLoginViewController.primaryStage=primaryStage;
       Parent root= FXMLLoader.load(getClass().getResource("view/NepsLoginView.fxml"));

       primaryStage.setTitle("东软环境管理系统-公众监督员端");
       primaryStage.setScene(new Scene(root,625,450));
       primaryStage.show();


        //JavafxUtil.showStage(NepsMain.class,"NepsLoginView.fxml",primaryStage,"东软环境管理系统-公众监督员端");

    }

    public static void main(String[] args){
        launch(args);
    }

}
