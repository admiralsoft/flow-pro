package org.jeecg.modules.account.service;

import org.jeecg.modules.account.entity.SalAccountItem;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 套账因子行
 * @Author: jeecg-boot
 * @Date:   2022-12-27
 * @Version: V1.0
 */
public interface ISalAccountItemService extends IService<SalAccountItem> {

	/**
	 * 套账标识查询因子行
	 * @param mainId
	 * @return
	 */
	List<SalAccountItem> selectByMainId(String mainId);

}
