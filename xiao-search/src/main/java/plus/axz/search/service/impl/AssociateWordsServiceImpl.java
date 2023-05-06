package plus.axz.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.model.search.pojos.AssociateWords;
import plus.axz.search.mapper.AssociateWordsMapper;
import plus.axz.search.model.Trie;
import plus.axz.search.service.AssociateWordsService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars 联想词实现类
 */
@Slf4j
@Service
public class AssociateWordsServiceImpl extends ServiceImpl<AssociateWordsMapper, AssociateWords> implements AssociateWordsService {
    @Override
    public ResponseResult search(UserSearchDto userSearchDto) {
        // 1.检查参数
        if (userSearchDto.getPage_size()>50){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"搜索词过多，输入关键字再重试下~");
        }
        // 2.模糊查询数据
        Page pageNum = new Page(0, userSearchDto.getPage_size());
        List<AssociateWords> list = list();
        IPage page = page(pageNum, Wrappers.<AssociateWords>lambdaQuery().like(AssociateWords::getAssociateWords, userSearchDto.getSearch_words()));
        return ResponseResult.okResult(page.getRecords());
    }

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public ResponseResult searchV2(UserSearchDto userSearchDto) {
        // 1.从缓存中获取数据
        String associateList = redisTemplate.opsForValue().get("associate_list");
        List<AssociateWords> associateWords = null;
        if (StringUtils.isNotEmpty(associateList)){
            // 2.缓存中存在，直接拿数据
            associateWords = JSON.parseArray(associateList, AssociateWords.class);
        }else {
            // 3.缓存中不存在，从数据库中获取数据，存储到redis中
            associateWords =list();
            redisTemplate.opsForValue().set("associate_list",JSON.toJSONString(associateWords));
        }
        // 4.构建trie数据结构，从trie中获取数据，封装返回
        Trie trie = new Trie();
        for (AssociateWords associateWord : associateWords) {
            trie.insert(associateWord.getAssociateWords());
        }
        // 从前台搜索拿数据
        List<String> start = trie.startWith(userSearchDto.getSearch_words());
        List<AssociateWords> resultList = new ArrayList<>();
        for (String s : start) {

            AssociateWords words = new AssociateWords();
            words.setAssociateWords(s);
            resultList.add(words);
        }
        return ResponseResult.okResult(resultList);
    }
}
