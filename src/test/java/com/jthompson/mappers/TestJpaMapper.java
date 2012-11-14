package com.jthompson.mappers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

import org.springframework.dao.DataAccessException;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestJpaMapper 
{
	@Test
	@SneakyThrows
	public void TestMapper()
	{
		
		
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString("FIRST_NAME")).thenReturn("Andrew");
		when(rs.getString("lastName")).thenReturn("Thompson");
		when(rs.getString("id")).thenReturn("012345");
		when(rs.getString("email")).thenReturn("me@me.com");
		
		
		
		JpaAnnotationBasedMapper<PlayDto> mapper = new JpaAnnotationBasedMapper<PlayDto>(PlayDto.class);
		
		PlayDto dto = mapper.mapRow(rs, 0);
		
		Assert.assertEquals(dto.getFirstName(), "Andrew");
		
	}
	
	@Test
	@SneakyThrows
	public void TestExtractor()
	{
		ResultSet rs = mock(ResultSet.class);
		when(rs.getObject("FIRST_NAME")).thenReturn("Andrew");
		when(rs.getObject("lastName")).thenReturn("Thompson");
		when(rs.getObject("id")).thenReturn("012345");
		when(rs.getObject("email")).thenReturn("me@me.com");
		
		JpaAnnotationBasedResultSetExtractor<PlayDto> extractor = new JpaAnnotationBasedResultSetExtractor<PlayDto>(PlayDto.class) {
			
			@SneakyThrows
			public PlayDto extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				
				PlayDto dto = new PlayDto();
				
				dto.setFirstName( (String)grabField("firstName", rs) );
				
				return dto;
			}
		};
		
		PlayDto dto = extractor.extractData(rs);
		
		Assert.assertEquals(dto.getFirstName(), "Andrew");
	}
	
	@Test
	@SneakyThrows
	public void TestExtractorList()
	{
		ResultSet rs = mock(ResultSet.class);
		when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(rs.getObject("FIRST_NAME")).thenReturn("Andrew").thenReturn("Lester");
		when(rs.getObject("lastName")).thenReturn("Thompson").thenReturn("Tester");
		when(rs.getObject("id")).thenReturn("012345").thenReturn("234567");
		when(rs.getObject("email")).thenReturn("me@me.com").thenReturn("you@you.com");
		
		List<PlayDto> results = null;
		
		JpaAnnotationBasedResultSetExtractor<List<PlayDto>> extractor = new JpaAnnotationBasedResultSetExtractor<List<PlayDto>>(PlayDto.class) {
			
			public List<PlayDto> extractData(ResultSet rs) throws SQLException ,DataAccessException {
				List<PlayDto> results = new ArrayList<PlayDto>();
				
				while(rs.next())
				{
					PlayDto dto = new PlayDto();
					
					dto.setFirstName( (String)grabField("firstName", rs) );
					
					results.add(dto);
				}
				
				return results;
			};
		};
		
		results = extractor.extractData(rs);
		
		Assert.assertEquals(results.size(), 2);
		
		
	}

}
