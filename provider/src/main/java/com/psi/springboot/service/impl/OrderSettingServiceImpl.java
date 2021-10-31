package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psi.springboot.pojo.Ordersetting;
import com.psi.springboot.mappers.OrdersettingMapper;
import com.psi.springboot.service.OrderSettingService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.POIUtils;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Override
    @Transactional
    public void uploadTempleate(List<String[]> list) {
        if (list != null && list.size() > 0) {
            try {
                for (String[] row : list) {
                    if (row == null || "".equals(row)) {
                        continue;
                    }
                    //List存储的每一个String[]，每一条都是一行数据，类型为Ordersetting
                    Ordersetting ordersetting = new Ordersetting();
                    //创建日期模板
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    //设置预约日期
                    LocalDate localDate = LocalDate.parse(row[0], dateTimeFormatter);
                    ordersetting.setOrderdate(localDate);
                    //设置预约人数
                    ordersetting.setNumber(Integer.parseInt(row[1]));
                    //创建查询对象
                    QueryWrapper<Ordersetting> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("orderDate", localDate);
                    Ordersetting ordersetting1 = ordersettingMapper.selectOne(queryWrapper);
                    if (ordersetting1 != null) {
                        //已经存在该日期的预约数据，则修改预约人数
                        ordersetting1.setNumber(Integer.parseInt(row[1]));
                        ordersettingMapper.updateById(ordersetting1);
                    } else {
                        //不存在，插入该预约信息
                        ordersettingMapper.insert(ordersetting);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    @Override
    public Result getDayInfoByDate(String date) {
        Result result = new Result();
        //前台传过来的日期为yyyy-MM，这里需要设置一个初始日期和截至日期
        String startDate = date + "-1";
        String endDate = date + "-31";
        try {
            //查询这个日期范围内的所有预约数据
            QueryWrapper<Ordersetting> queryWrapper = new QueryWrapper<>();
            queryWrapper.between("orderDate", startDate, endDate);
            //获取这个月的所有预约信息
            List<Ordersetting> list = ordersettingMapper.selectList(queryWrapper);
            List<Map<String, Integer>> dataList = new ArrayList<>();
            list.forEach(row -> {
                //row为查询出来的每条预约数据
                Map<String, Integer> map = new HashMap<>();
                //获取月份的天数页面才显示人数，？？？
                map.put("date", row.getOrderdate().getDayOfMonth());
                map.put("number", row.getNumber());
                map.put("reservations", row.getReservations());
                dataList.add(map);
            });
            result.setFlag(true);
            result.setData(dataList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_ORDER_FAIL);
        }
        return result;
    }

    @Override
    @Transactional
    public Result updatePersonCount(Ordersetting ordersetting) {
        Result result = new Result();
        try {
            QueryWrapper<Ordersetting> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("orderDate", ordersetting.getOrderdate());
            List<Ordersetting> list = ordersettingMapper.selectList(queryWrapper);
            if (list != null && list.size() > 0) {
                //前端传来的对象没有id，无法根据id修改
                //  ordersetting.setId(list.get(0).getId());
                ordersettingMapper.update(ordersetting, queryWrapper);
                result.setMessage("修改预约信息成功！");
            } else {
                ordersettingMapper.insert(ordersetting);
                result.setMessage("添加预约信息成功！");
            }
            result.setFlag(true);

        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("设置预约信息失败！");
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
