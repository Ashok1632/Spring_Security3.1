package com.ashok.config;

public class AppConstants {

	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "10";
	public static final String SORT_BY = "postId";
	public static final String SORT_DIR = "asc";
	public static final Integer NORMAL_USER = 502;
	public static final Integer ADMIN_USER=501;
	public static final String[] PUBLIC_URLS = { "/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };
	public static final String[] USER_URLS = { "/api/public/**" };
	public static final String[] ADMIN_URLS = { "/api/v1/users/admin" };
}
