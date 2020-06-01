package life.majiang.community.service;

import life.majiang.community.mapper.AdMapper;
import life.majiang.community.model.Ad;
import life.majiang.community.model.AdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    @Autowired
    private AdMapper adMapper;

    public List<Ad> list() {
        AdExample adExample = new AdExample();
        //选择有效时间包括当前时刻的记录
        adExample.createCriteria()
                .andStatusEqualTo(1)
                .andGmtStartLessThan(System.currentTimeMillis())
                .andGmtEndGreaterThan(System.currentTimeMillis());
        return adMapper.selectByExample(adExample);
    }
}
