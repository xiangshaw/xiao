package plus.axz.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.model.behavior.pojos.BehaviorEntry;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.model.user.pojos.User;
import plus.axz.search.feign.BehaviorFeign;
import plus.axz.search.service.ArticleSearchService;
import plus.axz.search.service.UserSearchService;
import plus.axz.utils.threadlocal.AppThreadLocalUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxiang
 * @date 2022年06月24日
 * @particulars App文章搜索
 */
@Service
@Log4j2
public class ArticleSearchServiceImpl implements ArticleSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private UserSearchService userSearchService;

    @Override
    public ResponseResult search(UserSearchDto dto) throws IOException {
        // 1.检查参数
        if (dto == null || StringUtils.isBlank(dto.getSearch_words())){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 只有在首页查询的时候才会保存
        if (dto.getFromIndex() == 0){
            BehaviorEntry entry = getEntry(dto);
            if (entry == null){
                return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
            }
            // 行为实体 和 搜索词
            userSearchService.insert(entry.getEntryId(),dto.getSearch_words());

        }

        // 2.从ES索引库中检索数据
        //构建搜索请求对象，需要指定索引库名称
        SearchRequest searchRequest = new SearchRequest("article_info");
        //条件构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据关键字分词查询--》title  content
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(dto.getSearch_words()).field("title").defaultOperator(Operator.OR);
        boolQueryBuilder.must(queryStringQueryBuilder);
        // 查询小于minBehotTime的数据
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime").lt(dto.getMinBehotTime());
        boolQueryBuilder.filter(rangeQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        // 分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(dto.getPage_size());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 3.封装返回
        List<Map> articleList = new ArrayList<>();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            Map map = JSON.parseObject(sourceAsString, Map.class);
            articleList.add(map);
        }
        return ResponseResult.okResult(articleList);
    }
    @Autowired
    private BehaviorFeign behaviorFeign;

    /**
     * 获取行为实体
     * @param userSearchDto
     * @return
     */
    private BehaviorEntry getEntry(UserSearchDto userSearchDto) {
        User user = AppThreadLocalUtils.getUser();
        return behaviorFeign.findByUserIdOrEntryId(user.getId(),userSearchDto.getEquipmentId());
    }
}
