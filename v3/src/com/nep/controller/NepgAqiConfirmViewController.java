// com.nep.controller.NepgAqiConfirmViewController.java
package com.nep.controller;

import com.nep.NepaMain;
import com.nep.dto.AqiLimitDto;
import com.nep.entity.AqiFeedback;
import com.nep.entity.GridMember;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.util.CommonUtil;
import com.nep.util.JavafxUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NepgAqiConfirmViewController implements Initializable {
    @FXML private TableView<AqiFeedback> txt_tableView;
    @FXML private Pane txt_pane;
    @FXML private TextField txt_afId;
    @FXML private TextField txt_so2;
    @FXML private TextField txt_co;
    @FXML private TextField txt_pm;
    @FXML private Label label_so2level;
    @FXML private Label label_so2explain;
    @FXML private Label label_colevel;
    @FXML private Label label_coexplain;
    @FXML private Label label_pmlevel;
    @FXML private Label label_pmexplain;
    @FXML private Label label_confirmlevel;
    @FXML private Label label_confirmexplain;
    @FXML private Label label_realName;

    public static GridMember gridMember;
    public static Stage primaryStage;

    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();
    private int so2level;
    private int colevel;
    private int pmlevel;
    private AqiLimitDto confirmDto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化pane容器样式
        txt_pane.setStyle("-fx-border-color: #CCC;");

        // 初始化网格员姓名
        if (gridMember != null) {
            label_realName.setText(gridMember.getRealName());
        } else {
            System.err.println("Error: GridMember is null!");
            label_realName.setText("未知网格员");
        }

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

        TableColumn<AqiFeedback, String> addressColumn = new TableColumn<>("具体地址");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<AqiFeedback, String> infoColumn = new TableColumn<>("反馈信息");
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("information"));

        txt_tableView.getColumns().addAll(afIdColumn, afNameColumn, dateColumn, estimateGradeColumn,
                provinceNameColumn, cityNameColumn, addressColumn, infoColumn);

        // 通过现有接口方法获取数据
        loadDataThroughExistingMethods();

        // 添加编号文本框事件监听
        txt_afId.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !txt_afId.getText().isEmpty()) {
                try {
                    int afId = Integer.parseInt(txt_afId.getText());
                    boolean valid = false;

                    // 通过现有接口方法验证ID
                    List<AqiFeedback> afList = ((AqiFeedbackServiceImpl) aqiFeedbackService).getAllFeedbacksFromDB();
                    for (AqiFeedback afb : afList) {
                        if (afb.getGmName() != null && afb.getGmName().equals(gridMember.getRealName()) &&
                                afb.getAfId() == afId && afb.getState().equals("已指派")) {
                            valid = true;
                            break;
                        }
                    }

                    if (!valid) {
                        JavafxUtil.showAlert(primaryStage, "数据错误", "AQI反馈数据编号有误", "请重新输入AQI反馈数据编号", "warn");
                        txt_afId.setText("");
                    }
                } catch (NumberFormatException e) {
                    JavafxUtil.showAlert(primaryStage, "格式错误", "请输入有效的编号", "提示", "warn");
                    txt_afId.setText("");
                }
            }
        });

        // 其他事件监听保持不变...
        txt_so2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    try {
                        AqiLimitDto dto = CommonUtil.so2Limit(Double.parseDouble(newValue));
                        updateLabels(label_so2level, label_so2explain, dto);
                        so2level = dto.getIntlevel();
                        updateConfirmLevel();
                    } catch (NumberFormatException e) {
                        resetLabels(label_so2level, label_so2explain);
                    }
                } else {
                    resetLabels(label_so2level, label_so2explain);
                }
            }
        });

        txt_co.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    try {
                        AqiLimitDto dto = CommonUtil.coLimit(Double.parseDouble(newValue));
                        updateLabels(label_colevel, label_coexplain, dto);
                        colevel = dto.getIntlevel();
                        updateConfirmLevel();
                    } catch (NumberFormatException e) {
                        resetLabels(label_colevel, label_coexplain);
                    }
                } else {
                    resetLabels(label_colevel, label_coexplain);
                }
            }
        });

        txt_pm.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    try {
                        AqiLimitDto dto = CommonUtil.pmLimit(Double.parseDouble(newValue));
                        updateLabels(label_pmlevel, label_pmexplain, dto);
                        pmlevel = dto.getIntlevel();
                        updateConfirmLevel();
                    } catch (NumberFormatException e) {
                        resetLabels(label_pmlevel, label_pmexplain);
                    }
                } else {
                    resetLabels(label_pmlevel, label_pmexplain);
                }
            }
        });
    }

    // 通过现有接口方法获取数据（避免新增方法）
    private void loadDataThroughExistingMethods() {
        ObservableList<AqiFeedback> data = FXCollections.observableArrayList();

        // 通过现有接口方法获取所有反馈
        List<AqiFeedback> afList = ((AqiFeedbackServiceImpl) aqiFeedbackService).getAllFeedbacksFromDB();

        // 过滤已指派给当前网格员的数据
        for (AqiFeedback afb : afList) {
            if (afb.getGmName() != null && afb.getGmName().equals(gridMember.getRealName()) &&
                    afb.getState().equals("已指派")) {
                data.add(afb);
            }
        }

        txt_tableView.setItems(data);
    }

    public void confirmData() {
        // 输入验证
        if (txt_afId.getText().isEmpty()) {
            JavafxUtil.showAlert(primaryStage, "输入错误", "请输入反馈编号", "提示", "warn");
            return;
        }

        if (txt_so2.getText().isEmpty() || txt_co.getText().isEmpty() || txt_pm.getText().isEmpty()) {
            JavafxUtil.showAlert(primaryStage, "输入错误", "请填写完整的污染物数据", "提示", "warn");
            return;
        }

        try {
            AqiFeedback afb = new AqiFeedback();
            afb.setAfId(Integer.parseInt(txt_afId.getText()));
            afb.setState("已实测");
            afb.setSo2(Double.parseDouble(txt_so2.getText()));
            afb.setCo(Double.parseDouble(txt_co.getText()));
            afb.setPm(Double.parseDouble(txt_pm.getText()));
            afb.setConfirmDate(CommonUtil.currentDate());
            afb.setConfirmLevel(confirmDto.getLevel());
            afb.setConfirmExplain(confirmDto.getExplain());
            afb.setGmName(gridMember.getRealName());

            aqiFeedbackService.confirmData(afb);
            JavafxUtil.showAlert(primaryStage, "提交成功", "污染物实测数据提交成功", "", "info");

            // 刷新页面数据表格（复用现有方法）
            loadDataThroughExistingMethods();
            reset();
        } catch (NumberFormatException e) {
            JavafxUtil.showAlert(primaryStage, "格式错误", "请输入有效的数值", "提示", "warn");
        }
    }

    private void updateLabels(Label levelLabel, Label explainLabel, AqiLimitDto dto) {
        levelLabel.setText(dto.getLevel());
        levelLabel.setStyle("-fx-text-fill:" + dto.getColor() + ";");
        explainLabel.setText(dto.getExplain());
        explainLabel.setStyle("-fx-background-color:" + dto.getColor() + ";");
    }

    private void resetLabels(Label levelLabel, Label explainLabel) {
        levelLabel.setText("无");
        levelLabel.setStyle("-fx-text-fill:black;");
        explainLabel.setText("");
        explainLabel.setStyle("-fx-background-color:none;");
    }

    private void updateConfirmLevel() {
        confirmDto = CommonUtil.confirmLevel(so2level, colevel, pmlevel);
        label_confirmlevel.setText(confirmDto.getLevel());
        label_confirmlevel.setStyle("-fx-text-fill:" + confirmDto.getColor() + ";");
        label_confirmexplain.setText(confirmDto.getExplain());
        label_confirmexplain.setStyle("-fx-background-color:" + confirmDto.getColor() + ";");
    }

    public void reset() {
        txt_afId.setText("");
        txt_so2.setText("");
        txt_co.setText("");
        txt_pm.setText("");

        resetLabels(label_so2level, label_so2explain);
        resetLabels(label_colevel, label_coexplain);
        resetLabels(label_pmlevel, label_pmexplain);
        resetLabels(label_confirmlevel, label_confirmexplain);

        so2level = 0;
        colevel = 0;
        pmlevel = 0;
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