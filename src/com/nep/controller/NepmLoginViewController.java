package com.nep.controller;

import com.nep.NepmMain;
import com.nep.service.AdminService;
import com.nep.service.impl.AdminServiceImpl;
import com.nep.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NepmLoginViewController {
    @FXML
    private TextField txt_id;
    @FXML
    private PasswordField txt_password;
    //多态
    private AdminService adminService = new AdminServiceImpl();
    //主舞台
    public static Stage primaryStage;

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

    public void login() {
        // 输入验证
        if (txt_id.getText().isEmpty()) {
            JavafxUtil.showAlert(primaryStage, "登录失败", "请输入用户名", "提示", "warn");
            return;
        }

        if (txt_password.getText().isEmpty()) {
            JavafxUtil.showAlert(primaryStage, "登录失败", "请输入密码", "提示", "warn");
            return;
        }

        boolean isLogin = adminService.login(txt_id.getText(), txt_password.getText());
        if (isLogin) {
            NepmMainViewController.primaryStage = primaryStage;
            JavafxUtil.showStage(NepmMain.class, "view/NepmMainView.fxml", primaryStage, "东软环保公众监督平台-管理端-主功能界面");
        } else {
            JavafxUtil.showAlert(primaryStage, "登录失败", "用户名或密码错误", "请重新输入", "warn");
        }
    }
}

