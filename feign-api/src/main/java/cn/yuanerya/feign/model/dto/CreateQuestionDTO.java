package cn.yuanerya.feign.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateQuestionDTO implements Serializable {
    private static final long serialVersionUID = -5957433707110390852L;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
}
