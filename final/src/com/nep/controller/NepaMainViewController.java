package com.nep.controller;

import com.nep.NepaMain;
import com.nep.entity.AqiFeedback;
import com.nep.service.AqiFeedbackService;
import com.nep.service.impl.AqiFeedbackServiceImpl;
import com.nep.util.JavafxUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class NepaMainViewController implements Initializable {
    // 修正表格列与FXML中的fx:id匹配
    @FXML
    private TableColumn<AqiFeedback, String> dateColumn;
    @FXML
    private TableColumn<AqiFeedback, Double> so2Column;
    @FXML
    private TableColumn<AqiFeedback, Double> coColumn;
    @FXML
    private TableColumn<AqiFeedback, Double> pm25Column;

    @FXML
    private BarChart<?, ?> so2BarChart;
    @FXML
    private BarChart<?, ?> coBarChart;
    @FXML
    private BarChart<?, ?> pm25BarChart;
    @FXML
    private PieChart pollutionPieChart;
    @FXML
    private TableView<AqiFeedback> feedbackTableView;

    private AqiFeedbackService feedbackService = new AqiFeedbackServiceImpl();
    public static Stage primaryStage;

    private NepaMain mainApp;

    @FXML
    private void openManagerLogin(ActionEvent event) {
        try {
            mainApp.showManagerLogin();
        } catch (IOException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(NepaMain.primaryStage, "错误", "加载登录界面失败",
                    "无法打开管理端登录界面：" + e.getMessage(), "error");
        }
    }

    @FXML
    private void openGridLogin(ActionEvent event) {
        try {
            mainApp.showGridLogin();
        } catch (IOException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(NepaMain.primaryStage, "错误", "加载登录界面失败",
                    "无法打开网格员端登录界面：" + e.getMessage(), "error");
        }
    }

    @FXML
    private void openSupervisorLogin(ActionEvent event) {
        try {
            mainApp.showSupervisorLogin();
        } catch (IOException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(NepaMain.primaryStage, "错误", "加载登录界面失败",
                    "无法打开公众监督员端登录界面：" + e.getMessage(), "error");
        }
    }

    // 由MainApplication设置引用
    public void setMainApp(NepaMain mainApp) {
        this.mainApp = mainApp;
        primaryStage = mainApp.primaryStage; // 初始化primaryStage
    }

    /**
     * 从数据库加载AQI反馈数据并更新图表
     */
    public void loadFeedbackDataFromDatabase() {
        try {


            // 清空现有图表数据
            if (so2BarChart != null) so2BarChart.getData().clear();
            if (coBarChart != null) coBarChart.getData().clear();
            if (pm25BarChart != null) pm25BarChart.getData().clear();
            if (pollutionPieChart != null) pollutionPieChart.getData().clear();
            if (feedbackTableView != null) feedbackTableView.getItems().clear();

            // 从数据库读取数据
            List<AqiFeedback> feedbackList = feedbackService.readFeedbackFromDatabase();

            if (feedbackList == null || feedbackList.isEmpty()) {
                JavafxUtil.showAlert(primaryStage, "数据为空", "数据库中未找到AQI反馈数据", "", "info");
                JavafxUtil.hideLoading();
                return;
            }

            // 更新柱状图数据
            updatePollutionBarCharts(feedbackList);

            // 更新饼图数据
            updatePollutionPieChart(feedbackList);

            // 更新表格数据
            updateFeedbackTable(feedbackList);

            // 隐藏加载提示
            JavafxUtil.hideLoading();

        } catch (Exception e) {
            e.printStackTrace();
            JavafxUtil.hideLoading();
            JavafxUtil.showAlert(primaryStage, "数据加载失败", "从数据库读取数据时发生错误",
                    e.getMessage(), "error");
        }
    }

    /**
     * 更新污染物浓度柱状图
     */
    private void updatePollutionBarCharts(List<AqiFeedback> feedbackList) {
        // SO2浓度柱状图
        XYChart.Series so2Series = new XYChart.Series();
        so2Series.setName("so2浓度统计");

        // CO浓度柱状图
        XYChart.Series coSeries = new XYChart.Series();
       coSeries.setName("co浓度统计");

        // PM2.5浓度柱状图
        XYChart.Series pm25Series = new XYChart.Series();
        pm25Series.setName("pm浓度统计");



        for (AqiFeedback feedback : feedbackList) {
            // 跳过数据不全的记录
            if (feedback.getSo2() == 0 && feedback.getCo() == 0 && feedback.getPm() == 0) continue;

            String key = feedback.getProvinceName() + "-" + feedback.getCityName();
            so2Series.getData().add(new XYChart.Data(key, feedback.getSo2()));
            coSeries.getData().add(new XYChart.Data(key, feedback.getCo()));
            pm25Series.getData().add(new XYChart.Data(key, feedback.getPm()));
        }

        if (so2BarChart != null) so2BarChart.getData().add(so2Series);
        if (coBarChart != null) coBarChart.getData().add(coSeries);
        if (pm25BarChart != null) pm25BarChart.getData().add(pm25Series);

        // 设置图表样式
        if (so2BarChart != null) so2BarChart.setStyle("-fx-bar-fill: #4285f4;"); // 蓝色
        if (coBarChart != null) coBarChart.setStyle("-fx-bar-fill: #ff6b6b;");   // 红色
        if (pm25BarChart != null) pm25BarChart.setStyle("-fx-bar-fill: #4cd137;"); // 绿色
        if (so2BarChart != null) so2BarChart.setAnimated(false);
        if (coBarChart != null) coBarChart.setAnimated(false);
        if (pm25BarChart != null) pm25BarChart.setAnimated(false);
    }

    /**
     * 更新污染等级饼图
     */
    private void updatePollutionPieChart(List<AqiFeedback> feedbackList) {
        Map<String, Integer> gradeCount = new HashMap<>();

        // 统计各等级数量
        for (AqiFeedback feedback : feedbackList) {
            String grade = feedback.getConfirmLevel() != null && !feedback.getConfirmLevel().isEmpty() ?
                    feedback.getConfirmLevel() : feedback.getEstimateGrade();
            gradeCount.put(grade, gradeCount.getOrDefault(grade, 0) + 1);
        }

        // 转换为饼图数据
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        String[] colors = {"#4285f4", "#ff6b6b", "#4cd137", "#f39c12", "#9b59b6", "#3498db"};

        int i = 0;
        for (Map.Entry<String, Integer> entry : gradeCount.entrySet()) {
            if (i >= colors.length) break;
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            i++;
        }

        if (pollutionPieChart != null) {
            pollutionPieChart.setData(pieData);
            pollutionPieChart.setStyle(
                    "-fx-pie-color: " + String.join(", ", colors) + ";" +
                            "-fx-label-text-fill: white;" +
                            "-fx-title-fill: #333333;" +
                            "-fx-title-font: 14px System;"
            );
        }
    }

    /**
     * 更新反馈数据表格
     */
    private void updateFeedbackTable(List<AqiFeedback> feedbackList) {
        if (feedbackTableView != null) {
            // 设置表格列
            if (dateColumn != null) dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            if (so2Column != null) so2Column.setCellValueFactory(new PropertyValueFactory<>("so2"));
            if (coColumn != null) coColumn.setCellValueFactory(new PropertyValueFactory<>("co"));
            if (pm25Column != null) pm25Column.setCellValueFactory(new PropertyValueFactory<>("pm"));

            // 添加数据
            ObservableList<AqiFeedback> tableData = FXCollections.observableArrayList(feedbackList);
            feedbackTableView.setItems(tableData);

            // 设置表格样式
            feedbackTableView.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-table-cell-border-color: #e0e0e0;" +
                            "-fx-table-header-border-color: #4285f4;" +
                            "-fx-padding: 10;" +
                            "-fx-font-size: 14px;"
            );
            if (feedbackTableView.getColumns() != null) {
                feedbackTableView.getColumns().forEach(col ->
                        col.setStyle("-fx-alignment: CENTER;")
                );
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 加载数据
        loadFeedbackDataFromDatabase();
    }
}