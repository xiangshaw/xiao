package plus.axz.model.search.dtos;

import lombok.Data;
import plus.axz.model.common.annotation.IdEncrypt;

import java.util.Date;

/**
 * @author xiaoxiang
 * description es搜索文章、搜索历史记录
 */
@Data
public class UserSearchDto {

    // 设备ID
    @IdEncrypt
    Integer equipmentId;
    /**
     * 搜索关键字
     */
    String search_words;
    /**
     * 当前页
     */
    int pageNum;
    /**
     * 分页条数
     */
    int page_size;
    /**
     * 最小时间
     */
    Date minBehotTime;

    /**
     * 接收搜索历史记录id
     */
    Integer id;

    /*查看是否首页*/
    public int getFromIndex() {
        if (this.pageNum < 1) {
            // 0 即为首页
            return 0;
        }
        if (this.page_size < 1) {
            this.page_size = 10;
        }
        return
                this.page_size * (pageNum - 1);
    }
}
