package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;    //是否有向前按钮默认为false
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }

        if(page<1){         //容错显示，当输入超出界限的页面值的情况
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        this.page=page;
        pages.add(page);
        for(int i=1;i<=3;i++){       //设置每次出现的页码
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示上一页按钮
        if(page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否展示下一页按钮
        if(page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //判断是否展示跳转至第一页按钮
        if (pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //判断是否展示跳转至最后一页按钮
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
