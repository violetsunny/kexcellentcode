package top.kexcellent.back.code;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * starter
 *
 * @author anyone
 * @since Fri Jun 11 10:13:28 CST 2021
 */
@SpringBootApplication(scanBasePackages = {"top.kdla"})
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@MapperScan()
public class ApplicationRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("app start");
    }
}
