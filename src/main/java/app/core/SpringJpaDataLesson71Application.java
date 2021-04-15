package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import app.core.exceptions.CouponSystemException;
import app.core.filters.LoginFilter;
import app.core.services.CompanyService;
import app.core.sessions.SessionContext;
import app.core.tests.Test;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableSwagger2
public class SpringJpaDataLesson71Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringJpaDataLesson71Application.class, args);
		Test t = ctx.getBean(Test.class);
		t.testAll();
//		CompanyService company = ctx.getBean(CompanyService.class);
//		company.getCompanyCoupons();
	}
	
	@Bean
	public FilterRegistrationBean<LoginFilter> filterRegistration(SessionContext sessionCtx) {
		// create a registration bean
		FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<LoginFilter>();
		// create our login filter
		LoginFilter loginFilter = new LoginFilter(sessionCtx);
		// do the registration
		filterRegistrationBean.setFilter(loginFilter);
		// set the url pattern for the filter
		filterRegistrationBean.addUrlPatterns("/api/*");
		return filterRegistrationBean;
	}

}
