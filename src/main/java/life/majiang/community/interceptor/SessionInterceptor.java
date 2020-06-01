package life.majiang.community.interceptor;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import life.majiang.community.service.AdService;
import life.majiang.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {//这就是一个Spring MVC中的拦截器类，对登录请求进行拦截处理
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AdService adService;

    private String redirectUri;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置context级别的属性
        request.getServletContext().setAttribute("redirectUri",redirectUri);
        //没有登陆的时候也可以看到导航栏
        request.getServletContext().setAttribute("ads",adService.list());

        Cookie[] cookies = request.getCookies();
        if (cookies!=null&& cookies.length!=0) {//按照cookie获取相应的token，再用token从数据库中获取用户对象
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        HttpSession session=request.getSession();
                        request.getSession().setAttribute("user", users.get(0));
                        Long unreadCount=notificationService.unreadCount(users.get(0).getId());//成功登陆后立刻获取是否存在未读通知
                        request.getSession().setAttribute("unreadCount",unreadCount);//将通知数量通过session传给前端
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
