package plus.axz.search.controller.v2;

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
 * @particulars 联想词V2
 */
@RestController
@RequestMapping("/api/v2/associate")
public class AssociateWordsV2Controller implements AssociateWordsControllerApi {

    @Autowired
    private AssociateWordsService associateWordsService;

    @PostMapping("/search")
    @Override
    public ResponseResult search(@RequestBody UserSearchDto dto) {
        return associateWordsService.searchV2(dto);
    }
}
