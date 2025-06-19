package com.nep.service;

import com.nep.entity.AqiFeedback;

import java.util.List;

public interface AqiFeedbackService {
    public void saveFeedBack(AqiFeedback afb);
    /**
     * 指派网格员,修改反馈信息中网格员和状态
     */
    public void assignGridMember(String afId,String realName);

    /**
     * 提交实测AQI数据
     * @param afb
     */
    public void confirmData(AqiFeedback afb);


    // 从数据库查询反馈数据
    List<AqiFeedback> findFeedBackBySupervisor(String supervisorName);

    List<AqiFeedback> readFeedbackFromDatabase();

    /**
     * 从数据库读取AQI反馈数据
     */

}
