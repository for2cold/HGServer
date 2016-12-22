package com.kazyle.hugohelper.server.config.configuration;

import com.kazyle.hugohelper.server.config.domain.data.Constants;
import com.kazyle.hugohelper.server.function.core.user.entity.User;
import org.joda.time.DateTime;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Kazyle on 2016/8/23.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
                        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500")
                        ,new ErrorPage(HttpStatus.FORBIDDEN, "/error/403")
                );
            }
        };
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // TODO Auto-generated method stub
        exceptionResolvers.add(exceptionResolver());
    }

    @Bean
    public HandlerExceptionResolver exceptionResolver() {
        return new HandlerExceptionResolver() {

            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                // TODO Auto-generated method stub
                ModelAndView mav = new ModelAndView();
                mav.setViewName("error/500");
                return mav;
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        registry.addInterceptor(cmmonInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor()).addPathPatterns("/front/**");
    }

    @Bean
    public HandlerInterceptorAdapter authInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
                if (null == user) {
                    response.sendRedirect(request.getContextPath() + "/login");
                    return false;
                }
                return super.preHandle(request, response, handler);
            }
        };
    }

    @Bean
    public HandlerInterceptorAdapter cmmonInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                // TODO Auto-generated method stub
                if (request.getAttribute(Constants.CONTEXT_PATH) == null) {
                    request.setAttribute(Constants.CONTEXT_PATH, request.getContextPath());
                }
                DateTime dateTime = new DateTime();
                request.setAttribute("nowMonth", dateTime.toString("yyyy-MM"));
                return true;
            }
        };
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public HandlerMethodArgumentResolver currentUserResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

}
