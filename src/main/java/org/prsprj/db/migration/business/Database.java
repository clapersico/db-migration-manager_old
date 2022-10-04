package org.prsprj.db.migration.business;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.prsprj.db.migration.exception.DbNotAnalyzedException;
import org.prsprj.db.migration.exception.SchemaRequiredException;
import org.prsprj.db.migration.mybatis.ConnectionFactory;
import org.prsprj.db.migration.mybatis.mapper.TabelleMapper;

import lombok.Getter;

public class Database {
	@Getter private String dbName;
	@Getter private List<Tabella> tabelle;
	@Getter private String url;
	@Getter private String username;
	@Getter private String password;
	@Getter private String driver;
	
	private DBMS dbms;
	private String schema;
	private SqlSessionFactory connectionFactory;
	private boolean dbAnalizzato = false;
	
	public Database(String url, String username, String password, String driver, DBMS dbms, String db) throws SchemaRequiredException {
		this(url, username, password, driver, dbms, null, db);
	}
	
	public Database(String url, String username, String password, String driver, DBMS dbms, String schema, String db) throws SchemaRequiredException {
		this.dbms = dbms;
		
		if (schema == null && dbms.isSchemaRequired())
			throw new SchemaRequiredException();
		
		this.schema = schema;
		this.dbName = db;
		this.url = url;
		this.username = username;
		this.password = password;
		this.driver = driver;
	}
	
	public boolean analyze() {
		boolean analizzato = true;
		SqlSession session = null;
		
		try {
			session = this.openConnection();
			TabelleMapper tabelleMapper = session.getMapper(TabelleMapper.class);
			tabelle = tabelleMapper.listaTabelle(this.dbName);
		} catch (IOException e) {
			e.printStackTrace();
			analizzato = false;
		} finally {
			if(session != null)
				session.close();
		}
		
		this.dbAnalizzato = analizzato;
		return analizzato;
	}
	
	public MigrationPlan prepareMigration(Database dbTarget) throws DbNotAnalyzedException {
		MigrationPlan plan = new MigrationPlan();
		
		if(!this.dbAnalizzato)
			throw new DbNotAnalyzedException(this);
		
		if(!dbTarget.dbAnalizzato)
			throw new DbNotAnalyzedException(dbTarget);
		
		for(Tabella t1 : this.tabelle) {
			boolean exists = false;
			
			for(Tabella t2: dbTarget.getTabelle()) {
				if(t1.getNomeTabella().equals(t2.getNomeTabella()))
					exists = true;
			}
			
			if(exists) {
				
			}else {
				plan.creaTabella(t1);
			}
		}
		
		return plan;
	}
	
	private void createConnection() throws IOException {
		this.connectionFactory = ConnectionFactory.getSqlSessionFactory(url, driver, username, password, dbName);
	}
	
	private SqlSession openConnection() throws IOException {
		if(this.connectionFactory == null)
			this.createConnection();
		
		return this.connectionFactory.openSession();
	}
}
