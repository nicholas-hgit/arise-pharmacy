package com.arise.pharmacy.security.authorities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    REPORT_READ("report:read");

    private final String authority;


}
