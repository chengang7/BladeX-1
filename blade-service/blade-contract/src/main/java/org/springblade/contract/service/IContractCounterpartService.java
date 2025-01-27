package org.springblade.contract.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.contract.entity.ContractCounterpartEntity;
import org.springblade.contract.excel.ContractCounterpartExcel;
import org.springblade.contract.vo.ContractCounterpartRequestVO;
import org.springblade.contract.vo.ContractCounterpartResponseVO;
import org.springblade.core.mp.base.BaseService;

import java.util.List;

/**
 * 相对方管理 服务类
 *
 * @author XHB
 * @date : 2020-09-23 19:35:05
 */
public interface IContractCounterpartService extends BaseService<ContractCounterpartEntity> {

	/**
	 * 分页查询
	 * @param page
	 * @param counterpart
	 * @return
	 */
	IPage<ContractCounterpartResponseVO> pageList(IPage<ContractCounterpartEntity> page, ContractCounterpartRequestVO counterpart);

	/**
	 * 根据相对方名称获取集合
	 * @param unifiedSocialCreditCode 信用代码
	 * @param name
	 * @return
	 */
	List<ContractCounterpartEntity> getByUnifiedSocialCreditCode(String unifiedSocialCreditCode, String name);
	/**
	 * 重写向对方vo 返回附件列表到视图
	 * @param id
	 * @return
	 */
	ContractCounterpartResponseVO getById(Long id);


	/**
	 * 导入相对方数据数据
	 * @param data
	 * @param isCovered
	 */
	void importCounterpart(List<ContractCounterpartExcel> data, Boolean isCovered);
}
