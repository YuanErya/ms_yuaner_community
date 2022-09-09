package cn.yuanerya.feign.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateQuestionDTO implements Serializable {


    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
}
