package cn.yuanerya.feign.clients;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.entity.YeStar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient("starservice")
public interface StarClient {

    @PostMapping("/star/add/{user_id}/{answer_id}")
    ApiResult<YeStar> add(@PathVariable("user_id") String user_id,
                                 @PathVariable("answer_id") String answer_id);

    @DeleteMapping("/star/removeStar/{user_id}/{answer_id}")
    ApiResult<YeStar> removeStar(@PathVariable("user_id") String user_id,
                                         @PathVariable("answer_id") String answer_id);

}
