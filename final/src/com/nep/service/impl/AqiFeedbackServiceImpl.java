package com.nep.service.impl;

import MySQL_Link.DatabaseUtil;
import com.nep.entity.AqiFeedback;
import com.nep.service.AqiFeedbackService;
import com.nep.util.JavafxUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AqiFeedbackServiceImpl implements AqiFeedbackService {
    @Override
    public void saveFeedBack(AqiFeedback afb) {
        String sql = "INSERT INTO aqi_feedback " +
                "(af_name, province_name, city_name, address, information, " +
                "estimate_grade, date, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, afb.getAfName());
            pstmt.setString(2, afb.getProvinceName());
            pstmt.setString(3, afb.getCityName());
            pstmt.setString(4, afb.getAddress());
            pstmt.setString(5, afb.getInformation());
            pstmt.setString(6, afb.getEstimateGrade());
            pstmt.setString(7, afb.getDate());
            pstmt.setString(8, afb.getState());

            pstmt.executeUpdate();

            // 获取自动生成的ID
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                afb.setAfId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(null, "数据库错误", "保存反馈信息失败", e.getMessage(), "error");
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
    }

    @Override
    public void assignGridMember(String afId, String realName) {
        String sql = "UPDATE aqi_feedback SET gm_name = ?, state = '已指派' WHERE af_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, realName);
            pstmt.setInt(2, Integer.parseInt(afId));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(null, "数据库错误", "指派网格员失败", e.getMessage(), "error");
        }
    }

    @Override
    public void confirmData(AqiFeedback afb) {
        String sql = "UPDATE aqi_feedback SET " +
                "state = ?, confirm_date = ?, so2 = ?, co = ?, pm = ?, " +
                "confirm_level = ?, confirm_explain = ? " +
                "WHERE af_id = ? AND gm_name = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, afb.getState());
            pstmt.setString(2, afb.getConfirmDate());
            pstmt.setDouble(3, afb.getSo2());
            pstmt.setDouble(4, afb.getCo());
            pstmt.setDouble(5, afb.getPm());
            pstmt.setString(6, afb.getConfirmLevel());
            pstmt.setString(7, afb.getConfirmExplain());
            pstmt.setInt(8, afb.getAfId());
            pstmt.setString(9, afb.getGmName());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(null, "数据库错误", "确认数据失败", e.getMessage(), "error");
        }
    }

    @Override
    public List<AqiFeedback> findFeedBackBySupervisor(String supervisorName) {
        List<AqiFeedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM aqi_feedback WHERE af_name = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supervisorName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                AqiFeedback afb = new AqiFeedback();
                afb.setAfId(rs.getInt("af_id"));
                afb.setAddress(rs.getString("address"));
                afb.setAfName(rs.getString("af_name"));
                afb.setProvinceName(rs.getString("province_name"));
                afb.setCityName(rs.getString("city_name"));
                afb.setEstimateGrade(rs.getString("estimate_grade"));
                afb.setInformation(rs.getString("information"));
                afb.setDate(rs.getString("date"));
                afb.setState(rs.getString("state"));
                // 设置其他字段...

                feedbackList.add(afb);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询反馈数据失败", e);
        }
        return feedbackList;
    }

    // 新增私有方法：从数据库查询所有反馈（不暴露给接口）
    public List<AqiFeedback> getAllFeedbacksFromDB() {
        List<AqiFeedback> list = new ArrayList<>();
        String sql = "SELECT * FROM aqi_feedback ORDER BY date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AqiFeedback af = new AqiFeedback();
                af.setAfId(rs.getInt("af_id"));
                af.setAfName(rs.getString("af_name"));
                af.setProvinceName(rs.getString("province_name"));
                af.setCityName(rs.getString("city_name"));
                af.setAddress(rs.getString("address"));
                af.setInformation(rs.getString("information"));
                af.setEstimateGrade(rs.getString("estimate_grade"));
                af.setDate(rs.getString("date"));
                af.setState(rs.getString("state"));
                af.setGmName(rs.getString("gm_name"));
                af.setConfirmDate(rs.getString("confirm_date"));
                af.setSo2(rs.getDouble("so2"));
                af.setCo(rs.getDouble("co"));
                af.setPm(rs.getDouble("pm"));
                af.setConfirmLevel(rs.getString("confirm_level"));
                af.setConfirmExplain(rs.getString("confirm_explain"));
                list.add(af);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 从数据库读取AQI反馈数据
     */
    @Override
    public List<AqiFeedback> readFeedbackFromDatabase() {
        List<AqiFeedback> feedbackList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM aqi_feedback";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                AqiFeedback feedback = new AqiFeedback();

                // 修正字段映射和类型转换
                feedback.setAfId(rs.getInt("af_id"));
                feedback.setAfName(rs.getString("af_name")); // 修正字段名大小写
                feedback.setProvinceName(rs.getString("province_name"));
                feedback.setCityName(rs.getString("city_name"));
                feedback.setAddress(rs.getString("address"));
                feedback.setInformation(rs.getString("information"));
                feedback.setEstimateGrade(rs.getString("estimate_grade"));
                feedback.setDate(rs.getString("date"));
                feedback.setState(rs.getString("state"));
                feedback.setGmName(rs.getString("gm_name"));
                feedback.setConfirmDate(rs.getString("confirm_date"));
                feedback.setSo2(rs.getDouble("so2"));
                feedback.setCo(rs.getDouble("co"));
                feedback.setPm(rs.getDouble("pm"));
                feedback.setConfirmLevel(rs.getString("confirm_level"));
                feedback.setConfirmExplain(rs.getString("confirm_explain"));

                feedbackList.add(feedback);

                // 添加调试输出
                System.out.println("读取反馈: ID=" + feedback.getAfId() + ", 名称=" + feedback.getAfName());
            }

            System.out.println("共读取 " + feedbackList.size() + " 条反馈数据");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, stmt, rs);
        }

        return feedbackList;
    }
}