package org.jeecg.modules.factor.service;

import org.jeecg.modules.factor.entity.SalFactor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息
 * @Author: jeecg-boot
 * @Date:   2022-12-26
 * @Version: V1.0
 */
public interface ISalFactorService extends IService<SalFactor> {

}
