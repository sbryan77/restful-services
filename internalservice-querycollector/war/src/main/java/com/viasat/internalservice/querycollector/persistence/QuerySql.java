package com.viasat.internalservice.querycollector.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.internalservice.querycollector.persistence.data.DynamoResult;
import com.viasat.internalservice.querycollector.props.LoadCollectorProps;

/**
 * Created by sbryan on 4/13/2017.
 */
public class QuerySql
{
	private static final Logger LOGGER = LoggerFactory.getLogger(QuerySql.class);
	private static final String DATASOURCE_LOCATION = "jdbc/QueryCollectionDataSource";
	private static LoadCollectorProps loadCollectorProps;
	Connection conn = null;

	public QuerySql()
	{
	}

	public List<QueryResponse> getMetrics(DynamoResult dynamoResult)
	{
		List<QueryResponse> queryResponses = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		int rsSize = 0;
		try
		{
			preparedStatement = conn.prepareStatement(dynamoResult.getCollectionQuery());
			preparedStatement.setInt(1, dynamoResult.getCollectionInterval());

			// execute select SQL stetement
			LOGGER.debug("Executing prepared statement");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				rsSize ++;
				LOGGER.debug("Retrieving row from resultSet");
				QueryResponse queryResponse = new QueryResponse();
				queryResponse.setMetricName(dynamoResult.getMetricDefinition());
				queryResponse.setField(rs.getDouble(dynamoResult.getMetricDefinition()));
				Map<String, String> tags = new HashMap<>();
				for (String tag : dynamoResult.getTags())
				{
					tags.put(tag, rs.getString(tag));
				}
				queryResponse.setTags(tags);
				queryResponses.add(queryResponse);
			}
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception querying Oracle Database", ex);
		}
		finally
		{
			LOGGER.info("Result set size ["+rsSize+"]");
			try
			{
				if (preparedStatement != null)
				{
					preparedStatement.close();
				}
			}
			catch (Exception ex1)
			{
				LOGGER.error("Exception closing statement", ex1);
			}
		}
		return queryResponses;
	}


	public Connection getConnection() throws SQLException
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a connection object
			String hostsString = loadCollectorProps.getOracleHost();
			String[] hosts = hostsString.split(",");
			String port = loadCollectorProps.getOraclePort();
			String connectionString = "jdbc:oracle:thin:@(description=(address_list= ";
			for (String host : hosts)
			{
				connectionString += "(address=(protocol=tcp)(port="+port +")(host="+host+")) ";
			}
			connectionString += ") (connect_data=(service_name="+ loadCollectorProps.getOracleService()+") ))";

			LOGGER.info("Connection String: " + connectionString);
			conn = DriverManager.getConnection(connectionString,loadCollectorProps.getOracleUser(),loadCollectorProps.getOraclePassword());
		} catch (Exception e){
			LOGGER.error("Connection to Orcale threw an exception: ",e);
		}
		return conn;
	}

	public LoadCollectorProps getLoadCollectorProps()
	{
		return loadCollectorProps;
	}

	public void setLoadCollectorProps(LoadCollectorProps loadCollectorProps)
	{
		this.loadCollectorProps = loadCollectorProps;
	}
}
