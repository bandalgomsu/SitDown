package sitdownserver.com.sitdown.common.error.user;


import sitdownserver.com.sitdown.common.error.BusinessException;
import sitdownserver.com.sitdown.common.error.ErrorCode;

public class UserAccessDeniedException extends BusinessException {
    public UserAccessDeniedException() {
        super(ErrorCode.USER_ACCESS_DENIED);
    }
}
