package cn.yuanerya.feign.model.dto;

import lombok.Data;

import java.io.Serializable;

//用为评论和回答都只是需要从客户端获取内容，所以公用
@Data
public class AnswerAndCommentDTO implements Serializable {


    /**
     * 内容
     */
    private String content;
}
