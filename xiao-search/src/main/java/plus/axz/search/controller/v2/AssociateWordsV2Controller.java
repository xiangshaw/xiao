package plus.axz.search.controller.v2;

import lombok.RequiredArgsConstructor;
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
 * description 联想词V2
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/associate")
public class AssociateWordsV2Controller implements AssociateWordsControllerApi {

    private final AssociateWordsService associateWordsService;

    @PostMapping("/search")
    @Override
    public ResponseResult<?> search(@RequestBody UserSearchDto dto) {
        return associateWordsService.searchV2(dto);
    }
}
