package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.AddressService;
import cn.tedu.store.service.DistrictService;
import cn.tedu.store.service.ex.AccessException;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 实现类
 * @author 原来你是光啊！
 *
 */
@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private DistrictService districtService;

	@Value("${project.address-max-count}")
	private int addressMaxCount;

	@Override
	public void addnew(Integer uid, String username, Address address) {
		// 根据uid统计该用户的收货地址数量
		Integer count = countByUid(uid);
		// 判断数量是否超出设置值(假设为3)
		if (count >= addressMaxCount) {
			// 是：抛出AddressCountLimitException
			throw new AddressCountLimitException(
					"增加收货地址失败！收货地址数量已经达到上限("+ addressMaxCount + ")！");
		}

		// 根据以上统计的数量是否为0，得到isDefault值
		Integer isDefault = count == 0 ? 1 : 0;
		// 创建当前时间对象now
		Date now = new Date();

		// 补全数据：省市区的名称
		String provinceCode = address.getProvinceCode();
		String provinceName = districtService.getNameByCode(provinceCode);
		address.setProvinceName(provinceName);
		String cityCode = address.getCityCode();
		String cityName = districtService.getNameByCode(cityCode);
		address.setCityName(cityName);
		String areaCode = address.getAreaCode();
		String areaName = districtService.getNameByCode(areaCode);
		address.setAreaName(areaName);
		// 补全数据：uid
		address.setUid(uid);
		// 补全数据：is_default
		address.setIsDefault(isDefault);
		// 补全数据：4项日志(username, now)
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);
		// 执行插入收货地址数据
		insert(address);
	}

	@Override
	public List<Address> getByUid(Integer uid) {
		List<Address> list = findByUid(uid);
		for (Address address : list) {
			address.setUid(null);
			address.setProvinceCode(null);
			address.setCityCode(null);
			address.setAreaCode(null);
			address.setIsDefault(null);
			address.setCreatedUser(null);
			address.setCreatedTime(null);
			address.setModifiedUser(null);
			address.setModifiedTime(null);
		}
		return list;
	}

	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {
		// 根据参数aid查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException(
					"设置默认收货地址失败！尝试访问的数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不一致
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessException(
					"设置默认收货地址失败！非法访问已经被拒绝！");
		}

		// 将该用户的所有收货地址设置为非默认
		updateNonDefaultByUid(uid);

		// 将指定的收货地址设置为默认
		updateDefaultByAid(aid, username, new Date());
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username) {
		// 根据参数aid查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException(
					"设置默认收货地址失败！尝试访问的数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不一致
		// 注意：对比Integer类型的值时，如果值的范围在 -128~127 之间，
		//使用==或!=或equals()均可，如果超出这个范围，必须使用equals()
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessException(
					"设置默认收货地址失败！非法访问已经被拒绝！");
		}

		// 执行删除
		deleteByAid(aid);

		// 判断查询结果(对应刚刚删除的数据)中的isDefault是否不为1
		if (result.getIsDefault() != 1) {
			return;
		}

		// 统计当前用户还有多少收货地址
		Integer count = countByUid(uid);
		// 判断统计结果是否为0
		if (count == 0) {
			return;
		}

		// 查询当前用户的最后修改的那一条收货地址
		Address address = findLastModifiedByUid(uid);
		// 从本次查询中取出数据的aid
		Integer lastModifiedAid = address.getAid();
		// 执行设置默认收货地址
		updateDefaultByAid(lastModifiedAid, username, new Date());
	}

	@Override
	public Address getByAid(Integer aid, Integer uid) {
		// 根据参数aid查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException(
					"查询收货地址失败！尝试访问的数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不一致
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessException(
					"查询收货地址失败！非法访问已经被拒绝！");
		}

		// 将查询结果中的某些属性设置为null
		result.setUid(null);
		result.setProvinceCode(null);
		result.setCityCode(null);
		result.setAreaCode(null);
		result.setIsDefault(null);
		result.setCreatedUser(null);
		result.setCreatedTime(null);
		result.setModifiedUser(null);
		result.setModifiedTime(null);

		// 返回查询结果
		return result;
	}

	/**
	 * 插入收货地址数据
	 * @param address 收货地址数据
	 */
	private void insert(Address address) {
		Integer rows = addressMapper.insert(address);
		if (rows != 1) {
			throw new InsertException(
					"插入收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 根据收货地址id删除数据
	 * @param aid 收货地址id
	 */
	private void deleteByAid(Integer aid) {
		Integer rows = addressMapper.deleteByAid(aid);
		if (rows != 1) {
			throw new DeleteException(
					"删除收货地址失败！删除收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 收货地址的id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime) {
		Integer rows = addressMapper.updateDefaultByAid(aid, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("设置默认收货地址时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 将某用户的收货地址全部设置为非默认
	 * @param uid 用户id
	 */
	private void updateNonDefaultByUid(Integer uid) {
		Integer rows = addressMapper.updateNonDefaultByUid(uid);
		if (rows < 1) {
			throw new UpdateException("设置默认收货地址时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 统计某用户的收货地址数据的数量
	 * @param uid 用户的id
	 * @return 该用户的收货地址数据的数量
	 */
	private Integer countByUid(Integer uid) {
		return addressMapper.countByUid(uid);
	}

	/**
	 * 根据收货地址id查询收货地址详情
	 * @param aid 收货地址id
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}

	/**
	 * 查询某用户最后修改的收货地址
	 * @param uid 用户的id
	 * @return 该用户最后修改的收货地址，如果该用户没有收货地址，则返回null
	 */
	private Address findLastModifiedByUid(Integer uid) {
		return addressMapper.findLastModifiedByUid(uid);
	}

	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}






}
