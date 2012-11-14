package com.jthompson.mappers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

@Slf4j
public class JpaAnnotationBasedMapper<T> implements RowMapper<T>{

	private Class<T> clazz;
	
	public JpaAnnotationBasedMapper(Class<T> t)
	{		
		this.clazz = t;
	}
	
	public T mapRow(ResultSet rs, int cnt) throws SQLException 
	{
		T dto = null;
		try {
			dto = clazz.newInstance();
		} catch (Exception e) {
			log.error("Can't create new {}", clazz);
		}
		
		for ( Field f : dto.getClass().getDeclaredFields())
		{
			String columnName = f.getName();
			Column column = f.getAnnotation(Column.class);
			if(null != column && column.name() != null)
				columnName = column.name();
			
			try
			{
				BeanUtils.setProperty(dto, f.getName(), rs.getString(columnName));
			} catch (Exception e) {
				log.warn("Error setting property {}", f.getName());
			}
			
		}
		
		return dto;
	}
}
