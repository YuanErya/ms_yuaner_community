package cn.yuanerya.feign.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@TableName("ye_answer")
@AllArgsConstructor
@NoArgsConstructor
public class YeAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 回答内容
     */
    @NotBlank(message = "内容不可以为空")
    @TableField("`content`")
    private String content;

    /**
     * 回答者ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 问题ID
     */
    @TableField("question_id")
    private String questionId;
    /**
     * 点赞数目
     */
    @Builder.Default
    @TableField("star_num")
    private Integer starNum = 0;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;
}