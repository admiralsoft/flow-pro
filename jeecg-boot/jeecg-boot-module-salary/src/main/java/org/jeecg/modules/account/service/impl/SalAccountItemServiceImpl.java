package org.jeecg.modules.account.service.impl;

import org.jeecg.modules.account.entity.SalAccountItem;
import org.jeecg.modules.account.mapper.SalAccountItemMapper;
import org.jeecg.modules.account.service.ISalAccountItemService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 套账因子行
 * @Author: jeecg-boot
 * @Date:   2022-12-27
 * @Version: V1.0
 */
@Service
public class SalAccountItemServiceImpl extends ServiceImpl<SalAccountItemMapper, SalAccountItem> implements ISalAccountItemService {
	
	@Autowired
	private SalAccountItemMapper salAccountItemMapper;
	
	@Override
	public List<SalAccountItem> selectByMainId(String mainId) {
		return salAccountItemMapper.selectByMainId(mainId);
	}
}
