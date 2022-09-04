package com.yuanerya.starservice.service;

import cn.yuanerya.feign.model.entity.YeStar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IYeStarService extends IService<YeStar> {

    YeStar add(String user_id, String answer_id);

    List<YeStar> removeStar(String user_id, String answer_id);

}
