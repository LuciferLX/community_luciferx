package life.majiang.community.schedule;

import life.majiang.community.cache.HotTagCache;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 设置一个定时任务，每10000毫秒执行一次或者可以设置每天某一时刻自动执行一次
 * 暂时设置热门标签的排序算法为：查看当前标签的问题数目和这些问题下的回复数，两个数字加权求和，以标签为键，加权和为值存放在Map中，从高到低排列
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 10000)
    //@Scheduled(cron = "0 0 1 * * *")
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 20;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {    //读取问题列表,并且加权计算
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.setTags(priorities);
        hotTagCache.getTags().forEach(
                (k, v) -> {
                    System.out.print(k);
                    System.out.print(":");
                    System.out.print(v);
                    System.out.println();

                }
        );
        log.info("hotTagSchedule stop {}", new Date());
    }
}
