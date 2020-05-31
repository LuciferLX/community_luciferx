package life.majiang.community.controller;

import life.majiang.community.cache.HotTagCache;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {  //当有用户访问index页面时，代码会先从数据库中获取需要显示的数据再把index返回给调用者看
    @Autowired
    private QuestionService questionService;
    @Autowired
    private HotTagCache hotTagCache;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size,//默认每一页显示五条信息
                        @RequestParam(name="search",required = false) String search,//传入搜索信息
                        @RequestParam(name="tag",required = false) String tag
                        ){
        PaginationDTO pagination=questionService.list(search,tag,page,size);
        List tags=hotTagCache.getHots();
        model.addAttribute("pagination",pagination);//这个包含分页信息的model将会传给index.html
        model.addAttribute("search",search);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tags);
        return "index";
    }
}
