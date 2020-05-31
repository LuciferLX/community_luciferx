package life.majiang.community.dto;

import lombok.Data;

/**
 * 实现comparable接口，自定义比较方式
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.priority - ((HotTagDTO) o).getPriority();
    }
}
