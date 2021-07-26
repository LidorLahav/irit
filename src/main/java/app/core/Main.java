package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import app.core.filters.LoginFilter;
import app.core.jwt.JwtUtil;
import app.core.tests.Test;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableSwagger2
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
        Test t = ctx.getBean(Test.class);
        t.testAll();
    }

    @Bean
    public FilterRegistrationBean<LoginFilter> filterRegistration(JwtUtil jwt) {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<LoginFilter>();
        LoginFilter loginFilter = new LoginFilter(jwt);
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.addUrlPatterns("/api/*");
        return filterRegistrationBean;
    }

}
