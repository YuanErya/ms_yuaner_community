package cn.yuanerya.feign.model.vo;

import java.util.Date;
import java.util.List;

public class QuestionDetailVO {
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
     * 账号
     */
    private String username;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 回答统计
     */
    private Integer answerNum;

    /**
     * 该问题的回答信息，
     * 而且每个回答都携带评论信息
     */
    private List<AnswerVO> answers;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

}
