package plus.axz.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.admin.mapper.SensitiveMapper;
import plus.axz.admin.service.SensitiveService;
import plus.axz.model.admin.dtos.SensitiveDto;
import plus.axz.model.admin.pojos.Sensitive;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年03月23日
 * @particulars 敏感词实现类
 */
@Service
public class SensitiveServiceImpl extends ServiceImpl<SensitiveMapper, Sensitive> implements SensitiveService {

    @Autowired
    public SensitiveService sensitiveService;
    @Override
    public ResponseResult list(SensitiveDto dto) {
        // 1. 检查参数
        if(dto == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 分页参数赋值
        dto.checkParam();
        //2. 根据名称迷糊分页查询
        // 获取当前页 每页条数
        Page page = new Page<>(dto.getPage(), dto.getSize());
        // Sensitive泛型
        LambdaQueryWrapper<Sensitive> lambdaQueryWrapper = new LambdaQueryWrapper();
        // 判断拿到的敏感词不为空
        if (StringUtils.isNotBlank(dto.getSensitives())){
            // 模糊查询属性(名称)字段，前台传过来的值
            lambdaQueryWrapper.like(Sensitive::getSensitives,dto.getSensitives());
        }
        Page result = page(page, lambdaQueryWrapper);
        // 3. 结果返回
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) result.getTotal());
        pageResponseResult.setData(result.getRecords());
        return pageResponseResult;
    }

    @Override
    public ResponseResult insert(Sensitive sensitive) {
        // 1. 检查参数
        if(sensitive == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE);
        }
        // 查询数据库中敏感词信息
        List<Sensitive> list = list(Wrappers.<Sensitive>lambdaQuery().eq(Sensitive::getSensitives, sensitive.getSensitives()));
        if (list != null && list.size() == 1) {
            return ResponseResult.errorResult(ResultEnum.DATA_EXIST, "敏感词已经添加过啦~");
        }
        //2. 保存
        sensitive.setCreatedTime(new Date());
        save(sensitive);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(Sensitive sensitive) {
        // 1. 检查参数和敏感词id
        if(sensitive == null || sensitive.getId() == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_REQUIRE);
        }
        sensitive.setUpdateTime(new Date());
        // 2. 更新操作
        updateById(sensitive);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Integer id) {
        // 1.检查参数
        if (id == null){
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        //2. 根据id删除
        removeById(id);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
