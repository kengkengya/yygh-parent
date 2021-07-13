package com.atguigu.yygh.order.controller.api;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.model.order.OrderInfo;
import com.atguigu.yygh.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("预约下单")
@RestController
@RequestMapping("/api/order/prderInfo")
public class OrderApiController {

    @Autowired
    private OrderService orderService;



    @ApiOperation(value = "创建订单")
    @PostMapping("auth/submitOrder/{scheduleId}/{patientId}")
    public Result submitOrder(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable String scheduleId,
            @ApiParam(name = "patientId", value = "就诊人id", required = true)
            @PathVariable Long patientId) {
        System.out.println(scheduleId);
        System.out.println(patientId);
        Long order = orderService.saveOrder(scheduleId, patientId);
        return Result.ok();
    }

    @ApiOperation("根据订单id查询订单详情")
    @GetMapping("auth/getOrders/{orderId}")
    public Result getOrders(@PathVariable String orderId){
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        return Result.ok(orderInfo);
    }


}
