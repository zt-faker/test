package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.service.ex.ProductNotFoundException;

/**
 * 处理商品数据的业务层实现类
 * @author 原来你是光啊！
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<Product> getHotList() {
		List<Product> list=findHotList();
		for (Product product : list) {
			product.setItemType(null);
			product.setSellPoint(null);
			product.setNum(null);
			product.setStatus(null);
			product.setPriotity(null);
			product.setCreatedUser(null);
			product.setCreatedTime(null);
			product.setModifiedUser(null);
			product.setModifiedTime(null);
		}
		return findHotList();
	}

	@Override
	public Product getById(Integer id) {
		Product product=findById(id);
		if(product==null) {
			throw new ProductNotFoundException("找不到该商品信息");
		}
		product.setPriotity(null);
		product.setCreatedUser(null);
		product.setCreatedTime(null);
		product.setModifiedUser(null);
		product.setModifiedTime(null);
		return product;
	}
	
	/**
	 * 查询热销商品列表
	 * @return
	 */
	private List<Product> findHotList(){
		return productMapper.findHotList();
	}
	/**
	 * 查询热销商品列表
	 * @return
	 */
	private Product findById(Integer id){
		return productMapper.findById(id);
	}

	

	






}
