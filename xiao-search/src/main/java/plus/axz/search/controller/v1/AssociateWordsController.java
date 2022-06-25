package plus.axz.search.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.search.AssociateWordsControllerApi;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.search.dtos.UserSearchDto;
import plus.axz.search.service.AssociateWordsService;

/**
 * @author xiaoxiang
 * @date 2022年06月25日
 * @particulars 联想词前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/associate")
public class AssociateWordsController implements AssociateWordsControllerApi {
    @Autowired
    private AssociateWordsService associateWordsService;

    @Override
    @PostMapping("/search")
    public ResponseResult search(@RequestBody UserSearchDto userSearchDto) {
        return associateWordsService.search(userSearchDto);
    }
}
