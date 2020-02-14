package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmtryException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.UserService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{

	@Autowired
	private UserService service;

	//http://localhost:8080/users/reg?username=controller&password=123&gender=1&email=con@cn.com
	@RequestMapping("reg")
	public JsonResult<Void> reg(User user){
//		JsonResult<Void>jr=new JsonResult<Void>();
		service.reg(user);
//		jr.setState(1);
		return new JsonResult<Void>(OK);
	}

	//http://localhost:8080/users/login?username=zxc&password=123
	@RequestMapping("login")
	public JsonResult<User> login(String username,String password,HttpSession session){
		User data=service.login(username, password);
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		return new JsonResult<User>(OK,data);
	}

	//http://localhost:8080/users/password/change?oldPassword=123&newPassword=666
	@RequestMapping("password/change")
	public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
		//从参数session中取出uid和username
		Integer uid=getFromSessionUid(session);
		String username=getFromSessionUsername(session);
		//调用serviec的changePassword()方法执行修改密码
		service.changePassword(uid, oldPassword, newPassword, username);
		//返回结果
		return new JsonResult<Void>(OK);
	}
	
	//http://localhost:8080/users/info/show
	@GetMapping("info/show")
	public JsonResult<User> showInfo(HttpSession session){
		//从session获取uid，再调用serviec的showInfo()查询用户数据
		User data=service.showInfo(getFromSessionUid(session));
		return new JsonResult<User>(OK,data);
	}
	
	//http://localhost:8080/users/info/change?phone=1388888888&email=zhoutao@qq.com&gender=1
	@RequestMapping("info/change")
	public JsonResult<Void> changeInfo(User user,HttpSession session){
		//从session获取uid和username，再调用serviec的changeInfo()执行修改
		service.changeInfo(getFromSessionUid(session), getFromSessionUsername(session), user);
		return new JsonResult<Void>(OK);
	}
	
	/**
	 * 允许上传的文件大小的上限值，以字节为单位
	 */
	public static final long MAX_SIZE=101*1024;
	/**
	 * 允许上传的文件类型的集合
	 */
	public static final java.util.List<String>AVATAR_TYPES=new ArrayList<String>();
	static {
		AVATAR_TYPES.add("image/png");
		AVATAR_TYPES.add("image/gif");
		AVATAR_TYPES.add("image/bmp");
		AVATAR_TYPES.add("image/jpeg");
	}
	@PostMapping("avatar/change")
	public JsonResult<String> changeAvatar(MultipartFile file,HttpSession session){
		//日志
		System.err.println("UserController.changeAvatar()");
		
		//判断文件是否为空
		boolean isEmpty=file.isEmpty();
		if(isEmpty) {
			//上传文件为空，则抛出异常
			throw new FileEmtryException("上传失败！请选择您要上传的文件");
		}
		
		//获取文件大小
		long size=file.getSize();
		//判断文件大小是否超过限制
		if(size>MAX_SIZE) {
			throw new FileSizeException("上传失败！不允许上传超过"+(MAX_SIZE/1024)+"KB的文件");
		}
		
		//获取文件MIME类型
		String contentType=file.getContentType();
		//判断上传的文件类型是否符合：image/png,image/jpeg,image/bmp,image/gif
		if(!AVATAR_TYPES.contains(contentType)) {
			throw new FileTypeException("上传失败！仅允许上传以下"+AVATAR_TYPES+"类型的文件");
		}
		
		//获取原始文件名(客户端设备中的文件名)
		String originalFilename=file.getOriginalFilename();
		System.err.println("\tOriginalFilename:"+originalFilename);
		//将文件上传到哪个文件夹
		String parent=session.getServletContext().getRealPath("upload");
		System.err.println("\tupload path="+parent);
		//创建保存的上传文件的文件夹
		File dir=new File(parent);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		//保存上传的文件时使用的文件名
		String fileName=""+System.currentTimeMillis()+System.nanoTime();
		String suffix="";
		int beginIndex=originalFilename.lastIndexOf(".");
		if(beginIndex>=1) {
			suffix=originalFilename.substring(beginIndex);
		}
		
		String child=fileName+suffix;
		//将客户端端上传的文件保存到服务端
		File dest=new File(parent,child);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new FileUploadException("上传失败！您的文件状态异常");
		} catch (IOException e) {
			throw new FileUploadException("上传失败！您的文件读写异常");
		}
		
		//将保存的文件记录到数据库中
		String avatar="/upload/"+child;
		System.err.println("\tavatar path="+avatar);
		Integer uid=getFromSessionUid(session);
		String username=getFromSessionUsername(session);
		service.changeAvatar(uid, avatar, username);
		
		//返回
		return new JsonResult<String>(OK,avatar);
	}
	
}
