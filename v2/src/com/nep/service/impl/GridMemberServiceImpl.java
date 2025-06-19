package com.nep.service.impl;
import com.nep.entity.GridMember;
import com.nep.service.GridMemberService;
import MySQL_Link.DatabaseUtil;
import com.nep.util.JavafxUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GridMemberServiceImpl implements GridMemberService {
    @Override
    public GridMember login(String loginCode, String password) {
        String sql = "SELECT * FROM grid_member WHERE login_code = ? AND password = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loginCode);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    GridMember gm = new GridMember();

                    gm.setLoginCode(rs.getString("login_code"));
                    gm.setPassword(rs.getString("password"));
                    gm.setRealName(rs.getString("real_name"));
                    gm.setGmTel(rs.getString("gm_tel"));
                    gm.setState(rs.getString("state"));
                    return gm;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(null, "数据库错误", "网格员登录失败", e.getMessage(), "error");
        }
        return null;
    }


    @Override
    public List<GridMember> getAllGridMembers() {
        List<GridMember> list = new ArrayList<>();
        String sql = "SELECT * FROM grid_member WHERE state = '工作中'";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GridMember gm = new GridMember();

                gm.setLoginCode(rs.getString("login_code"));
                gm.setPassword(rs.getString("password"));
                gm.setRealName(rs.getString("real_name"));
                gm.setGmTel(rs.getString("gm_tel"));
                gm.setState(rs.getString("state"));
                list.add(gm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JavafxUtil.showAlert(null, "数据库错误", "获取网格员列表失败", e.getMessage(), "error");
        }
        return list;
    }
}
