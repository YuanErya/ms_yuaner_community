package com.yuanerya.questionservice.mapper;


import cn.yuanerya.feign.model.entity.YeAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface YeAnswerMapper extends BaseMapper<YeAnswer> {
    /**
     * 根据问题的id删除回答
     * 主要是用于在删除问题的时候，携带删除该问题的回答和评论
     * @param question_id
     * @return
     */
    @Delete("DELETE FROM ye_answer WHERE question_id=#{question_id}")
    int deleteByQuestionId(@Param("question_id") String question_id);}
