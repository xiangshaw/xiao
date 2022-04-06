package plus.axz.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.admin.mapper.TagMapper;
import plus.axz.admin.service.TagService;
import plus.axz.model.admin.dtos.TagDto;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年03月22日
 * @particulars
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult findByNameAndPage(TagDto dto) {
        // 1. 检查参数
        if (dto == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "标签不存在");
        }
//                int a = 1/0;/*测试全局异常*/
        // 2. 分页检查,赋予默认值
        dto.checkParam();
        // 3. mybatis-plus 按照名称 模糊 分页查询
        // 当前页，每页显示条数
        Page page = new Page(dto.getPage(), dto.getSize());
        // 泛型
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper();
        // 判断拿到的name不为空
        if (StringUtils.isNotBlank(dto.getTagName())) {
            // 模糊查询属性名称（字段），传过来的值
            lambdaQueryWrapper.like(Tag::getTagName, dto.getTagName());
        }

        IPage result = page(page, lambdaQueryWrapper);
        // 4. 结果封装          当前页数，每页条数，总条数
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) result.getTotal());
        // 要把数据都携带过去，从result中拿到每一行记录
        responseResult.setData(result.getRecords());
        return responseResult;
    }

    @Override
    public ResponseResult insert(Tag tag) {
        // 1. 检查参数
        if (tag == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 查询数据库中标签信息
        List<Tag> list = list(Wrappers.<Tag>lambdaQuery().eq(Tag::getTagName, tag.getTagName()));
        if (list != null && list.size() == 1){
            return ResponseResult.errorResult(ResultEnum.DATA_EXIST,"标签已经添加过啦~");
        }
        // 当前创建时间，需要在service中赋值
        tag.setCreateTime(new Date());
        // 2. 保存
        save(tag);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(Tag tag) {
        // 1. 检查参数
        if (tag == null && tag.getId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        tag.setUpdateTime(new Date());
        updateById(tag);
        // 2.修改
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Integer id) {
        // 1. 检查参数
        if (id == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前标签是否存在 和 是否 有效
        Tag tag = getById(id);/*先根据id查数据*/
        if (tag == null) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST);
        }/*再查是否有效,返回true是有效(1)*/
        if (tag.getStatus()) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "标签有效不能删除");
        }
        // 3. 删除标签
        removeById(id);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
