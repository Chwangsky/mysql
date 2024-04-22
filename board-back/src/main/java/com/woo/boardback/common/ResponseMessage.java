package com.woo.boardback.common;

public interface ResponseMessage {

    // HTTP Status Code 200
    String SUCCESS = "Success.";

    // HTTP STATUS 400
    String VALIDATION_FAILED = "Validation failed.";
    String DUPLICATE_EMAIL = "Duplicate email.";
    String DUPLICATE_NICKNAME = "Duplicate nickname.";
    String DUPLICATE_TEL_NUMBER = "Duplicate tel number.";
    String NOT_EXISTED_USER = "This user does not exist.";
    String NOT_EXISTED_BOARD = "This board does not exist.";

    // HTTP STATUS 401
    String SIGN_IN_FAIL = "Login information mismatch.";
    String AUTHORIZATION_FAIL = "Authorization failed";

    // HTTP STATUS 403
    String NO_PERMISSION = "Do not have permission";

    // HTTP STATUS 500
    String DATABASE_ERROR = "Database error.";
    
}
