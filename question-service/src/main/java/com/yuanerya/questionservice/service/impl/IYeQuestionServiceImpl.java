package com.yuanerya.questionservice.service.impl;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.CreateQuestionDTO;
import cn.yuanerya.feign.model.entity.YeQuestion;
import cn.yuanerya.feign.model.entity.YeUser;
import cn.yuanerya.feign.model.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;

import com.yuanerya.questionservice.mapper.YeAnswerMapper;
import com.yuanerya.questionservice.mapper.YeCommentMapper;
import com.yuanerya.questionservice.mapper.YeQuestionMapper;
import com.yuanerya.questionservice.service.IYeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class IYeQuestionServiceImpl extends ServiceImpl<YeQuestionMapper, YeQuestion> implements IYeQuestionService {

    @Autowired
    private YeQuestionMapper yeQuestionMapper;
    @Autowired
    private YeAnswerMapper yeAnswerMapper;
    @Autowired
    private YeCommentMapper yeCommentMapper;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<QuestionVO> getPage(Integer pageNo, Integer pageSize){
        Page page=new Page(pageNo,pageSize);
        yeQuestionMapper.getPage(page);
        return page;
    }

    /**
     * 创建一个新的问题
     * @param dto
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)//用于保证数据库的同步
    public YeQuestion create(CreateQuestionDTO dto, YeUser user) {
//在问题的数据表中插入数据
        YeQuestion yq = YeQuestion.builder()
                .userId(user.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(yq);
        return yq;
    }

    /**
     * 删除自己的问题
     * @param question_id 传入的问题的id
     * @param user_id
     * @return
     */
    @Override
    public ApiResult delete(String question_id, String user_id) {
        try {
            if(yeQuestionMapper.selectById(question_id).getUserId().equals(user_id)){
                //检验操作者
                yeQuestionMapper.deleteById(question_id);
                yeAnswerMapper.deleteByQuestionId(question_id);
                yeCommentMapper.deleteByQuestionId(question_id);}
            else {
                return ApiResult.failed("非法操作，您只能删除你自己发布的问题！");
            }

    }catch (Exception e){
            return ApiResult.failed("操作失败");
        }
        return ApiResult.success("操作成功,删除了："+question_id);
    }

    /**
     * 跟新自己的问题
     * @param question_id
     * @param user_id
     * @param dto
     * @return
     */
    @Override
    public ApiResult checkAndUpdate(String question_id, String user_id, CreateQuestionDTO dto) {
        try {
            if(yeQuestionMapper.selectById(question_id).getUserId().equals(user_id)) {
                //检验操作者
                YeQuestion question=yeQuestionMapper.selectById(question_id);
                question.setContent(dto.getContent());
                question.setTitle(dto.getTitle());
                question.setModifyTime(new Date());
                yeQuestionMapper.updateById(question);
            }
            else{
                return ApiResult.failed("非法操作，您只能修改你自己发布的问题！");
            }
        }catch (Exception e){
            return ApiResult.failed("操作失败");
        }
        return ApiResult.success("修改成功，修改的问题为：" + question_id);
    }
    }

