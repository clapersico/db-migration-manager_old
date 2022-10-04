package org.prsprj.db.migration.mybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConnectionFactory {
	private static final String MYBATIS_CONFIGURATION_FILE = "mybatis/config_mysql.xml";
	
	public static SqlSessionFactory getSqlSessionFactory(String url, String driver, String username, String password, String dbName) throws IOException {
			if(!url.endsWith("/"))
				url += "/";
			
			url += dbName;
				
			Properties properties = new Properties();
			properties.put("url", url);
			properties.put("driver", driver);
			properties.put("username", username);
			properties.put("password", password);
			
			Reader reader = Resources.getResourceAsReader(MYBATIS_CONFIGURATION_FILE);
			
			return new SqlSessionFactoryBuilder().build(reader, properties);
	}
}
