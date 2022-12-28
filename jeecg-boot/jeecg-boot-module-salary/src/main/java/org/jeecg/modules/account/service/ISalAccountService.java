package org.jeecg.modules.account.service;

import org.jeecg.modules.account.entity.SalAccountItem;
import org.jeecg.modules.account.entity.SalAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 薪资套账
 * @Author: jeecg-boot
 * @Date:   2022-12-27
 * @Version: V1.0
 */
public interface ISalAccountService extends IService<SalAccount> {

	/**
	 * 添加一对多
	 * @param salAccount
	 * @param salAccountItemList
	 */
	void saveMain(SalAccount salAccount, List<SalAccountItem> salAccountItemList) ;

	/**
	 * 修改一对多
	 * @param salAccount
	 * @param salAccountItemList
	 */
	void updateMain(SalAccount salAccount, List<SalAccountItem> salAccountItemList);
	
	/**
	 * 删除一对多
	 * @param id
	 */
	void delMain(String id);
	
	/**
	 * 批量删除一对多
	 * @param idList
	 */
	void delBatchMain(Collection<? extends Serializable> idList);

	/**
	 * 套账克隆
	 * @param id
	 */
	void cloneAccount(String id);
}
