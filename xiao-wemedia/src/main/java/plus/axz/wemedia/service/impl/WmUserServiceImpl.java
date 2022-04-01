package plus.axz.wemedia.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.wemedia.dtos.WmUserDto;
import plus.axz.model.wemedia.pojos.WmUser;
import plus.axz.utils.common.JwtUtil;
import plus.axz.wemedia.mapper.WmUserMapper;
import plus.axz.wemedia.service.WmUserService;

import java.util.HashMap;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年03月25日
 * @particulars
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {
    @Override
    public ResponseResult login(WmUserDto dto) {
        // 1.检查参数
        if (StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getPassword())){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"用户名或密码错误");
        }
        // 2. 查询数据库用户信息
        List<WmUser> list = list(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, dto.getName()));
        if (list != null && list.size() ==1){
            WmUser wmUser = list.get(0);
            // 3.比对密码
            //加密处理，用户输入的密码+数据库中盐，，结合后拿到bytes，，加密后就是一个串
            String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + wmUser.getSalt()).getBytes());
            if (wmUser.getPassword().equals(pswd)){
                // 4.返回数据jwt
                // 定义map装载两参数
                HashMap<String, Object> map = new HashMap<>();
                //token怎么生成的，最后返回用户有两个，1. token，生成的jwt字符串    2.当前的用户
                map.put("token", JwtUtil.getToken(wmUser.getId().longValue()));
                //为了安全，密码和salt不以明文返回前台，设置为空
                wmUser.setPassword("");
                wmUser.setSalt("");
                // 将用户装入
                map.put("user",wmUser);
                // 返回jwt字符串和用户信息
                return ResponseResult.okResult(map);
            }else {
                return ResponseResult.errorResult(ResultEnum.LOGIN_PASSWORD_ERROR);
            }
        }else {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST,"用户不存在");
        }
    }
}
