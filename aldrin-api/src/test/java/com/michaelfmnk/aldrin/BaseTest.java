package com.michaelfmnk.aldrin;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.michaelfmnk.aldrin.security.JwtTokenUtil;
import com.michaelfmnk.aldrin.security.JwtUser;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@SqlGroup({@Sql(value = {"classpath:test-clean.sql"}), @Sql})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @Value("${jwt.header}")
    protected String headerName;

    protected final String prefix = "Bearer ";

    protected Headers headers;
    @LocalServerPort
    protected Integer port;
    @SpyBean
    protected JwtTokenUtil jwtTokenUtil;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected DataSource dataSource;


    @Test
    public void contextLoads(){

    }

    @PostConstruct
    public void init() {

        RestAssured.port = port;
        final String token = jwtTokenUtil.generateToken(
                JwtUser.builder()
                        .id(1)
                        .username("MichaelFMNK")
                        .build());
        headers = new Headers(new Header(headerName, prefix + token));
    }


}
