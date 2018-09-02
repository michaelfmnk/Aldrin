package com.michaelfmnk.aldrin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailjet.client.MailjetClient;
import com.michaelfmnk.aldrin.props.AuthProperties;
import com.michaelfmnk.aldrin.security.JwtTokenUtil;
import com.michaelfmnk.aldrin.security.JwtUser;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;


@SqlGroup({@Sql(value = {"classpath:test-clean.sql"}), @Sql})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    protected Headers headers;
    protected Headers adminHeaders;
    @LocalServerPort
    protected Integer port;
    @SpyBean
    protected JwtTokenUtil jwtTokenUtil;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected AuthProperties authProperties;
    @MockBean
    protected MailjetClient mailjetClient;

    @Before
    public void init() {
        RestAssured.port = port;
        final String userToken = jwtTokenUtil.generateToken(
                JwtUser.builder()
                        .id(1)
                        .login("michaelfmnk@gmail.com")
                        .build());
        headers = new Headers(new Header(authProperties.getHeaderName(), userToken));

        final String adminToken = jwtTokenUtil.generateToken(
                JwtUser.builder()
                        .id(3)
                        .login("admin@gmail.com")
                        .build());
        adminHeaders = new Headers(new Header(authProperties.getHeaderName(), adminToken));
    }

}
