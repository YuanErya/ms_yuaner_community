package com.yuanerya.focusservice.service.impl;

import cn.yuanerya.feign.model.entity.YeFocus;
import cn.yuanerya.feign.model.entity.YeUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuanerya.focusservice.mapper.YeFocusMapper;
import com.yuanerya.focusservice.service.IYeFocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IYeFocusServiceImpl extends ServiceImpl<YeFocusMapper, YeFocus> implements IYeFocusService {
    @Autowired
    private YeFocusMapper yeFocusMapper;

    /**
     * 建立关注
     *
     * @param user_id
     * @param focused_id
     * @return
     */
    @Override
    public YeFocus add(String user_id, String focused_id) {
        LambdaQueryWrapper<YeFocus> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YeFocus::getFocusedId, focused_id).eq(YeFocus::getUserId, user_id);
        YeFocus yeFocus = yeFocusMapper.selectOne(lqw);
        //上方代码是在校验当前关注是否已经被创建过
        if (yeFocus == null) {
            YeFocus focus = YeFocus.builder()
                    .userId(user_id)
                    .focusedId(focused_id)
                    .createTime(new Date())
                    .build();
            yeFocusMapper.insert(focus);
            return focus;
        } else {
            return null;
        }
    }

    /**
     * 移除关注
     *
     * @param user_id
     * @param focused_id
     * @return
     */
    @Override
    public YeFocus removeFocus(String user_id, String focused_id) {
        LambdaQueryWrapper<YeFocus> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YeFocus::getFocusedId, focused_id).eq(YeFocus::getUserId, user_id);
        YeFocus yeFocus = yeFocusMapper.selectOne(lqw);
        //上方代码是在校验当前关注是否已经被创建过
        if (yeFocus == null) {
            return null;
        } else {
            yeFocusMapper.deleteById(yeFocus.getId());
            return yeFocus;
        }
    }
}
