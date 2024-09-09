package plus.axz.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

import java.util.Date;

/**
 * @author xiaoxiang
 * description 文章信息表，存储已发布的文章
 */
@Data
@TableName("article")
public class Article {
    @TableId(value = "id", type = IdType.ID_WORKER)
    @IdEncrypt
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者id
     */
    @TableField("author_id")
    @IdEncrypt
    private Long authorId;
    /**
     * 作者名称
     */
    @TableField("author_name")
    private String authorName;
    /**
     * 频道id
     */
    @TableField("tag_id")
    private Integer tagId;
    /**
     * 频道名称
     */
    @TableField("tag_name")
    private String tagName;
    /**
     * 文章布局 0 无图文章 1 单图文章  2 多图文章
     */
    private Short layout;
    /**
     4 大V 文章
     1 热点文章
     2 置顶文章
     * 文章标记   0 普通文章
     */
    private Byte flag;
    /**
     3 精品文章
     * 文章封面图片 多张逗号分隔
     */
    private String images;
    /**
     * 标签
     */
    private String labels;
    /**
     * 点赞数量
     */
    private Integer likes;
    /**
     * 收藏数量
     */
    private Integer collection;
    /**
     * 评论数量
     */
    private Integer comment;
    /**
     * 阅读数量
     */
    private Integer views;
    /**
     * 省市
     */
    @TableField("province_id")
    private Integer provinceId;
    /**
     * 市区
     */
    @TableField("city_id")
    private Integer cityId;
    /**
     * 区县
     */
    @TableField("county_id")
    private Integer countyId;
    /**
     * 创建时间
     */
    @TableField("created_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;
    /**
     * 发布时间
     */
    @TableField("publish_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date publishTime;
    /**
     * 同步状态
     */
    @TableField("sync_status")
    private Boolean syncStatus;
    /**
     * 来源
     */
    private Boolean origin;
}
