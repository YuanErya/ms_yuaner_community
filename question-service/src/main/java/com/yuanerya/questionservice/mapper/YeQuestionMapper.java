package com.yuanerya.questionservice.mapper;


import cn.yuanerya.feign.model.entity.YeQuestion;
import cn.yuanerya.feign.model.vo.QuestionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface YeQuestionMapper extends BaseMapper<YeQuestion> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<QuestionVO> getPage(@Param("page") Page<QuestionVO> page);

}
