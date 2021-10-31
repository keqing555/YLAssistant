package com.psi.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psi.springboot.mappers.*;
import com.psi.springboot.pojo.*;
import com.psi.springboot.service.OrderService;
import com.psi.springboot.util.MessageConstant;
import com.psi.springboot.util.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrdersettingMapper ordersettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public Result submitOrder(Map<String, Object> paramsMap) {
        Result result = new Result();
        //获取传参
        String idCard = (String) paramsMap.get("idCard");
        String name = (String) paramsMap.get("name");
        String sex = (String) paramsMap.get("sex");
        String telephone = (String) paramsMap.get("telephone");
        //日期转为LocalDate
        LocalDate orderDate = LocalDate.parse((String) paramsMap.get("orderDate"));
        Integer setmealId = Integer.parseInt((String) paramsMap.get("setmealId"));
        try {
            //判断该手机号是否已注册
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phoneNumber", telephone);
            Member member = memberMapper.selectOne(queryWrapper);
            if (member != null) {
                //已注册
                QueryWrapper<Order> queryWrapper1 = new QueryWrapper<>();
                //从预约日期及其以后的排程
                //  queryWrapper1.ge("orderDate", orderDate);//ge：大于等于；gt：大于
                //从今天开始第二天的排程
                queryWrapper1.gt("orderDate", new Date());
                List<Order> orderList = orderMapper.selectList(queryWrapper1);
                for (Order order : orderList) {
                    if (order.getSetmealId() == setmealId) {
                        //预约过
                        result.setFlag(false);
                        result.setMessage(MessageConstant.HAS_ORDERED);
                        return result;
                    }
                }
            } else {
                //未注册，自动注册
                Member newMember = new Member();
                newMember.setName(name);
                newMember.setSex(sex);
                newMember.setIdcard(idCard);
                newMember.setPhonenumber(telephone);
                newMember.setRegtime(LocalDate.now());
                memberMapper.insert(newMember);
            }
            //判断预约人数是否已满，或者是否有预约排程
            QueryWrapper<Ordersetting> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("orderDate", orderDate);
            Ordersetting ordersetting = ordersettingMapper.selectOne(queryWrapper1);
            if (ordersetting != null) {
                if (ordersetting.getNumber() <= ordersetting.getReservations()) {
                    //预约已满
                    result.setFlag(false);
                    result.setMessage(MessageConstant.ORDER_FULL);
                    return result;
                }
            } else {
                //没有排程
                result.setFlag(false);
                result.setMessage(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
                return result;
            }
            //开始预约
            Order order = new Order();
            //根据手机号再次查询-新增-的用户，以获取id
            order.setMemberId(memberMapper.selectOne(queryWrapper).getId());
            order.setOrderdate(orderDate);
            order.setOrderstatus("未就诊");
            order.setOrdertype("微信小程序");
            order.setSetmealId(setmealId);
            //插入预约数据
            orderMapper.insert(order);
            //预约人数 +1
            ordersetting.setReservations(ordersetting.getReservations() + 1);
            ordersettingMapper.updateById(ordersetting);
            result.setFlag(true);
            result.setMessage(MessageConstant.ORDER_SUCCESS);
            result.setData(order.getId());//返回预约订单id
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("提交预约失败，请稍后再试");
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result findOrderById(int id) {
        Order order = orderMapper.selectById(id);
        //查询 member
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", order.getMemberId());
        Member member = memberMapper.selectOne(queryWrapper);
        //查询 setmeal
        QueryWrapper<Setmeal> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", order.getSetmealId());
        Setmeal setmeal = setmealMapper.selectOne(queryWrapper1);
        Map<String, String> orderInfo = new HashMap<>();
        orderInfo.put("member", member.getName());
        orderInfo.put("setmeal", setmeal.getName());
        orderInfo.put("orderDate", order.getOrderdate().toString());
        orderInfo.put("orderType", order.getOrdertype());
        //返回数据
        Result result = new Result();
        result.setData(orderInfo);
        return result;
    }
}
