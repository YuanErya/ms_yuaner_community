package cn.yuanerya.feign.clients;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.vo.FootPrintVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static cn.yuanerya.feign.jwt.JwtUtil.HEADER_STRING;

@FeignClient("questionservice")
public interface QuestionClient {
    @GetMapping("/question/getUserAll")
    ApiResult<FootPrintVO> getUserAll(@RequestHeader(value = HEADER_STRING) String token);

}
