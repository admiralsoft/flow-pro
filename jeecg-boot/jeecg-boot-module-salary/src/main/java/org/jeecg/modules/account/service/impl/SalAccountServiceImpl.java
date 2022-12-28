package org.jeecg.modules.account.service.impl;

import org.jeecg.modules.account.entity.SalAccount;
import org.jeecg.modules.account.entity.SalAccountItem;
import org.jeecg.modules.account.mapper.SalAccountItemMapper;
import org.jeecg.modules.account.mapper.SalAccountMapper;
import org.jeecg.modules.account.service.ISalAccountService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 薪资套账
 * @Author: jeecg-boot
 * @Date:   2022-12-27
 * @Version: V1.0
 */
@Service
public class SalAccountServiceImpl extends ServiceImpl<SalAccountMapper, SalAccount> implements ISalAccountService {

	@Autowired
	private SalAccountMapper salAccountMapper;
	@Autowired
	private SalAccountItemMapper salAccountItemMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(SalAccount salAccount, List<SalAccountItem> salAccountItemList) {
		salAccountMapper.insert(salAccount);
		if(salAccountItemList!=null && salAccountItemList.size()>0) {
			for(SalAccountItem entity:salAccountItemList) {
				//外键设置
				entity.setAccountId(salAccount.getId());
				salAccountItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(SalAccount salAccount,List<SalAccountItem> salAccountItemList) {
		salAccountMapper.updateById(salAccount);
		
		//1.先删除子表数据
		salAccountItemMapper.deleteByMainId(salAccount.getId());
		
		//2.子表数据重新插入
		if(salAccountItemList!=null && salAccountItemList.size()>0) {
			for(SalAccountItem entity:salAccountItemList) {
				//外键设置
				entity.setAccountId(salAccount.getId());
				salAccountItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		salAccountItemMapper.deleteByMainId(id);
		salAccountMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			salAccountItemMapper.deleteByMainId(id.toString());
			salAccountMapper.deleteById(id);
		}
	}

	@Override
	public void cloneAccount(String id) {

	}

}
