package org.jeecg.modules.factor.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.factor.dto.FactorDto;
import org.jeecg.modules.factor.entity.SalFactor;
import org.jeecg.modules.factor.service.ISalFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 /**
 * @Description: 工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息
 * @Author: jeecg-boot
 * @Date:   2022-12-26
 * @Version: V1.0
 */
@Api(tags="工资项因子")
@RestController
@RequestMapping("/factor/salFactor")
@Slf4j
public class SalFactorController extends JeecgController<SalFactor, ISalFactorService> {
	@Autowired
	private ISalFactorService salFactorService;
	
	/**
	 * 分页列表查询
	 *
	 * @param salFactor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-分页列表查询")
	@ApiOperation(value="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-分页列表查询", notes="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SalFactor salFactor,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SalFactor> queryWrapper = QueryGenerator.initQueryWrapper(salFactor, req.getParameterMap());
		Page<SalFactor> page = new Page<>(pageNo, pageSize);
		IPage<SalFactor> pageList = salFactorService.page(page, queryWrapper);
		return Result.OK(pageList);
		
	}

	 /**
	  * 查询全部
	  * @return
	  */
	 @AutoLog(value = "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-查询全部")
	 @ApiOperation(value="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-查询全部", notes="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-分页列表查询")
	 @GetMapping(value = "/alllist")
	 public Result<?> queryAllList() {
		 List<SalFactor> allList = salFactorService.list();
		 List<FactorDto> resultData = new ArrayList<>();
		 FactorDto target;
		 int i = 0;
		 for (SalFactor source:allList) {
			 i++;
			 target = new FactorDto();
			 target.setFactor_id(source.getId());
			 target.setFactor_code(source.getCode());
			 target.setFactor_catalog(source.getCatalog());
			 target.setFactor_name(source.getName());
			 target.setDisplay("Y");
			 target.setSequence(i);
			 resultData.add(target);
		 }
		 return Result.OK(resultData);
	 }
	
	/**
	 *   添加
	 *
	 * @param salFactor
	 * @return
	 */
	@AutoLog(value = "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-添加")
	@ApiOperation(value="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-添加", notes="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SalFactor salFactor) {
		salFactorService.save(salFactor);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param salFactor
	 * @return
	 */
	@AutoLog(value = "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-编辑")
	@ApiOperation(value="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-编辑", notes="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SalFactor salFactor) {
		salFactorService.updateById(salFactor);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-通过id删除")
	@ApiOperation(value="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-通过id删除", notes="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		salFactorService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-批量删除")
	@ApiOperation(value="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-批量删除", notes="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.salFactorService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-通过id查询")
	@ApiOperation(value="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-通过id查询", notes="工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		SalFactor salFactor = salFactorService.getById(id);
		if(salFactor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(salFactor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param salFactor
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SalFactor salFactor) {
        return super.exportXls(request, salFactor, SalFactor.class, "工资项因子，影响工资的因素，可以是固定值工资项，可以是动态计算工资项，可以是外来的影响因数比如员工考核信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SalFactor.class);
    }

}
