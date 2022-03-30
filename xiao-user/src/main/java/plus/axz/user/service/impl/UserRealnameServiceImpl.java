package plus.axz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.axz.common.constants.user.UserConstants;
import plus.axz.model.article.pojos.Author;
import plus.axz.model.common.dtos.PageResponseResult;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;
import plus.axz.model.user.dtos.AuthDto;
import plus.axz.model.user.pojos.User;
import plus.axz.model.user.pojos.UserRealname;
import plus.axz.model.wemedia.WmUser;
import plus.axz.user.feign.ArticleFeign;
import plus.axz.user.feign.WemediaFeign;
import plus.axz.user.mapper.UserMapper;
import plus.axz.user.mapper.UserRealnameMapper;
import plus.axz.user.service.UserRealnameService;

import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年03月24日
 * @particulars 查询认证用户
 */

@Service
@Transactional
public class UserRealnameServiceImpl extends ServiceImpl<UserRealnameMapper, UserRealname> implements UserRealnameService {
    @Override
    public ResponseResult loadListByStatus(AuthDto dto) {
        // 1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 分页检查
        dto.checkParam();
        // 2.根据状态分页查询
        LambdaQueryWrapper<UserRealname> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (dto.getStatus() != null) {
            // 获取状态
            lambdaQueryWrapper.eq(UserRealname::getStatus, dto.getStatus());
        }
        // 分页条件构建
        Page pageParam = new Page(dto.getPage(), dto.getSize());
        // 需要 分页对象 和 状态
        Page page = page(pageParam, lambdaQueryWrapper);

        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        pageResponseResult.setData(page.getRecords());
        return pageResponseResult;
    }

    @Override
    @GlobalTransactional
    public ResponseResult updateStatusById(AuthDto dto, Short status) {
        // 1.  检查参数
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 检查状态    /*0创建中    1待审核    2审核失败    9审核通过*/
        if (checkStatus(status)) {
            // 返回true，参数无效
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2. 修改状态
        UserRealname userRealname = new UserRealname();
        //调用mybatis-plus方法updateByid 根据id修改
        userRealname.setId(dto.getId());
        userRealname.setStatus(status);
        // 状态为2 可添加拒绝原因
        if (dto.getMsg() != null) {
            // 装入拒绝原因
            userRealname.setReason(dto.getMsg());
        }
        updateById(userRealname);
        // 3. 如果审核状态是9通过，创建自媒体账户，创建作者信息
        if (status.equals(UserConstants.PASS_AUTH)) {
            //9通过，创建自媒体账户和作者信息
            ResponseResult result = createWmUserAndAuthor(dto);
            if (result != null) {
                return result;
            }
        }
        // 4. 异常回滚测试
        int a = 1/0;
        return ResponseResult.okResult(ResultEnum.SUCCESS);
    }

    @Autowired
    private UserMapper userMapper;
    //注入远程接口
    @Autowired
    private WemediaFeign wemediaFeign;

    /**
     * 审核通过，创建自媒体账户，创建作者信息
     *
     * @author xiaoxiang
     * @date 2022/3/30
     */
    private ResponseResult createWmUserAndAuthor(AuthDto dto) {
        // 获取user信息
        Integer userRealnameid = dto.getId();
        //获取userRealname对象，获取到当前的id也就是userid
        UserRealname userRealname = getById(userRealnameid);
        User user = userMapper.selectById(userRealname.getUserId());
        if (user == null) {
            return ResponseResult.errorResult(ResultEnum.PARAM_INVALID, "未找到用户");
        }
        // 根据名称查询作者信息（User的name就是当前自媒体的name），获取到wmUser对象
        WmUser wmUser = wemediaFeign.findByName(user.getName());
        // 创建自媒体账户（为空才创建）
        if (wmUser == null) {
            wmUser = new WmUser();
            wmUser.setUserId(user.getId());
            wmUser.setName(user.getName());
            wmUser.setPassword(user.getPassword());
            wmUser.setSalt(user.getSalt());
            wmUser.setPhone(user.getPhone());
            wmUser.setStatus(9);
            wmUser.setCreatedTime(new Date());
            wemediaFeign.save(wmUser);
        }
        // 创建作者信息
        createAuthor(wmUser);

        // 都创建好之后（ap_user的flag属性）需要标识为自媒体人，，0普通用户，1自媒体人，2大V
        user.setFlag((short) 1);
        userMapper.updateById(user);
        return null;
    }

    /**
     * 创建作者信息
     * @param wmUser
     * @author xiaoxiang
     * @date 2022/3/30
     */
    @Autowired
    private ArticleFeign articleFeign;
    private void createAuthor(WmUser wmUser) {
        Integer userId = wmUser.getUserId();
        Author author = articleFeign.findByUserId(userId);
        if (author == null){
            author = new Author();
            author.setName(wmUser.getName());
            author.setCreatedTime(new Date());
            author.setUserId(userId);
            author.setType(UserConstants.AUTH_TYPE);
            articleFeign.save(author);
        }

    }

    /**
     * 检查状态
     *
     * @param status
     * @return boolean
     * @author xiaoxiang
     * @date 2022/3/25
     */
    private boolean checkStatus(Short status) {
        // 如果status==null 或者    status不等于2 并且    不等于9
        if (status == null || (!status.equals(UserConstants.FAIL_AUTH) && !status.equals(UserConstants.PASS_AUTH))) {
            return true;
        }
        return false;
    }
}
