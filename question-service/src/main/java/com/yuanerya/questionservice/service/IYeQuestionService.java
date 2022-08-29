package com.yuanerya.questionservice.service;


import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.CreateQuestionDTO;
import cn.yuanerya.feign.model.entity.YeQuestion;
import cn.yuanerya.feign.model.entity.YeUser;
import cn.yuanerya.feign.model.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IYeQuestionService extends IService<YeQuestion> {
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<QuestionVO> getPage(Integer pageNo, Integer pageSize);

    /**
     * 发布帖子
     * @param dto
     * @param
     * @return
     */
    YeQuestion create(CreateQuestionDTO dto, YeUser user);

    /**
     * 根据问题的ID删除问题以及它下属的回答和评论
     * @param question_id 传入的问题的id
     * @return 返回操作成功
     */
    ApiResult delete(String question_id, String user_id);

    /**
     * 检查发起该操作的是否为发布该问题的用户
     * 并修改发布的问题的内容
     * @param question_id
     * @param user_id
     * @param dto
     * @return
     */
    ApiResult checkAndUpdate(String question_id,String user_id, CreateQuestionDTO dto);

    /**
     * 根据用户的id进行查询用户的全部问题
      * @param user_id
     * @return
     */
    List<YeQuestion> getMyQuestionsByUserId(String user_id);
}
