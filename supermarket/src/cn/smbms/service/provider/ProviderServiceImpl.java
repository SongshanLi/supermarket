package cn.smbms.service.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.pojo.Provider;
@Component
public class ProviderServiceImpl implements ProviderService {
	@Autowired
	private ProviderDao providerDao;
	@Autowired
	private BillDao  billDao;

	@Override
	public boolean add(Provider provider) {
		int i = -1;
		try {
			i = providerDao.add( provider);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean flag = false;
		if(i>0)
			flag = true;
		return flag;
	}

	@Override
	public List<Provider> getProviderList(String proName,String proCode) {
		List<Provider> list = null;
		try {
			list = providerDao.getProviderList(proName, proCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
	 * 若订单表中无该供应商的订单数据，则可以删除
	 * 若有该供应商的订单数据，则不可以删除
	 * 返回值billCount
	 * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
	 * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
	 * 
	 * ---判断
	 * 如果billCount = -1 失败
	 * 若billCount >= 0 成功
	 */
	@Override
	public int deleteProviderById(String delId) {
		int i=0;
		int d=0;
		try {
			i = billDao.getBillCountByProviderId(delId);
			if(i==0) {
				d=providerDao.deleteProviderById(delId);
				if(d>0) {
					return 0;
				}else {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public Provider getProviderById(String id) {
		Provider p = null ;
		try {
			p = providerDao.getProviderById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public boolean modify(Provider provider) {
		boolean flag = false;
		try {
			if(providerDao.modify(provider) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
