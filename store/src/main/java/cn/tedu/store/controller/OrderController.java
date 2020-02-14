package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.CartService;
import cn.tedu.store.service.OrderService;
/**
 * 处理购物车的相关请求的控制器类
 * @author 原来你是光啊！
 *
 */
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;
/**
 * 处理订单相关请求的控制器类
 */
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	
	// http://localhost:8080/orders/create?aid=24&cids=8&cids=9&cids=11
	@RequestMapping("create")
	public JsonResult<Order> create(Integer aid,Integer[]cids,HttpSession session){
		Integer uid=getFromSessionUid(session);
		String username=getFromSessionUsername(session);
		Order data=orderService.create(aid, cids, uid, username);
		return new JsonResult<>(OK,data);
	}
	
}

