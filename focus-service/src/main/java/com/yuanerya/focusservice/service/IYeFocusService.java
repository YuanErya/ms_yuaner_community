package com.yuanerya.focusservice.service;

import cn.yuanerya.feign.model.entity.YeFocus;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IYeFocusService extends IService<YeFocus> {

    YeFocus add(String user_id, String focused_id);

    YeFocus removeFocus(String user_id, String focused_id);
}
