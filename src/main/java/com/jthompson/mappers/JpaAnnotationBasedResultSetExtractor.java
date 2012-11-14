package com.jthompson.mappers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.Column;

import lombok.SneakyThrows;

import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class JpaAnnotationBasedResultSetExtractor<T> implements ResultSetExtractor<T> 
{

	protected Class<?> clazz;
	
	public JpaAnnotationBasedResultSetExtractor(Class<? extends Object> t)
	{		
		this.clazz = t;
	}
	


	@SneakyThrows
	protected Object grabField(Field f, ResultSet rs)
	{
		String columnName = f.getName();
		Column column = f.getAnnotation(Column.class);
		if(null != column && column.name() != null)
			columnName = column.name();
		
		return rs.getObject(columnName);
	}
	
	@SneakyThrows
	protected Object grabField(String fName, ResultSet rs)
	{
		Field f = clazz.getDeclaredField(fName);
		String columnName = f.getName();
		Column column = f.getAnnotation(Column.class);
		if(null != column && column.name() != null)
			columnName = column.name();
		
		return rs.getObject(columnName);
	}
	

	
}
