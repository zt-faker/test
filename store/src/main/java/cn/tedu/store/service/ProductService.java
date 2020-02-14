package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Product;

/**
 * 处理商品数据的业务层接口
 * @author 原来你是光啊！
 *
 */
public interface ProductService {
	
	/**
	 * 查询热销商品列表
	 * @return
	 */
	List<Product> getHotList();
	
	
	/**
	 * 根据id查询商品数据
	 * @param id
	 * @return
	 */
	Product getById(Integer id);
	
	
}
