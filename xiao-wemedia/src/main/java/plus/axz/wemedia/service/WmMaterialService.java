package plus.axz.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmMaterialDto;
import plus.axz.model.wemedia.pojos.WmMaterial;

/**
 * @author xiaoxiang
 * @date 2022年04月02日
 * @particulars 图片上传接口
 */
public interface WmMaterialService extends IService<WmMaterial> {
    /**
     * 素材-上传图片
     * @author xiaoxiang
     * @date 2022/4/2
     * @param file
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult uploadPicture(MultipartFile file);
    /**
     * 查询素材
     * @author xiaoxiang
     * @date 2022/4/2
     * @param dto
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult findList(WmMaterialDto dto);
    /**
     * 根据id删除图片
     * @author xiaoxiang
     * @date 2022/4/2
     * @param id
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    public ResponseResult delPicture(Integer id);

    /**
     * 收藏与取消收藏
     * @author xiaoxiang
     * @date 2022/4/2
     * @param id
     * @param type
     * @return
     * 0不收藏    1收藏
     */
    ResponseResult updateStatus(Integer id, Short type);
}
