package sitdownserver.com.sitdown.common.error.user;


import sitdownserver.com.sitdown.common.error.EntityNotFoundException;
import sitdownserver.com.sitdown.common.error.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
