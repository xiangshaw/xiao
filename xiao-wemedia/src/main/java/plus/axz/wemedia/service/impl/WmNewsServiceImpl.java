package plus.axz.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.axz.common.constants.message.NewsAutoScanConstants;
import plus.axz.common.constants.message.WmNewsMessageConstants;
import plus.axz.common.constants.wemedia.WemediaContans;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.wemedia.dtos.WmNewsDto;
import plus.axz.model.wemedia.dtos.WmNewsPageReqDto;
import plus.axz.model.wemedia.pojos.WmMaterial;
import plus.axz.model.wemedia.pojos.WmNews;
import plus.axz.model.wemedia.pojos.WmNewsMaterial;
import plus.axz.model.wemedia.pojos.WmUser;
import plus.axz.model.wemedia.vo.WmNewsVo;
import plus.axz.utils.threadlocal.WmThreadLocalUtils;
import plus.axz.wemedia.mapper.WmMaterialMapper;
import plus.axz.wemedia.mapper.WmNewsMapper;
import plus.axz.wemedia.mapper.WmNewsMaterialMapper;
import plus.axz.wemedia.mapper.WmUserMapper;
import plus.axz.wemedia.service.WmNewsService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaoxiang
 * description
 */
@RequiredArgsConstructor
@Service
@Transactional
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Value("${fdfs.url}")
    private String fileServerUrl;

    @Override
    public ResponseResult<?> findAll(WmNewsPageReqDto dto) {
        // 1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        dto.checkParam();
        // 2.分页条件查询(获取当前页，每页条数)
        Page<WmNews> pageParam = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 状态精确查找
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }
        // 标签精确查询
        if (dto.getTagId() != null) {
            lambdaQueryWrapper.eq(WmNews::getTagId, dto.getTagId());
        }
        // 关键字模糊查询 按照标题模糊查询
        if (StringUtils.isNotBlank(dto.getKeyWord())) {
            lambdaQueryWrapper.eq(WmNews::getTitle, dto.getKeyWord());
        }
        // 时间范围模糊查询
        if (dto.getBeginPubdate() != null && dto.getEndPubdate() != null) {
            lambdaQueryWrapper.between(WmNews::getPublishTime, dto.getBeginPubdate(), dto.getKeyWord());
        }
        // 根据自媒体人精确查询
        WmUser user = WmThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(ResultEnum.NEED_LOGIN);
        }
        lambdaQueryWrapper.eq(WmNews::getUserId, user.getId());
        // 按照发布时间倒序排序
        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);

        IPage<WmNews> pageResult = page(pageParam, lambdaQueryWrapper);
        // 3.结果封装查询
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) pageResult.getTotal());
        pageResponseResult.setData(pageResult.getRecords());
        pageResponseResult.setHost(fileServerUrl);
        return pageResponseResult;
    }


    @Override
    public ResponseResult<?> saveNews(WmNewsDto dto, Short isSubmit) {
        // 1.检查参数
        if (dto == null || StringUtils.isBlank(dto.getContent())) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.保存或修改文章
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(dto, wmNews);
        if (WemediaContans.WM_NEWS_AUTO_TYPE.equals(dto.getType())) {
            // 数据库不会存入-1值，后面将赋值该type
            wmNews.setType(null);
        }
        if (dto.getImages() != null && dto.getImages().size() > 0) {
            //[sdsds.jpg,sagyg2jpg] 需要去掉括号、前缀url、排除空格
            wmNews.setImages(dto.getImages().toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(fileServerUrl, "")
                    .replace(" ", ""));
        }
        // 保存或修改文章方法
        saveWmNews(wmNews, isSubmit);
        // 3.关联文章与素材关系
        // 从前端文章内容页面中抽取图片信息
        String content = dto.getContent();
        List<Map> list = JSON.parseArray(content, Map.class);
        // 从url中抽取图片链接
        List<String> materials = ectractUrlInfo(list);
        // 3.1关联内容中的图片与素材的关系
        // 为1才保存关系,以及文章不等于空
        if (isSubmit == WmNews.Status.SUBMIT.getCode() && materials.size() != 0) {
            // 根据当前内容保存关系(素材图片和文章id)
            ResponseResult<?> responseResult = saveRelativeInfoForContent(materials, wmNews.getId());
            // 没有问题就是null，有问题的话就是有值
            if (responseResult != null) {
                return responseResult;
            }
        }
        // 3.2关联封面中的图片与素材关系，设置news的type，自动(从文章内容取图片)
        if (isSubmit == WmNews.Status.SUBMIT.getCode()) {
            ResponseResult<?> responseResult = saveRelativeInfoForCover(dto, materials, wmNews);
            // 没有问题就是null,有问题的话就是有值
            if (responseResult == null) {
                return responseResult;
            }
        }
        return null;
    }

    // 根据id查文章
    @Override
    public ResponseResult<?> findWmNewById(Integer id) {
        // 1.检查参数
        if (id == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "文章ID不可缺");
        }
        // 2.查询数据
        WmNews wmNews = getById(id);
        if (wmNews == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "该文章不存在");
        }
        // 3.结果返回
        ResponseResult<?> responseResult = ResponseResult.okResult(wmNews);
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    // 删除文章
    @Override
    public ResponseResult<?> delNews(Integer id) {
        // 1.检查参数
        if (id == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "文章ID不可缺");
        }
        // 2.查询
        WmNews wmNews = getById(id);
        if (wmNews == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "该文章不存在");
        }
        // 3.判断文章状态 status !=9 并且 enable!=1  已上架  发布
        if (wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode()) && wmNews.getEnable().equals(WemediaContans.WM_NEWS_ENABLE_UP)) {
            return ResponseResult.errorResult(ResultEnum.DATA_EXIST, "该文章已经发布，请先下架操作");
        }
        // 4.除去素材和文章关系
        wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId, wmNews.getId()));
        // 5.删除文章
        removeById(wmNews.getId());
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    // 文章上下架
    @Override
    public ResponseResult<?> downOrUp(WmNewsDto dto) {
        // 1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "该文章不存在");
        }
        // 2.查询文章
        WmNews wmNews = getById(dto.getId());
        if (wmNews == null) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST, "该文章不存在");
        }
        // 3.判断文章是否已经发布
        if (!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode())) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST, "该文章不是发布状态，不能上下架");
        }
        // 4.修改文章状态
        if (dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2) {
            // 1上架 0下架
            if (wmNews.getArticleId() != null) {
                // 存两个参数
                HashMap<String, Object> map = new HashMap<>();
                map.put("enable", dto.getEnable());
                map.put("articleId", wmNews.getArticleId());
                // 至此 消息发送成功
                kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC, JSON.toJSONString(map));
            }
            update(Wrappers.<WmNews>lambdaUpdate()
                    .eq(WmNews::getId, dto.getId())
                    .set(WmNews::getEnable, dto.getEnable()));
        }
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    //  在自媒体文章审核的时候，审核通过后，判断了文章的发布时间大于当前时间，这个时候并没有真正的发布文章，而是把文章的状态设置为了8（审核通过待发布）
    // 定时任务的作用就是每分钟去扫描这些待发布的文章，如果当前文章的状态为8，并且发布时间小于当前时间的，立刻发布当前文章
    @Override
    public List<Integer> findRelease() {
        //文章状态为8发布时间要小于等于当前时间
        List<WmNews> list = list(Wrappers.<WmNews>lambdaQuery().eq(WmNews::getStatus, 8).le(WmNews::getPublishTime, new Date()));
        //WmNews中都是对象，转为list集合
        return list.stream().map(WmNews::getId).collect(Collectors.toList());
    }

    private final WmNewsMapper wmNewsMapper;

    // 根据标题模糊分页查询文章信息
    @Override
    public PageResponseResult findList(NewsAuthDto dto) {
        // 1.分页检查
        dto.checkParam();
        // 2.设置分页条件-因mybatis-plus未支持多表查询，此处自定义
        // 第一页从0开始
        dto.setPage((dto.getPage() - 1) * dto.getSize());
        if (StringUtils.isNotBlank(dto.getTitle())) {
            // like查询
            dto.setTitle("%" + dto.getTitle() + "%");
        }
        // 3.分页查询
        List<WmNewsVo> list = wmNewsMapper.findListAndPage(dto);
        //分页查询，需要统计数据
        int count = wmNewsMapper.findListCount(dto);
        // 4.结果返回
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), count);
        pageResponseResult.setData(list);
        return pageResponseResult;
    }

    private final WmUserMapper wmUserMapper;

    // 根据文章id查询文章详情
    @Override
    public WmNewsVo findWmNewsVo(Integer id) {
        // 1.查询文章信息
        WmNews wmNews = getById(id);
        // 2.查询作者
        WmUser wmUser = null;
        if (wmNews != null && wmNews.getUserId() != null) {
            wmUser = wmUserMapper.selectById(wmNews.getUserId());
        }
        // 3.封装vo信息返回
        WmNewsVo wmNewsVo = new WmNewsVo();
        // 将上面数据都拷贝过来
        BeanUtils.copyProperties(wmNews, wmNewsVo);
        if (wmUser != null) {
            wmNewsVo.setAuthorName(wmUser.getName());
        }
        return wmNewsVo;
    }

    // =======================各方法实现====================================

    private final WmNewsMaterialMapper wmNewsMaterialMapper;


    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 保存或修改文章方法实现
     */
    private void saveWmNews(WmNews wmNews, Short isSubmit) {
        wmNews.setStatus(isSubmit);
        wmNews.setUserId(WmThreadLocalUtils.getUser().getId());
        wmNews.setTagId(wmNews.getTagId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        // 默认上架
        wmNews.setEnable((short) 1);
        boolean flag = false;
        if (wmNews.getId() == null) {
            // 保存
            flag = save(wmNews);
        } else {
            // 如果是修改，则先删除素材与文章的关系
            LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper<>();
            // 查找文章id
            queryWrapper.eq(WmNewsMaterial::getNewsId, wmNews.getId());
            // 查到多少删多少
            wmNewsMaterialMapper.delete(queryWrapper);
            // 修改
            flag = updateById(wmNews);
        }
        // 生产者-发送消息
        if (flag) {
            // 生产者发送消息和数据
            kafkaTemplate.send(NewsAutoScanConstants.WM_NEWS_AUTO_SCAN_TOPIC, JSON.toJSONString(wmNews.getId()));
        }
    }

    // URL中提取图片信息
    private List<String> ectractUrlInfo(List<Map> list) {
        ArrayList<String> materials = new ArrayList<>();
        for (Map map : list) {
            // 如果type是image，则取到图片链接
            if (map.get("type").equals(WemediaContans.WM_NEWS_TYPE_IMAGE)) {
                // 从value中拿到图片链接
                String imgUrl = (String) map.get("value");
                // 去掉图片前面的IP地址
                imgUrl = imgUrl.replace(fileServerUrl, "");
                materials.add(imgUrl);
            }
        }
        return materials;
    }

    /**
     * 保存素材与文章内容的关系,指明引用类型， 0 内容引用 1主图引用
     */
    private ResponseResult<?> saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        return saveRelativeInfo(materials, newsId, WemediaContans.WM_NEWS_CONTENT_REFERENCE);
    }

    /**
     * 保存关系
     */
    private final WmMaterialMapper wmMaterialMapper;

    private ResponseResult<?> saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        // 1.获取数据库素材信息
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(WmMaterial::getUrl, materials);
        lambdaQueryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtils.getUser().getId());
        List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(lambdaQueryWrapper);
        // 2.通过图片的路径获取素材的id
        // 思路：dbMaterials转为map数据    key value 取value值
        List<String> materialsIds = new ArrayList<>();
        Map<String, Integer> urlidMap = dbMaterials.stream().collect(Collectors.toMap(WmMaterial::getUrl, WmMaterial::getId));
        for (String val : materials) {
            String materialId = String.valueOf(urlidMap.get(val));
            if ("null".equals(materialId)) {
                return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "应用图片失效");
            }
            // 若找到了
            materialsIds.add(materialId);
        }
        // 3.批量保存数据
        wmNewsMaterialMapper.saveRelations(materialsIds, newsId, type);
        /*返回null表示没有问题，继续往下运行*/
        return null;
    }

    /**
     * 设置封面与素材的关系
     */
    private ResponseResult<?> saveRelativeInfoForCover(WmNewsDto dto, List<String> materials, WmNews wmNews) {
        List<String> images = dto.getImages();
        // 自动匹配封面
        // 如果是自动就需要从文中找图片设置封面
        if (dto.getType().equals(WemediaContans.WM_NEWS_AUTO_TYPE)) {
            // 内容中图片数量小于等于2 设置为单图
            if (materials.size() > 0 && materials.size() < 2) {
                wmNews.setType(WemediaContans.WM_NEWS_SINGLE_TYPE);
                // 取一张图
                images = materials.stream().limit(1).collect(Collectors.toList());
            } else if (materials.size() > 2) {
                //内容中图片数量大于2 设置为多图
                wmNews.setType(WemediaContans.WM_NEWS_MANY_TYPE);
                // 取三张图
                images = materials.stream().limit(3).collect(Collectors.toList());
            } else {
                //内容中无图    设置无图
                /*不取图，，images本为空，就不需要赋值了*/
                wmNews.setType(WemediaContans.WM_NEWS_NONE_TYPE);
            }
            //修改文章信息
            //判断当前有无图片
            if (images != null && images.size() > 0) {
                wmNews.setImages(images.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(fileServerUrl, "")
                        .replace(" ", ""));
            }
            updateById(wmNews);
        }
        // 保存封面图片与素材的关系
        if (images != null && images.size() > 0) {
            ResponseResult responseResult = saveRelativeInfoForImage(images, wmNews.getId());
            if (responseResult != null) {
                return responseResult;
            }
        }
        //没有问题就返回    null
        return null;
    }

    /*
     * 保存封面与素材关系
     */
    private ResponseResult<?> saveRelativeInfoForImage(List<String> images, Integer id) {
        List<String> materials = new ArrayList<>();
        for (String image : images) {
            materials.add(image.replace(fileServerUrl, ""));
        }
        return saveRelativeInfo(materials, id, WemediaContans.WM_NEWS_COVER_REFERENCE);
    }
}
