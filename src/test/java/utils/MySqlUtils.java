package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import xiao.fei.dao.AnswerModelDao;
import xiao.fei.mapper.AnswerModelMapper;
import xiao.fei.models.AnswerModel;
import xiao.fei.utils.MybatisUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Author fei
 */
public class MySqlUtils {

    @Test
    public void testMyBatis() throws IOException {
        String resource = "xiao/fei/mapper/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testAdd(){

        List<AnswerModel> answerModels = MybatisUtil.getSqlSession().getMapper(AnswerModelMapper.class).findAll();
        answerModels.stream().forEach(System.out::println);
    }

}
