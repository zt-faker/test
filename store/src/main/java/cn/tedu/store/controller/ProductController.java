package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.ProductService;
/**
 * 处理省市区的相关请求的控制器类
 * @author 原来你是光啊！
 *
 */
import cn.tedu.store.util.JsonResult;
@RequestMapping("products")
@RestController
public class ProductController extends BaseController {
	
	@Autowired
	private ProductService productService;
	
	// http://localhost:8080/products/hot
	@GetMapping("hot")
	public JsonResult<List<Product>> getHotList(){
		//调用业务对象获取数据
		List<Product>data=productService.getHotList();
		//返回OK与数据
		return new JsonResult<List<Product>>(OK,data);
	}
	
	// http://localhost:8080/products/10000002/details
	@GetMapping("{id}/details")
	public JsonResult<Product> getById(@PathVariable("id")Integer id){
		//调用业务对象获取数据
		Product data=productService.getById(id);
		//返回OK与数据
		return new JsonResult<>(OK,data);
	}
	
	
	
	
	

}
