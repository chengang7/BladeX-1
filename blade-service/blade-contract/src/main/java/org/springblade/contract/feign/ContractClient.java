package org.springblade.contract.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.abutment.feign.IAbutmentClient;
import org.springblade.abutment.vo.CounterpartVo;
import org.springblade.contract.entity.ContractCounterpartEntity;
import org.springblade.contract.entity.ContractFormInfoEntity;
import org.springblade.contract.entity.ContractSigningEntity;
import org.springblade.contract.entity.ContractTemplateEntity;
import org.springblade.contract.mapper.ContractCounterpartMapper;
import org.springblade.contract.mapper.ContractFormInfoMapper;
import org.springblade.contract.mapper.ContractTemplateMapper;
import org.springblade.contract.service.IContractCounterpartService;
import org.springblade.contract.service.IContractFormInfoService;
import org.springblade.contract.service.IContractSigningService;
import org.springblade.contract.service.IContractTemplateService;
import org.springblade.contract.vo.ContractFormInfoResponseVO;
import org.springblade.contract.vo.ContractTemplateResponseVO;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.feign.IFileClient;
import org.springblade.system.entity.TemplateEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 合同Feign实现类
 *
 * @author Chill
 */
@Slf4j
@ApiIgnore
@RestController
@AllArgsConstructor
public class ContractClient implements IContractClient{

	private IFileClient fileClient;
	private IAbutmentClient abutmentClient;
    private IContractFormInfoService formInfoService;
	private ContractFormInfoMapper contractFormInfoMapper;
	private IContractTemplateService templateService;
	private ContractTemplateMapper templateMapper;
	private IContractSigningService contractSigningService;
	private IContractCounterpartService iContractCounterpartService;
	private ContractCounterpartMapper counterpartMapper;
	private IContractCounterpartService counterpartService;
	//模板路径
	private static final String ftlPath="D:/ftl/";

	@Override
	public R<Boolean> saveBatch(List<ContractCounterpartEntity> listInsert) {
		return R.data(counterpartService.saveBatch(listInsert));
	}

	@Override
	public R<Boolean> updateById(ContractCounterpartEntity updateCounterpart) {
		return R.data(counterpartService.updateById(updateCounterpart));
	}

	@Override
	public R<List<ContractCounterpartEntity>> selectByName(String unifiedSocialCreditCode) {
		return R.data(counterpartMapper.selectByName(unifiedSocialCreditCode));
	}

	//private static final String ftlPath="/ftl/";
    @Override
    @GetMapping(CONTRACT)
    public R<ContractFormInfoResponseVO> getById(Long id) {
        return R.data(formInfoService.getById(id));
    }

	@Override
	@GetMapping(STATUS)
	public R<List<ContractFormInfoEntity>> getByStatus(String status) {
		return R.data(formInfoService.getByStatus(status));
	}

	@Override
	@GetMapping(CHOOSE)
	public R<List<ContractFormInfoEntity>> getChooseList() {
		return R.data(formInfoService.getChooseList());
	}

	@Override
	@PostMapping(TEMPLATE_UPDATE)
	public R<ContractTemplateResponseVO> templateUpdate(TemplateEntity entity) {
		ContractTemplateEntity templateFieldEntity=new ContractTemplateEntity();
		QueryWrapper<ContractTemplateEntity> queryWrapper = Condition.getQueryWrapper(templateFieldEntity)
			.eq("template_code",entity.getTemplateCode())
			.eq("is_deleted",0);
			/*.eq("template_status","10")
			.or().eq("template_status","40");*/
		List<ContractTemplateEntity> list = templateService.list(queryWrapper);
		for (ContractTemplateEntity v : list) {
			if (Func.isEmpty(templateMapper.latestById(v.getId()))) {
				//模板生成后修改成带使用  用作审批使用
				v.setTemplateStatus("10");
				v.setJson(entity.getJson());
				templateService.updateById(v);
			}
		}
		return null;
	}

	@Override
	@GetMapping(CONTRACT_SAVE)
	public R saveContractFormInfo(Long id, String status) {
		ContractFormInfoEntity contractFormInfo = contractFormInfoMapper.selectById(id);
		if(Func.isEmpty(contractFormInfo)){
			return R.fail("合同信息不存在");
		}
		//审批通过
		if("30".equals(status)){
			log.info("审批状态为25说明为驳回，30说明为审批通过，140说明为起草废除,此节点状态为："+status);
			if("1".equals(contractFormInfo.getContractForm())){
				log.info("合同形式为1表示为电子签章-我司平台，审批通过后直接转到已归档节点（待结案），至此合同形式为："+contractFormInfo.getContractForm());
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = df.format(new Date());
				ContractSigningEntity entity = new ContractSigningEntity();
				entity.setContractId(contractFormInfo.getId());
				/*File filePDF=null;
				FileOutputStream fos = null;
				try {
					filePDF = new File(ftlPath + contractFormInfo.getContractListName() +date+ ".pdf");
					fos = new FileOutputStream(filePDF);
					R<String> token=abutmentClient.token();
					String url="http://sa.pec.com.cn:9080/common/file/downloadSinged?id="+contractFormInfo.getTextFilePdf()+"&token="+token.getData();
					fos.write(AsposeWordToPdfUtils.getUrlFileData(url));
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				File filePDFNew=new File(ftlPath + contractFormInfo.getContractListName() +date+ ".pdf");
				R<FileVO> filePDFVO = null;
				try {
					MultipartFile multipartFile = new MockMultipartFile("file", filePDFNew.getName(),
						ContentType.MULTIPART.toString(), new FileInputStream(filePDFNew));
					filePDFVO = fileClient.save(multipartFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				assert filePDFVO != null;
				entity.setTextFiles(filePDFVO.getData().getId() + ",");*/
				entity.setSubmissionType(" ");
				entity.setAddressee(" ");
				contractSigningService.save(entity);
				log.info("并创建保存对应合同的归档信息："+entity.getContractId());
				contractFormInfo.setContractStatus("60");
			}else if("3".equals(contractFormInfo.getContractForm())){
				log.info("合同形式为3表示为电子合同-对方平台，审批通过后直接转到用印节点（带手动归档），至此合同形式为："+contractFormInfo.getContractForm());
				contractFormInfo.setContractStatus("50");
			}else{
				log.info("合同形式为2，4表示为实体签章-我司不用电子印/实体签章-我司用电子印，审批通过后转到印节，至此合同形式为："+contractFormInfo.getContractForm());
				contractFormInfo.setContractStatus("30");
			}
		}else{
			contractFormInfo.setContractStatus(status);
		}
		formInfoService.saveOrUpdate(contractFormInfo);
		return R.success("审核成功");
	}

	@Override
	@GetMapping(TEMPLATE_GET_ID)
	public R<ContractTemplateEntity> getByTemplateId(Long id) {
		ContractTemplateEntity templateFieldEntity=templateService.getById(id);
		return R.data(templateFieldEntity);
	}
}
