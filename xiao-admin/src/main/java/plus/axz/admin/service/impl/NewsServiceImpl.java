package plus.axz.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import plus.axz.admin.mapper.NewsMapper;
import plus.axz.admin.service.NewsService;
import plus.axz.model.admin.dtos.NewsDto;
import plus.axz.model.admin.pojos.News;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * description 新闻实现类
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Override
    public ResponseResult<?> insert(News news) {
        // 1. 检查参数
        if (news == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 查询数据库中标签信息
        List<News> list = list(Wrappers.<News>lambdaQuery().eq(News::getTitle, news.getTitle()));
        if (list != null && list.size() == 1){
            return ResponseResult.errorResult(ResultEnum.DATA_EXIST,"已经添加过啦~");
        }
        // 当前创建时间，需要在service中赋值
        news.setSubmitedTime(new Date());
        // 2. 保存
        save(news);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult<?> update(News news) {
        // 1. 检查参数
        if (news == null && news.getId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        news.setSubmitedTime(new Date());
        updateById(news);
        // 2.修改
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult<?> deleteById(Integer id) {
        // 1. 检查参数
        if (id == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前标签是否存在 和 是否 有效
        News news = getById(id);
        if (news == null) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 3. 删除标签
        removeById(id);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult<?> findByNameAndPage(NewsDto dto) {
        // 1. 检查参数
        if (dto == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "作者不存在");
        }
        // 2. 分页检查,赋予默认值
        dto.checkParam();
        // 3. mybatis-plus 按照名称 模糊 分页查询
        // 当前页，每页显示条数
        Page<News> page = new Page<>(dto.getPage(), dto.getSize());
        // 泛型
        LambdaQueryWrapper<News> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 判断拿到的name不为空
        if (StringUtils.isNotBlank(dto.getAuthor())) {
            // 模糊查询属性名称（字段），传过来的值
            lambdaQueryWrapper.like(News::getAuthor, dto.getAuthor());
        }

        IPage<News> result = page(page, lambdaQueryWrapper);
        // 4. 结果封装          当前页数，每页条数，总条数
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) result.getTotal());
        // 要把数据都携带过去，从result中拿到每一行记录
        responseResult.setData(result.getRecords());
        return responseResult;
    }
}
