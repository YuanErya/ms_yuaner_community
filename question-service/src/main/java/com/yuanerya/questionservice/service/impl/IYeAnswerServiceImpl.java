package com.yuanerya.questionservice.service.impl;

import cn.yuanerya.feign.clients.StarClient;
import cn.yuanerya.feign.clients.UserClient;
import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.AnswerAndCommentDTO;
import cn.yuanerya.feign.model.entity.YeAnswer;
import cn.yuanerya.feign.model.entity.YeQuestion;
import cn.yuanerya.feign.model.entity.YeStar;
import cn.yuanerya.feign.model.entity.YeUser;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;

import com.yuanerya.questionservice.mapper.YeAnswerMapper;
import com.yuanerya.questionservice.mapper.YeCommentMapper;
import com.yuanerya.questionservice.service.IYeAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class IYeAnswerServiceImpl extends ServiceImpl<YeAnswerMapper, YeAnswer> implements IYeAnswerService {
    @Autowired
    private YeAnswerMapper yeAnswerMapper;
    @Autowired
    private YeCommentMapper yeCommentMapper;
    @Resource
    private StarClient starClient;
    @Resource
    private UserClient userClient;

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

    /**
     * 根据用户的ID查询该用户所发布的全部的回答
     * @return
     */

    @Override
    public List<YeAnswer> getMyAnswersByUserId(String user_id) {
        return yeAnswerMapper.selectList(
                new LambdaQueryWrapper<YeAnswer>().eq(YeAnswer::getUserId,user_id));
    }

    /**
     * 点赞
     * @param user_id
     * @param answer_id
     * @return
     */
    @Override
    public ApiResult<Integer> tostar(String user_id, String answer_id) {
        YeStar star=starClient.add(user_id, answer_id).getData();
        YeUser user =userClient.getUserById(user_id).getData();
        YeAnswer answer =yeAnswerMapper.selectById(answer_id);
        if(star==null){
            return ApiResult.failed("该回答获赞数目："+answer.getStarNum()+"请勿重复点赞");
        }else {
            answer.setStarNum(answer.getStarNum()+1);
            user.setStaredNum(user.getStaredNum()+1);
            yeAnswerMapper.updateById(answer);
            userClient.updataStarNum(user);
            return ApiResult.success(answer.getStarNum(), "点赞成功");
        }

    }

    /**
     * 取消点赞
     * @param user_id
     * @param answer_id
     * @return
     */
    @Override
    public ApiResult<Integer> removeStar(String user_id, String answer_id) {
        List<YeStar> star=starClient.removeStar(user_id, answer_id).getData();
        YeUser user =userClient.getUserById(user_id).getData();
        YeAnswer answer =yeAnswerMapper.selectById(answer_id);
        if(star!=null&&star.size() != 0) {
            answer.setStarNum(answer.getStarNum()-1);
            user.setStaredNum(user.getStaredNum()-1);
            yeAnswerMapper.updateById(answer);
            userClient.updataStarNum(user);
            return ApiResult.success(answer.getStarNum(), "取消点赞成功");
        }else{
            return ApiResult.failed("该回答获赞数目："+answer.getStarNum()+"您并未点赞此回答");
        }
    }
}
