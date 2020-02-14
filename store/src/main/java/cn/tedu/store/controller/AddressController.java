package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.AddressService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{

	
	@Autowired
	private AddressService addressService;
	
	// http://localhost:8080/addresses/addnew?receiver=我是你爸爸
	@RequestMapping("addnew")
	public JsonResult<Void> addnew(Address address,HttpSession session){
		Integer uid=getFromSessionUid(session);
		String username=getFromSessionUsername(session);
		addressService.addnew(uid, username, address);
		return new JsonResult<Void>(OK);
	}
	
	
	// http://localhost:8080/addresses/info?uid=20
	@GetMapping("info")
	public JsonResult<List<Address>> info(HttpSession session){
		Integer uid=getFromSessionUid(session);
		List<Address> list=addressService.getByUid(uid);
		return new JsonResult<List<Address>>(OK,list);
	}
	
	
	// http://localhost:8080/addresses/21/set_default
		@RequestMapping("{aid}/set_default")
		public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session) {
			Integer uid = getFromSessionUid(session);
			String username = getFromSessionUsername(session);
			addressService.setDefault(aid, uid, username);
			return new JsonResult<>(OK);
		}
	
		// http://localhost:8080/addresses/31/set_delete
		@RequestMapping("{aid}/set_delete")
		public JsonResult<Void> setDelete(@PathVariable("aid") Integer aid,HttpSession session) {
			Integer uid = getFromSessionUid(session);
			String username = getFromSessionUsername(session);
			addressService.delete(aid, uid, username);
			return new JsonResult<>(OK);
		}
		
		
		
	
	
	
	
	
	
}
