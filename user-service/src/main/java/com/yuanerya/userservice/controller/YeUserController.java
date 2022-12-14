package com.yuanerya.userservice.controller;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.jwt.JwtUtil;
import cn.yuanerya.feign.model.dto.LoginDTO;
import cn.yuanerya.feign.model.dto.ModifyUserDTO;
import cn.yuanerya.feign.model.dto.RegisterDTO;
import cn.yuanerya.feign.model.entity.YeUser;
import cn.yuanerya.feign.model.vo.FootPrintVO;
import com.yuanerya.userservice.service.IYeUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static cn.yuanerya.feign.jwt.JwtUtil.HEADER_STRING;
import static cn.yuanerya.feign.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/user")
public class YeUserController {
    @Resource
    private IYeUserService iYeUserService;

    /**
     * 注册
     *
     * @param dto
     * @return
     */
    @PostMapping("/register")
    public ApiResult register(@Valid @RequestBody RegisterDTO dto) {
        return iYeUserService.register(dto);
    }

    /**
     * @param dto 前端发送来的信息
     * @return 返回成功信息，并将生成的token返回给前端  taken是根据用户名加密生成的一段乱码
     */
    @PostMapping("/login")
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = iYeUserService.login(dto);
        if (token == null) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("token", token);
        return ApiResult.success(map, "登录成功");
    }

    /**
     * 进行token验证，验证成功后，通过Header获取到tokren,进行解析得到用户名
     * 根据UserName再到数据库中进行查询，获取到用户的全部信息
     *
     * @param userName
     * @return 用户信息
     */
    @GetMapping(value = "/info")
    public ApiResult<YeUser> getUser(@RequestHeader(value = USER_NAME) String userName) {
        YeUser user = iYeUserService.getAndCheckUserByUsername(userName);
        return ApiResult.success(user);
    }

    /**
     * 获取我所发布的问题，回答和评论
     *
     * @param userName 通过Header获取到tokren,进行解析得到用户名根据UserName再到数据库中进行查询，获取到用户ID
     * @return 返回vo
     */
    @GetMapping("/getFootprint")
    public ApiResult<FootPrintVO> getFootprint(@RequestHeader(value = USER_NAME) String userName) {

        return ApiResult.success(iYeUserService.getFootprint(userName));
    }

    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    @PutMapping("/modifyUser")
    public ApiResult modifyUser(@Valid @RequestBody ModifyUserDTO dto,
                                @RequestHeader(value = USER_NAME) String userName){
        String user_id = iYeUserService.getYeUserByUsername(userName).getId();
        return iYeUserService.modifyUser(user_id,dto);
    }


    /**
     * 注销登录
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

    /**
     * 校验用户的token是为为可用
     *
     * @param token
     * @return 校验成功则返回用户实体类
     */
    @GetMapping("/checkUser")
    public ApiResult<YeUser> checkUser(@RequestHeader(value = HEADER_STRING) String token) {
        String userName;
        YeUser user;
        try {
            userName = JwtUtil.parseToken(token);
            user = iYeUserService.getYeUserByUsername(userName);
        } catch (Exception e) {
            return ApiResult.failed("非法用户，校验失败");
        }
        return ApiResult.success(user, "校验身份成功");
    }

    /**
     * 根据用户ID查询到用户
     * 主要是用在question的远程调用，查询组装问题的分页查询所需要展示内容的那个新对象questionVO
     *
     * @param user_id
     * @return
     */

    @GetMapping("/getUserById/{user_id}")
    public ApiResult<YeUser> getUserById(@PathVariable("user_id") String user_id) {
        YeUser user = iYeUserService.getById(user_id);
        return ApiResult.success(user);
    }

    /**
     * 通过用户名获取 用户信息
     * @param user_name
     * @return
     */
    @GetMapping("/getUserByUserName/{user_name}")
    ApiResult<YeUser> getUserByUserName(@PathVariable("user_name")String user_name){
        YeUser user = iYeUserService.getYeUserByUsername(user_name);
        if(user!=null){
            return ApiResult.success(user);
        }
        return ApiResult.failed("查无此人");

    }

    /**
     * 添加关注
     * @param focused_id
     * @param userName
     * @return
     */
    @PutMapping("/tofocus/{focused_id}")
    public ApiResult<Integer> tofocus(@PathVariable("focused_id") String focused_id,
                                      @RequestHeader(value = USER_NAME) String userName) {
        String user_id = iYeUserService.getYeUserByUsername(userName).getId();
        return iYeUserService.tofocus(user_id, focused_id);
    }

    /**
     * 取消关注
     * @param focused_id
     * @param userName
     * @return
     */
    @DeleteMapping("/removeFocus/{focused_id}")
    ApiResult<Integer> removeFocus(@PathVariable("focused_id") String focused_id,
                                   @RequestHeader(value = USER_NAME) String userName){
        String user_id = iYeUserService.getYeUserByUsername(userName).getId();
        return iYeUserService.removeFocus(user_id, focused_id);
    }

    /**
     * 更新用户的获赞数
     * @param user
     * @return
     */
    @PutMapping("/updata/staredNum")
    ApiResult updataStarNum(@Valid @RequestBody YeUser user){
        iYeUserService.updateById(user);
        return ApiResult.success();
    }
}
