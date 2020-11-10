package org.springblade.contract.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springblade.core.tool.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 接收/提供服务计划清单 实体类
 *
 * @author szw
 * @date : 2020-11-05 17:06:55
 */
@Getter
@Setter
@TableName("contract_performance")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ContractPerformance对象", description = "接收/提供服务计划清单")
public class ContractPerformanceEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 服务内容
	 */
	@NotBlank(message = "服务内容不能为空")
	@ApiModelProperty(value = "服务内容", required = true)
	private String name;
	/**
	 * 交易类别
	 */
	@NotBlank(message = "交易类别不能为空")
	@ApiModelProperty(value = "交易类别", required = true)
	private String type;
	/**
	 * 币种
	 */
	@NotBlank(message = "币种不能为空")
	@ApiModelProperty(value = "币种", required = true)
	private String currencyCategory;
	/**
	 * 计划缴纳时间
	 */
	@NotNull(message = "计划缴纳时间不能为空")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE)
	@ApiModelProperty(value = "计划缴纳时间", required = true)
	private Date planPayTime;
	/**
	 * 计划缴纳金额
	 */
	@NotNull(message = "计划缴纳金额不能为空")
	@ApiModelProperty(value = "计划缴纳金额", required = true)
	private BigDecimal planPayAmount;
	/**
	 * 实际缴纳时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE)
	@ApiModelProperty(value = "实际缴纳时间")
	private Date actualPayTime;
	/**
	 * 实际缴纳金额
	 */
	@ApiModelProperty(value = "实际缴纳金额")
	private BigDecimal actualPayAmount;
	/**
	 * 计划退回时间
	 */
	@NotNull(message = "计划退回时间不能为空")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE)
	@ApiModelProperty(value = "计划退回时间", required = true)
	private Date planReturnTime;
	/**
	 * 计划退回金额
	 */
	@NotNull(message = "计划退回金额不能为空")
	@ApiModelProperty(value = "计划退回金额", required = true)
	private BigDecimal planReturnAmount;
	/**
	 * 实际退回时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE)
	@ApiModelProperty(value = "实际退回时间")
	private Date actualReturnTime;
	/**
	 * 实际退回金额
	 */
	@ApiModelProperty(value = "实际退回金额")
	private BigDecimal actualReturnAmount;
	/**
	 * 合同id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "合同id")
	private Long contractId;
	/**
	 * 厂别
	 */
	@ApiModelProperty(value = "厂别")
	private String factories;
	/**
	 * 接收条件
	 */
	@ApiModelProperty(value = "接收条件")
	private String acceptanceConditions;

}