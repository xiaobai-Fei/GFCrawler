package xiao.fei.dao;

import org.apache.ibatis.session.SqlSession;
import xiao.fei.mapper.AnswerModelMapper;
import xiao.fei.models.AnswerModel;
import xiao.fei.utils.MybatisUtil;

import java.util.List;

/**
 * @Author fei
 */
public class AnswerModelDao implements AnswerModelMapper {

    public static void addAnswers(List<AnswerModel> answerModels) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            //映射文件的命名空间.SQL片段的ID，就可以调用对应的映射文件中的SQL
            for (AnswerModel answerModel : answerModels)
                sqlSession.insert("xiao.fei.mapper.AnswerModelMapper.insert_answer", answerModel);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            throw e;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }

    @Override
    public List<AnswerModel> findAll() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            //映射文件的命名空间.SQL片段的ID，就可以调用对应的映射文件中的SQL
            List<AnswerModel> answerModels =sqlSession.selectOne("xiao.fei.mapper.AnswerModelMapper.findAll");
            sqlSession.commit();
            return answerModels;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            throw e;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }

    public AnswerModel findAnswerById(String this_is_answer_id2) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            //映射文件的命名空间.SQL片段的ID，就可以调用对应的映射文件中的SQL
             AnswerModel answerModel = sqlSession.selectOne("xiao.fei.mapper.AnswerModelMapper.findAnswerById", this_is_answer_id2);
            sqlSession.commit();
            return answerModel;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            throw e;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }

    @Override
    public List<AnswerModel> findLike(String keyword) {
        return null;
    }

    @Override
    public void insertAnswer(AnswerModel answerModel) {

    }

    @Override
    public void deleteAnswerById(Integer id) {

    }

    @Override
    public void updateAnswer(AnswerModel user) {

    }
}
