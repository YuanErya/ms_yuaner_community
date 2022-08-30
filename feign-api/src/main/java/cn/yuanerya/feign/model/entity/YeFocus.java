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
@TableName("ye_focus")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class YeFocus {
    /**
     * 该条关注数据的ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 关注者ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 被关注者ID
     */
    @TableField("focused_id")
    private String focusedId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


}
