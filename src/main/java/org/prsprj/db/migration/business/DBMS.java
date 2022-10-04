package org.prsprj.db.migration.business;

import lombok.Getter;

public enum DBMS {
	MYSQL(false);
	
	@Getter private boolean schemaRequired;
	
	private DBMS(boolean schemaRequired) {
		this.schemaRequired = schemaRequired;
	}
}
