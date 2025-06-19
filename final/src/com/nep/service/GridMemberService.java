package com.nep.service;

import com.nep.entity.GridMember;

import java.util.List;

public interface GridMemberService {
    /**
     * 网格员登录
     * @param loginCode
     * @param password
     * @return
     */
    public GridMember login(String loginCode, String password);

    // 新增方法：获取所有网格员
    List<GridMember> getAllGridMembers();

}
