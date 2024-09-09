package plus.axz.admin.service;

import plus.axz.model.admin.dtos.NewsAuthDto;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;

/**
 * @author xiaoxiang
 * description 自动审核
 */
public interface WeMediaNewsAutoScanService {
    // 自媒体文章审核
    public void autoScanByMediaNewsId(Integer id);

    // 人工审核
    // 根据文章标题分页查询自媒体文章列表
    public PageResponseResult findNews(NewsAuthDto dto);

    // 根据文章id文章信息
    public ResponseResult<?> findOne(Integer id);

    /**
     * 审核通过、驳回
     * @param type  0驳回 1通过
     */
    public ResponseResult<?> updateStatus(NewsAuthDto dto,Integer type);
}
