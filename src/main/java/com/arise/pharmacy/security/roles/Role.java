package com.arise.pharmacy.security.roles;

import static com.arise.pharmacy.security.authorities.Authority.USER_READ;
import static com.arise.pharmacy.security.authorities.Authority.USER_WRITE;
import static com.arise.pharmacy.security.authorities.Authority.ORDER_READ;
import static com.arise.pharmacy.security.authorities.Authority.ORDER_WRITE;
import static com.arise.pharmacy.security.authorities.Authority.PRODUCT_READ;
import static com.arise.pharmacy.security.authorities.Authority.PRODUCT_WRITE;
import static com.arise.pharmacy.security.authorities.Authority.REPORT_READ;

import com.arise.pharmacy.security.authorities.Authority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(List.of(USER_READ,USER_WRITE,ORDER_READ,ORDER_WRITE,PRODUCT_READ,PRODUCT_WRITE,REPORT_READ)),
    STAFF(List.of(USER_READ,ORDER_READ,ORDER_WRITE,PRODUCT_READ)),
    SHOPPER(List.of(USER_READ,USER_WRITE,ORDER_READ,ORDER_WRITE,PRODUCT_READ));

    private final List<Authority> authorities;
}
