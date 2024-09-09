package plus.axz.user.service;

import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.UserRelationDto;

/**
 * @author xiaoxiang
 * description
 */
public interface UserRelationService {
    /**
     * 用户关注/取消关注
     */
    ResponseResult<?> follow(UserRelationDto dto);
}
