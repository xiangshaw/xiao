package plus.axz.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.common.fastdfs.FastDFSClient;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.wemedia.dtos.WmMaterialDto;
import plus.axz.model.wemedia.pojos.WmMaterial;
import plus.axz.model.wemedia.pojos.WmNewsMaterial;
import plus.axz.model.wemedia.pojos.WmUser;
import plus.axz.utils.threadlocal.WmThreadLocalUtils;
import plus.axz.wemedia.mapper.WmMaterialMapper;
import plus.axz.wemedia.mapper.WmNewsMaterialMapper;
import plus.axz.wemedia.service.WmMaterialService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoxiang
 * @date 2022年04月02日
 * @particulars 素材图片素材实现类
 */
@Service
@Log4j2
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Value("${fdfs.url}")
    private String fileServerUrl;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Override
    public ResponseResult uploadPicture(MultipartFile file) {
        // 1. 检查参数
        if (file == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2. 图片上传fastdfs
        String fileId = null;
        try {
            fileId = fastDFSClient.uploadFile(file);
        } catch (IOException e){
            e.printStackTrace();
            return ResponseResult.errorResult(ResultEnum.SERVER_ERROR,"图片上传失败");
        }
        // 3. 保存素材数据到表中material
        // 线程中获取用户信息
        WmUser user = WmThreadLocalUtils.getUser();
        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUrl(fileId);
        // 0 默认不收藏 1 收藏
        wmMaterial.setIsCollection((short)0);
        wmMaterial.setUserId(user.getId());
        // 0 默认图片 1 视频
        wmMaterial.setType((short)0);
        wmMaterial.setCreatedTime(new Date());
        save(wmMaterial);
        // 拼接图片地址
        wmMaterial.setUrl(fileServerUrl + fileId);
        return ResponseResult.okResult(wmMaterial);
    }

    @Override
    public ResponseResult findList(WmMaterialDto dto) {
        // 1. 检查参数
        dto.checkParam();
        // 2. 带条件分页查询
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 3. 获取当前登录用户
        Integer uid = WmThreadLocalUtils.getUser().getId();
        lambdaQueryWrapper.eq(WmMaterial::getUserId,uid);
        // 4. 是否收藏？不等于null且等于1
        if (dto.getIsCollection() != null && dto.getIsCollection().shortValue() ==1){
            lambdaQueryWrapper.eq(WmMaterial::getIsCollection,dto.getIsCollection());
        }
        // 5. 按照日期倒序排序
        lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);
        IPage pageParam  = new Page(dto.getPage(), dto.getSize());
        IPage resultPage = page(pageParam, lambdaQueryWrapper);
        // 6. 结果返回
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) resultPage.getTotal());
        List<WmMaterial> datas = resultPage.getRecords();
        // 7. 为每一个图片添加前缀
        datas.stream().map(item->{item.setUrl(fileServerUrl+item.getUrl());
            return item;
        }).collect(Collectors.toList());
        responseResult.setData(datas);
        return responseResult;
    }

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;
    @Override
    public ResponseResult delPicture(Integer id) {
        // 1. 检查参数
        if (id == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前图片是否被引用
        WmMaterial wmMaterial = getById(id);
        if (wmMaterial==null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3. 查询图片是否被引用
        LambdaQueryWrapper<WmNewsMaterial> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(WmNewsMaterial::getMaterialId,id);
        Integer count = wmNewsMaterialMapper.selectCount(lambdaQueryWrapper);
        if (count>0){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"当前图片被引用");
        }
        // 4. 删除数据库中图片
        removeById(id);
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public ResponseResult updateStatus(Integer id, Short type) {
        // 1. 检查参数
        if (id == null){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2. 更新状态
        // 获取当前用户信息
        Integer uId = WmThreadLocalUtils.getUser().getId();
        update(Wrappers.<WmMaterial>lambdaUpdate().set(WmMaterial::getIsCollection,type)
                .eq(WmMaterial::getId,id)
                .eq(WmMaterial::getUserId,uId)); // 防止不是自己的图片
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }
}
