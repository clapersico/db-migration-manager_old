package org.prsprj.db.migration;

import java.io.IOException;

import org.prsprj.db.migration.business.DBMS;
import org.prsprj.db.migration.business.Database;
import org.prsprj.db.migration.business.MigrationPlan;
import org.prsprj.db.migration.exception.DbNotAnalyzedException;
import org.prsprj.db.migration.exception.SchemaRequiredException;

public class Application {

	public static void main(String[] args) throws IOException, SchemaRequiredException, DbNotAnalyzedException {		
		Database db1 = new Database("jdbc:mysql://localhost:3306", "root", "secret", "com.mysql.cj.jdbc.Driver", DBMS.MYSQL, "db1");
		Database db2 = new Database("jdbc:mysql://localhost:3306", "root", "secret", "com.mysql.cj.jdbc.Driver", DBMS.MYSQL, "db2");
	
		db1.analyze();
		db2.analyze();
		MigrationPlan plan = db1.prepareMigration(db2);
		
		plan.build();
		plan.printStmt();
	}
}
