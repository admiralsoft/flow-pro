package org.jeecg.modules.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.account.entity.SalAccount;
import org.jeecg.modules.account.entity.SalAccountItem;
import org.jeecg.modules.account.service.ISalAccountItemService;
import org.jeecg.modules.account.service.ISalAccountService;
import org.jeecg.modules.account.vo.SalAccountPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 薪资套账
 * @Author: dongjb
 * @Date:   2022-12-27
 * @Version: V1.0
 */
@Api(tags="薪资套账")
@RestController
@RequestMapping("/account/salAccount")
@Slf4j
public class SalAccountController {
	@Autowired
	private ISalAccountService salAccountService;
	@Autowired
	private ISalAccountItemService salAccountItemService;
	

	 /**
	  * 分页列表查询
	  * @param salAccount
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	@AutoLog(value = "薪资套账-分页列表查询")
	@ApiOperation(value="薪资套账-分页列表查询", notes="薪资套账-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SalAccount salAccount,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SalAccount> queryWrapper = QueryGenerator.initQueryWrapper(salAccount, req.getParameterMap());
		Page<SalAccount> page = new Page<>(pageNo, pageSize);
		IPage<SalAccount> pageList = salAccountService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param salAccountPage
	 * @return
	 */
	@AutoLog(value = "薪资套账-添加")
	@ApiOperation(value="薪资套账-添加", notes="薪资套账-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SalAccountPage salAccountPage) {
		SalAccount salAccount = new SalAccount();
		BeanUtils.copyProperties(salAccountPage, salAccount);
		salAccountService.saveMain(salAccount, salAccountPage.getSalAccountItemList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param salAccountPage
	 * @return
	 */
	@AutoLog(value = "薪资套账-编辑")
	@ApiOperation(value="薪资套账-编辑", notes="薪资套账-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SalAccountPage salAccountPage) {
		SalAccount salAccount = new SalAccount();
		BeanUtils.copyProperties(salAccountPage, salAccount);
		SalAccount salAccountEntity = salAccountService.getById(salAccount.getId());
		if(salAccountEntity==null) {
			return Result.error("未找到对应数据");
		}
		salAccountService.updateMain(salAccount, salAccountPage.getSalAccountItemList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "薪资套账-通过id删除")
	@ApiOperation(value="薪资套账-通过id删除", notes="薪资套账-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		salAccountService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "薪资套账-批量删除")
	@ApiOperation(value="薪资套账-批量删除", notes="薪资套账-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.salAccountService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "薪资套账-通过id查询")
	@ApiOperation(value="薪资套账-通过id查询", notes="薪资套账-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		SalAccount salAccount = salAccountService.getById(id);
		if(salAccount==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(salAccount);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "套账因子行通过主表ID查询")
	@ApiOperation(value="套账因子行主表ID查询", notes="套账因子行-通主表ID查询")
	@GetMapping(value = "/querySalAccountItemByMainId")
	public Result<?> querySalAccountItemListByMainId(@RequestParam(name="id") String id) {
		List<SalAccountItem> salAccountItemList = salAccountItemService.selectByMainId(id);
		return Result.OK(salAccountItemList);
	}

	 /**
	  * 通过主表id克隆套账
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "通过主表id克隆套账")
	 @ApiOperation(value="通过主表id克隆套账", notes="通过主表id克隆套账")
	 @GetMapping(value = "/cloneAccount")
	 public Result<?> cloneSalAccountAndItemListByMainId(@RequestParam(name="id") String id) {
		 salAccountService.cloneAccount(id);
		 return Result.OK("克隆套账成功");
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param salAccount
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SalAccount salAccount) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<SalAccount> queryWrapper = QueryGenerator.initQueryWrapper(salAccount, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<SalAccount> queryList = salAccountService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<SalAccount> salAccountList;
      if(oConvertUtils.isEmpty(selections)) {
          salAccountList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          salAccountList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<SalAccountPage> pageList = new ArrayList<>();
      for (SalAccount main : salAccountList) {
          SalAccountPage vo = new SalAccountPage();
          BeanUtils.copyProperties(main, vo);
          List<SalAccountItem> salAccountItemList = salAccountItemService.selectByMainId(main.getId());
          vo.setSalAccountItemList(salAccountItemList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "薪资套账列表");
      mv.addObject(NormalExcelConstants.CLASS, SalAccountPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("薪资套账数据", "导出人:"+sysUser.getRealname(), "薪资套账"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
		  // 获取上传文件对象
		  MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<SalAccountPage> list = ExcelImportUtil.importExcel(file.getInputStream(), SalAccountPage.class, params);
              for (SalAccountPage page : list) {
                  SalAccount po = new SalAccount();
                  BeanUtils.copyProperties(page, po);
                  salAccountService.saveMain(po, page.getSalAccountItemList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
