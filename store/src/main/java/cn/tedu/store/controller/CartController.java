package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.service.CartService;
/**
 * 处理购物车的相关请求的控制器类
 * @author 原来你是光啊！
 *
 */
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;
/**
 * 处理购物车相关请求的控制器类
 */
@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

	@Autowired
	private CartService cartService;
	
	// http://localhost:8080/carts/add_to_cart?pid=10000005&amount=3
	@RequestMapping("add_to_cart")
	public JsonResult<Void> addToCart(
		Integer pid, Integer amount, HttpSession session) {
		Integer uid = getFromSessionUid(session);
		String username = getFromSessionUsername(session);
		cartService.addToCart(uid,pid, amount,username);
		return new JsonResult<>(OK);
	}
	
	// http://localhost:8080/carts/
	@GetMapping("")
	public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
		Integer uid = getFromSessionUid(session);
		List<CartVO> data = cartService.getVOByUid(uid);
		return new JsonResult<>(OK, data);
	}
	
	// http://localhost:8080/carts/get_by_cids?cids=6&cids=7&cids=8&cids=9&cids=10&cids=11&cids=12&cids=13&cids=14&cids=15
	@GetMapping("get_by_cids")
	public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
		Integer uid = getFromSessionUid(session);
		List<CartVO> data = cartService.getVOByCids(cids, uid);
		return new JsonResult<>(OK, data);
	}
	
}

