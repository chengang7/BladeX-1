/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.contract.enums;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springblade.contract.constant.ContractFormInfoTemplateContract;
import org.springblade.contract.entity.*;
import org.springblade.contract.util.DataFormatUtils;
import org.springblade.contract.util.MoneyToChiness;
import org.springblade.contract.vo.*;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.vo.FileVO;
import org.springblade.system.entity.TemplateFieldJsonEntity;
import org.springblade.system.vo.TemplateRequestVO;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * 用户类型枚举
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
@Slf4j
public enum TemplateExporterEnum {

	//物流服务合同（二段仓储+配送）
	WLFW_23("WLFW_23") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			dataModel.put("sclPartya", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("sclPartyb", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("date", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date"))));
			//签订地点
			dataModel.put("sclSite", j.get("sclSite"));
			//仓储地点：
			dataModel.put("sclStorage", j.get("sclStorage"));
			//仓储面积
			dataModel.put("sclStorageee", j.get("sclStorageee"));
			//仓库要求：其他条件
			dataModel.put("sclConditionsa", j.get("sclConditionsa"));
			//仓储作业要求-1
			dataModel.put("sclNumber", j.get("sclNumber"));
			//仓储作业要求-5
			dataModel.put("sclServices", j.get("sclServices"));
			//产品收货标准-5
			dataModel.put("sclFood", j.get("sclFood"));
			dataModel.put("sclDrinks", j.get("sclDrinks"));
			dataModel.put("sclDairy", j.get("sclDairy"));
			dataModel.put("sclWater", j.get("sclWater"));
			// 车辆要求： -4
			dataModel.put("sclRequirementsp", j.get("sclRequirementsp"));
			//、运力要求：-1
			dataModel.put("sclRange", j.get("sclRange"));
			// 出货作业：-1
			dataModel.put("sclAreae", j.get("sclAreae"));
			// 出货作业：-2
			dataModel.put("sclRequirementse", j.get("sclRequirementse"));
			dataModel.put("sclContractd", j.get("sclContractd"));
			dataModel.put("sclSecond", j.get("sclSecond"));
			dataModel.put("sclBreach", j.get("sclBreach"));
			// 出货作业：-5
			dataModel.put("sclProvide", j.get("sclProvide"));
			dataModel.put("sclHours", j.get("sclHours"));
			dataModel.put("sclMorning", DataFormatUtils.systemTimeFormatH(j.get("sclMorning").toString()));
			dataModel.put("sclAfternoon", DataFormatUtils.systemTimeFormatH(j.get("sclAfternoon").toString()));
			dataModel.put("sclAdvance", j.get("sclAdvance"));
			dataModel.put("sclSeason", j.get("sclSeason"));
			// 退货作业-1
			dataModel.put("sclReturn", j.get("sclReturn"));
			//票据签收及货款回收要求-1
			dataModel.put("sclRequesta", DataFormatUtils.systemTimeFormatH(String.valueOf(j.get("sclRequesta"))));
			dataModel.put("sclItems", j.get("sclItems"));
			//票据签收及货款回收要求-2
			dataModel.put("sclDate", j.get("sclDate"));
			//票据签收及货款回收要求-36
			dataModel.put("sclDate1", j.get("sclDate1"));
			dataModel.put("sclDate2", j.get("sclDate2"));
			//信息反馈及报表要求-1
			dataModel.put("sclRequirementsf", j.get("sclRequirementsf"));
			dataModel.put("sclTransfer", j.get("sclTransfer"));
			//信息反馈及报表要求-3
			dataModel.put("sclOfs", j.get("sclOfs"));
			//信息反馈及报表要求-4
			dataModel.put("sclRequirementsss", j.get("sclRequirementsss"));
			//物流服务项目及费用计算标准（未税价）：
			if ("1".equals(j.get("traffic"))) {
				dataModel.put("traffic", "☑");
				dataModel.put("area", "☐");
				dataModel.put("sclAread", "_");
			} else if ("2".equals(j.get("traffic"))) {
				dataModel.put("traffic", "☐");
				dataModel.put("area", "☑");
				dataModel.put("sclAread", j.get("sclAread"));
			} else {
				dataModel.put("traffic", "☐");
				dataModel.put("area", "☐");
				dataModel.put("sclAread", "_");
			}
			//、行销品仓储费用：
			dataModel.put("sclWarehouse", j.get("sclWarehouse"));
			//配送费计算方式：
			dataModel.put("sclGoods", j.get("sclGoods"));
			//服务范围、物流服务项目及费用的计算标准：
			dataModel.put("day", j.get("day"));
			dataModel.put("day1", j.get("day1"));
			dataModel.put("day2", j.get("day2"));
			dataModel.put("day3", j.get("day3"));
			dataModel.put("day4", j.get("day4"));
			//、保证金条款
			dataModel.put("sclYuan", j.get("sclYuan"));
			dataModel.put("sclWhole", MoneyToChiness.tenThousand(j.get("sclYuan").toString()));
			dataModel.put("sclLead", j.get("sclLead"));
			//违约责任-1
			dataModel.put("sclRequirementsd", j.get("sclRequirementsd"));
			//违约责任-3
			dataModel.put("sclContract", j.get("sclContract"));
			dataModel.put("day5", j.get("day5"));
			//违约责任-5
			dataModel.put("sclNumber1", j.get("sclNumber1"));
			//违约责任-7
			dataModel.put("sclMultiple", j.get("sclMultiple"));
			dataModel.put("sclRequirementsa", j.get("sclRequirementsa"));
			//违约责任-8
			dataModel.put("sclContaining", j.get("sclContaining"));
			//违约责任-9
			dataModel.put("sclNinth", j.get("sclNinth"));
			//合同变更、解除和终止条款约定-1
			dataModel.put("startTimes", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getStartingTime().toString()));
			dataModel.put("endTimes", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getEndTime().toString()));
			//特别约定事项-2
			dataModel.put("sclCost", j.get("sclCost"));
			dataModel.put("day7", j.get("day7"));
			dataModel.put("sclConditions", j.get("sclConditions"));
			//特别约定事项-3
			dataModel.put("sclDate3", j.get("sclDate3"));
			//特别约定事项-4
			dataModel.put("day6", j.get("day6"));
			//（以下无正文）
			dataModel.put("address", j.get("address"));
			dataModel.put("addressb", j.get("addressb"));
			dataModel.put("telephone", j.get("telephone"));
			dataModel.put("telephoneb", j.get("telephoneb"));
			dataModel.put("fax", j.get("fax"));
			dataModel.put("faxb", j.get("faxb"));
			dataModel.put("accountNumber", j.get("accountNumber"));
			dataModel.put("accountNumberb", j.get("accountNumberb"));
			dataModel.put("deposit", j.get("deposit"));
			dataModel.put("depositb", j.get("depositb"));
			//附件4-<乙方授权委托书1>
			dataModel.put("client", j.get("client"));
			dataModel.put("client1", j.get("client1"));
			dataModel.put("number", j.get("number"));
			dataModel.put("client2", j.get("client2"));
			dataModel.put("number1", j.get("number1"));
			dataModel.put("client3", j.get("client3"));
			dataModel.put("number2", j.get("number2"));
			dataModel.put("mail", j.get("mail"));
			//委托事项：（请在以下方框内打勾）
			dataModel.put("shippingSummons", j.get("shippingSummons").toString().contains("1") ? "☑" : "☐");
			dataModel.put("dispatching", j.get("shippingSummons").toString().contains("2") ? "☑" : "☐");
			dataModel.put("management", j.get("shippingSummons").toString().contains("3") ? "☑" : "☐");
			dataModel.put("other", j.get("shippingSummons").toString().contains("4") ? "☑" : "☐");
			dataModel.put("otherContent", j.get("shippingSummons").toString().contains("4") ? j.get("otherContent") : "");
//            if ("1".equals(j.get("shippingSummons"))) {
//                dataModel.put("shippingSummons", "☑");
//                dataModel.put("dispatching", "☐");
//                dataModel.put("management", "☐");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            } else if ("2".equals(j.get("shippingSummons"))) {
//                dataModel.put("shippingSummons", "☐");
//                dataModel.put("dispatching", "☑");
//                dataModel.put("management", "☐");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            } else if ("3".equals(j.get("shippingSummons"))) {
//                dataModel.put("shippingSummons", "☐");
//                dataModel.put("dispatching", "☐");
//                dataModel.put("management", "☑");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            } else if ("4".equals(j.get("shippingSummons"))) {
//                dataModel.put("shippingSummons", "☐");
//                dataModel.put("dispatching", "☐");
//                dataModel.put("management", "☐");
//                dataModel.put("other", "☑");
//                dataModel.put("otherContent", j.get("otherContent"));
//            } else {
//                dataModel.put("shippingSummons", "☐");
//                dataModel.put("dispatching", "☐");
//                dataModel.put("management", "☐");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            }
			dataModel.put("date3", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date3"))));
			dataModel.put("date4", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date4"))));
			////附件4-<乙方授权委托书2>
			dataModel.put("client4", j.get("client4"));
			dataModel.put("client5", j.get("client5"));
			dataModel.put("number3", j.get("number3"));
			dataModel.put("phone", j.get("phone"));
			dataModel.put("mail1", j.get("mail1"));
			dataModel.put("postalAddress", j.get("postalAddress"));
			//厂商承诺书-此致 -》使用为特别约定
			dataModel.put("company", j.get("company"));
			//拼接附件
			dataModel.put("annex", j.get("annex"));
			//《出货传票》及《出货传票交接清单》样式
			dataModel.put("image", j.get("image"));

			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//作业外包协议
	WBXY_27("WBXY_27") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			dataModel.put("employer", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("contractor", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("employersAddress", j.get("employersAddress"));
			dataModel.put("addressContractor", j.get("addressContractor"));
			dataModel.put("operationA", j.get("operationA"));
			//承包期限
			dataModel.put("contractPeriodA", null == (contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getStartingTime().toString()));
			dataModel.put("contractPeriodB", null == (contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getEndTime().toString()));
			dataModel.put("compensationMuch", j.get("compensationMuch"));
			dataModel.put("damages", j.get("damages"));
			dataModel.put("accidentInsurance", j.get("accidentInsurance"));
			dataModel.put("contactPersonb", j.get("contactPersonb"));
			dataModel.put("contactInformation", j.get("contactInformation"));
			dataModel.put("otherStandards", j.get("otherStandards"));
			dataModel.put("thisAgreement", j.get("thisAgreement"));
			//履约保证金相关
			if (contractFormInfoEntity.getContractBond().size() <= 0) {
				dataModel.put("performance", "");
				dataModel.put("performanceA", "");
			} else if (contractFormInfoEntity.getContractBond().size() == 1) {
				if (contractFormInfoEntity.getContractBond().get(0).getIsNotBond().contains("1")) {
					dataModel.put("performance", "_");
					dataModel.put("performanceA", "_");
				} else if (contractFormInfoEntity.getContractBond().get(0).getIsNotBond().contains("0")) {
					String planPayAmount = String.valueOf(contractFormInfoEntity.getContractBond().get(0).getPlanPayAmount().divide(BigDecimal.valueOf(10000)));
					dataModel.put("performance", planPayAmount);
					dataModel.put("performanceA", MoneyToChiness.tenThousand(planPayAmount));
				}
			} else if (contractFormInfoEntity.getContractBond().size() >= 2) {
				AtomicReference<Double> valuesD = new AtomicReference<>(0D);
				contractFormInfoEntity.getContractBond().forEach(element -> {
					valuesD.updateAndGet(v -> v + Double.parseDouble(String.valueOf(element.getPlanPayAmount().divide(BigDecimal.valueOf(10000)))));
				});
				dataModel.put("performance", valuesD);
				dataModel.put("performanceA", MoneyToChiness.tenThousand(valuesD.toString()));
			}
			dataModel.put("fewDays", j.get("fewDays"));
			dataModel.put("exceedterm", j.get("exceedterm"));
			dataModel.put("vatinvoice", j.get("vatinvoice"));
			dataModel.put("deliveredMonth", j.get("deliveredMonth"));
			dataModel.put("receivesInvoice", j.get("receivesInvoice"));
			dataModel.put("contractOperation", j.get("contractOperation"));
			dataModel.put("accountName", j.get("accountName"));
			dataModel.put("accountNumber", j.get("accountNumber"));
			dataModel.put("bankDeposit", j.get("bankDeposit"));
			dataModel.put("terminationMoney", j.get("terminationMoney"));
			dataModel.put("compensation", j.get("compensation"));
			dataModel.put("liquidatedDamages", j.get("liquidatedDamages"));
			dataModel.put("liquidatedDamagesa", j.get("liquidatedDamagesa"));
			dataModel.put("otherAgreements", j.get("otherAgreements"));
			dataModel.put("agentA", j.get("agentA"));
			dataModel.put("agentB", j.get("agentB"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//新陈列协议书
	CLXY_42("CLXY_42") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			List<YwlANewDisplay1ResponseVO> YwlANewDisplay1 = new ArrayList();
			//把json串转换成集合
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//给当前范本的合同关联表设置一个编号 等于编号时循环处理放到list中
				if (ContractFormInfoTemplateContract.CONTRACT_YWLANEWDISPLAY1.equals(templateField.getRelationCode())) {
					YwlANewDisplay1 = JSON.parseArray(templateField.getTableData(),YwlANewDisplay1ResponseVO.class);
					for (int i = 0; i < YwlANewDisplay1.size(); i++) {
						YwlANewDisplay1.get(i).setYwlNumber(String.valueOf(i+1));
//						Map<String, Object> map = new HashMap();
//						map.put("ywlNumber", i + 1);
//						map.put("ywlDisplayProducts", jsonArr.getJSONObject(i).get("ywlDisplayProducts"));
//						map.put("ywlDisplayMode", jsonArr.getJSONObject(i).get("ywlDisplayMode"));
//						map.put("ywlMerchandisingStandards", jsonArr.getJSONObject(i).get("ywlMerchandisingStandards"));
//						list.add(map);
					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder().bind("YwlANewDisplay1", policy).build();
			dataModel.put("YwlANewDisplay1", YwlANewDisplay1);
			//这部分处理模板的变量字段
			dataModel.put("ywlPatya", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("ywlPatyb", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("ywlCooperationContent", j.get("ywlCooperationContent"));
			//日期格式的字段需要DataFormatUtils.systemTimeFormat处理一下
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dataModel.put("ywlTheStartTime", null == (contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getStartingTime())));
			dataModel.put("ywlEndOfTime", null == (contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getEndTime())));
			dataModel.put("ywlDisplayFee", j.get("ywlDisplayFee"));
			dataModel.put("ywlDisplayDfee", MoneyToChiness.moneyToChinese(j.get("ywlDisplayFee").toString()));
			//这里是处理下来选的字段的
			if ("1".equals(j.get("ywlDisplayType"))) {
				dataModel.put("ywlDisplayType", "☑");
				dataModel.put("ywlDisplayType1", "☐");
			} else if ("2".equals(j.get("ywlDisplayType"))) {
				dataModel.put("ywlDisplayType1", "☑");
				dataModel.put("ywlDisplayType", "☐");
			} else {
				dataModel.put("ywlDisplayType1", "☐");
				dataModel.put("ywlDisplayType", "☐");
			}
			dataModel.put("ywlOther", j.get("ywlOther"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//店招合同
	DZHT_35("DZHT_35") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			dataModel.put("ywlPatyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("ywlPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("ywlLocation", j.get("ywlLocation"));
			dataModel.put("ywlSuspensionStart", j.get("ywlSuspensionStart"));
			dataModel.put("ywlSuspensionEnd", j.get("ywlSuspensionEnd"));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dataModel.put("ywlAgreementPeriodStart", null == (contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getStartingTime())));
			dataModel.put("ywlAgreementPeriodEnd", null == (contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getEndTime())));
			dataModel.put("ywlProductionCosts", j.get("ywlProductionCosts"));
			dataModel.put("ywlAmountOf", MoneyToChiness.moneyToChinese(j.get("ywlProductionCosts").toString()));
			dataModel.put("ywlOtherConventions", j.get("ywlOtherConventions"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//媒体类：视频广告改编合同
	GBHT_14("GBHT_14") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			List<MtlAdaptationContract1ResponseVO> MtlAdaptationContract1 = new ArrayList();
			List<MtlAdaptationContract2ResponseVO> MtlAdaptationContract2 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//*视频广告改编合同关联表
				if (ContractFormInfoTemplateContract.CONTRACT_MTADAPTATIONCONTRACT1.equals(templateField.getRelationCode())) {
					MtlAdaptationContract1 = JSON.parseArray(templateField.getTableData(),MtlAdaptationContract1ResponseVO.class);
//					for (int i = 0; i < jsonArr.size(); i++) {
//						Map<String, Object> map = new HashMap();
//						map.put("formDelivery", jsonArr.getJSONObject(i).get("formDelivery"));
//						map.put("number", jsonArr.getJSONObject(i).get("number"));
//						map.put("contentTheme", jsonArr.getJSONObject(i).get("contentTheme"));
//						map.put("requirements", jsonArr.getJSONObject(i).get("requirements"));
//						map.put("expenses", jsonArr.getJSONObject(i).get("expenses"));
//						list.add(map);
//					}
				}
				if (ContractFormInfoTemplateContract.CONTRACT_MTADAPTATIONCONTRACT2.equals(templateField.getRelationCode())) {
					MtlAdaptationContract2 = JSON.parseArray(templateField.getTableData(),MtlAdaptationContract2ResponseVO.class);
//					for (int i = 0; i < jsonArr.size(); i++) {
//						Map<String, Object> map = new HashMap();
//						map.put("intellectualProperty", jsonArr.getJSONObject(i).get("intellectualProperty"));
//						map.put("sample", jsonArr.getJSONObject(i).get("sample"));
//						map.put("serviceLife", jsonArr.getJSONObject(i).get("serviceLife"));
//						map.put("useArea", jsonArr.getJSONObject(i).get("useArea"));
//						list1.add(map);
//					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("MtlAdaptationContract1", policy).bind("MtlAdaptationContract2",policy).build();
			dataModel.put("MtlAdaptationContract1", MtlAdaptationContract1);
			dataModel.put("MtlAdaptationContract2", MtlAdaptationContract2);
			//主表
			dataModel.put("mtlPatyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("mtlPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("mtlPatyAEmail", j.get("mtlPatyAEmail"));
			dataModel.put("mtlContactEmail", j.get("mtlContactEmail"));
			dataModel.put("mtlPatyBEmail", j.get("mtlPatyBEmail"));
			dataModel.put("mtlPatyBHome", j.get("mtlPatyBHome"));
			dataModel.put("mtlAdaptationIssues", j.get("mtlAdaptationIssues"));
			dataModel.put("mtlNameOfAdvertising", j.get("mtlNameOfAdvertising"));
			dataModel.put("mtlBasedOnTheContent", j.get("mtlBasedOnTheContent"));
			dataModel.put("mtlProductionStartTime", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getStartingTime().toString()));
			dataModel.put("mtlProductionCompletionTime", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getEndTime().toString()));
			dataModel.put("mtlHaveHasNot", j.get("mtlHaveHasNot"));
			dataModel.put("mtlUnpaidTaxRmb", Func.isNull(contractFormInfoEntity.getContractAmount()) ? "" : contractFormInfoEntity.getContractAmount());
			dataModel.put("mtlRate", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? "" : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("mtlTaxAmountIsRmb", Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? "" : contractFormInfoEntity.getContractTaxAmount());
			dataModel.put("mtlCompanyName", j.get("mtlCompanyName"));
			dataModel.put("mtlWhereItIs", j.get("mtlWhereItIs"));
			dataModel.put("mtlAccount", j.get("mtlAccount"));
			dataModel.put("yearA", j.get("yearA"));
			dataModel.put("yearB", j.get("yearB"));
			dataModel.put("yearC", j.get("yearC"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},

	//加工承揽合同（代工合同）
	CLHT_19("CLHT_19") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			List<SclConstructionProject1ResponseVO> SclConstructionProject1 = new ArrayList();
			List<SclConstructionProject2ResponseVO> SclConstructionProject2 = new ArrayList();
			List<SclConstructionProject3ResponseVO> SclConstructionProject3 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//加工承揽合同（代工合同）关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_SCLCONSTRUCTIONPROJECT1.equals(templateField.getRelationCode())) {
					SclConstructionProject1 = JSON.parseArray(templateField.getTableData(), SclConstructionProject1ResponseVO.class);
//					for (int i = 0; i < sclConstructionProject1List.size(); i++) {
//						JSONObject sclConstructionProject1 = JSON.parseObject(JSON.toJSONString(sclConstructionProject1List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("varieties", sclConstructionProject1.get("varieties"));
//						map.put("specifications", sclConstructionProject1.get("specifications"));
//						map.put("processingCost", sclConstructionProject1.get("processingCost"));
//						list.add(map);
//					}
				}
				//加工承揽合同（代工合同）关联表2
				if (ContractFormInfoTemplateContract.CONTRACT_SCLCONSTRUCTIONPROJECT2.equals(templateField.getRelationCode())) {
					SclConstructionProject2 = JSON.parseArray(templateField.getTableData(), SclConstructionProject2ResponseVO.class);
//					for (int i = 0; i < sclConstructionProject2List.size(); i++) {
//						JSONObject sclConstructionProject2 = JSON.parseObject(JSON.toJSONString(sclConstructionProject2List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("serialNumber", sclConstructionProject2.get("serialNumber"));
//						map.put("rawMaterial", sclConstructionProject2.get("rawMaterial"));
//						map.put("nameMaterial", sclConstructionProject2.get("nameMaterial"));
//						map.put("lossRate", sclConstructionProject2.get("lossRate"));
//						list1.add(map);
//					}
				}
				//加工承揽合同（代工合同）关联表3
				if (ContractFormInfoTemplateContract.CONTRACT_SCLCONSTRUCTIONPROJECT3.equals(templateField.getRelationCode())) {
					SclConstructionProject3 = JSON.parseArray(templateField.getTableData(), SclConstructionProject3ResponseVO.class);
//					for (int i = 0; i < sclConstructionProject3List.size(); i++) {
//						JSONObject sclConstructionProject3 = JSON.parseObject(JSON.toJSONString(sclConstructionProject3List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("productName", sclConstructionProject3.get("productName"));
//						map.put("productSpecifications", sclConstructionProject3.get("productSpecifications"));
//						map.put("supplier", sclConstructionProject3.get("supplier"));
//						map.put("withoutTax", sclConstructionProject3.get("withoutTax"));
//						map.put("remarks", sclConstructionProject3.get("remarks"));
//						list2.add(map);
//					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("SclConstructionProject1", policy).bind("SclConstructionProject2",policy).bind("SclConstructionProject3",policy).build();
			dataModel.put("SclConstructionProject1", SclConstructionProject1);
			dataModel.put("SclConstructionProject2", SclConstructionProject2);
			dataModel.put("SclConstructionProject3", SclConstructionProject3);
			//主表
			SclConstructionProjectEntity sclConstructionProject = JSONObject.toJavaObject(j, SclConstructionProjectEntity.class);
			dataModel.put("sclOrderingParty", j.get("sclOrderingParty"));
			dataModel.put("sclPartyAddress", j.get("sclPartyAddress"));
			dataModel.put("sclContractor", j.get("sclContractor"));
			dataModel.put("sclAddressOfContractor", j.get("sclAddressOfContractor"));
			dataModel.put("sclStartTime", j.get("sclStartTime"));
			dataModel.put("sclCompletionTime", j.get("sclCompletionTime"));
			dataModel.put("sclExpenseBearingParty", j.get("sclExpenseBearingParty"));
			dataModel.put("sclExpenseBearingParty1", j.get("sclExpenseBearingParty1"));
			dataModel.put("sclProcessingWay", j.get("sclProcessingWay"));
			dataModel.put("sclRiskOfLoss", j.get("sclRiskOfLoss"));
			dataModel.put("sclDateOfRequest", j.get("sclDateOfRequest"));
			dataModel.put("sclNameOfBank", j.get("sclNameOfBank"));
			dataModel.put("sclAccountName", j.get("sclAccountName"));
			dataModel.put("sclBankNumber", j.get("sclBankNumber"));
			dataModel.put("sclDeposit", j.get("sclDeposit"));
			dataModel.put("sclDeposits", j.get("sclDeposits"));
			dataModel.put("sclPenalties", j.get("sclPenalties"));
			dataModel.put("sclBreachOfContract", j.get("sclBreachOfContract"));
			dataModel.put("sclDatatime", j.get("sclDatatime"));
			dataModel.put("sclPaymentTime", j.get("sclPaymentTime"));
			dataModel.put("sclProcessingFee1", j.get("sclProcessingFee1"));
			dataModel.put("sclBreachOfContract78", j.get("sclBreachOfContract78"));
			dataModel.put("sclBreachOfContract711", j.get("sclBreachOfContract711"));
			dataModel.put("sclBreachOfContract712", j.get("sclBreachOfContract712"));
			dataModel.put("sclBreachOfContract7151", j.get("sclBreachOfContract7151"));
			dataModel.put("sclBreachOfContract7152", j.get("sclBreachOfContract7152"));
			dataModel.put("sclPaid", j.get("sclPaid"));
			dataModel.put("sclOrderingParty", j.get("sclOrderingParty"));
			dataModel.put("sclContractor", j.get("sclContractor"));
			dataModel.put("sclPartyAddress", j.get("sclPartyAddress"));
			dataModel.put("sclAddressOfContractor", j.get("sclAddressOfContractor"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//音频制作合同
	ZZHT_18("ZZHT_18") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			List<MtlAudioProductionContract1ResponseVO> MtlAudioProductionContract1 = new ArrayList();
			List<MtlAudioProductionContract2ResponseVO> MtlAudioProductionContract2 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//音频制作合同 关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_MTLAUDIOPRODUCTIONCONTRACT1.equals(templateField.getRelationCode())) {
					MtlAudioProductionContract1 = JSON.parseArray(templateField.getTableData(), MtlAudioProductionContract1ResponseVO.class);
//                    for (int i = 0; i < mtlAudioProductionContract1List.size(); i++) {
//                        JSONObject mtlAudioProductionContract1 = JSON.parseObject(JSON.toJSONString(mtlAudioProductionContract1List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//                        Map<String, Object> map = new HashMap();
//                        map.put("formDelivery", mtlAudioProductionContract1.get("formDelivery"));
//                        map.put("number", mtlAudioProductionContract1.get("number"));
//                        map.put("contentTheme", mtlAudioProductionContract1.get("contentTheme"));
///                       map.put("requirements", mtlAudioProductionContract1.get("requirements"));
//                        map.put("expenses", mtlAudioProductionContract1.get("expenses"));
//                        list.add(map);
//                    }
				}
				//音频制作合同 关联表2
				if (ContractFormInfoTemplateContract.CONTRACT_MTLAUDIOPRODUCTIONCONTRACT2.equals(templateField.getRelationCode())) {
					MtlAudioProductionContract2 = JSON.parseArray(templateField.getTableData(), MtlAudioProductionContract2ResponseVO.class);
//                    for (int i = 0; i < mtlAudioProductionContract2List.size(); i++) {
//                        JSONObject mtlAudioProductionContract2 = JSON.parseObject(JSON.toJSONString(mtlAudioProductionContract2List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//                        Map<String, Object> map = new HashMap();
//                        map.put("intellectualProperty", mtlAudioProductionContract2.get("intellectualProperty"));
//                        map.put("serviceLife", mtlAudioProductionContract2.get("serviceLife"));
//                        map.put("useArea", mtlAudioProductionContract2.get("useArea"));
//                        list1.add(map);
//                    }
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("MtlAudioProductionContract1", policy).bind("MtlAudioProductionContract2",policy).build();
			dataModel.put("MtlAudioProductionContract1", MtlAudioProductionContract1);
			dataModel.put("MtlAudioProductionContract2", MtlAudioProductionContract2);
			//主表
			MtlAudioProductionContractEntity mtlAudioProductionContract = JSONObject.toJavaObject(j, MtlAudioProductionContractEntity.class);
			dataModel.put("mtlPatyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("mtlPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("mtlPatyAEmail", j.get("mtlPatyAEmail"));
			dataModel.put("mtlContactEmail", j.get("mtlContactEmail"));
			dataModel.put("mtlPatyBEmail", j.get("mtlPatyBEmail"));
			dataModel.put("mtlPatyBHome", j.get("mtlPatyBHome"));
			dataModel.put("mtlAdaptationIssues", j.get("mtlAdaptationIssues"));
			dataModel.put("mtlNameOfTheAudio", j.get("mtlNameOfTheAudio"));
			dataModel.put("mtlAudioContent", j.get("mtlAudioContent"));
			dataModel.put("mtlHaveHasNot", j.get("mtlHaveHasNot"));
			dataModel.put("mtlProductionStartTime", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getStartingTime().toString()));
			dataModel.put("mtlProductionCompletionTime", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getEndTime().toString()));
			//2.乙方制作的作品被甲方全部确认后，应同时提交以下内容（勾选）：
			StringBuilder mtlTerm = new StringBuilder();
			if (j.get("mtlSubmitContent").toString().contains("1")) {
				mtlTerm.append("(1)");
			}
			if (j.get("mtlSubmitContent").toString().contains("2")) {
				mtlTerm.append("(2)");
			}
			if (j.get("mtlSubmitContent").toString().contains("3")) {
				mtlTerm.append("(3)");
			}
			if (j.get("mtlSubmitContent").toString().contains("4")) {
				mtlTerm.append("(4)");
			}
			dataModel.put("mtlSubmitContent", mtlTerm.toString());
			dataModel.put("mtlDesignatedPerson", j.get("mtlDesignatedPerson"));
			dataModel.put("mtlUnpaidTaxRmb", Func.isNull(contractFormInfoEntity.getContractAmount()) ? "" : contractFormInfoEntity.getContractAmount());
			dataModel.put("mtlRate", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? "" : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("mtlTaxAmountIsRmb", Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? "" : contractFormInfoEntity.getContractTaxAmount());
			dataModel.put("mtlCompanyName", j.get("mtlCompanyName"));
			dataModel.put("mtlWhereItIs", j.get("mtlWhereItIs"));
			dataModel.put("mtlAccount", j.get("mtlAccount"));
			dataModel.put("mtlMusicUse", j.get("mtlMusicUse"));
			dataModel.put("mtlLiquidatedDamages2", j.get("mtlLiquidatedDamages2"));
			dataModel.put("mtlSingerUse", j.get("mtlSingerUse"));
			dataModel.put("mtlDubbingUse", j.get("mtlDubbingUse"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//修图合同
	XTHT_17("XTHT_17") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map model = new HashMap();
			Map dataModel = new HashMap();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			List<MtlEditedTheContract1ResponseVO> MtlEditedTheContract1 = new ArrayList<>();
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//音频制作合同 关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_MTLEDITEDTHECONTRACT1.equals(templateField.getRelationCode())) {
					MtlEditedTheContract1 = JSON.parseArray(templateField.getTableData(), MtlEditedTheContract1ResponseVO.class);
					for (int i = 0; i < MtlEditedTheContract1.size(); i++) {
						MtlEditedTheContract1.get(i).setPictureSampleD(Pictures.ofUrl(MtlEditedTheContract1.get(i).getPictureSample().substring(0, MtlEditedTheContract1.get(i).getPictureSample().length() - 1),PictureType.JPEG).size(50, 50).create());
//						JSONObject mtlEditedTheContract1 = JSON.parseObject(JSON.toJSONString(mtlEditedTheContract1List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						MtlEditedTheContract1.get(i).setDeliveryTime(DataFormatUtils.systemTimeFormat(String.valueOf(mtlEditedTheContract1.get("deliveryTime"))));
//						Map<String, Object> map = new HashMap();
//						map.put("pictureSample", mtlEditedTheContract1.get("pictureSample"));
//						map.put("modificationRequirements", mtlEditedTheContract1.get("modificationRequirements"));
//						map.put("deliveryTime", DataFormatUtils.systemTimeFormat(String.valueOf(mtlEditedTheContract1.get("deliveryTime"))));
//						map.put("modeDelivery", mtlEditedTheContract1.get("modeDelivery"));
//						MtlEditedTheContract1.add(map);
					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("MtlEditedTheContract1", policy).build();
			dataModel.put("MtlEditedTheContract1", MtlEditedTheContract1);
			//主表
			MtlEditedTheContractEntity mtlEditedTheContract = JSONObject.toJavaObject(j, MtlEditedTheContractEntity.class);
			dataModel.put("mtlPatyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("mtlPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("mtlPatyAEmail", j.get("mtlPatyAEmail"));
			dataModel.put("mtlContactEmail", j.get("mtlContactEmail"));
			dataModel.put("mtlPatyBEmail", j.get("mtlPatyBEmail"));
			dataModel.put("mtlPatyBHome", j.get("mtlPatyBHome"));
			dataModel.put("mtlUnpaidTaxRmb", Func.isNull(contractFormInfoEntity.getContractAmount()) ? "" : contractFormInfoEntity.getContractAmount());
			dataModel.put("mtlRate", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? "" : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("mtlTaxAmountIsRmb", Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? "" : contractFormInfoEntity.getContractTaxAmount());
			dataModel.put("mtlCompanyName", j.get("mtlCompanyName"));
			dataModel.put("mtlWhereItIs", j.get("mtlWhereItIs"));
			dataModel.put("mtlAccount", j.get("mtlAccount"));
			model.put("dataModel", setFile(filepaths, dataModel));
			model.put("config", config);
			return model;
		}
	},
	//视频制作合同
	ZZHT_16("ZZHT_16") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map model = new HashMap();
			Map dataModel = new HashMap();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			List<MtlVideoProductionContract1ResponseVO> MtlVideoProductionContract1 = new ArrayList<>();
			List<MtlVideoProductionContract2ResponseVO> MtlVideoProductionContract2 = new ArrayList<>();
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//视频制作合同 关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_MTLVIDEOPRODUCTIONCONTRACT1.equals(templateField.getRelationCode())) {
					MtlVideoProductionContract1 = JSON.parseArray(templateField.getTableData(), MtlVideoProductionContract1ResponseVO.class);
				}
				//视频制作合同 关联表2
				if (ContractFormInfoTemplateContract.CONTRACT_MTLVIDEOPRODUCTIONCONTRACT2.equals(templateField.getRelationCode())) {
					MtlVideoProductionContract2 = JSON.parseArray(templateField.getTableData(), MtlVideoProductionContract2ResponseVO.class);
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("MtlVideoProductionContract1", policy).bind("MtlVideoProductionContract2", policy).build();
			dataModel.put("MtlVideoProductionContract1", MtlVideoProductionContract1);
			dataModel.put("MtlVideoProductionContract2", MtlVideoProductionContract2);
			//主表
			MtlVideoProductionContractEntity mtlVideoProductionContract = JSONObject.toJavaObject(j, MtlVideoProductionContractEntity.class);
			dataModel.put("mtlPatyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("mtlPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("mtlPatyAEmail", j.get("mtlPatyAEmail"));
			dataModel.put("mtlContactEmail", j.get("mtlContactEmail"));
			dataModel.put("mtlPatyBEmail", j.get("mtlPatyBEmail"));
			dataModel.put("mtlPatyBHome", j.get("mtlPatyBHome"));
			dataModel.put("mtlAdaptationIssues", j.get("mtlAdaptationIssues"));
			dataModel.put("mtlNameOfTheVideo", j.get("mtlNameOfTheVideo"));
			dataModel.put("mtlVideoContent", j.get("mtlVideoContent"));
			dataModel.put("mtlHaveHasNot", j.get("mtlHaveHasNot"));
			dataModel.put("mtlProductionStartTime", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getStartingTime().toString()));
			dataModel.put("mtlProductionCompletionTime", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getEndTime().toString()));
			//2.乙方制作的作品被甲方全部确认后，应同时提交以下内容（勾选）：
			StringBuilder mtlTerm = new StringBuilder();
			if (j.get("mtlSubmitContent").toString().contains("1")) {
				mtlTerm.append("(1)");
			}
			if (j.get("mtlSubmitContent").toString().contains("2")) {
				mtlTerm.append("(2)");
			}
			if (j.get("mtlSubmitContent").toString().contains("3")) {
				mtlTerm.append("(3)");
			}
			if (j.get("mtlSubmitContent").toString().contains("4")) {
				mtlTerm.append("(4)");
			}
			dataModel.put("mtlSubmitContent", mtlTerm.toString());
			dataModel.put("mtlAcceptance", j.get("mtlAcceptance"));
			dataModel.put("mtlUnpaidTaxRmb", Func.isNull(contractFormInfoEntity.getContractAmount()) ? "" : contractFormInfoEntity.getContractAmount());
			dataModel.put("mtlRate", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? "" : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("mtlTaxAmountIsRmb", Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? "" : contractFormInfoEntity.getContractTaxAmount());
			dataModel.put("mtlCompanyName", j.get("mtlCompanyName"));
			dataModel.put("mtlWhereItIs", j.get("mtlWhereItIs"));
			dataModel.put("mtlAccount", j.get("mtlAccount"));
			dataModel.put("mtlPortraitRights", j.get("mtlPortraitRights"));
			dataModel.put("mtlLiquidatedDamages2", j.get("mtlLiquidatedDamages2"));
			dataModel.put("mtlDubbingUse", j.get("mtlDubbingUse"));
			model.put("dataModel", setFile(filepaths, dataModel));
			model.put("config", config);
			return model;
		}
	},
	//视频广告拍摄制作合同
	ZZHT_15("ZZHT_15") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<MtlShootingAndProductionContract1ResponseVO> MtlShootingAndProductionContract1 = new ArrayList();
			List<MtlShootingAndProductionContract2ResponseVO> MtlShootingAndProductionContract2 = new ArrayList();
			List<MtlShootingAndProductionContract3ResponseVO> MtlShootingAndProductionContract3 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//视频广告拍摄制作合同 关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_MTLSHOOTINGANDPRODUCTIONCONTRACT1.equals(templateField.getRelationCode())) {
					MtlShootingAndProductionContract1 = JSON.parseArray(templateField.getTableData(), MtlShootingAndProductionContract1ResponseVO.class);
//					for (int i = 0; i < mtlShootingAndProductionContract1List.size(); i++) {
//						JSONObject mtlShootingAndProductionContract1 = JSON.parseObject(JSON.toJSONString(mtlShootingAndProductionContract1List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("formDelivery", mtlShootingAndProductionContract1.get("formDelivery"));
//						map.put("number", mtlShootingAndProductionContract1.get("number"));
//						map.put("contentTheme", mtlShootingAndProductionContract1.get("contentTheme"));
//						map.put("requirements", mtlShootingAndProductionContract1.get("requirements"));
//						map.put("expenses", mtlShootingAndProductionContract1.get("expenses"));
//						list.add(map);
//					}
				}
				//视频广告拍摄制作合同 关联表2
				if (ContractFormInfoTemplateContract.CONTRACT_MTLSHOOTINGANDPRODUCTIONCONTRACT2.equals(templateField.getRelationCode())) {
					MtlShootingAndProductionContract2 = JSON.parseArray(templateField.getTableData(), MtlShootingAndProductionContract2ResponseVO.class);
//					for (int i = 0; i < mtlShootingAndProductionContract2List.size(); i++) {
//						JSONObject mtlShootingAndProductionContract2 = JSON.parseObject(JSON.toJSONString(mtlShootingAndProductionContract2List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("intellectualProperty", mtlShootingAndProductionContract2.get("intellectualProperty"));
//						map.put("sample", mtlShootingAndProductionContract2.get("sample"));
//						map.put("serviceLife", mtlShootingAndProductionContract2.get("serviceLife"));
//						map.put("useArea", mtlShootingAndProductionContract2.get("useArea"));
//						list1.add(map);
//					}
				}
				//视频广告拍摄制作合同 关联表3
				if (ContractFormInfoTemplateContract.CONTRACT_MTLSHOOTINGANDPRODUCTIONCONTRACT3.equals(templateField.getRelationCode())) {
					MtlShootingAndProductionContract3 = JSON.parseArray(templateField.getTableData(), MtlShootingAndProductionContract3ResponseVO.class);
//					for (int i = 0; i < mtlShootingAndProductionContract3List.size(); i++) {
//						JSONObject mtlShootingAndProductionContract3 = JSON.parseObject(JSON.toJSONString(mtlShootingAndProductionContract3List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("file", mtlShootingAndProductionContract3.get("file"));
//						map.put("creationTime", DataFormatUtils.timeStamp2Date(String.valueOf(mtlShootingAndProductionContract3.get("creationTime"))));
//						map.put("completeplace", mtlShootingAndProductionContract3.get("completeplace"));
//						map.put("creator", mtlShootingAndProductionContract3.get("creator"));
//						map.put("employment", mtlShootingAndProductionContract3.get("employment"));
//						list2.add(map);
//					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("MtlShootingAndProductionContract1", policy).bind("MtlShootingAndProductionContract2", policy).bind("MtlShootingAndProductionContract3", policy).build();
			dataModel.put("MtlShootingAndProductionContract1", MtlShootingAndProductionContract1);
			dataModel.put("MtlShootingAndProductionContract2", MtlShootingAndProductionContract2);
			dataModel.put("MtlShootingAndProductionContract3", MtlShootingAndProductionContract3);
			//主表
			MtlShootingAndProductionContractEntity mtlShootingAndProductionContract = JSONObject.toJavaObject(j, MtlShootingAndProductionContractEntity.class);
			dataModel.put("mtlPatyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("mtlPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("mtlPatyAEmail", j.get("mtlPatyAEmail"));
			dataModel.put("mtlContactEmail", j.get("mtlContactEmail"));
			dataModel.put("mtlPatyBEmail", j.get("mtlPatyBEmail"));
			dataModel.put("mtlPatyBHome", j.get("mtlPatyBHome"));
			dataModel.put("mtlAdaptationIssues", j.get("mtlAdaptationIssues"));
			dataModel.put("mtlNameOfAdvertising", j.get("mtlNameOfAdvertising"));
			dataModel.put("mtlContentsOfAdvertisements", j.get("mtlContentsOfAdvertisements"));
			dataModel.put("mtlHaveHasNot", j.get("mtlHaveHasNot"));
			dataModel.put("mtlProductionStartTime", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getStartingTime().toString()));
			dataModel.put("mtlProductionCompletionTime", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getEndTime().toString()));
			//2.乙方制作的作品被甲方全部确认后，应同时提交以下内容（勾选）：
			StringBuilder mtlTerm = new StringBuilder();
			if (j.get("mtlSubmitCheck").toString().contains("1")) {
				mtlTerm.append("(1),");
			}
			if (j.get("mtlSubmitCheck").toString().contains("2")) {
				mtlTerm.append("(2),");
			}
			if (j.get("mtlSubmitCheck").toString().contains("3")) {
				mtlTerm.append("(3),");
			}
			if (j.get("mtlSubmitCheck").toString().contains("4")) {
				mtlTerm.append("(4)");
			}
			dataModel.put("mtlSubmitCheck", mtlTerm.toString());
			dataModel.put("mtlAcceptance", j.get("mtlAcceptance"));
			dataModel.put("mtlUnpaidTaxRmb", Func.isNull(contractFormInfoEntity.getContractAmount()) ? "" : contractFormInfoEntity.getContractAmount());
			dataModel.put("mtlRate", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? "" : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("mtlTaxAmountIsRmb", Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? "" : contractFormInfoEntity.getContractTaxAmount());
			dataModel.put("mtlCompanyName", j.get("mtlCompanyName"));
			dataModel.put("mtlWhereItIs", j.get("mtlWhereItIs"));
			dataModel.put("mtlAccount", j.get("mtlAccount"));
			dataModel.put("mtlDubbingUse", j.get("mtlDubbingUse"));
			dataModel.put("mtlMusicUse", j.get("mtlMusicUse"));
			dataModel.put("mtlSingerUse", j.get("mtlSingerUse"));
			dataModel.put("dateSigning", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("dateSigning"))));
			dataModel.put("jobContent", j.get("jobContent"));
			dataModel.put("contractNo", Func.isNull(contractFormInfoEntity.getContractNumber()) ? "" : contractFormInfoEntity.getContractNumber());
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//活动执行合同
	HDZX_05("HDZX_05") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			dataModel.put("cglPartya", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("cglPartyb", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("cglActivity", j.get("cglActivity"));
			dataModel.put("cglArea", j.get("cglArea"));
			dataModel.put("activityName", j.get("activityName"));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dataModel.put("cglByTime", null == (contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getStartingTime())));
			dataModel.put("cglAsTime", null == (contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getEndTime())));
			dataModel.put("scheduling", j.get("scheduling"));//附件2
			dataModel.put("cglPrice", j.get("cglPrice"));//附件3
			dataModel.put("planningScheme", j.get("planningScheme"));//附件1
			dataModel.put("cglTotal", contractFormInfoEntity.getContractAmount());
			dataModel.put("capitalization", MoneyToChiness.moneyToChinese(Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? "" : contractFormInfoEntity.getContractTaxAmount().toString()));
			dataModel.put("events", j.get("events"));
			dataModel.put("cglPayment", j.get("cglPayment"));
			if (j.get("cglPayment").toString().contains("2.1分期付款")) {
				dataModel.put("days", j.get("days"));
				dataModel.put("amount", j.get("amount"));
				dataModel.put("element", j.get("element"));
				dataModel.put("amountWords", MoneyToChiness.moneyToChinese(j.get("element").toString()));
				dataModel.put("cglProportion", j.get("cglProportion"));
				dataModel.put("cglLumpSum", j.get("cglLumpSum"));
				dataModel.put("cglCapitalize", MoneyToChiness.moneyToChinese(j.get("cglLumpSum").toString()));
				dataModel.put("other", "——");
			} else if (j.get("cglPayment").toString().contains("2.2其他方式")) {
				dataModel.put("days", "——");
				dataModel.put("amount", "——");
				dataModel.put("element", "——");
				dataModel.put("amountWords", "——");
				dataModel.put("cglProportion", "——");
				dataModel.put("cglLumpSum", "__");
				dataModel.put("cglCapitalize", "__");
				dataModel.put("other", j.get("other"));
			} else {
				dataModel.put("days", "__");
				dataModel.put("amount", "__");
				dataModel.put("element", "__");
				dataModel.put("amountWords", "__");
				dataModel.put("cglProportion", "__");
				dataModel.put("cglLumpSum", "__");
				dataModel.put("cglCapitalize", "__");
				dataModel.put("other", "__");
			}
			dataModel.put("cglBank", j.get("cglBank"));
			dataModel.put("cglAccountName", j.get("cglAccountName"));
			dataModel.put("cglAccount", j.get("cglAccount"));
			dataModel.put("cglInvoice", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? "" : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("days1", j.get("days1"));
			dataModel.put("days2", j.get("days2"));
			dataModel.put("breachContract", j.get("breachContract"));
			dataModel.put("one", j.get("planningScheme"));
			dataModel.put("two", j.get("scheduling"));
			dataModel.put("three", j.get("cglPrice"));
			dataModel.put("four", j.get("cglInspectionStandard"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//物流服务合同（二段配送）
	FWHT_24("FWHT_24") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			SclLogisticsServiceEntity sclLogisticsService = JSONObject.toJavaObject(j, SclLogisticsServiceEntity.class);
			dataModel.put("partya", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("partyb", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("date", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date"))));
			//签订地点
			dataModel.put("site", j.get("site"));
			//甲方依据《中华人民共和国招标投标法》对
			dataModel.put("storage", j.get("storage"));
			//中标的物流线路为：
			dataModel.put("area", j.get("area"));
			//正常单
			dataModel.put("no", DataFormatUtils.systemTimeFormatH(String.valueOf(j.get("no"))));
			//加单
			dataModel.put("storageee", DataFormatUtils.systemTimeFormatH(String.valueOf(j.get("storageee"))));
			//加急单
			dataModel.put("conditionsa", DataFormatUtils.systemTimeFormatH(String.valueOf(j.get("conditionsa"))));
			//3、 货物的配送-7
			dataModel.put("day", j.get("day"));
			//3、 货物的配送-8
			dataModel.put("day1", j.get("day1"));
			//乙方人员配备及车辆配置要求-2
			dataModel.put("services", j.get("services"));
			//乙方人员配备及车辆配置要求-4
			dataModel.put("drinks", j.get("drinks"));
			//1、甲方的权利和义务-4
			dataModel.put("dairy", j.get("dairy"));
			//1、甲方的权利和义务-7
			dataModel.put("dairy1", j.get("dairy1"));
			dataModel.put("requirementsp", j.get("requirementsp"));
			//1、甲方的权利和义务-8
			dataModel.put("range", j.get("range"));
			//1、甲方的权利和义务-9
			dataModel.put("areae", j.get("areae"));
			dataModel.put("requirementse", j.get("requirementse"));
			//1、甲方的权利和义务-17
			dataModel.put("contractd", DataFormatUtils.systemTimeFormatH(j.get("contractd").toString()));
			//七、担保条款
			dataModel.put("second", j.get("second"));
			dataModel.put("breach", MoneyToChiness.tenThousand(j.get("second").toString()));
			dataModel.put("provide", j.get("provide"));
			//八、物流费用计算标准（未税价）及付款时间：1-1
			dataModel.put("hours", j.get("hours"));
			dataModel.put("morning", j.get("morning"));
			dataModel.put("manifest", j.get("manifest"));
			dataModel.put("afternoon", j.get("afternoon"));
			//九、违约责任-1-1
			dataModel.put("advancess", j.get("advancess"));
			//九、违约责任-1-2
			dataModel.put("season", j.get("season"));
			//九、违约责任-2-4
			dataModel.put("times", j.get("times"));
			//九、违约责任-2-5
			dataModel.put("items", j.get("items"));
			//九、违约责任-2-9
			dataModel.put("date1", j.get("date1"));
			dataModel.put("requirementsddd", j.get("requirementsddd"));
			//九、违约责任-2-12
			dataModel.put("requirementsf", j.get("requirementsf"));
			dataModel.put("transfer", j.get("transfer"));
			//十二、合同的有效期限
			dataModel.put("date2", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getStartingTime().toString()));
			dataModel.put("requirementsss", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(contractFormInfoEntity.getEndTime().toString()));
			dataModel.put("standard", j.get("standard"));
			//十五、特别约定
			dataModel.put("aread", j.get("aread"));
			//（以下无正文）
			dataModel.put("address", j.get("address"));
			dataModel.put("addressb", j.get("addressb"));
			dataModel.put("telephone", j.get("telephone"));
			dataModel.put("telephoneb", j.get("telephoneb"));
			dataModel.put("contacts", j.get("contacts"));
			dataModel.put("contactsb", j.get("contactsb"));
			dataModel.put("representative", j.get("representative"));
			dataModel.put("representativeb", j.get("representativeb"));
			dataModel.put("agent", j.get("agent"));
			dataModel.put("agentb", j.get("agentb"));
			//附件2 乙方授权委托书-1
			dataModel.put("client", j.get("client"));
			dataModel.put("client1", j.get("client1"));
			dataModel.put("client2", j.get("client2"));
			dataModel.put("client3", j.get("client3"));
			dataModel.put("number2", j.get("number2"));
			dataModel.put("number1", j.get("number1"));
			dataModel.put("number", j.get("number"));
			dataModel.put("mail", j.get("mail"));
			//委托事项：（请在以下方框内打勾）
			dataModel.put("choice", j.get("choice").toString().contains("1") ? "☑" : "☐");
			dataModel.put("choice1", j.get("choice").toString().contains("2") ? "☑" : "☐");
			dataModel.put("choice2", j.get("choice").toString().contains("3") ? "☑" : "☐");
			dataModel.put("other", j.get("choice").toString().contains("4") ? "☑" : "☐");
			dataModel.put("otherContent", j.get("choice").toString().contains("4") ? j.get("otherContent") : "");
			//委托事项：（请在以下方框内打勾）
//            if ("1".equals(j.get("choice"))) {
//                dataModel.put("choice", "☑");
//                dataModel.put("choice1", "☐");
//                dataModel.put("choice2", "☐");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            } else if ("2".equals(j.get("choice"))) {
//                dataModel.put("choice", "☐");
//                dataModel.put("choice1", "☑");
//                dataModel.put("choice2", "☐");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            } else if ("3".equals(j.get("choice"))) {
//                dataModel.put("choice", "☐");
//                dataModel.put("choice1", "☐");
//                dataModel.put("choice2", "☑");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            } else if ("4".equals(j.get("choice"))) {
//                dataModel.put("choice", "☐");
//                dataModel.put("choice1", "☐");
//                dataModel.put("choice2", "☐");
//                dataModel.put("other", "☑");
//                dataModel.put("otherContent", j.get("otherContent"));
//            } else {
//                dataModel.put("choice", "☐");
//                dataModel.put("choice1", "☐");
//                dataModel.put("choice2", "☐");
//                dataModel.put("other", "☐");
//                dataModel.put("otherContent", "");
//            }
			//委托期间：
			dataModel.put("date5", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date5"))));
			dataModel.put("date6", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date6"))));
			// 乙方（签章）
			dataModel.put("signature", "🥇");
			//乙方授权委托书-2
			dataModel.put("client4", j.get("client4"));
			dataModel.put("client5", j.get("client5"));
			dataModel.put("number3", j.get("number3"));
			dataModel.put("phone", j.get("phone"));
			dataModel.put("mail1", j.get("mail1"));
			dataModel.put("postal", j.get("postal"));
			dataModel.put("date7", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date7"))));
			dataModel.put("date8", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date8"))));
			//附件:8：厂商承诺书
			dataModel.put("company", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("company1", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//媒体类：平面广告拍摄制作合同
	ZZHT_12("ZZHT_12") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			List<MtbProductionContract1ResponseVO> MtbProductionContract1 = new ArrayList();
			List<MtbProductionContract2ResponseVO> MtbProductionContract2 = new ArrayList();
			List<MtbProductionContract3ResponseVO> MtbProductionContract3 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//平面广告拍摄制作合同 关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_MTBPRODUCTIONCONTRACT1.equals(templateField.getRelationCode())) {
					MtbProductionContract1 = JSON.parseArray(templateField.getTableData(), MtbProductionContract1ResponseVO.class);
//					for (int i = 0; i < mtbProductionContract1List.size(); i++) {
//						JSONObject mtbProductionContract1 = JSON.parseObject(JSON.toJSONString(mtbProductionContract1List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("formDelivery", mtbProductionContract1.get("formDelivery"));
//						map.put("number", mtbProductionContract1.get("number"));
//						map.put("requirements", mtbProductionContract1.get("requirements"));
//						map.put("expenses", mtbProductionContract1.get("expenses"));
//						list.add(map);
//					}
				}
				//平面广告拍摄制作合同 关联表2
				if (ContractFormInfoTemplateContract.CONTRACT_MTBPRODUCTIONCONTRACT2.equals(templateField.getRelationCode())) {
					MtbProductionContract2 = JSON.parseArray(templateField.getTableData(), MtbProductionContract2ResponseVO.class);
//					for (int i = 0; i < mtbProductionContract2List.size(); i++) {
//						JSONObject mtbProductionContract2 = JSON.parseObject(JSON.toJSONString(mtbProductionContract2List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("intellectualProperty", mtbProductionContract2.get("intellectualProperty"));
//						map.put("smallKind", mtbProductionContract2.get("smallKind"));
//						map.put("serviceLife", mtbProductionContract2.get("serviceLife"));
//						map.put("useArea", mtbProductionContract2.get("useArea"));
//						list1.add(map);
//					}
				}
				//平面广告拍摄制作合同 关联表3
				if (ContractFormInfoTemplateContract.CONTRACT_MTBPRODUCTIONCONTRACT3.equals(templateField.getRelationCode())) {
					MtbProductionContract3 = JSON.parseArray(templateField.getTableData(), MtbProductionContract3ResponseVO.class);
//					for (int i = 0; i < mtbProductionContract3List.size(); i++) {
//						JSONObject mtbProductionContract3 = JSON.parseObject(JSON.toJSONString(mtbProductionContract3List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("wenJian", mtbProductionContract3.get("wenJian"));
//						map.put("shouChuang", DataFormatUtils.timeStamp2Date(String.valueOf(mtbProductionContract3.get("shouChuang"))));
//						map.put("wanCheng", mtbProductionContract3.get("wanCheng"));
//						map.put("createZhe", mtbProductionContract3.get("createZhe"));
//						map.put("zhiWuChuang", mtbProductionContract3.get("zhiWuChuang"));
//						list2.add(map);
//					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("MtbProductionContract1", policy).bind("MtbProductionContract2", policy).bind("MtbProductionContract3", policy).build();
			dataModel.put("MtbProductionContract1", MtbProductionContract1);
			dataModel.put("MtbProductionContract2", MtbProductionContract2);
			dataModel.put("MtbProductionContract3", MtbProductionContract3);
			//主表
			MtbProductionContractEntity mtbProductionContract = JSONObject.toJavaObject(j, MtbProductionContractEntity.class);
			dataModel.put("mtbPatyA", contractFormInfoEntity.getSealName());
			dataModel.put("mtbPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("mtbContactEmail", j.get("mtbContactEmail"));
			dataModel.put("mtbAddress", j.get("mtbAddress"));
			dataModel.put("mtbPatyBEmail", j.get("mtbPatyBEmail"));
			dataModel.put("mtbPatyBAddress", j.get("mtbPatyBAddress"));
			dataModel.put("mtbMakeMatters", j.get("mtbMakeMatters"));
			dataModel.put("mtbNameOfAdvertising", j.get("mtbNameOfAdvertising"));
			dataModel.put("mtbContentsOfAdvertisements", j.get("mtbContentsOfAdvertisements"));
			dataModel.put("mtbHaveHasNot", j.get("mtbHaveHasNot"));
			dataModel.put("mtbShootingStartTime", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getStartingTime())));
			dataModel.put("mtbShootingCompletionTime", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getEndTime())));
			//2.乙方制作的作品被甲方全部确认后，应同时提交以下内容（勾选）：
			StringBuilder mtlTerm = new StringBuilder();
			if (Func.isNull(j.get("mtbSubmitContent"))){
				mtlTerm.append("/");
			}else {
				if (j.get("mtbSubmitContent").toString().contains("1")) {
					mtlTerm.append("(1)");
				}
				if (j.get("mtbSubmitContent").toString().contains("2")) {
					mtlTerm.append("(2)");
				}
			}
			dataModel.put("mtbSubmitContent", mtlTerm.toString());
			dataModel.put("mtbAcceptancePersonnel", j.get("mtbAcceptancePersonnel"));
			dataModel.put("mtbUnpaidTaxRmb", Func.isNull(contractFormInfoEntity.getContractAmount()) ? "" : contractFormInfoEntity.getContractAmount());
			dataModel.put("mtbRate", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? "" : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("mtbTaxInclusiveInRmb", Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? "" : contractFormInfoEntity.getContractTaxAmount());
			dataModel.put("mtbCompanyName", j.get("mtbCompanyName"));
			dataModel.put("mtbBankOfPartyB", j.get("mtbBankOfPartyB"));
			dataModel.put("mtbPartyAccount", j.get("mtbPartyAccount"));
			dataModel.put("mtbPortrait", j.get("mtbPortrait"));
			dataModel.put("qiandingTime", j.get("qiandingTime"));
			dataModel.put("weituo", j.get("weituo"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//生产类：设备维修保养合同
	BYHT_21("BYHT_21") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<SclEquipmentMaintenance1ResponseVO> SclEquipmentMaintenance1 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//设备维修保养合同 关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_SCLEQUIOMENTMAINTENANCE1.equals(templateField.getRelationCode())) {
					SclEquipmentMaintenance1 = JSON.parseArray(templateField.getTableData(), SclEquipmentMaintenance1ResponseVO.class);
					for (int i = 0; i < SclEquipmentMaintenance1.size(); i++) {
						SclEquipmentMaintenance1.get(i).setSclNumber(i+1);
//						JSONObject slEquipmentMaintenance1 = JSON.parseObject(JSON.toJSONString(slEquipmentMaintenance1List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("sclNumber", i + 1);
//						map.put("sclDeviceName", slEquipmentMaintenance1.get("sclDeviceName"));
//						map.put("sclBrand", slEquipmentMaintenance1.get("sclBrand"));
//						map.put("sclSpecification", slEquipmentMaintenance1.get("sclSpecification"));
//						map.put("sclPrice", slEquipmentMaintenance1.get("sclPrice"));
//						map.put("sclNumbers", slEquipmentMaintenance1.get("sclNumbers"));
//						map.put("sclOther", slEquipmentMaintenance1.get("sclOther"));
//						list.add(map);
					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("SclEquipmentMaintenance1", policy).build();
			dataModel.put("SclEquipmentMaintenance1", SclEquipmentMaintenance1);
			//主表
			SclEquipmentMaintenanceEntity sclEquipmentMaintenance = JSONObject.toJavaObject(j, SclEquipmentMaintenanceEntity.class);
			dataModel.put("sclPatyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("sclPatyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("sclPatyBs", j.get("sclPatyBs"));
			dataModel.put("sclAddress", j.get("sclAddress"));
			dataModel.put("sclProjectName", j.get("sclProjectName"));
			dataModel.put("sclHome", j.get("sclHome"));
			AtomicReference<Double> valuesD = new AtomicReference<>(0D);
			SclEquipmentMaintenance1.forEach(element -> {
				valuesD.updateAndGet(v -> v + BigDecimal.valueOf(Double.parseDouble(element.getSclPrice().toString())).multiply(BigDecimal.valueOf(Double.parseDouble(element.getSclNumbers().toString()))).doubleValue());
			});
			dataModel.put("sclTotalRmb", valuesD);
			dataModel.put("sclCapitalRmb", MoneyToChiness.moneyToChinese(String.valueOf(valuesD)));
			dataModel.put("sclNote", j.get("sclNote"));
			dataModel.put("sclEquipment", j.get("sclEquipment"));
			//1、累计运行时间维修保养
			if (j.get("sclEquipment").toString().contains("1")) {
				dataModel.put("sclMaintenancessss", j.get("sclMaintenancessss"));
				dataModel.put("sclGuaranteePeriod", j.get("sclGuaranteePeriod"));
			} else {
				dataModel.put("sclMaintenancessss", "＿");
				dataModel.put("sclGuaranteePeriod", "＿");
			}
			//2、单次维修保养
			if (j.get("sclEquipment").toString().contains("2")) {
				dataModel.put("sclQuality", j.get("sclQuality"));
			} else {
				dataModel.put("sclQuality", "＿");
			}
			//3、月度维修保养
			if (j.get("sclEquipment").toString().contains("3")) {
				dataModel.put("sclRoutineMaintenancess", j.get("sclRoutineMaintenancess"));
				dataModel.put("sclMonth", j.get("sclMonth"));
			} else {
				dataModel.put("sclRoutineMaintenancess", "＿");
				dataModel.put("sclMonth", "＿");
			}
			//4、季度维修保养
			if (j.get("sclEquipment").toString().contains("4")) {
				dataModel.put("sclMaintenance", j.get("sclMaintenance"));
				dataModel.put("sclPeriod", j.get("sclPeriod"));
			} else {
				dataModel.put("sclMaintenance", "＿");
				dataModel.put("sclPeriod", "＿");
			}
			//5、年度维修保养
			if (j.get("sclEquipment").toString().contains("5")) {
				dataModel.put("sclMaintenances", j.get("sclMaintenances"));
				dataModel.put("sclRoutine", j.get("sclRoutine"));
			} else {
				dataModel.put("sclMaintenances", "＿");
				dataModel.put("sclRoutine", "＿");
			}
			dataModel.put("sclCumulative", j.get("sclCumulative"));
			dataModel.put("sclSpecificallyAgreed", j.get("sclSpecificallyAgreed"));
			dataModel.put("sclContract", j.get("sclContract"));
			dataModel.put("sclPartyAs", j.get("sclPartyAs"));
			dataModel.put("sclServiceTelephone", j.get("sclServiceTelephone"));
			dataModel.put("sclShouldBeIn", j.get("sclShouldBeIn"));
			dataModel.put("sclMaintenanceq", j.get("sclMaintenanceq"));
			dataModel.put("sclSides", j.get("sclSides"));
			dataModel.put("sclPayment", j.get("sclPayment"));
			if (j.get("sclSides").toString().contains("(3)")) {
				dataModel.put("sclOtherWay", j.get("sclOtherWay"));
			} else {
				dataModel.put("sclOtherWay", "＿");
			}
			dataModel.put("sclBank", j.get("sclBank"));
			dataModel.put("sclName", j.get("sclName"));
			dataModel.put("sclAccount", j.get("sclAccount"));
			dataModel.put("sclContracts", j.get("sclContracts"));
			dataModel.put("sclLimit", j.get("sclLimit"));
			dataModel.put("sclGold", j.get("sclGold"));
			dataModel.put("sclThan", j.get("sclThan"));
			dataModel.put("sclBou", j.get("sclBou"));
			dataModel.put("sclStart", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("sclStart"))));
			dataModel.put("sclLaste", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("sclLaste"))));
			dataModel.put("sclCompany", j.get("sclCompany"));
			dataModel.put("sclAgreed", j.get("sclAgreed"));
			dataModel.put("sclFujian", j.get("sclFujian"));
			dataModel.put("sclFujian2", j.get("sclFujian2"));
			dataModel.put("sclFujian3", j.get("sclFujian3"));
			dataModel.put("sclLianxirenjia", j.get("sclLianxirenjia"));
			dataModel.put("sclLianxirenyi", j.get("sclLianxirenyi"));
			dataModel.put("sclLianxielejia", j.get("sclLianxielejia"));
			dataModel.put("sclLianxieleyi", j.get("sclLianxieleyi"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//物流服务合同（一段+调拨运输）
	FWHT_25("FWHT_25") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			SclProductionCategoryEntity sclProductionCategory = JSONObject.toJavaObject(j, SclProductionCategoryEntity.class);
			dataModel.put("sclPartyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("sclPartyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			//签订时间
			dataModel.put("sclDateOfSigning", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("sclDateOfSigning"))));
			//签订地点
			dataModel.put("sclSite", new TextRenderData((String) j.get("sclSite"), Style.builder().buildBold().build()));
			dataModel.put("sclTimes", j.get("sclTimes"));
			//食品
			dataModel.put("sclStorage", j.get("sclStorage"));
			//乳饮
			dataModel.put("sclArea", j.get("sclArea"));
			//保证金履行方式一
			switch (j.get("bondChooseOne").toString()) {
				case "1":
					dataModel.put("bondChooseOne", "☑");
					dataModel.put("sclNo", j.get("sclNo"));
					dataModel.put("sclStorageee", MoneyToChiness.tenThousand(j.get("sclNo").toString()));
					dataModel.put("bondChooseTow", "☐");
					dataModel.put("sclBail", "/");
					dataModel.put("sclStorageee1", "/");
					dataModel.put("bondChooseThree", "☐");
					dataModel.put("sclAffiliatedEnterprise", "/");
					dataModel.put("sclContract", "/");
					dataModel.put("sclDeposit", "/");
					dataModel.put("sclStorageee2", "/");
					break;
				//保证金履行方式二
				case "2":
					dataModel.put("bondChooseOne", "☐");
					dataModel.put("sclNo", "/");
					dataModel.put("sclStorageee", "/");
					dataModel.put("bondChooseTow", "☑");
					dataModel.put("sclBail", j.get("sclBail"));
					dataModel.put("sclStorageee1", MoneyToChiness.tenThousand(j.get("sclBail").toString()));
					dataModel.put("bondChooseThree", "☐");
					dataModel.put("sclAffiliatedEnterprise", "/");
					dataModel.put("sclContract", "/");
					dataModel.put("sclDeposit", "/");
					dataModel.put("sclStorageee2", "/");
					break;
				case "3":
					dataModel.put("bondChooseOne", "☐");
					dataModel.put("sclNo", "/");
					dataModel.put("sclStorageee", "/");
					dataModel.put("bondChooseTow", "☐");
					dataModel.put("sclBail", "/");
					dataModel.put("sclStorageee1", "/");
					dataModel.put("bondChooseThree", "☑");
					dataModel.put("sclAffiliatedEnterprise", j.get("sclAffiliatedEnterprise"));
					dataModel.put("sclContract", j.get("sclContract"));
					dataModel.put("sclDeposit", j.get("sclDeposit"));
					dataModel.put("sclStorageee2", MoneyToChiness.tenThousand(j.get("sclDeposit").toString()));
					break;
				case "":
					dataModel.put("bondChooseOne", "☐");
					dataModel.put("sclNo", "/");
					dataModel.put("sclStorageee", "/");
					dataModel.put("bondChooseTow", "☐");
					dataModel.put("sclBail", "/");
					dataModel.put("sclStorageee1", "/");
					dataModel.put("bondChooseThree", "☐");
					dataModel.put("sclAffiliatedEnterprise", "/");
					dataModel.put("sclContract", "/");
					dataModel.put("sclDeposit", "/");
					dataModel.put("sclStorageee2", "/");
					break;
			}
			//运费结算相关
			dataModel.put("sclConditionsa", j.get("sclConditionsa"));
			dataModel.put("sclNumber", j.get("sclNumber"));
			dataModel.put("sclServices", j.get("sclServices"));
			dataModel.put("sclFood", j.get("sclFood"));
			//这里是处理下拉选的字段的
			if ("1".equals(j.get("sclDrinks"))) {
				dataModel.put("sclDrinks", "☑中石化☐中石油");
			} else if ("2".equals(j.get("sclDrinks"))) {
				dataModel.put("sclDrinks", "☐中石化☑中石油");
			} else {
				dataModel.put("sclDrinks", "☐中石化☐中石油");
			}
			//本合同期限为
			dataModel.put("sclDateOfs", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getStartingTime())));
			dataModel.put("sclRequirementsss", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getEndTime())));
			//特别约定（本条约定与其他条款约定不一致或者冲突的，以本条约定为准）
			dataModel.put("sclConvention", j.get("sclConvention"));
			dataModel.put("sclJfAddress", j.get("sclJfAddress"));
			dataModel.put("sclYfAddress", j.get("sclYfAddress"));
			dataModel.put("sclJfPhone", j.get("sclJfPhone"));
			dataModel.put("sclYfPhone", j.get("sclYfPhone"));
			dataModel.put("sclJfContact", j.get("sclJfContact"));
			dataModel.put("sclYfContact", j.get("sclYfContact"));
			dataModel.put("sclJfEntrusted", j.get("sclJfEntrusted"));
			dataModel.put("sclYfEntrusted", j.get("sclYfEntrusted"));
			dataModel.put("sclCompany", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());

			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	/**
	 * TAG-2021-05-02
	 */
	//广告制作安装合同模板
	GGZZ_04("GGZZ_04") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			CglAdvertisementProductionEntity cglAdvertisementProductionEntity = JSONObject.toJavaObject(j, CglAdvertisementProductionEntity.class);
			dataModel.put("sclPartyA", j.get("sclPartyA"));
			dataModel.put("cglAddressA", j.get("cglAddressA"));
			dataModel.put("cglPartyB", j.get("cglPartyB"));
			dataModel.put("cglAddress", j.get("cglAddress"));
			dataModel.put("sclArea", j.get("sclArea"));
			dataModel.put("quotationSheet", j.get("quotationSheet"));
			dataModel.put("cglEmail", j.get("cglEmail"));
			dataModel.put("individual", j.get("individual"));
			dataModel.put("day", j.get("day"));
			dataModel.put("dayA", j.get("dayA"));
			dataModel.put("dayB", j.get("dayB"));
			dataModel.put("dayC", j.get("dayC"));
			dataModel.put("dayD", j.get("dayD"));
			dataModel.put("dayE", j.get("dayE"));
			dataModel.put("year", j.get("year"));
			dataModel.put("dayF", j.get("dayF"));
			dataModel.put("paymentMethod", j.get("paymentMethod"));
			dataModel.put("type", j.get("type"));
			dataModel.put("dayG", j.get("dayG"));
			dataModel.put("otherWays", j.get("otherWays"));
			dataModel.put("corporateName", j.get("corporateName"));
			dataModel.put("bankDeposit", j.get("bankDeposit"));
			dataModel.put("account", j.get("account"));
			dataModel.put("dayH", j.get("dayH"));
			dataModel.put("money", j.get("money"));
			dataModel.put("dayI", j.get("dayI"));
			dataModel.put("specialAgreement", j.get("specialAgreement"));
			dataModel.put("date", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date"))));
			dataModel.put("dateA", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("dateA"))));
			dataModel.put("number", j.get("number"));
			dataModel.put("numberA", j.get("numberA"));
			dataModel.put("numberB", j.get("numberB"));
			dataModel.put("cglAttachment", j.get("cglAttachment"));
			dataModel.put("cglAttachmentA", j.get("cglAttachmentA"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//采购类_打样合同书
	DYHT_03("DYHT_03") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<CglProofingContract1ResponseVO> CglProofingContract1 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//采购类_打样合同书 关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_CGLPROOFINGCONTRACT1.equals(templateField.getRelationCode())) {
					CglProofingContract1 = JSON.parseArray(templateField.getTableData(), CglProofingContract1ResponseVO.class);
//					for (int i = 0; i < cglProofingContract1List.size(); i++) {
//						JSONObject glProofingContract1 = JSON.parseObject(JSON.toJSONString(cglProofingContract1List.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("proofingProducts", glProofingContract1.get("proofingProducts"));
//						map.put("proofingContent", glProofingContract1.get("proofingContent"));
//						map.put("textureMaterial", glProofingContract1.get("textureMaterial"));
//						map.put("unitPrice", glProofingContract1.get("unitPrice"));
//						map.put("number", glProofingContract1.get("number"));
//						map.put("totalAmount", glProofingContract1.get("totalAmount"));
//						list.add(map);
//					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder().bind("CglProofingContract1", policy).build();
			dataModel.put("CglProofingContract1", CglProofingContract1);
			//主表
			CglProofingContractEntity cglProofingContract = JSONObject.toJavaObject(j, CglProofingContractEntity.class);
			dataModel.put("partyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("partyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			AtomicReference<Double> valuesD = new AtomicReference<>(0D);
			CglProofingContract1.forEach(element -> {
				valuesD.updateAndGet(v -> v + Double.parseDouble(element.getTotalAmount().toString()));
			});
			dataModel.put("element", valuesD);
			dataModel.put("date", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("date"))));
			dataModel.put("element1", Func.isNull(contractFormInfoEntity.getContractAmount()) ? 0 : contractFormInfoEntity.getContractAmount());
			dataModel.put("taxRate", Func.isNull(contractFormInfoEntity.getContactTaxRate()) ? 0 : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("element2", Func.isNull(contractFormInfoEntity.getContractTaxAmount()) ? 0 : contractFormInfoEntity.getContractTaxAmount());
			dataModel.put("accountName", j.get("accountName"));
			dataModel.put("accountNumber", j.get("accountNumber"));
			dataModel.put("bankDeposit", j.get("bankDeposit"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//协议（二方）
	BMXY_01("BMXY_01") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<Map<String, Object>> list = new ArrayList();
			dataModel.put("contactPartyA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("contactPartyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("productNameA", j.get("productNameA"));
			dataModel.put("productContentA", j.get("productContentA"));
			dataModel.put("productNameB", j.get("productNameB"));
			dataModel.put("productContentB", j.get("productContentB"));
			dataModel.put("otherEnterprises", j.get("otherEnterprises"));
			dataModel.put("otherAgreements", j.get("otherAgreements"));
			dataModel.put("specificDate", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("specificDate"))));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//保密协议（三方）
	BMXY_02("BMXY_02") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<Map<String, Object>> list = new ArrayList();
			dataModel.put("jiaFang", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("tradeSide", j.get("tradeSide"));
			dataModel.put("manufacturer", j.get("manufacturer"));
			dataModel.put("productInvolved", j.get("productInvolved"));
			dataModel.put("contentsInformation", j.get("contentsInformation"));
			dataModel.put("productInvolvedA", j.get("productInvolvedA"));
			dataModel.put("contentsInformationA", j.get("contentsInformationA"));
			dataModel.put("ownershipSubject", j.get("ownershipSubject"));
			dataModel.put("blankField", j.get("blankField"));
			dataModel.put("otherAgreements", j.get("otherAgreements"));
			dataModel.put("fcUsingRange", j.get("fcUsingRange"));
			dataModel.put("fbUsingRange", j.get("fbUsingRange"));
			dataModel.put("scUsingRange", j.get("scUsingRange"));
			dataModel.put("sbUsingRange", j.get("sbUsingRange"));
			dataModel.put("specificDate", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("specificDate"))));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//房屋租赁合同模板
	FWZL_36("FWZL_36") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			YwbBusinessContractTemplateEntity ywbBusinessContractTemplateEntity = JSONObject.toJavaObject(j, YwbBusinessContractTemplateEntity.class);
			dataModel.put("ywbTenantry", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("ywbLessors", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("ywbCertificate", j.get("ywbCertificate"));
			dataModel.put("ywbAddress", j.get("ywbAddress"));
			dataModel.put("ywbCertificateB", j.get("ywbCertificateB"));
			dataModel.put("ywbResidence", j.get("ywbResidence"));
			dataModel.put("ywbAgrees", j.get("ywbAgrees"));
			dataModel.put("ywbBuiltupArea", j.get("ywbBuiltupArea"));
			dataModel.put("ywbRooms", j.get("ywbRooms"));
			dataModel.put("ywbPartyRoom", j.get("ywbPartyRoom"));
			dataModel.put("ywbTermStart", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getStartingTime())));
			dataModel.put("ywbTermEnd", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getEndTime())));
			dataModel.put("ywbShall", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getEndTime())));
			//移交房门钥匙及{ywbJiaofu}后视为交付完成。
			dataModel.put("ywbJiaofu", j.get("ywbJiaofu"));
			//合同期满后，乙方继续承租的，应提前{ywbRequirement}日向甲方提出续租要求,
			dataModel.put("ywbRequirement", j.get("ywbRequirement"));
			//第三条、租金及相关费用支付
			//租金金额
			dataModel.put("ywbStandard", j.get("ywbStandard"));
			//租金总计（含${ywbShuilv}%税）：
			dataModel.put("ywbShuilv", j.get("ywbShuilv"));
			if (j.get("ywbPayment").toString().contains("1")) {
				BigDecimal sum = BigDecimal.valueOf(Double.parseDouble(String.valueOf(j.get("ywbStandard"))))
					.multiply(BigDecimal.valueOf(Double.parseDouble(j.get("ywbShuilv").toString()) / 100 + 1))
					.multiply(BigDecimal.valueOf(getMonthDiff(
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getStartingTime())),
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getEndTime())))));
				dataModel.put("ywbPayment", "☑月/☐季/☐半年/☐年");
				dataModel.put("ywbZujinjine", sum);
				//租金金额大写
				dataModel.put("ywbZujinjined", MoneyToChiness.moneyToChinese(sum.toString()));
			}
			if (j.get("ywbPayment").toString().contains("2")) {
				BigDecimal sum = BigDecimal.valueOf(Double.parseDouble(String.valueOf(j.get("ywbStandard"))))
					.multiply(BigDecimal.valueOf(Double.parseDouble(j.get("ywbShuilv").toString()) / 100 + 1))
					.multiply(BigDecimal.valueOf(getMonthDiff(
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getStartingTime())),
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getEndTime()))
					) / 3));
				dataModel.put("ywbPayment", "☐月/☑季/☐半年/☐年");
				dataModel.put("ywbZujinjine", sum);
				dataModel.put("ywbZujinjined", MoneyToChiness.moneyToChinese(sum.toString()));
			}
			if (j.get("ywbPayment").toString().contains("3")) {
				BigDecimal sum = BigDecimal.valueOf(Double.parseDouble(String.valueOf(j.get("ywbStandard"))))
					.multiply(BigDecimal.valueOf(Double.parseDouble(j.get("ywbShuilv").toString()) / 100 + 1))
					.multiply(BigDecimal.valueOf(getMonthDiff(
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getStartingTime())),
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getEndTime()))
					) / 6));
				dataModel.put("ywbPayment", "☐月/☐季/☑半年/☐年");
				dataModel.put("ywbZujinjine", sum);
				dataModel.put("ywbZujinjined", MoneyToChiness.moneyToChinese(sum.toString()));
			}
			if (j.get("ywbPayment").toString().contains("4")) {
				BigDecimal sum = BigDecimal.valueOf(Double.parseDouble(String.valueOf(j.get("ywbStandard"))))
					.multiply(BigDecimal.valueOf(Double.parseDouble(j.get("ywbShuilv").toString()) / 100 + 1))
					.multiply(BigDecimal.valueOf(getMonthDiff(
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getStartingTime())),
						DataFormatUtils.GLNZTimeFormatBar(String.valueOf(contractFormInfoEntity.getEndTime()))
					) / 12));
				dataModel.put("ywbPayment", "☐月/☐季/☐半年/☑年");
				dataModel.put("ywbZujinjine", sum);
				dataModel.put("ywbZujinjined", MoneyToChiness.moneyToChinese(sum.toString()));
			}
			if (j.get("ywbPayment").toString().equals("")) {
				dataModel.put("ywbPayment", "☐月/☐季/☐半年/☐年");
				dataModel.put("ywbZujinjine", " ");
				dataModel.put("ywbZujinjined", " ");
			}
			//租金支付方式：乙方以银行汇款的方式按（${ywbBankRemittance}）支付；
			if (j.get("ywbBankRemittance").toString().contains("1")) {
				dataModel.put("ywbBankRemittance", "☑月/☐季/☐半年/☐年");
			}
			if (j.get("ywbBankRemittance").toString().contains("2")) {
				dataModel.put("ywbBankRemittance", "☐月/☑季/☐半年/☐年");
			}
			if (j.get("ywbBankRemittance").toString().contains("3")) {
				dataModel.put("ywbBankRemittance", "☐月/☐季/☑半年/☐年");
			}
			if (j.get("ywbBankRemittance").toString().contains("4")) {
				dataModel.put("ywbBankRemittance", "☐月/☐季/☐半年/☑年");
			}
			if (j.get("ywbBankRemittance").equals("")) {
				dataModel.put("ywbBankRemittance", "☐月/☐季/☐半年/☐年");
			}
			//3、租金支付时间：
			dataModel.put("ywbMdmbsContract", j.get("ywbMdmbsContract"));
			dataModel.put("ywbHmdbfPrevious", j.get("ywbHmdbfPrevious"));
			//4、押金：
			dataModel.put("ywbShallRmba", j.get("ywbShallRmba"));
			dataModel.put("ywbShallCapitali", MoneyToChiness.moneyToChinese(j.get("ywbShallRmba").toString()));
			dataModel.put("ywbProportion", j.get("ywbProportion"));
			//5、租赁期内的因使用租赁物而产生的下列费用中 甲方承担：
			StringBuilder putEquipment = new StringBuilder();
			if (j.get("ywbTerminclude").toString().contains("1")) {
				putEquipment.append("(1) ");
			}
			if (j.get("ywbTerminclude").toString().contains("2")) {
				putEquipment.append("(2) ");
			}
			if (j.get("ywbTerminclude").toString().contains("3")) {
				putEquipment.append("(3) ");
			}
			if (j.get("ywbTerminclude").toString().contains("4")) {
				putEquipment.append("(4) ");
			}
			if (j.get("ywbTerminclude").toString().contains("5")) {
				putEquipment.append("(5) ");
			}
			if (j.get("ywbTerminclude").toString().contains("6")) {
				putEquipment.append("(6) ");
			}
			if (j.get("ywbTerminclude").equals("[]")) {
				putEquipment.append("————");
			}
			dataModel.put("ywbTerminclude", putEquipment.toString());
			//5、租赁期内的因使用租赁物而产生的下列费用中 乙方承担：
			StringBuilder ywbPeriod = new StringBuilder();
			if (j.get("ywbPeriod").toString().contains("1")) {
				ywbPeriod.append("(1) ");
			}
			if (j.get("ywbPeriod").toString().contains("2")) {
				ywbPeriod.append("(2) ");
			}
			if (j.get("ywbPeriod").toString().contains("3")) {
				ywbPeriod.append("(3) ");
			}
			if (j.get("ywbPeriod").toString().contains("4")) {
				ywbPeriod.append("(4) ");
			}
			if (j.get("ywbPeriod").toString().contains("5")) {
				ywbPeriod.append("(5) ");
			}
			if (j.get("ywbPeriod").toString().contains("6")) {
				ywbPeriod.append("(6) ");
			}
			if (j.get("ywbPeriod").equals("[]")) {
				ywbPeriod.append("————");
			}
			dataModel.put("ywbPeriod", ywbPeriod.toString());
			dataModel.put("ywbRequirementaes", j.get("ywbRequirementaes"));
			dataModel.put("ywbCompensationaes", j.get("ywbCompensationaes"));
			dataModel.put("ywbTimeRequirement", j.get("ywbTimeRequirement"));
			dataModel.put("ywbAgreements", j.get("ywbAgreements"));
			dataModel.put("ywbHmcopies", j.get("ywbHmcopies"));
			dataModel.put("ywbHmcopiesaes", j.get("ywbHmcopiesaes"));
			dataModel.put("ywbHmcopiesbes", j.get("ywbHmcopiesbes"));
			dataModel.put("ywbQianmingjia", j.get("ywbQianmingjia"));
			dataModel.put("ywbQianmingyi", j.get("ywbQianmingyi"));
			dataModel.put("ywbQianmingdai", j.get("ywbQianmingdai"));
			dataModel.put("ywbQianmingdaia", j.get("ywbQianmingdaia"));
			dataModel.put("ywbKaihuhangjia", j.get("ywbKaihuhangjia"));
			dataModel.put("ywbKaihuhangjiaa", j.get("ywbKaihuhangjiaa"));
			dataModel.put("ywbZhanghaojia", j.get("ywbZhanghaojia"));
			dataModel.put("ywbZhanghaoyi", j.get("ywbZhanghaoyi"));
			dataModel.put("ywbDianhuajia", j.get("ywbDianhuajia"));
			dataModel.put("ywbDianhuayi", j.get("ywbDianhuayi"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//下脚品买卖合同模版
	MMHT_26("MMHT_26") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			dataModel.put("infSaler", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("infBuyer", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("infSalerAddr", j.get("infSalerAddr"));
			dataModel.put("infBuyerAddr", j.get("infBuyerAddr"));
			dataModel.put("infTypeFir", j.get("infTypeFir"));
			//周选择
			dataModel.put("infWeekStart", j.get("infWeekStart"));
			dataModel.put("infWeekEnd", j.get("infWeekEnd"));
			//时间选择
			dataModel.put("infTimeStart", DataFormatUtils.systemTimeFormatH(j.get("infTimeStart").toString()));
			dataModel.put("infTimeEnd", DataFormatUtils.systemTimeFormatH(j.get("infTimeEnd").toString()));
			dataModel.put("infSortAddr", j.get("infSortAddr"));
			dataModel.put("infLeastAmount", j.get("infLeastAmount"));
			dataModel.put("infTimeAmount", j.get("infTimeAmount"));
			dataModel.put("infLoadAddr", j.get("infLoadAddr"));
			//履约保证金相关
			if (contractFormInfoEntity.getContractBond().size() <= 0) {
				dataModel.put("infAppointAmount", "");
				dataModel.put("infCap", "");
				dataModel.put("infBreachAmountFir", "");
				dataModel.put("infBreachAmountSec", "");
			} else if (contractFormInfoEntity.getContractBond().size() >= 1) {
				if (contractFormInfoEntity.getContractBond().get(0).getIsNotBond().contains("1")) {
					dataModel.put("infAppointAmount", "_");
					dataModel.put("infCap", "_");
					dataModel.put("infBreachAmountFir", "_");
					dataModel.put("infBreachAmountSec", "_");
				} else if (contractFormInfoEntity.getContractBond().get(0).getIsNotBond().contains("0")) {
					String planPayAmount = String.valueOf(contractFormInfoEntity.getContractBond().get(0).getPlanPayAmount().divide(BigDecimal.valueOf(10000)));
					dataModel.put("infAppointAmount", planPayAmount);
					dataModel.put("infCap", MoneyToChiness.tenThousand(planPayAmount));
					dataModel.put("infBreachAmountFir", planPayAmount);
					dataModel.put("infBreachAmountSec", planPayAmount);
				}
			} else if (contractFormInfoEntity.getContractBond().size() >= 2) {
				AtomicReference<Double> valuesD = new AtomicReference<>(0D);
				contractFormInfoEntity.getContractBond().forEach(element -> {
					valuesD.updateAndGet(v -> v + Double.parseDouble(String.valueOf(element.getPlanPayAmount().divide(BigDecimal.valueOf(10000)))));
				});
				dataModel.put("infAppointAmount", valuesD);
				dataModel.put("infCap", MoneyToChiness.tenThousand(String.valueOf(valuesD)));
				dataModel.put("infBreachAmountFir", valuesD);
				dataModel.put("infBreachAmountSec", valuesD);
			}
			dataModel.put("infTimeLeastFir", j.get("infTimeLeastFir"));
			dataModel.put("infTimeLeastSec", j.get("infTimeLeastSec"));
			dataModel.put("infSalerAccoutName", j.get("infSalerAccoutName"));
			dataModel.put("infBuyerAccoutName", j.get("infBuyerAccoutName"));
			dataModel.put("infSalerAccoutId", j.get("infSalerAccoutId"));
			dataModel.put("infBuyerAccoutId", j.get("infBuyerAccoutId"));
			dataModel.put("infSalerAccoutBank", j.get("infSalerAccoutBank"));
			dataModel.put("infBuyerAccoutBank", j.get("infBuyerAccoutBank"));
			dataModel.put("infTimeLeastThi", j.get("infTimeLeastThi"));
			dataModel.put("infSalerMail", j.get("infSalerMail"));
			dataModel.put("infBuyerMail", j.get("infBuyerMail"));
			dataModel.put("infBreachAmountThi", j.get("infBreachAmountThi"));
			dataModel.put("infBreachAmountFou", j.get("infBreachAmountFou"));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dataModel.put("infContractStart", null == (contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getStartingTime())));
			dataModel.put("infContractEnd", null == (contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.systemTimeFormat(simpleDateFormat.format(contractFormInfoEntity.getEndTime())));
			dataModel.put("infContractNum", j.get("infContractNum"));
			dataModel.put("infContractSum", j.get("infContractSum"));
			dataModel.put("infSalerPhone", j.get("infSalerPhone"));
			dataModel.put("infBuyerPhone", j.get("infBuyerPhone"));
			dataModel.put("infSalerPerson", j.get("infSalerPerson"));
			dataModel.put("infBuyerPerson", j.get("infBuyerPerson"));
			//承诺书（废面、废油、废茶渣项目需填写此项，一般下脚品填写“无”即可）
			dataModel.put("infSurf", j.get("infSurf"));
			dataModel.put("infOil", j.get("infOil"));
			dataModel.put("infTeaSurf", j.get("infTeaSurf"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//配送服务合同
	FWHT_38("FWHT_38") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			DistServiceContractEntity sclLogisticsService = JSONObject.toJavaObject(j, DistServiceContractEntity.class);
			dataModel.put("clientA", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("trusteeB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("clientAddress", j.get("clientAddress"));
			dataModel.put("trusteeAddress", j.get("trusteeAddress"));
			dataModel.put("designatedAddress", j.get("designatedAddress"));
			dataModel.put("several", j.get("several"));
			dataModel.put("inWay", j.get("inWay"));
			dataModel.put("validityContractA", Func.isNull(contractFormInfoEntity.getStartingTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getStartingTime())));
			dataModel.put("validityContractB", Func.isNull(contractFormInfoEntity.getEndTime()) ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(contractFormInfoEntity.getEndTime())));
			dataModel.put("clientTelephone", j.get("clientTelephone"));
			dataModel.put("trusteeTelephone", j.get("trusteeTelephone"));
			dataModel.put("clientFax", j.get("clientFax"));
			dataModel.put("trusteeFax", j.get("trusteeFax"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//生产项目外包服务合同
	FWHT_22("FWHT_22") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<ProductOutServiceContract1ResponseVO> ProductOutServiceContract1 = new ArrayList();
			List<ProductOutServiceContract2ResponseVO> ProductOutServiceContract2 = new ArrayList();
			List<ProductOutServiceContract3ResponseVO> ProductOutServiceContract3 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//生产项目外包服务合同关联表1
				if (ContractFormInfoTemplateContract.CONTRACT_PRODUCTOUTSERVICECONTRACT1.equals(templateField.getRelationCode())) {
					ProductOutServiceContract1 = JSON.parseArray(templateField.getTableData(), ProductOutServiceContract1ResponseVO.class);
					for (int i = 0; i < ProductOutServiceContract1.size(); i++) {
						ProductOutServiceContract1.get(i).setNum(i+1);
//						JSONObject productOutServiceContract1 = JSON.parseObject(JSON.toJSONString(productOutServiceContract1ResponseVOList.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("Num", i + 1);
//						map.put("name", productOutServiceContract1.get("name"));
//						map.put("unit", productOutServiceContract1.get("unit"));
//						map.put("unitPrice", productOutServiceContract1.get("unitPrice"));
//						map.put("content", productOutServiceContract1.get("content"));
//						list.add(map);
					}
				}
				//生产项目外包服务合同关联表2
				if (ContractFormInfoTemplateContract.CONTRACT_PRODUCTOUTSERVICECONTRACT2.equals(templateField.getRelationCode())) {
					ProductOutServiceContract2 = JSON.parseArray(templateField.getTableData(), ProductOutServiceContract2ResponseVO.class);
					for (int i = 0; i < ProductOutServiceContract2.size(); i++) {
						ProductOutServiceContract2.get(i).setRewardNum(i+1);
//						JSONObject productOutServiceContract2 = JSON.parseObject(JSON.toJSONString(productOutServiceContract2ResponseVOList.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("rewardNum", i + 1);
//						map.put("rewardContent", productOutServiceContract2.get("rewardContent"));
//						map.put("rewardAmount", productOutServiceContract2.get("rewardAmount"));
//						list1.add(map);
					}
				}
				//生产项目外包服务合同关联表3
				if (ContractFormInfoTemplateContract.CONTRACT_PRODUCTOUTSERVICECONTRACT3.equals(templateField.getRelationCode())) {
					ProductOutServiceContract3 = JSON.parseArray(templateField.getTableData(), ProductOutServiceContract3ResponseVO.class);
					for (int i = 0; i < ProductOutServiceContract3.size(); i++) {
						ProductOutServiceContract3.get(i).setPunishNum(i+1);
//						JSONObject productOutServiceContract3 = JSON.parseObject(JSON.toJSONString(productOutServiceContract3ResponseVOList.get(i), filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						map.put("punishNum", i + 1);
//						map.put("punishContent", productOutServiceContract3.get("punishContent"));
//						map.put("punishAmount", productOutServiceContract3.get("punishAmount"));
//						list2.add(map);
					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder()
				.bind("ProductOutServiceContract1", policy).bind("ProductOutServiceContract2", policy).bind("ProductOutServiceContract3", policy).build();
			dataModel.put("ProductOutServiceContract1", ProductOutServiceContract1);
			dataModel.put("ProductOutServiceContract2", ProductOutServiceContract2);
			dataModel.put("ProductOutServiceContract3", ProductOutServiceContract3);
			//主表
			ProductOutServiceContractEntity productOutServiceContractEntity = JSONObject.toJavaObject(j, ProductOutServiceContractEntity.class);
			dataModel.put("proSaler", j.get("proSaler"));
			dataModel.put("proSalerAddr", j.get("proSalerAddr"));
			dataModel.put("proBuyer", j.get("proBuyer"));
			dataModel.put("proBuyerAddr", j.get("proBuyerAddr"));
			dataModel.put("proTimeStart", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("proTimeStart"))));
			dataModel.put("proTimeEnd", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("proTimeEnd"))));
			dataModel.put("proAmount", j.get("proAmount"));
			dataModel.put("proAgeRequire", j.get("proAgeRequire"));
			dataModel.put("proMaxPercent", j.get("proMaxPercent"));
			dataModel.put("proTimeAmount", j.get("proTimeAmount"));
			dataModel.put("proBuyerPerson", j.get("proBuyerPerson"));
			dataModel.put("proBuyerPhone", j.get("proBuyerPhone"));
			dataModel.put("proLastDayFir", j.get("proLastDayFir"));
			dataModel.put("proLastDaySec", j.get("proLastDaySec"));
			dataModel.put("proLastDayThi", j.get("proLastDayThi"));
			dataModel.put("inPayType", j.get("inPayType"));
			dataModel.put("proBuyerAccountName", j.get("proBuyerAccountName"));
			dataModel.put("proBuyerAccountId", j.get("proBuyerAccountId"));
			dataModel.put("proBuyerAccountBank", j.get("proBuyerAccountBank"));
			dataModel.put("proBondAmountFir", j.get("proBondAmountFir"));
			dataModel.put("proLastDayFou", j.get("proLastDayFou"));
			dataModel.put("proLastDayFiv", j.get("proLastDayFiv"));
			dataModel.put("proSalerAccoutName", j.get("proSalerAccoutName"));
			dataModel.put("proSalerAccoutId", j.get("proSalerAccoutId"));
			dataModel.put("proSalerAccoutBank", j.get("proSalerAccoutBank"));
			dataModel.put("proBondAmountSec", j.get("proBondAmountSec"));
			dataModel.put("proBondAmountThi", j.get("proBondAmountThi"));
			dataModel.put("proBondAmountFou", j.get("proBondAmountFou"));
			dataModel.put("proBondAmountFiv", j.get("proBondAmountFiv"));
			dataModel.put("proBondAmountSix", j.get("proBondAmountSix"));
			dataModel.put("proSupplyArrange", j.get("proSupplyArrange"));
			dataModel.put("proAnnexFir", j.get("proAnnexFir"));
			dataModel.put("proAnnexSec", j.get("proAnnexSec"));

			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//设备投放使用协议
	SBTF_40("SBTF_40") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			DeviceLaunchUseContractEntity ywbBusinessContractTemplateEntity = JSONObject.toJavaObject(j, DeviceLaunchUseContractEntity.class);
			dataModel.put("devSaler", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("devBuyer", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("devSalerAddr", j.get("devSalerAddr"));
			dataModel.put("devBuyerAddr", j.get("devBuyerAddr"));
			dataModel.put("devBuyerNum", getCounterpart(contractFormInfoEntity).get("legalRepresentative").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("legalRepresentative").get(0));
			dataModel.put("devNumber", j.get("devNumber"));
			dataModel.put("devNumberInWord", MoneyToChiness.moneyToChinese(String.valueOf(j.get("devNumber"))));
			if ("新投放本次有收押".equals(j.get("newRelease").toString())) {
				dataModel.put("newRelease", "☑");
				dataModel.put("agreementRenewal", "☐");
			} else {
				dataModel.put("newRelease", "☐");
				dataModel.put("agreementRenewal", "☑");
			}
			dataModel.put("devBrand", j.get("devBrand"));
			dataModel.put("devModel", j.get("devModel"));
			dataModel.put("devValue", j.get("devValue"));
			dataModel.put("devBorroStart", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("devBorroStart"))));
			dataModel.put("devBorroEnd", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("devBorroEnd"))));
			dataModel.put("devCode", j.get("devCode"));
			//设备投放勾选
			StringBuilder putEquipment = new StringBuilder();
			if (j.get("putEquipment").toString().contains("1")) {
				putEquipment.append("☑冰箱,");
			} else {
				putEquipment.append("☐冰箱,");
			}
			if (j.get("putEquipment").toString().contains("2")) {
				putEquipment.append("☑水冷柜（卧柜）,");
			} else {
				putEquipment.append("☐水冷柜（卧柜）,");
			}
			if (j.get("putEquipment").toString().contains("3")) {
				putEquipment.append("☑冰桶,");
			} else {
				putEquipment.append("☐冰桶,");
			}
			if (j.get("putEquipment").toString().contains("4")) {
				putEquipment.append("☑风幕柜,");
			} else {
				putEquipment.append("☐风幕柜,");
			}
			if (j.get("putEquipment").toString().contains("5")) {
				putEquipment.append("☑热饮机,");
			} else {
				putEquipment.append("☐热饮机,");
			}
			if (j.get("putEquipment").toString().contains("6")) {
				putEquipment.append("☑冷热一体机");
			} else {
				putEquipment.append("☐冷热一体机");
			}
			dataModel.put("putEquipment", putEquipment.toString());
			//设备摆放位置勾选
			StringBuilder devPlace = new StringBuilder();
			if (j.get("devPlace").toString().contains("1")) {
				devPlace.append("☑第一层,");
			} else {
				devPlace.append("☐第一层,");
			}
			if (j.get("devPlace").toString().contains("2")) {
				devPlace.append("☑第二层,");
			} else {
				devPlace.append("☐第二层,");
			}
			if (j.get("devPlace").toString().contains("3")) {
				devPlace.append("☑第三层,");
			} else {
				devPlace.append("☐第三层,");
			}
			if (j.get("devPlace").toString().contains("4")) {
				devPlace.append("☑第四层,");
			} else {
				devPlace.append("☐第四层,");
			}
			if (j.get("devPlace").toString().contains("5")) {
				devPlace.append("☑第五层,");
			} else {
				devPlace.append("☐第五层,");
			}
			if (j.get("devPlace").toString().contains("6")) {
				devPlace.append("☑第六层,");
			} else {
				devPlace.append("☐第六层,");
			}
			dataModel.put("devPlace", devPlace.toString());
			//设备投放值
			StringBuilder devPlaceValue = new StringBuilder();
			for (int i = 1; i <= 6; i++) {
				if (j.get("devPlace").toString().contains(String.valueOf(i))) {
					devPlaceValue.append("第" + i + "层,");
				}
			}
			dataModel.put("devPlaceValue", Func.isBlank(devPlaceValue.toString()) ? "" : devPlaceValue.toString().substring(0, devPlaceValue.length() - 1));
			dataModel.put("devLeastDate", j.get("devLeastDate"));
			dataModel.put("devDeposit", j.get("devDeposit"));
			dataModel.put("devDepositInWord", MoneyToChiness.moneyToChinese(j.get("devDeposit").toString()));
// 签约人    dataModel.put("devSalerPerson",j.get("devSalerPerson"));
// 签订时间  dataModel.put("devSalerTime",DataFormatUtils.systemTimeFormat(String.valueOf(j.get("devSalerTime"))));
// 负责人    dataModel.put("devBuyerPerson",j.get("devBuyerPerson"));
// 签订时间  dataModel.put("devBuyerTime",DataFormatUtils.systemTimeFormat(String.valueOf(j.get("devBuyerTime"))));
			dataModel.put("otherAgreements", j.get("otherAgreements"));

			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//班车服务合同
	FWHT_51("FWHT_51") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle = new HashMap();
			Map dataModel = new HashMap();
			//主表
			BusServiceContractEntity productOutServiceContractEntity = JSONObject.toJavaObject(j, BusServiceContractEntity.class);
			dataModel.put("busSaler", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("busBuyer", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("busSalerAddr", j.get("busSalerAddr"));
			dataModel.put("busBuyerAddr", j.get("busBuyerAddr"));
			dataModel.put("busTimeA", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("busTimeA"))));
			dataModel.put("busTimeB", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("busTimeB"))));
			dataModel.put("busServiceTimeStart", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("busServiceTimeStart"))));
			dataModel.put("busServiceTimeEnd", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("busServiceTimeEnd"))));
			dataModel.put("busDateRequireFir", j.get("busDateRequireFir"));
			dataModel.put("busInvoiceType", j.get("busInvoiceType"));
			dataModel.put("busBuyerAccountName", j.get("busBuyerAccountName"));
			dataModel.put("busBuyerAccountId", j.get("busBuyerAccountId"));
			dataModel.put("busBuyerAccountBank", j.get("busBuyerAccountBank"));
			dataModel.put("busDateRequireSec", j.get("busDateRequireSec"));
			dataModel.put("infAnnexFir", j.get("infAnnexFir"));

			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	//市调合同（定性+定量）
	SDHT_13("SDHT_13") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<MtbMarketResearchContract1ResponseVO> IMtbMarketResearchContract1 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//市调合同（定性+定量） 关联表1
				if (ContractFormInfoTemplateContract.CONTRAT_IMTBMARKETRESEARCHCONTRACT1.equals(templateField.getRelationCode())) {
					IMtbMarketResearchContract1 = JSON.parseArray(templateField.getTableData(), MtbMarketResearchContract1ResponseVO.class);
//					for (int i = 0; i < mtbMarketResearchContract1ResponseVOList.size(); i++) {
						//JSONObject mtbProductionContract= JSON.parseObject(JSON.toJSONString(mtbMarketResearchContract1ResponseVOList.get(i),filter, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty));
//						Map<String, Object> map = new HashMap();
//						mtbMarketResearchContract1ResponseVOList.get(i).setMtbTime(null == mtbMarketResearchContract1ResponseVOList.get(i).getMtbTime() ? "" : DataFormatUtils.GLNZTimeFormat(String.valueOf(mtbMarketResearchContract1ResponseVOList.get(i).getMtbTime())));
//						mtbMarketResearchContract1ResponseVOList.get(i).setMtbMatter(null == mtbMarketResearchContract1ResponseVOList.get(i).getMtbMatter() ? "" : mtbMarketResearchContract1ResponseVOList.get(i).getMtbMatter());
//						list.add(map);
//					}
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder().bind("IMtbMarketResearchContract1", policy).build();
			dataModel.put("IMtbMarketResearchContract1", IMtbMarketResearchContract1);
			//主表
			MtbMarketResearchContractEntity mtbMarketResearchContractEntity = JSONObject.toJavaObject(j, MtbMarketResearchContractEntity.class);
			dataModel.put("patya", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("patyb", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("partyAddress", j.get("partyAddress"));
			dataModel.put("patyAddressb", j.get("patyAddressb"));
			dataModel.put("number", j.get("number"));
			dataModel.put("patyPhoneb", j.get("patyPhoneb"));
			dataModel.put("patyFax", j.get("patyFax"));
			dataModel.put("patyFaxb", j.get("patyFaxb"));
			dataModel.put("projectName", j.get("projectName"));
			dataModel.put("attachment", j.get("attachment"));
			//研究方法：单选
			if ("定性研究".equals(j.get("research").toString())) {
				dataModel.put("research", "☑");
				dataModel.put("methods", "☐");
				dataModel.put("element", j.get("element"));
				dataModel.put("element1", j.get("element1"));
				dataModel.put("element2", j.get("element2"));
				dataModel.put("element3", j.get("element3"));
				dataModel.put("element4", j.get("element4"));
				dataModel.put("element5", BigDecimal.valueOf(
					Double.parseDouble("".equals(j.get("element").toString()) ? "0.0" : j.get("element").toString())
						+ Double.parseDouble("".equals(j.get("element1").toString()) ? "0.0" : j.get("element1").toString())
						+ Double.parseDouble("".equals(j.get("element2").toString()) ? "0.0" : j.get("element2").toString())
						+ Double.parseDouble("".equals(j.get("element3").toString()) ? "0.0" : j.get("element3").toString())
						+ Double.parseDouble("".equals(j.get("element4").toString()) ? "0.0" : j.get("element4").toString())).multiply(
					BigDecimal.valueOf(null == contractFormInfoEntity.getContactTaxRate() ? 1 : contractFormInfoEntity.getContactTaxRate() + 1)
				));
				dataModel.put("element6", "");
				dataModel.put("element7", "");
				dataModel.put("element8", "");
				dataModel.put("element9", "");
				dataModel.put("element10", "");
				dataModel.put("element11", "");
				dataModel.put("element12", "");
			} else {
				dataModel.put("research", "☐");
				dataModel.put("methods", "☑");
				dataModel.put("element6", j.get("element6"));
				dataModel.put("element7", j.get("element7"));
				dataModel.put("element8", j.get("element8"));
				dataModel.put("element9", j.get("element9"));
				dataModel.put("element10", j.get("element10"));
				dataModel.put("element11", j.get("element11"));
				dataModel.put("element12", BigDecimal.valueOf(
					Double.parseDouble("".equals(j.get("element6").toString()) ? "0.0" : j.get("element6").toString())
						+ Double.parseDouble("".equals(j.get("element7").toString()) ? "0.0" : j.get("element7").toString())
						+ Double.parseDouble("".equals(j.get("element8").toString()) ? "0.0" : j.get("element8").toString())
						+ Double.parseDouble("".equals(j.get("element9").toString()) ? "0.0" : j.get("element9").toString())
						+ Double.parseDouble("".equals(j.get("element11").toString()) ? "0.0" : j.get("element11").toString())
						+ Double.parseDouble("".equals(j.get("element10").toString()) ? "0.0" : j.get("element10").toString())).multiply(
					BigDecimal.valueOf(null == contractFormInfoEntity.getContactTaxRate() ? 1 : contractFormInfoEntity.getContactTaxRate() + 1)
				));
				dataModel.put("element", "");
				dataModel.put("element1", "");
				dataModel.put("element2", "");
				dataModel.put("element3", "");
				dataModel.put("element4", "");
				dataModel.put("element5", "");
			}
			dataModel.put("numberRespondents", j.get("numberRespondents"));
			dataModel.put("requireRespondents", j.get("requireRespondents"));
			//乙方需按照本合同约定的时间安排向甲方以书面的形式提交以下成果物：多选
			if (StringUtils.join(j.get("results"), "-").contains("调研简报")) {
				dataModel.put("results", "☑");
			} else {
				dataModel.put("results", "☐");
			}
			if (StringUtils.join(j.get("results"), "-").contains("调研报告(PPT中文报告)")) {
				dataModel.put("research1", "☑");
			} else {
				dataModel.put("research1", "☐");
			}
			if (StringUtils.join(j.get("results"), "-").contains("视频录像文件")) {
				dataModel.put("video", "☑");
			} else {
				dataModel.put("video", "☐");
			}
			if (StringUtils.join(j.get("results"), "-").contains("定性研究现场原始问答记录")) {
				dataModel.put("qualitative", "☑");
			} else {
				dataModel.put("qualitative", "☐");
			}
			if (StringUtils.join(j.get("results"), "-").contains("定量研究原始数据记录")) {
				dataModel.put("quantitative1", "☑");
			} else {
				dataModel.put("quantitative1", "☐");
			}
			if (StringUtils.join(j.get("results"), "-").contains("其他")) {
				dataModel.put("other", "☑");
				dataModel.put("other1", j.get("other1"));
				dataModel.put("other2", "——");
			} else {
				dataModel.put("other", "☐");
				dataModel.put("other1", "——");
				dataModel.put("other2", "——");
			}
			dataModel.put("totalCost", null == contractFormInfoEntity.getContractAmount() ? 0 : contractFormInfoEntity.getContractAmount());
			dataModel.put("rate", null == contractFormInfoEntity.getContactTaxRate() ? 0 : contractFormInfoEntity.getContactTaxRate());
			dataModel.put("amount", null == contractFormInfoEntity.getContractTaxAmount() ? 0 : contractFormInfoEntity.getContractTaxAmount());
			//双方确认：市调所需的除甲方产品以外其他物品购买费用由
			if ("乙方承担".equals(j.get("undertakesb").toString())) {
				dataModel.put("undertakesb", "☑");
				dataModel.put("undertakesa", "☐");
			} else {
				dataModel.put("undertakesb", "☐");
				dataModel.put("undertakesa", "☑");
			}
			dataModel.put("company", j.get("company"));
			dataModel.put("bank", j.get("bank"));
			dataModel.put("account", j.get("account"));
			dataModel.put("contactAddress", j.get("contactAddress"));
			dataModel.put("contact", j.get("contact"));
			dataModel.put("postcode", j.get("postcode"));
			dataModel.put("telephone", j.get("telephone"));
			dataModel.put("patyContact", j.get("patyContact"));
			dataModel.put("contact1", j.get("contact1"));
			dataModel.put("postcode1", j.get("postcode1"));
			dataModel.put("phon", j.get("phon"));
			dataModel.put("annex", j.get("annex"));
			dataModel.put("annex1", j.get("annex1"));
			modle.put("dataModel",setFile(filepaths,dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	//劳务派遣合同
	LWHT_52("LWHT_52") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			LaborDispatchEntity laborDispatchEntity = JSONObject.toJavaObject(j, LaborDispatchEntity.class);
			dataModel.put("ywbFirstParty", Func.isNull(contractFormInfoEntity.getSealName()) ? "" : contractFormInfoEntity.getSealName());
			dataModel.put("ywbPartyB", getCounterpart(contractFormInfoEntity).get("name").size() <= 0 ? "未选择相对方" : getCounterpart(contractFormInfoEntity).get("name").get(0));
			dataModel.put("ywbAddressA", j.get("ywbAddressA"));
			dataModel.put("ywbAddressB", j.get("ywbAddressB"));
			dataModel.put("principal", j.get("principal"));
			dataModel.put("liquidatedDamages", j.get("liquidatedDamages"));
			dataModel.put("date", j.get("date"));
			dataModel.put("job", j.get("job"));
			dataModel.put("days", j.get("days"));
			dataModel.put("age", j.get("age"));
			dataModel.put("assessmentDay", j.get("assessmentDay"));
			dataModel.put("accountingDate", j.get("accountingDate"));
			dataModel.put("invoiceDate", j.get("invoiceDate"));
			dataModel.put("accountName", j.get("accountName"));
			dataModel.put("bank", j.get("bank"));
			dataModel.put("accountNumber", j.get("accountNumber"));
			dataModel.put("assessmentPeriod", j.get("assessmentPeriod"));
			dataModel.put("returnm", j.get("returnm"));
			dataModel.put("dispatch", j.get("dispatch"));
			dataModel.put("reassignment", j.get("reassignment"));
			dataModel.put("applicationForAssignment", j.get("applicationForAssignment"));
			dataModel.put("name", j.get("name"));
			dataModel.put("phone", j.get("phone"));
			dataModel.put("paymentPeriod", j.get("paymentPeriod"));
			dataModel.put("late", j.get("late"));
			dataModel.put("absenteeism", j.get("absenteeism"));
			dataModel.put("overdue", j.get("overdue"));
			dataModel.put("lack", j.get("lack"));
			dataModel.put("iiquidatedDamages", j.get("iiquidatedDamages"));
			dataModel.put("supplementFive", j.get("supplementFive"));
			dataModel.put("supplementSix", j.get("supplementSix"));
			dataModel.put("payment", j.get("payment"));
			dataModel.put("delayedPayment", j.get("delayedPayment"));
			dataModel.put("relieve", j.get("relieve"));
			dataModel.put("specificDateStart", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("specificDateStart"))));
			dataModel.put("specificDateEnd", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("specificDateEnd"))));
			dataModel.put("expirationOfContract", j.get("expirationOfContract"));
			dataModel.put("addressA", j.get("addressA"));
			dataModel.put("addressB", j.get("addressB"));
			dataModel.put("telephoneA", j.get("telephoneA"));
			dataModel.put("telephoneB", j.get("telephoneB"));

			modle.put("dataModel",setFile(filepaths,dataModel));
			return modle;
		}
	},
	/**
	 * TAG-21-05-00
	 * 物流服务合同（冷冻）
	 */
	WLFW_59("WLFW_59") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			//迭代器遍历json对象
			Iterator iter = j.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if (entry.getKey() == null) {
					continue;
				}
				log.info("==key" + entry.getKey().toString());
				log.info("==value" + entry.getKey().toString());
				dataModel.put(entry.getKey().toString(), entry.getValue().toString());
			}
			dataModel.put("sclStartTime", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("sclStartTime"))));
			dataModel.put("sclEndOfTime", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("sclEndOfTime"))));
			dataModel.put("sclSigningDate", DataFormatUtils.systemTimeFormat(String.valueOf(j.get("sclSigningDate"))));
			dataModel.put("sclMarginRmb2", MoneyToChiness.tenThousand(String.valueOf(j.get("sclMarginRmb"))));
			modle.put("dataModel",setFile(filepaths, dataModel));
			return modle;
		}
	},
	/**
	 * TAG-21-05-01
	 */
	//统一e商城平台入驻服务协议（统一经销商）目前使用模板
	FWXY_29("FWXY_29") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			//迭代器遍历json对象
			Iterator iter = j.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if (entry.getKey() == null) {
					continue;
				}
				log.info("==key" + entry.getKey().toString());
				log.info("==value" + entry.getKey().toString());
				dataModel.put(entry.getKey().toString(), entry.getValue().toString());
			}
			modle.put("dataModel",setFile(filepaths, dataModel));
			return modle;
		}
	},
	/**
	 * TAG-21-05-05
	 * 2021年统一e商城平台入驻服务协议（统一经销商）
	 */
	FWXY_50("FWXY_50") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			//迭代器遍历json对象
			Iterator iter = j.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if (entry.getKey() == null) {
					continue;
				}
				log.info("==key" + entry.getKey().toString());
				log.info("==value" + entry.getKey().toString());
				dataModel.put(entry.getKey().toString(), entry.getValue().toString());
			}
			modle.put("dataModel",setFile(filepaths, dataModel));
			return modle;
		}
	},
	/**
	 * TAG-21-05-03
	 * 	//买卖合同（行销品）
	 */
	MMHT_07("MMHT_07") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<CglCategorySalesContracts1ResponseVO> CglCategorySalesContracts1 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//买卖合同（行销品）(关联表1)
				if (ContractFormInfoTemplateContract.CONTRACT_CGLCATEGORYSALESCONTRACTS1.equals(templateField.getRelationCode())) {
					CglCategorySalesContracts1 = JSON.parseArray(templateField.getTableData(), CglCategorySalesContracts1ResponseVO.class);
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder().bind("CglCategorySalesContracts1", policy).build();
			dataModel.put("CglCategorySalesContracts1", CglCategorySalesContracts1);
			//迭代器遍历json对象
			Iterator iter = j.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if (entry.getKey() == null) {
					continue;
				}
				log.info("==key" + entry.getKey().toString());
				log.info("==value" + entry.getKey().toString());
				dataModel.put(entry.getKey().toString(), entry.getValue().toString());
			}
			modle.put("dataModel",setFile(filepaths, dataModel));
			modle.put("config",config);
			return modle;
		}
	},
	/**
	 * TAG-21-05-04
	 * 	买卖合同（国内设备购买）
	 */
	MMHT_06("MMHT_06") {
		@Override
		public Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j) {
			Map modle=new HashMap();
			Map dataModel = new HashMap();
			List<CglSalesContract1ResponseVO> CglSalesContract1 = new ArrayList();
			List<TemplateFieldJsonEntity> templateFieldList = JSON.parseArray(json, TemplateFieldJsonEntity.class);
			for (TemplateFieldJsonEntity templateField : templateFieldList) {
				//买卖合同（国内设备购买）(关联表1)
				if (ContractFormInfoTemplateContract.CONTRACT_CGLSALESCONTRACT1ENTITY.equals(templateField.getRelationCode())) {
					CglSalesContract1 = JSON.parseArray(templateField.getTableData(), CglSalesContract1ResponseVO.class);
				}
			}
			HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
			Configure config = Configure.builder().bind("CglSalesContract1", policy).build();
			dataModel.put("CglSalesContract1", CglSalesContract1);
			//迭代器遍历json对象
			Iterator iter = j.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				if (entry.getKey() == null) {
					continue;
				}
				log.info("==key" + entry.getKey().toString());
				log.info("==value" + entry.getKey().toString());
				dataModel.put(entry.getKey().toString(), entry.getValue().toString());
			}
			modle.put("dataModel",setFile(filepaths, dataModel));
			modle.put("config",config);
			return modle;
		}
	};


	public abstract Map setScheduler(List<String> filepaths, ContractFormInfoEntity contractFormInfoEntity, TemplateRequestVO templateVO, String json, JSONObject j);

	@Getter
	public String type;

	/**
	 * 通过轮询来获得相应的方法
	 */
	public static TemplateExporterEnum fromValue(String type) {
		return Stream.of(TemplateExporterEnum.values()).filter(fileType ->
			StringUtils.equals(fileType.getType(), type)
		).findFirst().get();
	}

	/**
	 * 处理相对方名称
	 **/
	public static Map<String, List<String>> getCounterpart(ContractFormInfoEntity formInfo) {
		Map<String, List<String>> dataModel = new HashMap();
		List<String> list = new ArrayList<>();
		List<String> listLegalRepresentative = new ArrayList<>();
		formInfo.getCounterpart().forEach(counterpart -> {
			list.add(counterpart.getName());
		});
		formInfo.getCounterpart().forEach(counterpart -> {
			listLegalRepresentative.add(counterpart.getLegalRepresentative());
		});
		dataModel.put("name", list);
		dataModel.put("legalRepresentative", listLegalRepresentative);
		return dataModel;
	}

	/**
	 * 插入文件
	 **/
	public static Map setFile(List<String> filepaths, Map dataModel) {
		for (int i = 0; i < filepaths.size(); i++) {
			dataModel.put("docx_word" + i, new DocxRenderData(new File(filepaths.get(i))));
		}
		return dataModel;
	}


	/**
	 * 获取两个日期相差的月数
	 *
	 * @param d1 较大的日期
	 * @param d2 较小的日期
	 * @return 如果d1>d2返回 月数差 否则返回0
	 */
	@SneakyThrows
	public static int getMonthDiff(String d2, String d1) {
		if (d1.contains("__") || d2.contains("__")) {
			return 0;
		} else {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			//使用SimpleDateFormat的parse()方法生成Date
			Date date1 = sf.parse(d1);
			Date date2 = sf.parse(d2);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(date1);
			c2.setTime(date2);
			if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
				return 0;
			}
			int year1 = c1.get(Calendar.YEAR);
			int year2 = c2.get(Calendar.YEAR);
			int month1 = c1.get(Calendar.MONTH);
			int month2 = c2.get(Calendar.MONTH);
			int day1 = c1.get(Calendar.DAY_OF_MONTH);
			int day2 = c2.get(Calendar.DAY_OF_MONTH);
			// 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
			int yearInterval = year1 - year2;
			// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
			if (month1 < month2 || month1 == month2 && day1 < day2) {
				yearInterval--;
			}
			// 获取月数差值
			int monthInterval = (month1 + 12) - month2;
			if (day1 < day2) {
				monthInterval--;
			}
			monthInterval %= 12;
			return yearInterval * 12 + monthInterval;
		}
	}

	/**
	 * 处理json串中为null的过滤方法
	 */
	private static ValueFilter filter = new ValueFilter() {
		@Override
		public Object process(Object o, String s, Object o1) {
			if (o1 == null) {
				return "";
			}
			return o1;
		}
	};


}
