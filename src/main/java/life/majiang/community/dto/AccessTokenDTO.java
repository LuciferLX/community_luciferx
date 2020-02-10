package life.majiang.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {   //DTO为数据传输模型的缩写
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
