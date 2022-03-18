package com.zwm.actuator;

import com.zwm.util.CommunityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@WebEndpoint(id = "datasource")
public class DataBaseEndPoint {
    //监控数据库是否连接成功的端点
    @Autowired
    private DataSource dataSource;

    private static final Logger logger = LoggerFactory.getLogger(DataBaseEndPoint.class);

    @ReadOperation
    public String checkConnection() {
        try (
                Connection connection = dataSource.getConnection();
        ) {
            return CommunityUtils.getJsonString(0, "获取数据库连接成功！");
        } catch (SQLException sqlException) {
            logger.error("获取数据库连接失败：" + sqlException.getMessage());
            return CommunityUtils.getJsonString(1, "获取数据库连接失败！");
        }
    }
}
