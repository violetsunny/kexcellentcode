package top.kexcellent.back.code.dao;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
//@Configuration
@EnableConfigurationProperties(DruidProperties.class)
@EnableTransactionManagement   //开启事务支持
@MapperScan(basePackages = {"com.oyo.ota.mapper", "com.oyo.common.mq.dao.mapper"}, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class DataSourceConfig {

    @Autowired
    private DruidProperties druidProperties;

    //@Bean(name = "mysqlDataSource")
    @Primary
    public DataSource mysqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidProperties.getUrl());
        dataSource.setUsername(druidProperties.getUserName());
        dataSource.setPassword(druidProperties.getPassword());
        dataSource.setDriverClassName(druidProperties.getDriverClass());
        dataSource.setInitialSize(druidProperties.getInitialSize());
        dataSource.setMinIdle(druidProperties.getMinIdle());
        dataSource.setMaxActive(druidProperties.getMaxActive());
        dataSource.setTestOnBorrow(druidProperties.getTestOnBorrow());
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    //@Bean(name = "mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(mysqlDataSource());
        bean.setTypeAliasesPackage("com.oyo.ota.model,com.oyo.common.mq.dao.entity");
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //映射下划线到驼峰模式
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        //添加插件
        //CatMybatisPlugin catMybatisPlugin = new CatMybatisPlugin();
        //bean.setPlugins(new Interceptor[]{catMybatisPlugin});
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:**/*Mapper.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //配置事务管理器
    //@Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager() {
        return new DataSourceTransactionManager(mysqlDataSource());
    }

    //配置模板
    //@Bean(name = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
