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
	
	public void print() {
		if(tabelleDaCreare.size() > 0) {
			log.info("+**************************************+");
			log.info("| Tabelle da Creare                    |");
			log.info("+**************************************+");
			
			for(Tabella t : this.tabelleDaCreare) {
				log.info(t.getNomeTabella());
			}
			
			log.info("\n\n");
		}
	}
	
	private void buildCreaTabella(Tabella t) {
		Table tabella = Table.create(t.getNomeTabella());
		tabella.varCharColumn("pippo").size(50).build();
		
		String query = Queries.createTable(tabella).build().toString();
		this.istruzioni.add(query);
	}
}
