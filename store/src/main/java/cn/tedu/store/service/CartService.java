package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.vo.CartVO;

/**
 * 处理商品数据的业务层接口
 * @author 原来你是光啊！
 *
 */
public interface CartService {

	void addToCart(Integer uid,Integer pid,Integer amout,String username);

	List<CartVO> getVOByUid(Integer uid);
	
	List<CartVO> getVOByCids(Integer [] cids,Integer uid);
}
