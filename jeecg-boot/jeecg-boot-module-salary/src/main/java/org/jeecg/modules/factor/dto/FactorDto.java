package org.jeecg.modules.factor.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: FactorDto
 * PackageNmae: org.jeecg.modules.factor.dto
 * Description:
 *
 * @Author: 董建斌
 * @Create: 2022/12/26 - 21:55
 * @Version: v1.0
 */
@Data
public class FactorDto implements Serializable {
    private java.lang.String factor_id;
    private java.lang.String factor_code;
    private java.lang.String factor_name;
    private java.lang.String factor_catalog;
    private java.lang.String display;
    private int sequence;
}
