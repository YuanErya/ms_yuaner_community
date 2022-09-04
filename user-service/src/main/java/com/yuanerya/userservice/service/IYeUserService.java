package com.yuanerya.userservice.service;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.LoginDTO;
import cn.yuanerya.feign.model.dto.RegisterDTO;
import cn.yuanerya.feign.model.entity.YeUser;
import cn.yuanerya.feign.model.vo.FootPrintVO;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IYeUserService extends IService<YeUser> {

    /**
     * 注册功能的实现
     * @param dto
     * @return
     */
    ApiResult register(RegisterDTO dto);

    /**
     * 登录
     * @param dto
     * @return
     */
    String login(LoginDTO dto);

    /**
     * 根据用户名获取到用户
     * @param username
     * @return
     */
    YeUser getYeUserByUsername(String username);

    /**
     * 拉取用户信息，同时核验点赞数和关注数目是否为真
     * @param username
     * @return
     */

    YeUser getAndCheckUserByUsername(String username);

    /**
     * 根据用户的Id在各个表中查询发表的记录
     * @param userName 获取到用户的ID
     * @return 返回VO
     */
    FootPrintVO getFootprint(String userName);

    /**
     * 关注功能
     * @param user_id
     * @param focused_id
     * @return
     */
    ApiResult<Integer> tofocus(String user_id, String focused_id);

    /**
     * 取消关注
     * @param user_id
     * @param focused_id
     * @return
     */
    ApiResult<Integer> removeFocus(String user_id, String focused_id);
}
