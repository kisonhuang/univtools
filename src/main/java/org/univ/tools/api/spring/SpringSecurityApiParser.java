package org.univ.tools.api.spring;

public class SpringSecurityApiParser extends SpringApiParser {

	public static void main(String[] args) {
		new SpringSecurityApiParser().writeAll();
	}

	@Override
	protected String getProjectPath() {
		return "D:/Workspace/univtech/univ-tech/spring/spring-security-5.4.1";
	}

	protected String getProjectUrl() {
		return "https://spring.io/projects/spring-security";
	}

	@Override
	protected String getApiName() {
		return "Spring Security 5.4.1 API";
	}

	@Override
	protected String getApiUrl() {
		return "https://docs.spring.io/spring-security/site/docs/5.4.1/api";
	}

}
