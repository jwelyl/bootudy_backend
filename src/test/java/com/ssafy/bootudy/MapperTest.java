package com.ssafy.bootudy;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bootudy.model.mapper.MemberMapper;

@SpringBootTest
@Transactional
public class MapperTest {
	@Autowired
	MemberMapper memberMapper;
	
	@Test
	public void beanTest() {
		assertNotNull(memberMapper);
	}
}
