package org.prsprj.db.migration.business;

import java.util.ArrayList;
import java.util.List;

import de.jaggl.sqlbuilder.core.queries.Queries;
import de.jaggl.sqlbuilder.core.schema.Table;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MigrationPlan {
	private List<Tabella> tabelleDaCreare;
	private List<String> istruzioni;
	
	public MigrationPlan() {
		this.tabelleDaCreare = new ArrayList<Tabella>();
		this.istruzioni = new ArrayList<String>();
	}
	
	public void creaTabella(Tabella tabella) {
		this.tabelleDaCreare.add(tabella);
	}
	
	public void build() {
		if(tabelleDaCreare.size() > 0) {
			for(Tabella t : this.tabelleDaCreare)
				this.buildCreaTabella(t);
		}
	}
	
	public void printStmt() {
		 for(String instruction : this.istruzioni)
			 System.out.println(instruction);
	}
	
	private void buildCreaTabella(Tabella t) {
		Table tabella = Table.create(t.getNomeTabella());
		
		for(Column column : t.getColumns()) {
			switch(column.getDatatype()) {
			case "varchar":
				tabella.varCharColumn(column.getColumnName()).size(column.getLenght()).build();
				break;
				
			case "float":
				tabella.floatColumn(column.getColumnName()).build();
				break;
				
			default:
				log.error("Datatype non gestito: " + column.getDatatype());
			}
		}		
		
		String query = Queries.createTable(tabella).build().toString();
		this.istruzioni.add(query);
	}
}
