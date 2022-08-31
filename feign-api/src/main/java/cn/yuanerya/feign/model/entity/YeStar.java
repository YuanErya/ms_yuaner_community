package cn.yuanerya.feign.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@TableName("ye_star")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class YeStar {

    /**
     * 该条点赞数据的ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 点赞者ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 被点赞回答ID
     */
    @TableField("answer_id")
    private String answerId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


}
