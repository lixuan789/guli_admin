package com.lixuan.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lixuan.statistics.client.UcenterClient;
import com.lixuan.statistics.entity.Daily;
import com.lixuan.statistics.mapper.DailyMapper;
import com.lixuan.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-10-13
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
        //获取统计信息
        Integer registerNum = (Integer) ucenterClient.registerCount(day).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        //删除已存在的统计对象
        QueryWrapper<Daily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        Integer count = baseMapper.selectCount(dayQueryWrapper);
        if (count>0){//有则更新
            baseMapper.update(daily,dayQueryWrapper);
        }else {//没有就插入
            baseMapper.insert(daily);
        }
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<Daily> query = Wrappers.<Daily>query();
        query.select(type,"date_calculated");
        query.between("date_calculated",begin,end);
        List<Daily> list = baseMapper.selectList(query);

        ArrayList<Integer> data = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        for (Daily day:list){
            date.add(day.getDateCalculated());
            switch (type) {
                case "register_num":
                    data.add(day.getRegisterNum());
                    break;
                case "login_num":
                    data.add(day.getLoginNum());
                    break;
                case "video_view_num":
                    data.add(day.getVideoViewNum());
                    break;
                case "course_num":
                    data.add(day.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map=new HashMap<>();
        map.put("data",data);
        map.put("date",date);
        return map;
    }
}
