package plus.axz.model.common.dtos;

import java.io.Serializable;
/**
 * @author xiaoxiang
 * description 分页通用返回
 */
public class PageResponseResult extends ResponseResult implements Serializable {
    private Integer currentPage;
    private Integer size;
    private Integer total;
                                        /*当前页，每页显示条数，总条数*/
    public PageResponseResult(Integer currentPage, Integer size, Integer total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
    }

    public PageResponseResult() {

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
