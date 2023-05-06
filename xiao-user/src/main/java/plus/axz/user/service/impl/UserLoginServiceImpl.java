package plus.axz.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.dtos.LoginDto;
import plus.axz.model.user.dtos.ULoginDto;
import plus.axz.model.user.pojos.User;
import plus.axz.user.mapper.UserMapper;
import plus.axz.user.service.UserLoginService;
import plus.axz.utils.common.JwtUtil;

import java.util.HashMap;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars app登录
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult login(LoginDto dto) {
        // 1.检查参数
        if (dto.getEquipmentId() == null && (StringUtils.isEmpty(dto.getPhone()) && StringUtils.isEmpty(dto.getPassword()))){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.手机号+密码登录
        if (!StringUtils.isEmpty(dto.getPhone()) && !StringUtils.isEmpty(dto.getPassword())){
            // 用户登录
            User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, dto.getPhone()));
            if (user != null){
                String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + user.getSalt()).getBytes()); // 传过来的密码加密
                if (user.getPassword().equals(pswd)){ // 和是数据库密码做对比
                    HashMap<String, Object> map = new HashMap<>();
                    user.setPassword(""); // 置空
                    user.setSalt("");
                    map.put("token", JwtUtil.getToken(user.getId().longValue()));
                    map.put("user",user);
                    return ResponseResult.okResult(map);
                }else {
                    return ResponseResult.errorResult(ResultEnum.LOGIN_PASSWORD_ERROR);
                }
            }else {
                return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST,"用户不存在");
            }
        }else {
            // 3.设备登录
            if (dto.getEquipmentId() ==null){
                return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("token",JwtUtil.getToken(0l)); // 默认设置0l，方便后续过滤
            return ResponseResult.okResult(map);
        }
    }

    @Override
    public ResponseResult ulogin(ULoginDto dto) {
        // 1.检查参数
        if ((StringUtils.isEmpty(dto.getPhone()) && StringUtils.isEmpty(dto.getPassword()))){
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.手机号+密码登录
        // 用户登录
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, dto.getPhone()));
        if (user != null){
            String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + user.getSalt()).getBytes()); // 传过来的密码加密
            if (user.getPassword().equals(pswd)){ // 和是数据库密码做对比
                HashMap<String, Object> map = new HashMap<>();
                user.setPassword(""); // 置空
                user.setSalt("");
                map.put("token", JwtUtil.getToken(user.getId().longValue()));
                map.put("user",user);
                return ResponseResult.okResult(map);
            }else {
                return ResponseResult.errorResult(ResultEnum.LOGIN_PASSWORD_ERROR);
            }
        }else {
            return ResponseResult.errorResult(ResultEnum.DATA_NOT_EXIST,"用户不存在");
        }

    }
}
