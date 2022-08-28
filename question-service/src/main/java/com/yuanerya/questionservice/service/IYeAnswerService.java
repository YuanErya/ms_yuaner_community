package com.yuanerya.questionservice.service;


import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.AnswerAndCommentDTO;
import cn.yuanerya.feign.model.entity.YeAnswer;
import cn.yuanerya.feign.model.entity.YeUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IYeAnswerService extends IService<YeAnswer> {
    /**
     * 创建新回答
     * @param dto
     * @param user
     * @param question_id
     * @return
     */
    YeAnswer answer(AnswerAndCommentDTO dto, YeUser user, String question_id);

    /**
     * 删除回答
     * @param answer_id
     * @param user_id
     * @return
     */
    ApiResult delete(String answer_id, String user_id);

    /**
     * 检查发起该操作的是否为回答者本人
     * 并修改回答内容
     * @param answer_id
     * @param user_id
     * @param dto
     * @return
     */
    ApiResult checkAndUpdate(String answer_id, String user_id,AnswerAndCommentDTO dto);
}
