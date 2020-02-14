package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.DistrictService;
/**
 * 处理省市区的业务层实现类
 * @author 原来你是光啊！
 *
 */
@Service
public class DistrictServiceImpl implements DistrictService{

	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	public List<District> getByParent(String parent) {
		//调用持久层对象的查询，得到列表
		List<District> lists=districtMapper.findByParent(parent);
		//遍历查询到的列表
		for (District dis : lists) {
			//将列表中id和parent设置为null
			dis.setId(null);
			dis.setParent(null);
		}
		//返回列表
		return lists;
	}

	@Override
	public String getNameByCode(String code) {
		return districtMapper.findNameByCode(code);
	}

	
}
