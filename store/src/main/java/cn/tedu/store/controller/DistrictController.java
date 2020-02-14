package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.DistrictService;
/**
 * 处理省市区的相关请求的控制器类
 * @author 原来你是光啊！
 *
 */
import cn.tedu.store.util.JsonResult;
@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController {
	
	@Autowired
	private DistrictService districtService;
	
	// http://localhost:8080/districts/?parent=86
	@GetMapping({"/",""})
	public JsonResult<List<District>> getParent(String parent){
		//调用业务对象获取数据
		List<District>data=districtService.getByParent(parent);
		//返回OK与数据
		return new JsonResult<List<District>>(OK,data);
	}
	
	
	
	
	

}
