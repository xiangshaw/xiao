package plus.axz.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmMaterialDto;
import plus.axz.model.wemedia.pojos.WmMaterial;

/**
 * @author xiaoxiang
 * description  图片上传接口
 */
public interface WmMaterialService extends IService<WmMaterial> {
    /**
     * 素材-上传图片
     */
    ResponseResult<?> uploadPicture(MultipartFile file);

    /**
     * 查询素材
     */
    ResponseResult<?> findList(WmMaterialDto dto);

    /**
     * 根据id删除图片
     */
    ResponseResult<?> delPicture(Integer id);

    /**
     * 收藏与取消收藏
     * 0不收藏    1收藏
     */
    ResponseResult<?> updateStatus(Integer id, Short type);
}
