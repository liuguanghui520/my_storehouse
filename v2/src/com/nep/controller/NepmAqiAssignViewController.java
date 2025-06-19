package com.nep.controller;
import com.nep.entity.AqiFeedback;
import com.nep.entity.GridMember;
import com.nep.service.AqiFeedbackService;
import com.nep.service.GridMemberService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.service.impl.GridMemberServiceImpl;
import com.nep.util.JavafxUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NepmAqiAssignViewController implements Initializable {
    @FXML private Pane txt_pane1;
    @FXML private Pane txt_pane2;
    @FXML private Pane txt_pane3;
    @FXML private TextField txt_afId;
    @FXML private Label label_afId;
    @FXML private Label label_afName;
    @FXML private Label label_proviceName;
    @FXML private Label label_cityName;
    @FXML private Label label_address;
    @FXML private Label label_infomation;
    @FXML private Label label_estimateGrade;
    @FXML private Label label_date;
    @FXML private ComboBox<String> combo_realName;

    public static Stage aqiInfoStage;
    private AqiFeedbackService aqiFeedbackService = new AqiFeedbackServiceImpl();
    private GridMemberService gridMemberService = new GridMemberServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化三个pane容器样式
        txt_pane1.setStyle("-fx-border-color: #CCC;");
        txt_pane2.setStyle("-fx-background-color: #CCC;");
        txt_pane3.setStyle("-fx-border-color: #CCC;");

        // 标签初始化
        initController();

        // 从数据库加载工作中的网格员
        loadAvailableGridMembers();
    }

    private void loadAvailableGridMembers() {
        // 通过新接口方法获取所有工作中的网格员
        List<GridMember> glist = gridMemberService.getAllGridMembers();

        // 添加到下拉列表
        for (GridMember gm : glist) {
            combo_realName.getItems().add(gm.getRealName());
        }

        // 如果没有网格员，提供默认选项
        if (combo_realName.getItems().isEmpty()) {
            combo_realName.getItems().add("无可用网格员");
        } else {
            combo_realName.setValue(combo_realName.getItems().get(0)); // 默认选中第一个
        }
    }

    public void queryFeedback() {
        String afId = txt_afId.getText();

        // 输入验证
        if (afId.isEmpty()) {
            JavafxUtil.showAlert(aqiInfoStage, "查询失败", "请输入反馈编号", "提示", "warn");
            return;
        }

        // 通过现有接口方法获取所有反馈
        List<AqiFeedback> alist = ((AqiFeedbackServiceImpl) aqiFeedbackService).getAllFeedbacksFromDB();
        boolean found = false;

        // 查找匹配的反馈
        for (AqiFeedback af : alist) {
            if (af.getAfId().toString().equals(afId) && af.getState().equals("未指派")) {
                found = true;
                label_afId.setText(af.getAfId() + "");
                label_afName.setText(af.getAfName());
                label_address.setText(af.getAddress());
                label_cityName.setText(af.getCityName());
                label_date.setText(af.getDate());
                label_estimateGrade.setText(af.getEstimateGrade());
                label_infomation.setText(af.getInformation());
                label_proviceName.setText(af.getProvinceName());
                break;
            }
        }

        if (!found) {
            JavafxUtil.showAlert(aqiInfoStage, "查询失败", "未找到当前编号反馈信息或已被指派", "请重新输入", "warn");
            initController();
        }
    }

    public void assignGridMember() {
        // 前置判断
        if (label_afId.getText().equals("无")) {
            JavafxUtil.showAlert(aqiInfoStage, "指派失败", "未找到要指派的反馈信息", "请先查询反馈信息", "warn");
            return;
        }

        if (combo_realName.getValue() == null ||
                combo_realName.getValue().equals("无可用网格员") ||
                combo_realName.getValue().equals("请选择网格员")) {
            JavafxUtil.showAlert(aqiInfoStage, "指派失败", "没有可用的网格员", "请稍后再试", "warn");
            return;
        }

        // 执行指派
        String afId = label_afId.getText();
        aqiFeedbackService.assignGridMember(afId, combo_realName.getValue());

        // 显示成功提示
        JavafxUtil.showAlert(aqiInfoStage, "指派成功", "AQI反馈信息指派成功!", "请等待网格员实测数据", "info");

        // 重置界面
        initController();
    }

    // 界面控件初始化方法
    public void initController() {
        txt_afId.setText("");
        label_afId.setText("无");
        label_afName.setText("无");
        label_address.setText("无");
        label_cityName.setText("无");
        label_date.setText("无");
        label_estimateGrade.setText("无");
        label_infomation.setText("无");
        label_proviceName.setText("无");
        combo_realName.setValue("请选择网格员");
    }
}