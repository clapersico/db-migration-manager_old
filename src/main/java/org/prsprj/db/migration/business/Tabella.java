package org.prsprj.db.migration.business;

import java.util.List;

import lombok.Data;

@Data
public class Tabella {
	private String nomeTabella;
	private List<Column> columns;
}
