package org.prsprj.db.migration.exception;

import org.prsprj.db.migration.business.Database;

public class DbNotAnalyzedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DbNotAnalyzedException(Database db) {
		super(String.format("Database %s not yet analyzed", db.getDbName()));
	}
}
