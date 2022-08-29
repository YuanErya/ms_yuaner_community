package com.yuanerya.questionservice.service;


import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.AnswerAndCommentDTO;
import cn.yuanerya.feign.model.entity.YeComment;
import cn.yuanerya.feign.model.entity.YeQuestion;
import cn.yuanerya.feign.model.entity.YeUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IYeCommentService extends IService<YeComment> {
    /**
     * 创建新的评论
     * @param dto
     * @param user
     * @param answer_id
     * @return
     */
    YeComment comment(AnswerAndCommentDTO dto, YeUser user, String answer_id);

    /**
     * 删除评论
     * @param comment_id
     * @param user_id
     * @return
     */
    ApiResult delete(String comment_id, String user_id);

    /**
     * 检查发起该操作的是否为评论的本人
     * 并完成修改评论
     * @param comment_id
     * @param user_id
     * @param dto
     * @return
     */
    ApiResult checkAndUpdate(String comment_id,String user_id,AnswerAndCommentDTO dto);

    /**
     * 根据用户的id进行查询用户的全部评论
     * @param user_id
     * @return
     */
    List<YeComment> getMyCommentsByUserId(String user_id);
}
