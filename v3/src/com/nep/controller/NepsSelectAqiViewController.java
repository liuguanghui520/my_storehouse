package com.nep.controller;

import com.nep.NepaMain;
import com.nep.NepsMain;
import com.nep.entity.Aqi;
import com.nep.entity.AqiFeedback;
import com.nep.entity.ProvinceCity;
import com.nep.entity.Supervisor;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.util.CommonUtil;
import MySQL_Link.DatabaseUtil;
import com.nep.util.JavafxUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors; // 引入Collectors类

public class NepsSelectAqiViewController implements Initializable {

    public static Stage primaryStage;
    @FXML private TableView<Aqi> txt_tableView;
    @FXML private ComboBox<String> txt_province;
    @FXML private ComboBox<String> txt_city;
    @FXML private TextField txt_address;
    @FXML private ComboBox<String> txt_level;
    @FXML private TextArea txt_information;
    @FXML private Label label_realname;
    public static Supervisor supervisor;

    private AqiFeedbackService service = new AqiFeedbackServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化表格列
        initTableColumns();

        // 从数据库加载AQI等级数据
        ObservableList<Aqi> aqiData = FXCollections.observableArrayList(loadAqiFromDB());
        txt_tableView.setItems(aqiData);

        // 初始化等级下拉框（Java 8兼容写法）
        txt_level.getItems().addAll(FXCollections.observableArrayList(
                aqiData.stream().map(Aqi::getLevel).collect(Collectors.toList())
        ));
        txt_level.setValue("预估AQI等级");

        // 从数据库加载省市数据
        List<ProvinceCity> provinceCityList = loadProvinceCityFromDB();
        initProvinceComboBox(provinceCityList);
        initCityComboBox(provinceCityList);

        // 显示监督员姓名

            label_realname.setText(supervisor.getRealName());

    }

    // 初始化表格列
    private void initTableColumns() {
        TableColumn<Aqi, String> levelCol = new TableColumn<>("级别");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<Aqi, String> explainCol = new TableColumn<>("说明");
        explainCol.setCellValueFactory(new PropertyValueFactory<>("explain"));

        TableColumn<Aqi, String> impactCol = new TableColumn<>("对健康影响");
        impactCol.setCellValueFactory(new PropertyValueFactory<>("impact"));

        txt_tableView.getColumns().addAll(levelCol, explainCol, impactCol);
    }

    // 从数据库加载AQI等级数据
    private List<Aqi> loadAqiFromDB() {
        List<Aqi> aqiList = new ArrayList<>();
        String sql = "SELECT level, explain_text, impact FROM aqi";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                aqiList.add(new Aqi(
                        rs.getString("level"),
                        rs.getString("explain_text"),
                        rs.getString("impact")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JavafxUtil.showAlert(primaryStage, "数据错误", "加载AQI等级失败", e.getMessage(), "error");
        }
        return aqiList;
    }

    // 从数据库加载省市数据
    private List<ProvinceCity> loadProvinceCityFromDB() {
        List<ProvinceCity> pcList = new ArrayList<>();
        String sql = "SELECT province_name, city_names FROM province_city";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ProvinceCity pc = new ProvinceCity();
                pc.setProvinceName(rs.getString("province_name"));

                // 处理城市列表
                String cityNamesStr = rs.getString("city_names");
                if (cityNamesStr != null && !cityNamesStr.isEmpty()) {
                    String[] cityArray = cityNamesStr.split("，");
                    pc.setCityName(Arrays.asList(cityArray));
                } else {
                    pc.setCityName(new ArrayList<>()); // 空城市列表
                }

                pcList.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JavafxUtil.showAlert(primaryStage, "数据错误", "加载省市数据失败", e.getMessage(), "error");
        }

        // 调试输出：检查加载的省份数据
        System.out.println("加载的省份数量: " + pcList.size());
        for (ProvinceCity pc : pcList) {
            System.out.println("省份: " + pc.getProvinceName() + ", 城市数量: " + pc.getCityName().size());
        }

        return pcList;
    }

    // 初始化省份下拉框
    private void initProvinceComboBox(List<ProvinceCity> pcList) {
        txt_province.getItems().addAll(FXCollections.observableArrayList(
                pcList.stream().map(ProvinceCity::getProvinceName).collect(Collectors.toList())
        ));
        txt_province.setValue("请选择省区域");

        // 省份变更时刷新城市列表
        txt_province.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals("请选择省区域")) {
                List<String> cityList = pcList.stream()
                        .filter(pc -> pc.getProvinceName().equals(newVal))
                        .findFirst()
                        .map(ProvinceCity::getCityName)
                        .orElse(new ArrayList<>());

                txt_city.getItems().clear();
                txt_city.getItems().addAll(FXCollections.observableArrayList(cityList));
                txt_city.setValue("请选择市区域");
            }
        });
    }

    // 初始化城市下拉框（默认值）
    private void initCityComboBox(List<ProvinceCity> pcList) {
        txt_city.setValue("请选择市区域");
    }

    // 保存反馈数据到数据库
    public void saveFeedBack() {
        if (validateInput()) {
            AqiFeedback afb = createFeedbackObject();
            service.saveFeedBack(afb);

            JavafxUtil.showAlert(primaryStage, "提交成功", "AQI反馈数据已提交", "感谢您的监督！", "info");
            navigateToFeedbackList();
        }
    }

    // 验证输入数据
    private boolean validateInput() {
        if (txt_province.getValue() == null || txt_province.getValue().startsWith("请选择")) {
            JavafxUtil.showAlert(primaryStage, "输入错误", "请选择省份", "请先选择所属省份", "warning");
            return false;
        }

        if (txt_city.getValue() == null || txt_city.getValue().startsWith("请选择")) {
            JavafxUtil.showAlert(primaryStage, "输入错误", "请选择城市", "请先选择所属城市", "warning");
            return false;
        }

        if (txt_level.getValue() == null || txt_level.getValue().startsWith("预估")) {
            JavafxUtil.showAlert(primaryStage, "输入错误", "请选择AQI等级", "请预估当前AQI等级", "warning");
            return false;
        }

        if (txt_information.getText().trim().isEmpty()) {
            JavafxUtil.showAlert(primaryStage, "输入错误", "请填写反馈信息", "请描述具体的空气质量情况", "warning");
            return false;
        }
        return true;
    }

    // 创建反馈对象
    private AqiFeedback createFeedbackObject() {
        AqiFeedback afb = new AqiFeedback();
        afb.setAddress(txt_address.getText());
        afb.setAfName(supervisor.getRealName());
        afb.setProvinceName(txt_province.getValue());
        afb.setCityName(txt_city.getValue());
        afb.setEstimateGrade(txt_level.getValue());
        afb.setInformation(txt_information.getText());
        afb.setDate(CommonUtil.currentDate());
        afb.setState("未指派");
        return afb;
    }

    // 跳转到反馈列表页面
    public void feedBackList() {
        navigateToFeedbackList();
    }

    private void navigateToFeedbackList() {
        NepsFeedbackViewController.primaryStage = primaryStage;
        JavafxUtil.showStage(NepsMain.class, "view/NepsFeedbackView.fxml", primaryStage,
                "东软环保公众监督平台-公众监督员端-AQI反馈数据列表");
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