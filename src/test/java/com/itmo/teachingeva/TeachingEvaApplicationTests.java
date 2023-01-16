package com.itmo.teachingeva;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itmo.teachingeva.domain.StudentClass;
import com.itmo.teachingeva.mapper.StudentClassMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class TeachingEvaApplicationTests {

    @Resource
    private StudentClassMapper studentClassMapper;


    @Test
    void contextLoads() {
        List<StudentClass> studentClasses = studentClassMapper.queryClassToList();

        Map<Integer, String> classMap = studentClasses.stream().collect(Collectors.toMap(StudentClass::getId, StudentClass::getCid));

        System.out.println(classMap.get(1));
    }

}
