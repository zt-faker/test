package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据持久层接口
 * @author 原来你是光啊！
 *
 */
public interface CartMapper {
	
	Integer insert(Cart cart);
	
	Integer updateNumByCid(
			@Param("cid")Integer cid,
			@Param("num")Integer num,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	Cart findByUidAndPid(Integer uid,Integer pid);
	
	List<CartVO> findVOByUid(Integer uid);
		
	List<CartVO> findVOByCids(Integer [] cids);
	
	
}
