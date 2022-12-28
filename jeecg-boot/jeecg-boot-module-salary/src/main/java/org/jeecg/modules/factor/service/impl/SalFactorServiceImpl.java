package org.jeecg.modules.factor.service.impl;

import org.jeecg.modules.factor.entity.SalFactor;
import org.jeecg.modules.factor.mapper.SalFactorMapper;
import org.jeecg.modules.factor.service.ISalFactorService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息
 * @Author: jeecg-boot
 * @Date:   2022-12-26
 * @Version: V1.0
 */
@Service
public class SalFactorServiceImpl extends ServiceImpl<SalFactorMapper, SalFactor> implements ISalFactorService {

}
