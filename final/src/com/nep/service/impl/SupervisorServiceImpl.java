package com.nep.service.impl;

import com.nep.controller.NepsFeedbackViewController;
import com.nep.controller.NepsRegisterViewController;
import com.nep.controller.NepsSelectAqiViewController;
import com.nep.entity.Supervisor;
import com.nep.service.SupervisorService;
import MySQL_Link.DatabaseUtil;
import com.nep.util.JavafxUtil;
import java.sql.*;

public class SupervisorServiceImpl implements SupervisorService {
    @Override
    public Supervisor login(String loginCode, String password) {
        String sql = "SELECT * FROM supervisor WHERE logincode = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isLogin = false;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginCode);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Supervisor supervisor = new Supervisor();
                supervisor.setLoginCode(rs.getString("logincode"));
                supervisor.setPassword(rs.getString("password"));
                supervisor.setRealName(rs.getString("realname"));
                supervisor.setSex(rs.getString("sex"));  // 从数据库获取性别值

                NepsSelectAqiViewController.supervisor = supervisor;
                NepsFeedbackViewController.supervisor = supervisor;
                isLogin = true;
                return supervisor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 确保Stage参数不为null，假设使用NepsRegisterViewController的primaryStage
            JavafxUtil.showAlert(NepsRegisterViewController.primaryStage, "数据库错误", "登录验证失败", e.getMessage(), "error");
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }

        return null;
    }

    @Override
    public boolean register(Supervisor supervisor) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean registered = false;

        try {
            conn = DatabaseUtil.getConnection();

            // 检查用户名是否已存在
            String checkSql = "SELECT * FROM supervisor WHERE logincode = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, supervisor.getLoginCode());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return false;
            }

            // 修正插入语句，使用正确的字段列表
            String insertSql = "INSERT INTO supervisor (logincode, password, realname, sex) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, supervisor.getLoginCode());
            pstmt.setString(2, supervisor.getPassword());
            pstmt.setString(3, supervisor.getRealName());
            pstmt.setString(4, supervisor.getSex());

            int affectedRows = pstmt.executeUpdate();
            registered = affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            // 确保Stage参数不为null
            JavafxUtil.showAlert(NepsRegisterViewController.primaryStage, "数据库错误", "注册失败", e.getMessage(), "error");
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }

        return registered;
    }
}