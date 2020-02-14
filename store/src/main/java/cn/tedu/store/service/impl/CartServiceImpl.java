package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.service.CartService;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.ProductNotFoundException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层实现类
 * @author 原来你是光啊！
 *
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private ProductService productService;

	@Override
	public void addToCart(Integer uid, Integer pid, Integer amout, String usernaem) {
		//创建当前时间对象
		Date now=new Date();
		//根据uid和pid查询购物车数据
		Cart result=findByUidAndPid(uid, pid);
		//判断结果是否为null
		if(result==null) {
			//是：表示该用户未将该商品添加到购物车，则需要插入购物车数据
			//调用productService.getByid()方法查询商品数据
			Product product=productService.getById(pid);
			//创建Cart对象
			Cart cart=new Cart();
			//补全数据：uid，pid，num，price(从查询结果获取)
			cart.setUid(uid);
			cart.setPid(pid);
			cart.setNum(amout);
			cart.setPrice(product.getPrice());
			//补全数据：4个日志
			cart.setCreatedUser(usernaem);
			cart.setCreatedTime(now);
			cart.setModifiedUser(usernaem);
			cart.setModifiedTime(now);
			//调用insert(cart)执行插入
			insert(cart);
		}else {
			//否：表示用户已将商品添加到购物车，则需要修改购物车数据
			//从查询结果中得到cid
			Integer cid=result.getCid();
			//从查询结果中得到num，与参数amout相加，得到新的数量
			Integer num=result.getNum()+amout;
			//调用updateNumByCid(cid,num,modifiedUser,modifiedTime)方法执行修改
			updateNumByCid(cid, num, usernaem, now);
		}
	}

	
	@Override
	public List<CartVO> getVOByUid(Integer uid) {
		List<CartVO>list=findVOByUid(uid);
		return list;
	}
	
	@Override
	public List<CartVO> getVOByCids(Integer[] cids,Integer uid) {
		List<CartVO>carts=findVOByCids(cids);
		//遍历查询结果，判断集合中袁术是否归属当前用户，不是则移除
		Iterator<CartVO> it=carts.iterator();
		while(it.hasNext()) {
			CartVO cart=it.next();
			if(!cart.getUid().equals(uid)) {
				it.remove();
			}
		}
		
		return carts;
	}
	
	

	private void insert(Cart cart) {
		Integer rows=cartMapper.insert(cart);
		if(rows!=1) {
			throw new InsertException("购物车插入数据失败");
		}
	}

	private void updateNumByCid(
			@Param("cid")Integer cid,
			@Param("num")Integer num,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime) {
		Integer rows=cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
		if(rows!=1) {
			throw new UpdateException("修改购物车数据失败");
		}
	}

	private Cart findByUidAndPid(Integer uid,Integer pid) {
		return cartMapper.findByUidAndPid(uid, pid);
	}


	private List<CartVO> findVOByUid(Integer uid) {
		List<CartVO>list=cartMapper.findVOByUid(uid);
		return list;
	}


	private List<CartVO> findVOByCids(Integer [] cids){
		List<CartVO>list=cartMapper.findVOByCids(cids);
		return list;
	}





}
