package xiao.fei.mapper;

import xiao.fei.models.AnswerModel;

import java.util.List;

/**
 * @Author fei
 */
public interface AnswerModelMapper {
    List<AnswerModel> findAll();

    AnswerModel findAnswerById(String answer_id);

    List<AnswerModel> findLike(String keyword);

    void insertAnswer(AnswerModel answerModel);

    void deleteAnswerById(Integer id);

    void updateAnswer(AnswerModel user);

}
