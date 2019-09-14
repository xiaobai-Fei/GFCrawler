package xiao.fei.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultModel {

    private List<AnswerModel> answerModelList = new ArrayList<>();

    private String nextURL;

    private boolean is_end;

    public boolean add(AnswerModel answerModel){
        return answerModelList.add(answerModel);
    }

    public boolean isEmpty(){
        return  answerModelList.isEmpty();
    }

    public boolean is_end(){
        if (nextURL==null && is_end){
            return true;
        }
        return  false;
    }

}
