package com.yuanerya.questionservice.service.impl;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.AnswerAndCommentDTO;
import cn.yuanerya.feign.model.entity.YeAnswer;
import cn.yuanerya.feign.model.entity.YeUser;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;

import com.yuanerya.questionservice.mapper.YeAnswerMapper;
import com.yuanerya.questionservice.mapper.YeCommentMapper;
import com.yuanerya.questionservice.service.IYeAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class IYeAnswerServiceImpl extends ServiceImpl<YeAnswerMapper, YeAnswer> implements IYeAnswerService {
    @Autowired
    private YeAnswerMapper yeAnswerMapper;
    @Autowired
    private YeCommentMapper yeCommentMapper;

    /**
     * 创建一个新的回答
     * @param dto
     * @param user
     * @param question_id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)//用于保证数据库的同步
    public YeAnswer answer(AnswerAndCommentDTO dto, YeUser user, String question_id) {
        YeAnswer answer = YeAnswer.builder()
                .userId(user.getId())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .questionId(question_id)
                .createTime(new Date())
                .build();
        this.baseMapper.insert(answer);
        return answer;
    }

    /**
     * 删除回答
     * @param answer_id
     * @param user_id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)//用于保证数据库的同步
    public ApiResult delete(String answer_id,String user_id) {
        try {
            if(yeAnswerMapper.selectById(answer_id).getUserId().equals(user_id)){
                //判断语句实在检验操作用户是否为回答者
                yeAnswerMapper.deleteById(answer_id);
                yeCommentMapper.deleteByAnswerId(answer_id);}
            else{
                return ApiResult.failed("非法操作，您只能删除你自己的回答！");
            }

        }catch (Exception e) {
            return ApiResult.failed("操作失败");
        }
        return ApiResult.success("操作成功，删除了："+answer_id);
    }

    /**
     * 更新回答
     * @param answer_id
     * @param user_id
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)//用于保证数据库的同步
    public ApiResult<String> checkAndUpdate(String answer_id, String user_id, AnswerAndCommentDTO dto) {
        try {
            if(yeAnswerMapper.selectById(answer_id).getUserId().equals(user_id)) {
                //检验操作者
                YeAnswer answer=yeAnswerMapper.selectById(answer_id);
                answer.setContent(dto.getContent());
                answer.setModifyTime(new Date());
                yeAnswerMapper.updateById(answer);
            }
            else{
                return ApiResult.failed("非法操作，您只能修改你自己的回答！");
            }
        }catch (Exception e){
            return ApiResult.failed("操作失败");
        }
        return ApiResult.success("修改成功，修改的回答为：" + answer_id);
    }
}
