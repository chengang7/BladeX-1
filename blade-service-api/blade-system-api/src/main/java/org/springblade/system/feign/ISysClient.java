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
package org.springblade.system.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.*;
import org.springblade.system.vo.DataSealAuthorityResponseVO;
import org.springblade.system.vo.DeptVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
	//fallback = ISysClientFallback.class
)
public interface ISysClient {

	String API_PREFIX = "/client";
	String MENU = API_PREFIX + "/menu";
	String DEPT = API_PREFIX + "/dept";
	String DEPT_TREE = API_PREFIX + "/dept-tree";
	String DEPT_IDS = API_PREFIX + "/dept-ids";
	String DEPT_NEW_ID = API_PREFIX + "/dept-new-id";
	String DEPT_NAME = API_PREFIX + "/dept-name";
	String DEPT_NAMES = API_PREFIX + "/dept-names";
	String DEPT_CHILD = API_PREFIX + "/dept-child";
	String POST = API_PREFIX + "/post";
	String POST_IDS = API_PREFIX + "/post-ids";
	String POST_NAME = API_PREFIX + "/post-name";
	String POST_NAMES = API_PREFIX + "/post-names";
	String ROLE = API_PREFIX + "/role";
	String ROLE_IDS = API_PREFIX + "/role-ids";
	String ROLE_NAME = API_PREFIX + "/role-name";
	String ROLE_NAMES = API_PREFIX + "/role-names";
	String ROLE_ALIAS = API_PREFIX + "/role-alias";
	String ROLE_ALIASES = API_PREFIX + "/role-aliases";
	String TENANT = API_PREFIX + "/tenant";
	String TENANT_ID = API_PREFIX + "/tenant-id";
	String PARAM = API_PREFIX + "/param";
	String PARAM_VALUE = API_PREFIX + "/param-value";
	String SAVE_DEPT_API=API_PREFIX +"/save-dept-api";
	String SAVE_POST_API=API_PREFIX +"/save-post-api";
	String SAVE_USER_DEPART_API=API_PREFIX +"/save-user-depart-api";
	String SUBMIT_DEPT_API=API_PREFIX +"/submit-dept-api";
	String SUBMIT_POST_API=API_PREFIX +"/submit-post-api";
	String GET_DEPT_ID_BY_LUNID = API_PREFIX + "/get-dept-id-by-lunid";
	String GET_USER_DEPART_ID_BY_LUNID= API_PREFIX + "/get-user-depart-id-by-lunid";
	String GET_ANCESTOR_IDS = API_PREFIX + "/get-ancestor-ids";
	String GET_POST_ID_BY_LUNID = API_PREFIX + "/get-post-id-by-lunid";
	String GET_DATA_SEAL_AUTHORITY = API_PREFIX + "/get_data_seal_authority";
	/**
	 * 获取菜单
	 *
	 * @param id 主键
	 * @return Menu
	 */
	@GetMapping(MENU)
	R<Menu> getMenu(@RequestParam("id") Long id);

	/**
	 * 获取部门树形结构
	 *
	 * @return
	 */
	@GetMapping(DEPT_TREE)
	R<List<DeptVO>> getDeptTree(@RequestParam("tenantId") String tenantId, @RequestParam("bladeUser") BladeUser bladeUser);

	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(DEPT)
	R<Dept> getDept(@RequestParam("id") Long id);

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	@GetMapping(DEPT_IDS)
	R<String> getDeptIds(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

	/**
	 * 根据部门当前id获取部门最新id，因为每次更新部门信息都会创建一条新的记录同时关联之前的数据
	 *
	 * @param id 部门当前id
	 * @return 部门最新id
	 */
	@GetMapping(DEPT_NEW_ID)
	R<Long> getDeptNewId(@RequestParam("id") Long id);

	/**
	 * 获取部门名
	 *
	 * @param id 主键
	 * @return 部门名
	 */
	@GetMapping(DEPT_NAME)
	R<String> getDeptName(@RequestParam("id") Long id);

	/**
	 * 获取部门名
	 *
	 * @param deptIds 主键
	 * @return
	 */
	@GetMapping(DEPT_NAMES)
	R<List<String>> getDeptNames(@RequestParam("deptIds") String deptIds);

	/**
	 * 获取子部门ID
	 *
	 * @param deptId
	 * @return
	 */
	@GetMapping(DEPT_CHILD)
	R<List<Dept>> getDeptChild(@RequestParam("deptId") Long deptId);

	/**
	 * 获取岗位
	 *
	 * @param id 主键
	 * @return Post
	 */
	@GetMapping(POST)
	R<Post> getPost(@RequestParam("id") Long id);

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return 岗位id
	 */
	@GetMapping(POST_IDS)
	R<String> getPostIds(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param id 主键
	 * @return 岗位名
	 */
	@GetMapping(POST_NAME)
	R<String> getPostName(@RequestParam("id") Long id);

	/**
	 * 获取岗位名
	 *
	 * @param postIds 主键
	 * @return
	 */
	@GetMapping(POST_NAMES)
	R<List<String>> getPostNames(@RequestParam("postIds") String postIds);

	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	@GetMapping(ROLE)
	R<Role> getRole(@RequestParam("id") Long id);

	/**
	 * 获取角色id
	 *
	 * @param tenantId  租户id
	 * @param roleNames 角色名
	 * @return 角色id
	 */
	@GetMapping(ROLE_IDS)
	R<String> getRoleIds(@RequestParam("tenantId") String tenantId, @RequestParam("roleNames") String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param id 主键
	 * @return 角色名
	 */
	@GetMapping(ROLE_NAME)
	R<String> getRoleName(@RequestParam("id") Long id);

	/**
	 * 获取角色别名
	 *
	 * @param id 主键
	 * @return 角色别名
	 */
	@GetMapping(ROLE_ALIAS)
	R<String> getRoleAlias(@RequestParam("id") Long id);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 主键
	 * @return
	 */
	@GetMapping(ROLE_NAMES)
	R<List<String>> getRoleNames(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取角色别名
	 *
	 * @param roleIds 主键
	 * @return 角色别名
	 */
	@GetMapping(ROLE_ALIASES)
	R<List<String>> getRoleAliases(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	@GetMapping(TENANT)
	R<Tenant> getTenant(@RequestParam("id") Long id);

	/**
	 * 获取租户
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	@GetMapping(TENANT_ID)
	R<Tenant> getTenant(@RequestParam("tenantId") String tenantId);

	/**
	 * 获取参数
	 *
	 * @param id 主键
	 * @return Param
	 */
	@GetMapping(PARAM)
	R<Param> getParam(@RequestParam("id") Long id);

	/**
	 * 获取参数配置
	 *
	 * @param paramKey 参数key
	 * @return String
	 */
	@GetMapping(PARAM_VALUE)
	R<String> getParamValue(@RequestParam("paramKey") String paramKey);

	/**
	 *
	 * @param dept
	 * @return
	 */
	@PostMapping(SAVE_DEPT_API)
	R<Boolean> saveOrUpdateBatchDept(@RequestBody List<Dept> dept);

	/**
	 *
	 * @param post
	 * @return
	 */
	@PostMapping(SAVE_POST_API)
	R<Boolean> saveOrUpdateBatchPost(@RequestBody List<Post> post);

	/**
	 *
	 * @param userDepart
	 * @return
	 */
	@PostMapping(SAVE_USER_DEPART_API)
	R<Boolean> saveOrUpdateBatchUserDepart(@RequestBody List<UserDepartEntity> userDepart);

	/**
	 *
	 * @param dept
	 * @return
	 */
	@PostMapping(SUBMIT_DEPT_API)
	R<Boolean> saveDept(@RequestBody Dept dept);


	/**
	 *
	 * @param post
	 * @return
	 */
	@PostMapping(SUBMIT_POST_API)
	R<Boolean> savePost(@RequestBody Post post);

	/**
	 * 根据部门Lunid获取部门id
	 *
	 * @param associationId 唯一标识
	 * @return deptId
	 */
	@GetMapping(GET_DEPT_ID_BY_LUNID)
	R<Long> getDeptIdByAssociationId(@RequestParam("associationId") String associationId);


	/**
	 * 根据部门Lunid获取部门id
	 *
	 * @param associationId 唯一标识
	 * @return deptId
	 */
	@GetMapping(GET_USER_DEPART_ID_BY_LUNID)
	R<Long> getUserDepartByAssociationId(@RequestParam("associationId") Long associationId);
	/**
	 * 将lunid祖籍列表转为blade祖籍列表
	 * @param ancestorIds
	 * @return
	 */
	@GetMapping(GET_ANCESTOR_IDS)
	R<String> getAncestors(@RequestParam("ancestorIds") Long ancestorIds);

	/**
	 * 根据岗位Lunid获取岗位id
	 *
	 * @param associationId 唯一标识
	 * @return postId
	 */
	@GetMapping(GET_POST_ID_BY_LUNID)
	R<Long> getPostIdByAssociationId(@RequestParam("associationId") String associationId);

	/**
	 * 根据角色ID查询用户数据权限数据
	 * @param userId
	 * @return
	 */
	@GetMapping(GET_DATA_SEAL_AUTHORITY)
	R<DataSealAuthorityResponseVO> getByIdData(@RequestParam("userId") String userId,
											   @RequestParam("roleId") String roleId );
}
