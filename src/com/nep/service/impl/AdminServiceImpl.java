package com.nep.service.impl;
import com.nep.entity.Admin;
import com.nep.service.AdminService;
import MySQL_Link.DatabaseUtil;
import com.nep.util.JavafxUtil;
import java.sql.*;

public class AdminServiceImpl implements AdminService {
    @Override
    public boolean login(String loginCode, String password) {
        String sql = "SELECT * FROM system_admin WHERE login_code = ? AND password = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loginCode);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // 存在记录则返回true
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(null, "数据库错误", "管理员登录失败", e.getMessage(), "error");
            return false;
        }
    }
}
