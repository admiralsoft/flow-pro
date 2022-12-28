package org.jeecg.modules.account.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 套账因子行
 * @Author: jeecg-boot
 * @Date:   2022-12-27
 * @Version: V1.0
 */
@ApiModel(value="sal_account_item对象", description="套账因子行")
@Data
@TableName("sal_account_item")
public class SalAccountItem implements Serializable {
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
	/**因子标识*/
	@Excel(name = "因子标识", width = 15)
    @ApiModelProperty(value = "因子标识")
    private java.lang.String factorId;
	/**薪资因子*/
	@Excel(name = "薪资因子", width = 15)
    @ApiModelProperty(value = "薪资因子")
    private java.lang.String factorName;
	/**因子种类*/
	@Excel(name = "因子种类", width = 15, dicCode = "salary_catalog")
    @ApiModelProperty(value = "因子种类")
    private java.lang.String factorCatalog;
	/**工资条序号*/
	@Excel(name = "工资条序号", width = 15)
    @ApiModelProperty(value = "工资条序号")
    private java.lang.Integer sequence;
	/**工资条显示*/
	@Excel(name = "工资条显示", width = 15)
    @ApiModelProperty(value = "工资条显示")
    private java.lang.String display;
	/**基数*/
	@Excel(name = "基数", width = 15)
    @ApiModelProperty(value = "基数")
    private java.lang.Integer base;
	/**操作数a*/
	@Excel(name = "操作数a", width = 15, dictTable = "sal_factor", dicText = "name", dicCode = "code")
    @ApiModelProperty(value = "操作数a")
    private java.lang.String firstOprand;
	/**操作符*/
	@Excel(name = "操作符", width = 15, dicCode = "salary_operator")
    @ApiModelProperty(value = "操作符")
    private java.lang.String operator;
	/**操作数b*/
	@Excel(name = "操作数b", width = 15, dictTable = "sal_factor", dicText = "name", dicCode = "code")
    @ApiModelProperty(value = "操作数b")
    private java.lang.String secondOprand;
	/**系数*/
	@Excel(name = "系数", width = 15)
    @ApiModelProperty(value = "系数")
    private java.lang.Integer coefficient;
	/**计算精度*/
	@Excel(name = "计算精度", width = 15, dicCode = "salary_accuracy")
    @ApiModelProperty(value = "计算精度")
    private java.lang.String accuracy;
	/**套账标识*/
    @ApiModelProperty(value = "套账标识")
    private java.lang.String accountId;
}
