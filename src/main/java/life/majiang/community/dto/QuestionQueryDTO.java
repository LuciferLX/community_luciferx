package life.majiang.community.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class QuestionQueryDTO {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;

}
