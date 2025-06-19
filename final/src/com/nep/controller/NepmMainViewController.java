package com.nep.controller;

import com.nep.NepaMain;
import com.nep.NepmMain;
import com.nep.NepsMain;
import com.nep.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NepmMainViewController implements Initializable {

    //主舞台
    public static Stage primaryStage;
    @FXML
    private WebView webView;
    @FXML
    private ImageView txt_imageView;
    private WebEngine webEngine;

    public ImageView getTxt_imageView() {
        return txt_imageView;
    }

    public void setTxt_imageView(ImageView txt_imageView) {
        this.txt_imageView = txt_imageView;
    }

    public void back() {
        try {
            // 显示主选择界面
            NepaMain nepaMain = new NepaMain();
            nepaMain.showMainSelectView();
        } catch (IOException e) {
            e.printStackTrace();
            // 显示错误信息
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("加载主选择界面失败");
            alert.setContentText("无法打开主选择界面：" + e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        //初始化图片
        Image image = new Image("image/welcome.png");
        txt_imageView.setImage(image);
        txt_imageView.setPreserveRatio(false);   //禁用保持纵横比
    }

    public void aqiInfo(){
        JavafxUtil.showSubStage(NepmMain.class, "view/NepmAqiInfoView.fxml", primaryStage, "东软环保公众监督平台-管理端-AQI反馈数据查询");
    }

    public void aqiAssign(){
        NepmAqiAssignViewController.aqiInfoStage = JavafxUtil.showSubStage(NepmMain.class, "view/NepmAqiAssignView.fxml", primaryStage, "东软环保公众监督平台-管理端-AQI反馈数据指派");;
    }

    public void aqiConfirm(){
        JavafxUtil.showSubStage(NepmMain.class, "view/NepmConfirmInfoView.fxml", primaryStage, "东软环保公众监督平台-管理端-AQI实测数据查询");
    }

}
