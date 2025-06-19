package com.nep.controller;
import com.nep.entity.AqiFeedback;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiInfoViewController implements Initializable {
    @FXML private TableView<AqiFeedback> txt_tableView;
    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化表格（修正列名拼写错误）
        TableColumn<AqiFeedback, Integer> afIdColumn = new TableColumn<>("编号");
        afIdColumn.setMinWidth(40);
        afIdColumn.setStyle("-fx-alignment: center;");
        afIdColumn.setCellValueFactory(new PropertyValueFactory<>("afId"));

        TableColumn<AqiFeedback, String> provinceNameColumn = new TableColumn<>("省区域");
        provinceNameColumn.setMinWidth(60);
        provinceNameColumn.setStyle("-fx-alignment: center;");
        provinceNameColumn.setCellValueFactory(new PropertyValueFactory<>("provinceName"));

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

        TableColumn<AqiFeedback, String> afNameColumn = new TableColumn<>("反馈者");
        afNameColumn.setMinWidth(60);
        afNameColumn.setStyle("-fx-alignment: center;");
        afNameColumn.setCellValueFactory(new PropertyValueFactory<>("afName"));

        TableColumn<AqiFeedback, String> infoColumn = new TableColumn<>("反馈信息");
        infoColumn.setMinWidth(210);
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("information"));

        txt_tableView.getColumns().addAll(
                afIdColumn, provinceNameColumn, cityNameColumn, estimateGradeColumn,
                dateColumn, afNameColumn, infoColumn
        );

        // 从数据库加载未指派数据
        loadUnassignedFeedbacks();
    }

    private void loadUnassignedFeedbacks() {
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();

        // 通过现有接口方法获取所有反馈
        List<AqiFeedback> afList = ((AqiFeedbackServiceImpl) aqiFeedbackService).getAllFeedbacksFromDB();

        // 过滤未指派的数据
        for (AqiFeedback afb : afList) {
            if (afb.getState() != null && afb.getState().equals("未指派")) {
                data.add(afb);
            }
        }

        txt_tableView.setItems(data);
    }
}