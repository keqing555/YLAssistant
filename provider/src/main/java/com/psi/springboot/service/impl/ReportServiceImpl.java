package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psi.springboot.mappers.MemberMapper;
import com.psi.springboot.mappers.OrderMapper;
import com.psi.springboot.mappers.SetmealMapper;
import com.psi.springboot.pojo.Member;
import com.psi.springboot.pojo.Order;
import com.psi.springboot.pojo.Setmeal;
import com.psi.springboot.service.ReportService;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result getMemberReport() {
        Result result = new Result();
        Map<String, List<Object>> map = new HashMap<>();
        List<Object> months = new ArrayList<>();
        List<Object> memberCount = new ArrayList<>();
        //获取当前日历
        Calendar calendar = Calendar.getInstance();
        //时间前推12个月
        calendar.add(Calendar.YEAR, -1);
        //设置日期模板yyyy-MM
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (int t = 1; t <= 12; t++) {
            calendar.add(Calendar.MONTH, 1);
            //获取格式化后的日期字符串
            String format = sdf.format(calendar.getTime());
            months.add(format);//添加到list
            String startTime = format + "-1";//该月的开始时间
            String endTime = format + "-31";//该月的结束时间
            //获取近12个月的会员注册数量
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("regTime", startTime);
            queryWrapper.le("regTime", endTime);
            Integer count = memberMapper.selectCount(queryWrapper);
            memberCount.add(count);
        }
        map.put("months", months);
        map.put("memberCount", memberCount);
        result.setData(map);
        return result;
    }

    @Override
    public Result getSetmealReport() {
        Map<String, List<Object>> map = new HashMap<>();
        List<Object> setmealNames = new ArrayList<>();
        List<Object> setmealCount = new ArrayList<>();

        //获取所有套餐id和名称
        List<Setmeal> setmealList = setmealMapper.selectList(null);
        for (Setmeal setmeal : setmealList) {
            //获取套餐id
            Integer setmealId = setmeal.getId();
            //获取套餐名称
            String name = setmeal.getName();
            setmealNames.add(name);//添加套餐名称
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("setmeal_id", setmealId);
            //添加套餐订单数量
            Integer num = orderMapper.selectCount(queryWrapper);
            //每个list里放不同的map2，所以每次都要new一个
            Map<String, Object> map2 = new HashMap<>();
            map2.put("name", name);
            map2.put("value", num);
            //添加套餐预约数量
            setmealCount.add(map2);
        }
        map.put("setmealNames", setmealNames);
        map.put("setmealCount", setmealCount);
        Result result = new Result();
        result.setData(map);
        return result;
    }

    @Override
    public Result getBusinessReportData() {
        Map<String, Object> map = new HashMap<>();
        /**
         * 会员数据统计
         */
        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("reportDate", sdf.format(new Date()));
        //获取今日新增会员数量
        QueryWrapper<Member> queryDay = new QueryWrapper<>();
        queryDay.eq("regTime", sdf.format(new Date()));
        map.put("todayNewMember", memberMapper.selectCount(queryDay));
        //获取总会员数量
        map.put("totalMember", memberMapper.selectCount(null));
        /**
         * 获取本周新增会员数量
         */
        Calendar calendar = Calendar.getInstance();
        //把日期调为本周一
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //获取本周一的日期字符串
        String monday = sdf.format(calendar.getTime());
        //把日期调为本周日
        calendar.add(Calendar.WEEK_OF_MONTH, 1);//加一周
        calendar.add(Calendar.DAY_OF_WEEK, -1);//减一天
        //获取本周日的日期字符串
        String sunday = sdf.format(calendar.getTime());
        QueryWrapper<Member> queryWeek = new QueryWrapper<>();
        queryWeek.ge("regTime", monday);
        queryWeek.le("regTime", sunday);
        map.put("thisWeekNewMember", memberMapper.selectCount(queryWeek));
        /**
         * 获取本月新增会员数
         */
        Calendar calendar2 = Calendar.getInstance();
        //获取本月第一天
        calendar2.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = sdf.format(calendar2.getTime());
        //获取本月最后一天
        calendar2.add(Calendar.MONTH, 1);
        calendar2.add(Calendar.DAY_OF_MONTH, -1);
        String lastDay = sdf.format(calendar2.getTime());
        QueryWrapper<Member> queryMonth = new QueryWrapper<>();
        queryMonth.between("regTime", firstDay, lastDay);
        map.put("thisMonthNewMember", memberMapper.selectCount(queryMonth));
        /**
         * 预约到诊数据统计
         */
        //今日预约数
        QueryWrapper<Order> queryDay2 = new QueryWrapper<>();
        queryDay2.eq("orderDate", new Date());
        map.put("todayOrderNumber", orderMapper.selectCount(queryDay2));
        //本周预约
        QueryWrapper<Order> queryWeek2 = new QueryWrapper<>();
        queryWeek2.between("orderDate", monday, sunday);
        map.put("thisWeekOrderNumber", orderMapper.selectCount(queryWeek2));
        //本月预约数
        QueryWrapper<Order> queryMonth2 = new QueryWrapper<>();
        queryMonth2.between("orderDate", firstDay, lastDay);
        map.put("thisMonthOrderNumber", orderMapper.selectCount(queryMonth2));
        /**
         * 热门套餐数据（前三）
         */
        map.put("hotSetmeal", orderMapper.getHotSetmeal());
        Result result = new Result();
        result.setData(map);
        return result;
    }
}
