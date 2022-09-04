package cn.yuanerya.feign.clients;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.entity.YeFocus;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient("focusservice")
public interface FocusClient {
    @PostMapping("/focus/add/{user_id}/{focused_id}")
    ApiResult<YeFocus> add(@PathVariable("user_id") String user_id,
                           @PathVariable("focused_id") String focused_id);

    @DeleteMapping("/focus/removeFocus/{user_id}/{focused_id}")
    ApiResult<List<YeFocus>> removeFocus(@PathVariable("user_id") String user_id,
                                         @PathVariable("focused_id") String focused_id);

    @GetMapping("/focus/checkNum/{user_id}")
    ApiResult<Integer> checkNum(@PathVariable ("user_id") String user_id);
}
