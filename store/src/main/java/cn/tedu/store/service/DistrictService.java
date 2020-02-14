package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.District;
/**
 * 处理省市区的业务层接口
 * @author 原来你是光啊！
 *
 */
public interface DistrictService {

	/**
	 * 查询全国所有省/某省所有市/某市所有区的列表
	 * @param parent 父级单位的行政代号
	 * @return
	 */
	List<District> getByParent(String parent);
	
	/**
	 * 根据省/市/区/编号查询对应的省市区名
	 * @param code 省/市/区/编号
	 * @return 省市区名
	 */
	String getNameByCode(String code);
	
	
	
}
