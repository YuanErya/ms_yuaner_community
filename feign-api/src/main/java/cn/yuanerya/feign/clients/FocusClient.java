package cn.yuanerya.feign.clients;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.entity.YeFocus;
import cn.yuanerya.feign.model.vo.FootPrintVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import static cn.yuanerya.feign.jwt.JwtUtil.HEADER_STRING;

@FeignClient("focusservice")
public interface FocusClient {
    @PostMapping("/focus/add/{user_id}/{focused_id}")
    ApiResult<YeFocus> add(@PathVariable("user_id") String user_id,
                           @PathVariable("focused_id") String focused_id);

    @DeleteMapping("/focus/removeFocus/{user_id}/{focused_id}")
    ApiResult<YeFocus> removeFocus(@PathVariable("user_id") String user_id,
                                   @PathVariable("focused_id") String focused_id);
}
