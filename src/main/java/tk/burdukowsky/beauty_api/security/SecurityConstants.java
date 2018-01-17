package tk.burdukowsky.beauty_api.security;

class SecurityConstants {
    static final String SECRET = "V82gxibGyH9F8SHgqaIstiawYTdvDmp/NNVyk3pSmBLzCUPR0XLMmj8/sYc7rjuwAQMhjvlX30NgtVRmo3cFfw==";
    static final long EXPIRATION_TIME = 864_000_000; // 10 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String CLAIM_ROLES = "roles";
}
