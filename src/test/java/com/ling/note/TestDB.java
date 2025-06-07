package com.ling.note;

import com.ling.note.util.DBUtil;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * &#064;Author:  LingWeiBo
 * &#064;Date:  2025/6/6 16:50
 */
public class TestDB {
    //private static final Logger logger = Logger.getLogger(TestDB.class.getName());
    private final Logger  logger = LoggerFactory.getLogger(TestDB.class);
    /*
    单元测试方法
    1. 创建一个测试类，并添加@Test注解
    2. 在测试方法中，创建一个数据库连接，并执行数据库操作
    3. 在测试方法中，断言数据库操作的结果是否符合预期
    4. 运行测试方法，查看测试结果
    5. 如果测试失败，根据失败原因修改测试代码，并重新运行测试方法
    6. 如果测试成功，则说明数据库操作正确，可以继续使用数据库连接
    7. 关闭数据库连接，释放资源
    8. 测试完成后，将数据库连接对象保存起来，以便后续使用
    9. 如果需要多次测试数据库操作，则可以在测试方法中重复执行数据库操作，并断言结果是否符合预期

      */

    @Test
    public  void testDB() throws SQLException {
        //使用日志工厂类，记录日志

        System.out.println("测试数据库连接");
        System.out.println(DBUtil.getConnection());
        DBUtil.close(DBUtil.getConnection(),null,null);
        System.out.println("测试数据库连接结束");
        logger.info("测试数据库连接结束"+DBUtil.getConnection());
    }
}
