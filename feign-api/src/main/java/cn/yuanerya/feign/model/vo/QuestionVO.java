package cn.yuanerya.feign.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

//用于列表查询时格式化输出
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO implements Serializable {
    private static final long serialVersionUID = -261082150965211545L;

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
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;


}
