package cn.yuanerya.feign.clients;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.entity.YeUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import static cn.yuanerya.feign.jwt.JwtUtil.HEADER_STRING;

@FeignClient("userservice")
public interface UserClient {
    @GetMapping("/user/checkUser")
    ApiResult<YeUser> checkUser(@RequestHeader(value = HEADER_STRING) String token);

    @GetMapping("/user/getUserById/{user_id}")
    ApiResult<YeUser> getUserById(@PathVariable("user_id")String user_id);
}
