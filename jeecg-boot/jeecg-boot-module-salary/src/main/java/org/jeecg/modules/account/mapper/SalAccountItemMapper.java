package org.jeecg.modules.account.mapper;

import java.util.List;
import org.jeecg.modules.account.entity.SalAccountItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 套账因子行
 * @Author: jeecg-boot
 * @Date:   2022-12-27
 * @Version: V1.0
 */
public interface SalAccountItemMapper extends BaseMapper<SalAccountItem> {

	/**
	 * 根据套账标识删除因子行
	 * @param mainId
	 * @return
	 */
	boolean deleteByMainId(@Param("mainId") String mainId);

	/**
	 * 根据套账标识查询因子行
	 * @param mainId
	 * @return
	 */
	List<SalAccountItem> selectByMainId(@Param("mainId") String mainId);
}
