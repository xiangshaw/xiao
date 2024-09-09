package plus.axz.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import plus.axz.admin.mapper.BadgeMapper;
import plus.axz.admin.service.BadgeService;
import plus.axz.model.admin.dtos.BadgeDto;
import plus.axz.model.admin.pojos.Badge;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * description 徽章服务实现类
 */
@Service
public class BadgeServiceImpl extends ServiceImpl<BadgeMapper, Badge> implements BadgeService {
    @Override
    public ResponseResult<?> findByNameAndPage(BadgeDto dto) {
        // 1.检查参数
        if (dto == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"徽章不存在");
        }
        // 2. 分页检查
        dto.checkParam();
        // 3.模糊分页查询
        // 当前页-每页条数
        Page<Badge> page = new Page<>(dto.getPage(), dto.getSize());
        // 泛型
        LambdaQueryWrapper<Badge> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getBadgeName())){
            lambdaQueryWrapper.like(Badge::getBadgeName,dto.getBadgeName());
        }
        IPage<Badge> result = page(page, lambdaQueryWrapper);
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) result.getTotal());
        pageResponseResult.setData(result.getRecords());
        return pageResponseResult;
    }

    @Override
    public ResponseResult<?> insert(Badge badge) {
        // 1.检查参数
        if (badge==null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 查询数据库信息
        List<Badge> list = list(Wrappers.<Badge>lambdaQuery().eq(Badge::getBadgeName, badge.getBadgeName()));
        if (list != null && list.size() ==1){
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        badge.setCreateTime(new Date());
        save(badge);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult<?> update(Badge badge) {
        // 1. 检查参数
        if (badge == null && badge.getId() == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        badge.setUpdateTime(new Date());
        updateById(badge);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult<?> deleteById(Integer id) {
        // 1.检查参数
        if (id == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 判断
        Badge badge = getById(id);
        if (badge == null){
            return  ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        removeById(id);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
