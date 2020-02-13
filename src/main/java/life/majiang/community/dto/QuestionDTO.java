package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {//DTO中我们可以自己添加一些需要的变量而不受数据库表的限制
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;      //QuestionDTO就比Question类多一个User类对象
}
