package org.prsprj.db.migration.business;

import lombok.Data;

@Data
public class Column {
	private String columnName;
	private String datatype;
	private int lenght;
}
