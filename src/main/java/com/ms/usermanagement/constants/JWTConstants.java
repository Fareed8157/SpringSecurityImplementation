package com.ms.usermanagement.constants;

import java.util.Date;

public interface JWTConstants {
    public static final Date TOKEN_VALIDITY=new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
}
