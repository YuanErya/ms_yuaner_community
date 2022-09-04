package com.yuanerya.starservice.service.impl;

import cn.yuanerya.feign.model.entity.YeFocus;
import cn.yuanerya.feign.model.entity.YeStar;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanerya.starservice.mapper.YeStarMapper;
import com.yuanerya.starservice.service.IYeStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IYeStarServiceImpl extends ServiceImpl<YeStarMapper,YeStar> implements IYeStarService {
    @Autowired
    private  YeStarMapper yeStarMapper;
    @Override
    public YeStar add(String user_id, String answer_id) {
        LambdaQueryWrapper<YeStar> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YeStar::getAnswerId, answer_id).eq(YeStar::getUserId, user_id);
        YeStar yeStar= yeStarMapper.selectOne(lqw);
        if (yeStar == null) {
            YeStar star = YeStar.builder()
                    .userId(user_id)
                    .answerId(answer_id)
                    .createTime(new Date())
                    .build();
            yeStarMapper.insert(star);
            return star;
        } else {
            return null;
        }
    }

    @Override
    public YeStar removeStar(String user_id, String answer_id) {
        LambdaQueryWrapper<YeStar> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YeStar::getAnswerId, answer_id).eq(YeStar::getUserId, user_id);
        YeStar yeStar= yeStarMapper.selectOne(lqw);
        //上方代码是在校验当前关注是否已经被创建过
        if (yeStar == null) {
            return null;
        } else {
            yeStarMapper.deleteById(yeStar.getId());
            return yeStar;
        }
    }


}
