package xiao.fei.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerModel {

    private String answer_id;

    private String answerer_id;

    private String url_token;

    private String name;

    private int gender ;

    private int age;

    private int height;

    private int weight;

    private int beauty;

    private String face_shape;

    private int pic_num;

    private int follower_count;

    private String headline;

    private String content;

    private int voteup_count;

    private int comment_count;

    private Date create_time;

    private Date update_time;

}
