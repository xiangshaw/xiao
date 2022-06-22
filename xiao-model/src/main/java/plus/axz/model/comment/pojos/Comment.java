package plus.axz.model.comment.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import plus.axz.model.common.annotation.IdEncrypt;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars app评论信息
 */
@Data
@Document("comment")
public class Comment {
    /**
     * id
     */
    private String id;

    /**
     * 用户ID
     */
    private Integer authorId;

    /**
     * 用户昵称
     */
    private String authorName;

    /**
     * 文章id或动态id
     */
    @IdEncrypt
    private Long entryId;

    /**
     * 频道标签ID
     */
    private Integer tagId;

    /**
     * 评论内容类型
     * 0 文章
     * 1 动态
     */
    private Short type;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 作者头像
     */
    private String image;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 回复数
     */
    private Integer reply;

    /**
     * 文章标记
     * 0 普通评论
     * 1 热点评论
     * 2 推荐评论
     * 3 置顶评论
     * 4 精品评论
     * 5 大V 评论
     */
    private Short flag;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 地理位置
     */
    private String address;

    /**
     * 评论排列序号
     */
    private Integer ord;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updatedTime;

}