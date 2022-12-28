package org.jeecg.modules.factor.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息
 * @Author: jeecg-boot
 * @Date:   2022-12-26
 * @Version: V1.0
 */
@Data
@TableName("sal_factor")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sal_factor对象", description="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息")
public class SalFactor implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**编码*/
	@Excel(name = "编码", width = 15)
    @ApiModelProperty(value = "编码")
    private java.lang.String code;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**种类*/
	@Excel(name = "种类", width = 15, dicCode = "salary_catalog")
	@Dict(dicCode = "salary_catalog")
    @ApiModelProperty(value = "种类")
    private java.lang.String catalog;
	/**增减项*/
	@Excel(name = "增减项", width = 15, dicCode = "salary_effect")
	@Dict(dicCode = "salary_effect")
    @ApiModelProperty(value = "增减项")
    private java.lang.String effect;
}
