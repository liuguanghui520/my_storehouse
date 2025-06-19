package com.nep.controller;

import com.nep.NepsMain;
import com.nep.entity.Supervisor;
import com.nep.service.SupervisorService;
import com.nep.service.impl.SupervisorServiceImpl;
import com.nep.util.JavafxUtil;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class NepsLoginViewController {

    @FXML
    private TextField txt_id;    //绑定登录账号文本框
    @FXML
    private PasswordField txt_password;    //绑定登录密码框

    public TextField getTxt_id() {
        return txt_id;
    }

    public void setTxt_id(TextField txt_id) {
        this.txt_id = txt_id;
    }

    public PasswordField getTxt_password() {
        return txt_password;
    }

    public void setTxt_password(PasswordField txt_password) {
        this.txt_password = txt_password;
    }

    private SupervisorService service=new SupervisorServiceImpl();
    public static Stage primaryStage;
    public void login(){
        Supervisor s=service.login(txt_id.getText(),txt_password.getText());
        if(s!=null){
            System.out.println("登录成功");
            NepsSelectAqiViewController.primaryStage=primaryStage;
            NepsSelectAqiViewController.supervisor=s;
            JavafxUtil.showStage(NepsMain.class,"view/NepsSelectAqiView.fxml", primaryStage, "东软环保公众监督平台-公众监督员端-AQI数据反馈");
        }else{
            JavafxUtil.showAlert(primaryStage,"登陆失败","用户名或密码错误","a=error","warn");
        }
    }

    public void register(){
        System.out.println("注册功能");
        NepsRegisterViewController.primaryStage=primaryStage;
        JavafxUtil.showStage(NepsMain.class,"view/NepsRegisterView.fxml",primaryStage,"东软环保公众监督员平台——注册功能");
    }
}
