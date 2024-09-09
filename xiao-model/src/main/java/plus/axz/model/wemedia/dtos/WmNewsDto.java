package plus.axz.model.wemedia.dtos;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * description 保存、修改文章，保存草稿
 */
@Data
public class WmNewsDto {
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * tag标签id
     */
    @TableField("tagId")
    private Integer tagId;
    /**
     * 文章分类标签
     */
    private String labels;
    /**
     * 发布时间
     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章封面类型  0 无图 1 单图 3 多图 -1 自动
     */
    private Short type;
    /**
     * 是否上架 0 下架 1 上架
     */
    private Short enable;
    /**
     * 提交时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date submitedTime;
    /**
     * 状态    提交为1  草稿为0
     */
    private Short status;
    /**
     * 拒绝理由
     */
    private String reason;
    /**
     * 封面图片列表    多张图以逗号隔开
     */
    private List<String> images;
}
