package cn.yuanerya.feign.model.vo;

import cn.yuanerya.feign.model.entity.YeComment;

import java.util.List;

public class AnswerVO {
    /**
     * 问题ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String alias;
    /**
     * 内容
     */
    private String content;
    /**
     * 回答的全部评论信息
     */
    private List<YeComment> comments;

}
