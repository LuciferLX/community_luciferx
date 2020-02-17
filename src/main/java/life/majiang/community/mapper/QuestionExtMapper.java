package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionQueryDTO;
import life.majiang.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {//这个接口可以让我们自定义一些Mybatis产生的Mapper中没有的方法，具体SQL实现也是去对应的XML文件找
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}