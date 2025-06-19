package com.nep.controller;

import com.nep.NepsMain;
import com.nep.entity.AqiFeedback;
import com.nep.entity.Supervisor;
import com.nep.io.FileIO;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.util.JavafxUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NepsFeedbackViewController implements Initializable {
    @FXML
    private TableView<AqiFeedback> txt_tableView;
    @FXML
    private Label txt_realName;
    //主舞台
    public static Stage primaryStage;
    //当前登录成功的公众监督员用户身份
    public static Supervisor supervisor;

    public TableView<AqiFeedback> getTxt_tableView() {
        return txt_tableView;
    }

    public void setTxt_tableView(TableView<AqiFeedback> txt_tableView) {
        this.txt_tableView = txt_tableView;
    }

    public Label getTxt_realName() {
        return txt_realName;
    }

    public void setTxt_realName(Label txt_realName) {
        this.txt_realName = txt_realName;
    }


    public void back(){
        JavafxUtil.showStage(NepsMain.class,"view/NepsSelectAqiView.fxml", primaryStage,"东软环保公众监督平台-公众监督员端-AQI数据反馈");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化当前用户名
        txt_realName.setText(supervisor.getRealName());

        // 初始化表格列
        TableColumn<AqiFeedback, Integer> afIdColumn = new TableColumn<>("编号");
        afIdColumn.setMinWidth(40);
        afIdColumn.setStyle("-fx-alignment: center;");
        afIdColumn.setCellValueFactory(new PropertyValueFactory<>("afId"));

        TableColumn<AqiFeedback, String> proviceNameColumn = new TableColumn<>("省区域");
        proviceNameColumn.setMinWidth(60);
        proviceNameColumn.setStyle("-fx-alignment: center;");
        proviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("provinceName"));

        TableColumn<AqiFeedback, String> cityNameColumn = new TableColumn<>("市区域");
        cityNameColumn.setMinWidth(60);
        cityNameColumn.setStyle("-fx-alignment: center;");
        cityNameColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));

        TableColumn<AqiFeedback, String> estimateGradeColumn = new TableColumn<>("预估等级");
        estimateGradeColumn.setMinWidth(60);
        estimateGradeColumn.setStyle("-fx-alignment: center;");
        estimateGradeColumn.setCellValueFactory(new PropertyValueFactory<>("estimateGrade"));

        TableColumn<AqiFeedback, String> dateColumn = new TableColumn<>("反馈时间");
        dateColumn.setMinWidth(80);
        dateColumn.setStyle("-fx-alignment: center;");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<AqiFeedback, String> infoColumn = new TableColumn<>("反馈信息");
        infoColumn.setMinWidth(330);
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("information"));

        txt_tableView.getColumns().addAll(afIdColumn, proviceNameColumn, cityNameColumn, estimateGradeColumn, dateColumn, infoColumn);

        // 通过现有接口方法获取数据（模拟文件读取）
        // 1. 创建一个临时反馈对象
        AqiFeedback tempFeedback = new AqiFeedback();
        tempFeedback.setAfName(supervisor.getRealName());

        // 2. 调用confirmData方法，利用其查询逻辑（注意：此方法会修改数据库，但我们只用于查询）
        AqiFeedbackService service=new AqiFeedbackServiceImpl();
        List<AqiFeedback> allFeedbacks = ((AqiFeedbackServiceImpl)service).getAllFeedbacksFromDB();

        // 3. 过滤当前监督员的反馈
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();
        for (AqiFeedback afb : allFeedbacks) {
            if (afb.getAfName().equals(supervisor.getRealName())) {
                data.add(afb);
            }
        }
        txt_tableView.setItems(data);
    }
}
