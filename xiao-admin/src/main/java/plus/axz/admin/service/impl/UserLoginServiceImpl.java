package plus.axz.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import plus.axz.admin.mapper.AdUserMapper;
import plus.axz.admin.service.UserLoginService;
import plus.axz.model.admin.dtos.AdUserDto;
import plus.axz.model.admin.pojos.AdUser;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.utils.common.JwtUtil;

import java.util.HashMap;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年03月23日
 * @particulars admin登录实现
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements UserLoginService {
    @Override
    public ResponseResult login(AdUserDto dto) {
        // 1. 检验用户名或密码为空
        if (StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getPassword())){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID,"用户名或密码错误");
        }
        // 2. 查询数据库中用户信息（mybatis:）
        List<AdUser> list = list(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, dto.getName()));
        if (list != null && list.size() == 1){ //=1有数据
            // 拿数据
            AdUser adUser = list.get(0);
            // 3. 比对密码
            //加密处理，当前用户输入的密码+数据库中盐，，，两者结合后拿到bytes，，加密后就是一个串
            String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + adUser.getSalt()).getBytes());
            if (adUser.getPassword().equals(pswd)){
                // 4. 密码比对正确，返回用户信息和jwt字符串
                HashMap<String, Object> map = new HashMap<>();
                // 生成token
                map.put("token", JwtUtil.getToken(adUser.getId().longValue()));
                // 为了安全，密码、盐    不以明文返回前台,设置为空
                adUser.setPassword("");
                adUser.setSalt("");
                // 用户装入
                map.put("user",adUser);
                // 返回jwt字符串和用户信息
                return ResponseResult.okResult(map);
            }else {
                return ResponseResult.errorResult(ResultEnum.LOGIN_PASSWORD_ERROR,"密码错误");
            }
        }else { // list.size 没有数据
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST,"用户名不存在");
        }
    }
}
