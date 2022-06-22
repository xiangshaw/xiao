package plus.axz.user.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.api.user.UserControllerApi;
import plus.axz.model.user.pojos.User;
import plus.axz.user.service.UserService;

/**
 * @author xiaoxiang
 * @date 2022年06月22日
 * @particulars 根据id查询app用户信息
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController implements UserControllerApi {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @Override
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }
}
