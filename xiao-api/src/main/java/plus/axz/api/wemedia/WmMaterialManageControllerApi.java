package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmMaterialDto;
import plus.axz.model.wemedia.pojos.WmMaterial;

/**
 * @author xiaoxiang
 * @date 2022年04月01日
 * @particulars
 * 1 根据素材id删除图片
 * 2 如果当前图片被引用，则不能删除
 * 3 在fastdfs删除中不需要带访问图片的url，只需要文件名，如下
 * http://192.168.20.221/group1/M00/00/00/wKjIgl5rYzyAb55kAAANashsaxzUz0834.png
 * 一旦该图片被引用会在这个表中插入数据，用作素材与文章的关联
 */
public interface WmMaterialManageControllerApi {
    /**
     * 素材图片上传
     * @author xiaoxiang
     * @date 2022/4/2
     * @param file
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    @ApiOperation("图片素材上传")
    public ResponseResult uploadPicture(MultipartFile file);
    /**
     * 查询素材列表
     * @author xiaoxiang
     * @date 2022/4/2
     * @param dto
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    @ApiOperation("查询素材列表")
    public ResponseResult findList(WmMaterialDto dto);
    /**
     * 根据id删除图片
     * @author xiaoxiang
     * @date 2022/4/2
     * @param id
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    @ApiOperation("根据id删除图片")
    public ResponseResult delPicture(Integer id);

    /**
     * 取消收藏
     * @author xiaoxiang
     * @date 2022/4/2
     * @param id
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    @ApiOperation("取消收藏")
    public ResponseResult cancelCollectionMaterial(Integer id);

    /**
     * 添加收藏
     * @author xiaoxiang
     * @date 2022/4/2
     * @param id
     * @return plus.axz.model.common.dtos.ResponseResult
     */
    @ApiOperation("添加收藏")
    public ResponseResult collectionMaterial(Integer id);
}
