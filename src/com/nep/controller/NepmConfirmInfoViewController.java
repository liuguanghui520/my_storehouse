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

public class NepmConfirmInfoViewController implements Initializable {
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

        TableColumn<AqiFeedback, Double> so2Column = new TableColumn<>("SQ2浓度(ug/m3)");
        so2Column.setMinWidth(80);
        so2Column.setStyle("-fx-alignment: center;");
        so2Column.setCellValueFactory(new PropertyValueFactory<>("so2"));

        TableColumn<AqiFeedback, Double> coColumn = new TableColumn<>("CO浓度(ug/m3)");
        coColumn.setMinWidth(80);
        coColumn.setStyle("-fx-alignment: center;");
        coColumn.setCellValueFactory(new PropertyValueFactory<>("co"));

        TableColumn<AqiFeedback, Double> pmColumn = new TableColumn<>("PM2.5浓度(ug/m3)");
        pmColumn.setMinWidth(80);
        pmColumn.setStyle("-fx-alignment: center;");
        pmColumn.setCellValueFactory(new PropertyValueFactory<>("pm"));

        TableColumn<AqiFeedback, String> confirmLevelColumn = new TableColumn<>("实测等级");
        confirmLevelColumn.setMinWidth(60);
        confirmLevelColumn.setStyle("-fx-alignment: center;");
        confirmLevelColumn.setCellValueFactory(new PropertyValueFactory<>("confirmLevel"));

        TableColumn<AqiFeedback, String> confirmExplainColumn = new TableColumn<>("等级说明");
        confirmExplainColumn.setMinWidth(60);
        confirmExplainColumn.setStyle("-fx-alignment: center;");
        confirmExplainColumn.setCellValueFactory(new PropertyValueFactory<>("confirmExplain"));

        TableColumn<AqiFeedback, String> confirmDateColumn = new TableColumn<>("实测日期");
        confirmDateColumn.setMinWidth(80);
        confirmDateColumn.setStyle("-fx-alignment: center;");
        confirmDateColumn.setCellValueFactory(new PropertyValueFactory<>("confirmDate"));

        TableColumn<AqiFeedback, String> gmNameColumn = new TableColumn<>("网格员");
        gmNameColumn.setMinWidth(60);
        gmNameColumn.setStyle("-fx-alignment: center;");
        gmNameColumn.setCellValueFactory(new PropertyValueFactory<>("gmName"));

        txt_tableView.getColumns().addAll(
                afIdColumn, provinceNameColumn, cityNameColumn, estimateGradeColumn,
                dateColumn, afNameColumn, so2Column, coColumn, pmColumn,
                confirmLevelColumn, confirmExplainColumn, confirmDateColumn, gmNameColumn
        );

        // 从数据库加载已实测数据
        loadConfirmedFeedbacks();
    }

    private void loadConfirmedFeedbacks() {
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();

        // 通过现有接口方法获取所有反馈
        List<AqiFeedback> afList = ((AqiFeedbackServiceImpl) aqiFeedbackService).getAllFeedbacksFromDB();

        // 过滤已实测的数据
        for (AqiFeedback afb : afList) {
            if (afb.getState() != null && afb.getState().equals("已实测")) {
                data.add(afb);
            }
        }

        txt_tableView.setItems(data);
    }
}