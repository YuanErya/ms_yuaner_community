package com.yuanerya.starservice.controller;

import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.entity.YeStar;
import com.yuanerya.starservice.service.IYeStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/star")

public class YeStarController {
    @Autowired
    private IYeStarService iYeStarService;

    /**
     * 建立点赞关系
     *
     * @param user_id
     * @param answer_id
     * @return
     */
    @PostMapping("/add/{user_id}/{answer_id}")
    public ApiResult<YeStar> add(@PathVariable("user_id") String user_id,
                                  @PathVariable("answer_id") String answer_id) {
        YeStar yeStar = iYeStarService.add(user_id, answer_id);
        if (yeStar != null) {
            return ApiResult.success(yeStar);
        } else {
            return ApiResult.failed("请勿重复点赞");
        }
    }


    /**
     * 取消点赞
     *
     * @param user_id
     * @param answer_id
     * @return
     */
    @DeleteMapping("/removeStar/{user_id}/{answer_id}")
    public ApiResult<List<YeStar>> removeStar(@PathVariable("user_id") String user_id,
                                             @PathVariable("answer_id") String answer_id) {
        List<YeStar> yeStar= iYeStarService.removeStar(user_id, answer_id);
        if (yeStar != null) {
            return ApiResult.success(yeStar, "取消点赞成功");
        } else {
            return ApiResult.failed("查询到从未点赞");
        }
    }
}
