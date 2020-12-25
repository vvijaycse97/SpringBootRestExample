package com.example.test;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.user.GithubUser;
import com.example.util.ConstantsUtil;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class SpringBootTests {
	private static final Logger LOG = LoggerFactory.getLogger(SpringBootTests.class);

	@Test
	public void testgithubUserInfoPage() {
		try {
			RestTemplate restTemplate = new RestTemplate();

			final String baseUrl = ConstantsUtil.GITHUB_USER_INFO + "/vvijaycse97";
			URI uri = new URI(baseUrl);

			GithubUser result = restTemplate.getForObject(uri, GithubUser.class);

			// Verify request succeed
			Assert.assertEquals("vvijaycse97", result.getLogin());
			Assert.assertEquals("2", result.getPublicRepos());

			// Assert.assertEquals(true, result.getBody().contains("employeeList"));
		} catch (Exception e) {
			LOG.error("Error occurred while trying to access github api from test class", e);
			LOG.error(e.getMessage());
		}
	}
}
