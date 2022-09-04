package com.yuanerya.userservice.service.impl;

import cn.yuanerya.feign.clients.FocusClient;
import cn.yuanerya.feign.clients.QuestionClient;
import cn.yuanerya.feign.common.api.ApiErrorCode;
import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.LoginDTO;
import cn.yuanerya.feign.model.dto.RegisterDTO;
import cn.yuanerya.feign.model.entity.*;
import cn.yuanerya.feign.model.vo.FootPrintVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yuanerya.feign.common.exception.ApiAsserts;
import cn.yuanerya.feign.jwt.JwtUtil;
import com.yuanerya.userservice.mapper.YeUserMapper;

import com.yuanerya.userservice.service.IYeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class IYeUserServiceImpl extends ServiceImpl<YeUserMapper, YeUser> implements IYeUserService {
    @Autowired
    private YeUserMapper yeUserMapper;
    @Resource
    private QuestionClient questionClient;
    @Resource
    private FocusClient focusClient;

    /**
     * 注册
     *
     * @param dto 前端来的信息
     * @return 将生成的用户信息返回给前端
     */
    @Override
    public ApiResult register(RegisterDTO dto) {
        //用户名和邮箱要与数据库中的已经存在的数据进行比对查重
        LambdaQueryWrapper<YeUser> lqw = new LambdaQueryWrapper<>();
        //判断名字和邮箱
        lqw.eq(YeUser::getUsername, dto.getName()).or().eq(YeUser::getEmail, dto.getEmail());
        YeUser yeUser = baseMapper.selectOne(lqw);
        if (yeUser != null) {
            return ApiResult.failed("账号或邮箱已存在");
        }
        YeUser registerUser = YeUser.builder()
                .username(dto.getName())
                .alias(dto.getAlias())
                .password(dto.getPass())
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        yeUserMapper.insert(registerUser);
        return ApiResult.success(registerUser);
    }

    /**
     * 用户登录
     *
     * @param dto 来自前端的登录的账号和密码
     * @return 返回生成的TOKEN
     */

    @Override
    public String login(LoginDTO dto) {
        String token =null;
        try {
            LambdaQueryWrapper<YeUser> lqw = new LambdaQueryWrapper<>();
            lqw.eq(YeUser::getUsername, dto.getName());
            YeUser yeUser = baseMapper.selectOne(lqw);
            if (!yeUser.getPassword().equals(dto.getPass())) {
                throw new Exception("密码错误，请输入正确的密码！");
            }
            token = JwtUtil.generateToken(yeUser.getUsername());
        } catch (Exception e) {
            return null;
        }
        return "Authorization:" + token;
    }


    /**
     * 根据用户名来进行查询
     *
     * @param username 传来的用户名
     * @return 返回符合要求的用户
     */
    @Override
    public YeUser getYeUserByUsername(String username) {
        return yeUserMapper.selectOne(new LambdaQueryWrapper<YeUser>().eq(YeUser::getUsername, username));
    }

    /**
     * 核验点赞和关注数目，返回用户信息
     * @param username
     * @return
     */
    @Override
    public YeUser getAndCheckUserByUsername(String username) {
        YeUser user=getYeUserByUsername(username);
        Integer fans_num=focusClient.checkNum(user.getId()).getData();
        if(fans_num!=user.getFansNum()){
            user.setFansNum(fans_num);
            yeUserMapper.updateById(user);
        }
        return user;
    }

    /**
     * 用户可以获取自己的所有问题、所有回答、所有评论。
     *
     * @param userName 获取到用户的ID
     * @return
     */
    @Override
    public FootPrintVO getFootprint(String userName) {
        FootPrintVO footprint = new FootPrintVO();
        footprint = questionClient.getUserAll(userName).getData();
        return footprint;
    }

    /**
     * 关注
     * @param user_id
     * @param focused_id
     * @return
     */
    @Override
    public ApiResult<Integer> tofocus(String user_id, String focused_id) {
        YeFocus foucs = focusClient.add(user_id, focused_id).getData();
        YeUser user = yeUserMapper.selectById(focused_id);
        if (foucs == null) {
            return ApiResult.failed("当前对方粉丝数目："+user.getFansNum()+"请勿重复关注");
        } else {
            user.setFansNum(user.getFansNum() + 1);
            yeUserMapper.updateById(user);
            return ApiResult.success(user.getFansNum(), "关注成功");
        }
    }

    /**
     * 取消关注
     * @param user_id
     * @param focused_id
     * @return
     */
    @Override
    public ApiResult<Integer> removeFocus(String user_id, String focused_id) {
        YeFocus foucs = focusClient.removeFocus(user_id, focused_id).getData();
        YeUser user = yeUserMapper.selectById(focused_id);
        if(foucs != null) {
            user.setFansNum(user.getFansNum() -1);
            yeUserMapper.updateById(user);
            return ApiResult.success(user.getFansNum(),"成功取消关注");
        }else{
            return ApiResult.failed("当前对方粉丝数目："+user.getFansNum()+"您并未关注此人");
        }

    }
}
