package plus.axz.wemedia.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.api.wemedia.WmMaterialManageControllerApi;
import plus.axz.common.constants.wemedia.WemediaContans;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmMaterialDto;
import plus.axz.wemedia.service.WmMaterialService;

/**
 * @author xiaoxiang
 * description  图片素材上传
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController implements WmMaterialManageControllerApi {

    private final WmMaterialService wmMaterialService;

    @PostMapping("/upload_picture")
    @Override
    public ResponseResult<?> uploadPicture(@RequestBody MultipartFile file) {
        return wmMaterialService.uploadPicture(file);
    }

    @PostMapping("/list")
    @Override
    public ResponseResult<?> findList(WmMaterialDto dto) {
        return wmMaterialService.findList(dto);
    }

    @DeleteMapping("/del_picture/{id}")
    @Override
    public ResponseResult<?> delPicture(@PathVariable("id") Integer id) {
        return wmMaterialService.delPicture(id);
    }

    @GetMapping("/cancel_collect/{id}")
    @Override
    //取消收藏
    public ResponseResult<?> cancelCollectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id, WemediaContans.CANCEL_COLLECT_MATERIAL);
    }

    @GetMapping("/collect/{id}")
    @Override
    // 收藏
    public ResponseResult<?> collectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id,WemediaContans.COLLECT_MATERIAL);
    }
}
