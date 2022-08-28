package com.yuanerya.userservice.service;

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
    YeUser register(RegisterDTO dto);

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
     * 根据用户的Id在各个表中查询发表的记录
     * @param userId 获取到用户的ID
     * @return 返回VO
     */
    FootPrintVO getFootprint(String userId);
}
