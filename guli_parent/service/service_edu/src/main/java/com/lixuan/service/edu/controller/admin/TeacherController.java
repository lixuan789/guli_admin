package com.lixuan.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Teacher;
import com.lixuan.service.edu.entity.vo.TeacherQueryVo;
import com.lixuan.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Api("讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("list")
    public Result listAll(){
        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("items",list).message("获取讲师列表成功");
    }

    @ApiOperation(value = "根据ID删除讲师", notes = "根据ID删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeById(@ApiParam(value = "讲师ID",required = true) @PathVariable("id") String id){
        boolean remove = teacherService.removeById(id);
        if (remove){
            return Result.ok().message("删除成功");
        }else {
            return Result.error().message("数据不存在");
        }
    }

    @ApiOperation("分页讲师列表")
    @PostMapping("list/{page}/{limit}")
    public Result listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                           @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                           @ApiParam("讲师列表查询对象") @RequestBody(required = false) TeacherQueryVo teacherQueryVo){
        IPage<Teacher> teacherIPage = teacherService.selectPage(page, limit, teacherQueryVo);
        List<Teacher> records = teacherIPage.getRecords();
        long total = teacherIPage.getTotal();
        return Result.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("新增讲师")
    @PostMapping("save")
    public Result save(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher){
        boolean result = teacherService.save(teacher);
        if (result) {
            return Result.ok().message("保存成功");
        } else {
            return Result.error().message("保存失败");
        }
    }

    @ApiOperation("更新讲师")
    @PutMapping("update")
    public Result updateById(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher){
        boolean result = teacherService.updateById(teacher);
        if(result){
            return Result.ok().message("修改成功");
        }else{
            return Result.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("get/{id}")
    public Result getById(@ApiParam(value = "讲师ID",required = true) @PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        if (teacher==null){
            return Result.error().message("数据不存在");
        }else {
            return Result.ok().data("item",teacher);
        }
    }

}

