package com.yuanerya.focusservice.controller;


import cn.yuanerya.feign.common.api.ApiResult;

import cn.yuanerya.feign.model.entity.YeFocus;
import com.yuanerya.focusservice.service.IYeFocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/focus")
public class YeFocusController {
    @Autowired
    private IYeFocusService iyeFocusService;

    /**
     * 建立关注
     *
     * @param user_id
     * @param focused_id
     * @return
     */
    @PostMapping("/add/{user_id}/{focused_id}")
    public ApiResult<YeFocus> add(@PathVariable("user_id") String user_id,
                                  @PathVariable("focused_id") String focused_id) {
        YeFocus yeFocus = iyeFocusService.add(user_id, focused_id);
        if (yeFocus != null) {
            return ApiResult.success(yeFocus);
        } else {
            return ApiResult.failed("请勿重复关注");
        }
    }

    /**
     * 取消关注
     *
     * @param user_id
     * @param focused_id
     * @return
     */
    @DeleteMapping("/removeFocus/{user_id}/{focused_id}")
    public ApiResult<YeFocus> removeFocus(@PathVariable("user_id") String user_id,
                                          @PathVariable("focused_id") String focused_id) {
        YeFocus yeFocus = iyeFocusService.removeFocus(user_id, focused_id);
        if (yeFocus != null) {
            return ApiResult.success(yeFocus, "取消关注成功");
        } else {
            return ApiResult.failed("查询到从未关注");
        }
    }

    @GetMapping("checkNum/{user_id}")
    public ApiResult<Integer> checkNum(@PathVariable ("user_id") String user_id){
        return ApiResult.success(iyeFocusService.checkNum(user_id));
    }
}
