package com.viasat.internalservice.querycollector.processor;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.avro.Schema;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viasat.internalservice.querycollector.persistence.QueryDynamo;
import com.viasat.internalservice.querycollector.persistence.QueryResponse;
import com.viasat.internalservice.querycollector.persistence.QuerySql;
import com.viasat.internalservice.querycollector.persistence.data.DynamoResult;
import com.viasat.internalservice.querycollector.props.LoadCollectorProps;
import com.viasat.internalservice.querycollector.rest.QueryCollectorImplTest;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
//@PrepareForTest(
//{ QueryProcessor.class })
public class QueryProcessorTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryCollectorImplTest.class);

	@Before
	public void setUp()
	{

	}

	@Test
	public void testCollect()
	{
		try
		{

		    QueryProcessor qp = new QueryProcessor();
            QueryDynamo qd = PowerMockito.mock(QueryDynamo.class);
            qp.setQueryDynamo(qd);
            QuerySql querySql = mock(QuerySql.class);
            qp.setQuerySql(querySql);
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            LoadCollectorProps lcp = mock(LoadCollectorProps.class);
            qp.setLoadCollectorProps(lcp);
            QueryResponse queryResponse = mock(QueryResponse.class);
            Schema schema = PowerMockito.mock(Schema.class);
            List<Schema> schemas = new ArrayList<>();
            schemas.add(schema);
            when(lcp.getAwsCollectorTableName()).thenReturn("CollectionQueryMetrics");
            when(lcp.getRegion()).thenReturn("us-west-2");

            List<QueryResponse> queryResponses = new ArrayList<>();
            queryResponses.add(queryResponse);

            String collectorIds = "query001";
            String region="us-west-2";
            String tableName="CollectionQueryMetrics";
            long unixTime = System.currentTimeMillis() / 1000L;
            List<DynamoResult> dyanmoResults = new ArrayList<>();
            DynamoResult dynamoResult = new DynamoResult();
            List<String> tags = new ArrayList<>();
            dynamoResult.setCollectionQuery("SELECT tt.TRANSACTION_TYPE AS \\\"orderType\\\",ts.TRANSACTION_STATUS as \\\"orderStatus\\\",(ht.UPDATE_DATE - TO_DATE('1970-01-01',"+
                "    'YYYY-MM-DD')) * 86400000 as \\\"OrderCompletionTime\\\", sc.SALES_CHANNEL as \\\"salesChannel\\\", sct.SALES_CHANNEL_TYPE as \\\"salesChannelType\\\","+
                "(ht.UPDATE_DATE - ht.CREATE_DATE) * 86400000 as \\\"OrderProcessingTime\\\" FROM WB_DATA_OWNER.H_TRANSACTION ht, WB_DATA_OWNER.TRANSACTION_TYPE tt, "+
                    "WB_DATA_OWNER.TRANSACTION_STATUS ts, WB_DATA_OWNER.SALES_CHANNEL sc, WB_DATA_OWNER.SALES_CHANNEL_TYPE sct WHERE HT.UPDATE_DATE > SYSDATE - 3000 / 86400"+
                    "AND TT.TRANSACTION_TYPE_ID = HT.TRANSACTION_TYPE_ID AND TS.TRANSACTION_STATUS_ID = HT.TRANSACTION_STATUS_ID AND "+
                    "sc.SALES_CHANNEL_ID = ht.SALES_CHANNEL_ID AND sct.SALES_CHANNEL_TYPE_ID = sc.SALES_CHANNEL_TYPE_ID ORDER BY ht.UPDATE_DATE, TT.TRANSACTION_TYPE, "+
                    "ts.TRANSACTION_STATUS_ID");
            dynamoResult.setCollectorKey("query001");
            dynamoResult.setDatabusEndPoint("http://databus.dev.idb.viasat.io:8080");
            dynamoResult.setDatabusStreamName("denveritdbqueries");
            tags.add("orderType");
            tags.add("orderStatus");
            tags.add("salesChannel");
            tags.add("salesChannelType");
            dynamoResult.setTags(tags);
            dynamoResult.setMetricDefinition("OrderProcessingTime");
            dyanmoResults.add(dynamoResult);

            when(qd.getCollectorInfo(collectorIds, region, tableName)).thenReturn(dyanmoResults);
            when(querySql.getMetrics(dynamoResult)).thenReturn(queryResponses);
            qp.collect(collectorIds, unixTime);
		}
		catch (Exception ex)
		{
			LOGGER.error("Exception when attempting configure ", ex);
			ex.printStackTrace();
			fail();
		}
	}
    @Test
    public void testSetQueryDynamo() {
        QueryDynamo qd = new QueryDynamo();
        QueryProcessor qp = new QueryProcessor();
        qp.setQueryDynamo(qd);
        QueryDynamo qdResp = qp.getQueryDynamo();
        assertEquals(qd,qdResp);
    }
    @Test
    public void testSetQuerySql() {
        QuerySql qsql = new QuerySql();
        QueryProcessor qp = new QueryProcessor();
        qp.setQuerySql(qsql);
        QuerySql qsqlResp = qp.getQuerySql();
        assertEquals(qsql,qsqlResp);
    }
    @Test
    public void testSetLoadProperties() {
        LoadCollectorProps loadCollectorProps = new LoadCollectorProps();
        QueryProcessor qp = new QueryProcessor();
        qp.setLoadCollectorProps(loadCollectorProps);
        LoadCollectorProps lqpResp = qp.getLoadCollectorProps();
        assertEquals(loadCollectorProps,lqpResp);
    }
    @Test
    public void testSetSelfMonitor() {
        SelfMonitorProcessor smp = new SelfMonitorProcessor();
        QueryProcessor qp = new QueryProcessor();
        qp.setSelfMonitorProcessor(smp);
        SelfMonitorProcessor smpResp = qp.getSelfMonitorProcessor();
        assertEquals(smp,smpResp);
    }
    }
