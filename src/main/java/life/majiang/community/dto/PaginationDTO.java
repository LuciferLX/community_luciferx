package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO<T> {     //这个类专门用来实现分页和页码按钮逻辑
    private List<T> data;
    private boolean showPrevious;    //是否有向前按钮默认为false
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page=page;
        pages.add(page);
        for(int i=1;i<=3;i++){       //设置每次出现的页码放在pages集合中
            if(page-i>0){
                pages.add(0,page-i);    //每次加到0的位置防止页面次序混乱
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示上一页按钮<
        if(page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页按钮>
        if(page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //判断是否展示跳转至第一页按钮<<
        if (pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //判断是否展示跳转至最后一页按钮>>
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
