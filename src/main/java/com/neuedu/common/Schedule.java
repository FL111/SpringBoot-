package com.neuedu.common;

import com.neuedu.dao.OrderMapper;
import com.neuedu.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class Schedule {
    @Autowired
    OrderMapper orderMapper;
    @Scheduled(cron = "0 0 */1 * * ?")
    private void configureTasks(){
        List<Order> orderList=orderMapper.deleteOrderUnpaid();
        for(Order order:orderList){
            Calendar c=Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY,c.get(Calendar.HOUR_OF_DAY)-1);
            if (order.getCreateTime().before(c.getTime())){
                order.setStatus(0);
                orderMapper.updateByPrimaryKey(order);
            }
        }
    }
}
