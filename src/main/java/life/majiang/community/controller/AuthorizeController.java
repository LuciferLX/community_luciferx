package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import life.majiang.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *
 */
@Controller
@Slf4j   //lombok中会自动加载log的注解
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")       //会去配置文件里读取clientId在运行时的值
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;
    //当点击登录访问GitHub的authorize接口后，接收从GitHub的回复并提取其中的信息放入AccessTokenDTO中
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            //登录成功，写cookie和session，逻辑是查询数据库中是否已经有这个用户的记录，若有，直接登录成功
            User user = new User();     //创建一个新的User来存储用户信息，这个User和数据库是映射的
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);   //把用户信息更新或存放到数据库中
            response.addCookie(new Cookie("token",token)); //把token存放到网页的cookie中
            return "redirect:/";        //重定向到根目录，就是首页，注意redirect:后跟的是目录而不是页面的名字
        }else{
            log.error("callback get github error, {}",githubUser);  //这样当出错时就会打印错误信息，会把后一个参数值自动放到空括号里
            //登录失败重新登录
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){//退出登录时用新的空cookie覆盖旧cookie即可
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);//设置为立即删除
        response.addCookie(cookie);
        return "redirect:/";
    }
}
