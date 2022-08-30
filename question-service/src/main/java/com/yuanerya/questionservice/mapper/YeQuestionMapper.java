package com.yuanerya.questionservice.mapper;


import cn.yuanerya.feign.model.entity.YeQuestion;
import cn.yuanerya.feign.model.vo.QuestionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeQuestionMapper extends BaseMapper<YeQuestion> {


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Select("select * from ye_question limit #{pageNum},#{pageSize}")
    List<YeQuestion> selectPage(Integer pageNum, Integer pageSize);

}
