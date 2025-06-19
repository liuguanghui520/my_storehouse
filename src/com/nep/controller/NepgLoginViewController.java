package com.nep.controller;

import com.nep.NepaMain;
import com.nep.NepgMain;
import com.nep.entity.GridMember;
import com.nep.service.GridMemberService;
import com.nep.service.impl.GridMemberServiceImpl;
import com.nep.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NepgLoginViewController {
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_password;
    //多态
    private GridMemberService gridMemberService = new GridMemberServiceImpl();
    //主舞台
    public static Stage primaryStage;

    public TextField getTxt_id() {
        return txt_id;
    }
    public void setTxt_id(TextField txt_id) {
        this.txt_id = txt_id;
    }
    public TextField getTxt_password() {
        return txt_password;
    }
    public void setTxt_password(TextField txt_password) {
        this.txt_password = txt_password;
    }

    public void login(){
        if(txt_id.getText().equals("")){
            JavafxUtil.showAlert(primaryStage, "数据格式错误", "登录账号不能为空", "请重新输入登录账号","warn");
            return;
        }
        if(txt_password.getText().equals("")){
            JavafxUtil.showAlert(primaryStage, "数据格式错误", "登录密码不能为空", "请重新输入登录密码","warn");
            return;
        }
        NepgAqiConfirmViewController.primaryStage = primaryStage;
        GridMember gm = gridMemberService.login(txt_id.getText(), txt_password.getText());
        if(gm!=null){
            NepgAqiConfirmViewController.gridMember = gm;
            JavafxUtil.showStage(NepgMain.class, "view/NepgAqiConfirmView.fxml", primaryStage, "东软环保公众监督平台-确认AQI反馈数据");

        }else{
            JavafxUtil.showAlert(primaryStage, "登录失败", "登录账号和密码错误","请重新输入账号和密码","warn");
        }

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

}
