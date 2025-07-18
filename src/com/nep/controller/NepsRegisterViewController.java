package com.nep.controller;

import com.nep.NepsMain;
import com.nep.entity.Supervisor;
import com.nep.service.SupervisorService;
import com.nep.service.impl.SupervisorServiceImpl;
import com.nep.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NepsRegisterViewController {
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_repassword;
    @FXML
    private TextField txt_realname;
    @FXML
    private RadioButton txt_sex;

    //为注册页面添加主舞台
    public static Stage primaryStage;

    //使用多态创建服务层
    SupervisorService service=new SupervisorServiceImpl();


    public void register(){
        if(txt_id.getText().equals("")){
            JavafxUtil.showAlert(primaryStage,"注册失败","用户名不可为空","请输入用户名","warn");
            return;
        }

        if(!txt_password.getText().equals(txt_repassword.getText())){
            JavafxUtil.showAlert(primaryStage,"注册失败","两次输入的密码不一致！","请重新输入密码或进行检查","warn");
            txt_repassword.setText("");
            return;
        }

        Supervisor supervisor=new Supervisor();
        supervisor.setLoginCode(txt_id.getText());
        supervisor.setPassword(txt_password.getText());
        supervisor.setRealName(txt_realname.getText());
        supervisor.setSex(txt_sex.getText());

        boolean register=service.register(supervisor);
        if(register){
            JavafxUtil.showAlert(primaryStage,"注册成功","账号注册成功，请注意保存","请返回登录","info");
        }else{
            JavafxUtil.showAlert(primaryStage,"注册失败","手机号已被注册！","请重新输入手机号","warn");
            txt_id.setText("");
            return;
        }
        JavafxUtil.showStage(NepsMain.class,"view/NepsLoginView.fxml", primaryStage,"东软环保公众监督平台-公众监督员端");
    }

    public void back(){
        JavafxUtil.showStage(NepsMain.class,"view/NepsLoginView.fxml",primaryStage,"东软环保公众监督平台——公众监督员端");
    }

}
