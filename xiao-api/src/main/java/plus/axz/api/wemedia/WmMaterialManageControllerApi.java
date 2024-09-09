package plus.axz.api.wemedia;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.wemedia.dtos.WmMaterialDto;

/**
 * @author xiaoxiang
 * description
 * 1 根据素材id删除图片
 * 2 如果当前图片被引用，则不能删除
 * 3 在fastdfs删除中不需要带访问图片的url，只需要文件名，如下
 * http://192.168.20.221/group1/M00/00/00/wKjIgl5rYzyAb55kAAANashsaxzUz0834.png
 * 一旦该图片被引用会在这个表中插入数据，用作素材与文章的关联
 */
public interface WmMaterialManageControllerApi {

    @ApiOperation("图片素材上传")
    ResponseResult<?> uploadPicture(MultipartFile file);

    @ApiOperation("查询素材列表")
    ResponseResult<?> findList(WmMaterialDto dto);

    @ApiOperation("根据id删除图片")
    ResponseResult<?> delPicture(Integer id);

    @ApiOperation("取消收藏")
    ResponseResult<?> cancelCollectionMaterial(Integer id);

    @ApiOperation("添加收藏")
    ResponseResult<?> collectionMaterial(Integer id);
}
