package com.yuanerya.starservice.service;

import cn.yuanerya.feign.model.entity.YeStar;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IYeStarService extends IService<YeStar> {

    YeStar add(String user_id, String answer_id);

    YeStar removeStar(String user_id, String answer_id);

}
