enum ResponseCode {

    // HTTP Status Code 200
    SUCCESS = "SU",

    // HTTP STATUS 400
    VALIDATION_FAILED = "VF",
    DUPLICATE_EMAIL = "DE",
    DUPLICATE_NICKNAME = "DN",
    DUPLICATE_TEL_NUMBER = "DT",
    NOT_EXISTED_USER = "NU",
    NOT_EXISTED_BOARD = "NB",

    // HTTP STATUS 401
    SIGN_IN_FAIL = "SF",
    AUTHORIZATION_FAIL = "AF",

    // HTTP STATUS 403
    NO_PERMISSION = "NP",

    // HTTP STATUS 500
    DATABASE_ERROR = "DBE"
    
}

export default ResponseCode;