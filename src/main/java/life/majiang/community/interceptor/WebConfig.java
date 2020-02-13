package life.majiang.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptor sessionInterceptor;//这是一个Spring MVC中的拦截器类
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //下面这行可以设置对哪些地址进行拦截，对对哪些地址进行忽略
        //registry.addInterceptor(new ThemeInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");//对所有地址都进行拦截
    }
}
