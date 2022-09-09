package cn.yuanerya.feign.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ModifyUserDTO {
    @NotEmpty(message = "请输入新昵称")
    @Length(min = 2, max = 15, message = "长度在2-15")
    private String alias;

    @NotEmpty(message = "请输入简介")
    @Length(min = 2, max = 255, message = "长度在2-255")
    private String introduction;
}
