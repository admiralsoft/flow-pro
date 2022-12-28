package org.jeecg.modules.factor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.factor.entity.SalFactor;

/**
 * @Description: 工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息
 * @Author: jeecg-boot
 * @Date:   2022-12-26
 * @Version: V1.0
 */
public interface SalFactorMapper extends BaseMapper<SalFactor> {

}
