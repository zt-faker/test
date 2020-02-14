package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Product;

/**
 * 处理省市区地址的持久层接口
 * @author 原来你是光啊！
 *
 */
public interface ProductMapper {
	
	/**
	 * 查询热销商品列表
	 * @return
	 */
	List<Product> findHotList();
	
	/**
	 * 根据id查询商品数据
	 * @param id
	 * @return
	 */
	Product findById(Integer id);
	
}
