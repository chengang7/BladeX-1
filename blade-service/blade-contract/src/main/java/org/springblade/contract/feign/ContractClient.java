package org.springblade.contract.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springblade.contract.entity.ContractFormInfoEntity;
import org.springblade.contract.entity.ContractTemplateEntity;
import org.springblade.contract.mapper.ContractFormInfoMapper;
import org.springblade.contract.service.IContractFormInfoService;
import org.springblade.contract.service.IContractTemplateService;
import org.springblade.contract.vo.ContractFormInfoResponseVO;
import org.springblade.contract.vo.ContractTemplateResponseVO;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.system.entity.TemplateFieldEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同Feign实现类
 *
 * @author Chill
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class ContractClient implements IContractClient{

    private IContractFormInfoService formInfoService;
	private IContractTemplateService templateService;
    @Override
    @GetMapping(CONTRACT)
    public R<ContractFormInfoResponseVO> getById(Long id) {
        return R.data(formInfoService.getById(id));
    }

	@Override
	@GetMapping(TEMPLATE_UPDATE)
	public R<ContractTemplateResponseVO> templateUpdate(String templateCode, String json) {
		ContractTemplateEntity templateFieldEntity=new ContractTemplateEntity();
		QueryWrapper<ContractTemplateEntity> queryWrapper = Condition.getQueryWrapper(templateFieldEntity)
			.eq("template_code",templateCode)
			.eq("is_deleted",0)
			.eq("template_status","10")
			.or().eq("template_status","40");
		List<ContractTemplateEntity> list = templateService.list(queryWrapper);
		if(CollectionUtil.isNotEmpty(list)){
			list.get(0).setJson(json);
			templateService.updateById(list.get(0));
		}
		return null;
	}
}