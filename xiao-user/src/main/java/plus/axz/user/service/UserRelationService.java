package plus.axz.user.service;

import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.user.dtos.UserRelationDto;

/**
 * @author xiaoxiang
 * @date 2022年06月20日
 * @particulars
 */
public interface UserRelationService {
    /**
     * 用户关注/取消关注
     * @param dto
     * @return
     */
    public ResponseResult follow(UserRelationDto dto);
}
