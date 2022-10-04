package org.prsprj.db.migration.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.prsprj.db.migration.business.Tabella;

public interface TabelleMapper {
	public List<Tabella> listaTabelle(@Param("nomeDatabase") String nomeDatabase);
}
