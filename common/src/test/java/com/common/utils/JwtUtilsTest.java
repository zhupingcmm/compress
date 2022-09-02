package com.common.utils;

import com.common.base.Constants;
import lombok.val;
import org.junit.Test;

public class JwtUtilsTest {

    @Test
    public void testCreateToken() {
       String token = JwtUtil.createToken("compress", "zp", "1234", 1000l);
        System.out.println(token);
    }

    @Test
    public void testParseToken() {
        val token = JwtUtil.createToken("compress", "zp", "1234", 1000l);
        val claims = JwtUtil.parseToken(token);
        val username = (String)claims.get(Constants.USERNAME);
        val password = (String)claims.get(Constants.PASSWORD);
        assert username.equals("zp");
        assert password.equals("1234");
    }

    @Test
    public void testValidateToken() throws InterruptedException {
        val token = JwtUtil.createToken("compress", "zp", "1234", 1000l);
        val result = JwtUtil.validateToken(token);
        assert result;
    }
}
