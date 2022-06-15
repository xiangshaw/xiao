package plus.axz.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import plus.axz.admin.feign.ArticleFeign;
import plus.axz.admin.feign.WemediaFeign;
import plus.axz.admin.mapper.SensitiveMapper;
import plus.axz.admin.mapper.TagMapper;
import plus.axz.admin.service.WeMediaNewsAutoScanService;
import plus.axz.common.aliyun.GreeTextScan;
import plus.axz.common.aliyun.GreenImageScan;
import plus.axz.common.fastdfs.FastDFSClient;
import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.admin.pojos.Tag;
import plus.axz.model.article.pojos.Article;
import plus.axz.model.article.pojos.ArticleConfig;
import plus.axz.model.article.pojos.ArticleContent;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.wemedia.pojos.WmNews;
import plus.axz.model.wemedia.pojos.WmUser;
import plus.axz.model.wemedia.vo.WmNewsVo;
import plus.axz.utils.common.SensitiveWordUtil;

import java.util.*;

/**
 * @author xiaoxiang
 * @date 2022年05月03日
 * @particulars 文章自动审核
 */
@Service
@Log4j2
public class WemediaNewsAutoScanServiceImpl implements WeMediaNewsAutoScanService {
    @Autowired
    private WemediaFeign wemediaFeign;
    @Autowired
    private TagMapper tagMapper;

    @GlobalTransactional
    @Override
    public void autoScanByMediaNewsId(Integer id) {
        // 检查参数
        if (id == null) {
            log.error("当前审核id为里空");
            return;
        }
        // 1.根据自媒体查询文章信息
        WmNews wmNews = wemediaFeign.findById(id);
        if (wmNews == null) {
            log.error("审核的自媒体文章不存在，自媒体的id:{}", id);
            return;
        }
        // 2.文章状态为4（人工审核通过）直接保存数据和创建索引
        if (wmNews.getStatus() == 4) {
            saveWmArticle(wmNews);
            return;
        }
        // 3.文章状态为8 发布时间小于等于当前时间    直接保存数据
        if (wmNews.getStatus() == 8 && wmNews.getSubmitedTime().getTime() <= System.currentTimeMillis()) {
            // 保存数据
            saveWmArticle(wmNews);
            return;
        }
        // 4.文章状态为1，待审核
        if (wmNews.getStatus() == 1) {
            // 抽取文章内容中的纯文本和图片
            Map<String, Object> contentAndImagesResult = handleTextAndImages(wmNews);
            // 4.1 文本审核 - 传wmNews是为了审核失败改文章状态
            boolean textScanBoolean = handleTextScan((String) contentAndImagesResult.get("content"), wmNews);
            //如果报错或者审核失败，非true,需要终止该autoScanByMediaNewsId方法。
            if (!textScanBoolean) return;
            // 4.2 图片审核
            boolean imagesScanBoolean = handleImagesScan((List<String>) contentAndImagesResult.get("images"), wmNews);
            if (!imagesScanBoolean) return;
            // 4.3 自管理的敏感词审核
            boolean sensitiveScanBoolean = handleSensitive((String) contentAndImagesResult.get("content"),wmNews);
            if (!sensitiveScanBoolean) return;
            // 4.4 发布时间大于当前时间，修改文章状态为8
            if (wmNews.getPublishTime().getTime()>System.currentTimeMillis()){
                updateWmNews(wmNews,(short) 8,"审核通过待发布");
                return;
            }
            // 4.5 审核通过，修改自媒体文章状态为9 保存app文章相关数据
            saveWmArticle(wmNews);
        }
    }

    @Override
    public PageResponseResult findNews(NewsAuthDto dto) {
        // 分页查询
        PageResponseResult responseResult = wemediaFeign.findList(dto);
        // 返回的数据中有图片需要显示，需要回显一个fasfdfs服务器地址
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    @Override
    public ResponseResult findOne(Integer id) {
        // 1.参数检查
        if (id == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据
        WmNewsVo wmNewsVo = wemediaFeign.findWmNewsVo(id);
        // 3.结果封装
        ResponseResult responseResult = ResponseResult.okResult(wmNewsVo);
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    @Override
    public ResponseResult updateStatus(NewsAuthDto dto, Integer type) {
        // 1.参数检查
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.查询文章信息
        WmNews wmNews = wemediaFeign.findById(dto.getId());
        if (wmNews == null) {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.审核失败
        if (type.equals(0)) {/*失败*/
            updateWmNews(wmNews, (short) 2, dto.getMsg());
        } else if (type.equals(1)) {/*成功*/
            // 4.审核成功
            updateWmNews(wmNews, (short) 4, "人工审核通过");
        }
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    // 抽取文本内容和图片
    private Map<String, Object> handleTextAndImages(WmNews wmNews) {
        // 文章内容
        String content = wmNews.getContent();
        // 存文章内容
        StringBuilder contents = new StringBuilder();
        // 存图片
        List<String> images = new ArrayList<>();
        // 每一个map 是一个对象 text或image
        List<Map> list = JSONArray.parseArray(content, Map.class);
        for (Map map : list) {
            if (map.get("type").equals("text")) {
                // 拼接字符串
                contents.append(map.get("value"));
            }
            if (map.get("type").equals("image")) {
                images.add((String) map.get("value"));
            }
        }
        //  审核封面图片，0是无图文章，就无需向images装入数据
        if (wmNews.getImages() != null && wmNews.getType() != 0) {
            // 多图存储是由","分割的
            String[] split = wmNews.getImages().split(",");
            // 数组转集合，，，将图片存入images
            images.addAll(Arrays.asList(split));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", contents.toString());
        map.put("images", images);
        return map;
    }

    @Autowired
    private GreeTextScan greeTextScan;

    // 文本审核
    private boolean handleTextScan(String content, WmNews wmNews) {
        // 默认返回true,审核失败返回false
        boolean flag = true;
        try {
            Map map = greeTextScan.greeTextScan(content);
            // 审核不成功
            // 图片审核：    block,review。
            // 参考：https://help.aliyun.com/document_detail/70292.html?spm=a2c4g.11186623.6.616.5d7d1e7f9vDRz4
            if (!map.get("suggestion").equals("pass")) {
                if (!map.get("suggestion").equals("block")) {
                    updateWmNews(wmNews, (short) 2, "文章中有敏感词汇");
                    flag = false;
                }
                // 人工审核
                if (!map.get("suggestion").equals("review")) {
                    // 修改自媒体文章状态。并告知审核失败原因
                    updateWmNews(wmNews, (short) 3, "文章中有不确定词汇，已提交给人工审核");
                    flag = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Autowired
    private GreenImageScan greenImageScan;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Value("${fdfs.url}")
    private String fileServerUrl;

    // 图片审核
    private boolean handleImagesScan(List<String> images, WmNews wmNews) {
        if (images == null) {
            // 无图无需再审核
            return true;
        }
        boolean flag = true;
        List<byte[]> imageList = new ArrayList<>();
        try {
            // 拿到每一个图信息
            for (String image : images) {
                // 将图片前端地址设为空
                String imageName = image.replace(fileServerUrl, "");
                // 截取第一个
                int index = imageName.indexOf("/");
                // 截取从0到index
                String groupName = imageName.substring(0, index);
                String imagePath = imageName.substring(index + 1);
                // 下载文件，再传入imageScan接口
                byte[] imageBytes = fastDFSClient.download(groupName, imagePath);
                // 拿到某一个图片的byte数组
                imageList.add(imageBytes);
            }
            // 传入阿里云进行审核--阿里云图片审核
            Map map = greenImageScan.imageScan(imageList);
            // 审核不通过
            // 图片审核：    block,review。
            // 参考：https://help.aliyun.com/document_detail/70292.html?spm=a2c4g.11186623.6.616.5d7d1e7f9vDRz4
            if (!map.get("suggestion").equals("pass")) {
                if (map.get("suggestion").equals("block")) {
                    // 修改自媒体文章状态。并告知审核失败原因
                    updateWmNews(wmNews, (short) 2, "文章中图片有违规");
                    flag = false;
                }
                // 人工审核
                if (map.get("suggestion").equals("review")){
                    // 修改自媒体状态。并告知审核失败原因
                    updateWmNews(wmNews,(short) 3,"文章图片中有不确定元素，已提交给人工审核");
                    flag =false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    // 自管理敏感词审核
    @Autowired
    private SensitiveMapper sensitiveMapper;
    private boolean handleSensitive(String content, WmNews wmNews) {
        boolean flag = true;
        // 返回所有敏感词
        List<String> allSensitive = sensitiveMapper.findAllSensitive();
        // 初始化敏感词
        SensitiveWordUtil.initMap(allSensitive);
        // 文章内容过滤
        Map<String, Integer> resultMap = SensitiveWordUtil.matchWords(content);
        if (resultMap.size()>0){
            log.error("敏感词审核未通过，包含了敏感词:{}", resultMap);
            // 找到敏感词，审核不通过
            updateWmNews(wmNews,(short) 2,"文章中包含敏感词");
            flag = false;
        }
        return flag;
    }

    @Autowired
    private ArticleFeign articleFeign;

    /**
     * 保存文章相关数据
     *
     * @author xiaoxiang
     * @date 2022/5/5
     */
    private void saveWmArticle(WmNews wmNews) {
        // 保存文章
        Article article = saveArticle(wmNews);
        // 保存文章配置
        saveArticleConfig(article);
        // 保存文章内容
        saveArticleContent(article, wmNews);
        // 修改自媒体文章状态为9
        wmNews.setArticleId(article.getId());
        updateWmNews(wmNews, (short) 9, "审核通过");
        // TODO ES索引创建
    }

    // 保存文章
    private Article saveArticle(WmNews wmNews) {
        Article article = new Article();
        article.setTitle(wmNews.getTitle()); // 标题
        article.setLayout(wmNews.getType()); // 布局
        article.setImages(wmNews.getImages());
        article.setLabels(wmNews.getLabels());
        article.setCreatedTime(new Date());
        // 获取作者 - 根据自媒体用户id取自媒体人
        Integer wmNewsUserId = wmNews.getUserId();
        WmUser wmUser = wemediaFeign.findWmUserById(wmNewsUserId);
        if (wmNews != null) {
            String wmUserName = wmUser.getName();
            // 拿自媒体用户名-并关联作者信息
            Author author = articleFeign.selectAuthorByName(wmUserName);
            //实名认证审核的时候，自媒体人中插入数据，同时在作者中插入数据。所以可以通过以下方式查
            if (author != null) {
                article.setAuthorId(author.getId().longValue());
                article.setAuthorName(author.getName());
            }
        }
        // 获取频道标签类信息
        Integer tagId = wmNews.getTagId();
        Tag tag = tagMapper.selectById(tagId);
        if (tag != null) {
            article.setTagId(tag.getId());
            article.setTagName(tag.getTagName());
        }
        return articleFeign.saveArticle(article);
    }

    // 保存文章配置
    private void saveArticleConfig(Article article) {
        ArticleConfig articleConfig = new ArticleConfig();
        // 从文章获取id、默认可转发、默认不让删除、默认下架状态、默认开启评论
        articleConfig.setArticleId(article.getId());
        articleConfig.setIsForward(true);
        articleConfig.setIsDelete(false);
        articleConfig.setIsDown(true);
        articleConfig.setIsComment(true);
        articleFeign.saveArticleConfig(articleConfig);
    }

    // 保存文章内容
    private void saveArticleContent(Article article, WmNews wmNews) {
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(article.getId());
        articleContent.setContent(wmNews.getContent());
        articleFeign.saveArticleContent(articleContent);
    }

    // 抽取审核类
    private void updateWmNews(WmNews wmNews, short status, String msg) {
        wmNews.setStatus(status);
        wmNews.setReason(msg);
        wemediaFeign.updateWmNews(wmNews);
    }
}
