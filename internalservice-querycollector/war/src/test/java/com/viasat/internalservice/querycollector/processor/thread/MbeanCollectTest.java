package com.viasat.internalservice.querycollector.processor.thread;

import org.junit.After;

/**
 * Created by sbryan on 3/2/2017.
 */

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(
//        { QueryProcessor.class, URL.class})
public class MbeanCollectTest

{
//    private QueryCollectorPersistence queryCollectorPersistence;
//    private QueryProcessor queryProcessor;
//    private CollectorConfigType collectorConfigType;
//    private static final Logger LOGGER = LoggerFactory
//            .getLogger(QueryCollectorImplTest.class);
//    private static final String collectionQuery = "https://fcd-provisioningvolubill01.dev.sandbox.wdc1.wildblue.net:8443/manager/jmxproxy/?qry=*:type=Performance.Counter.Server,* ";
//    private String versionFilter = "ProvisioningVolubill-v4";
//    private static final String COLLECTOR_ID = "collector1";
//
//    @BeforeClass
//    public static void setUpClass() throws ClassNotFoundException, NamingException, SQLException
//    {
//
//    }
//
//    @Before
//    public void setUp()
//    {
//        queryCollectorPersistence = mock(QueryCollectorPersistence.class);
//        queryProcessor = mock(QueryProcessor.class);
//        collectorConfigType = mock(CollectorConfigType.class);
//
//    }

    /**
     * Tear down the test set up.
     */
    @After
    public void tearDown()
    {

    }

//    @Test
//    public void testCollect()
//    {
//        try
//        {
//            ConfigType cct = loadCollectorConfigType();
//            Map<String, Double> metricsCache = new HashMap<>();
//            CollectorConfigType collectorConfig = cct.getCollectorConfig();
//            String mbeanData = loadMbeanData();
//
//            CollectionInfoType cit = mock(CollectionInfoType.class);
//            CredentialsType ct = mock(CredentialsType.class);
//            CollectionSourceType cst = mock(CollectionSourceType.class);
//            DataBusConfigType dbct = mock(DataBusConfigType.class);
//            CollectorConfigType colConfigType = mock(CollectorConfigType.class);
//            URLConnection mockURLConnection = mock(URLConnection.class);
//            BufferedReader mockReader = mock(BufferedReader.class);
//            InputStream is = mock(InputStream.class);
//            InputStreamReader isr = mock(InputStreamReader.class);
//            SelfMonitorProcessor smp = mock(SelfMonitorProcessor.class);
//            when(cit.getCollectionSource()).thenReturn(cst);
//            when(cst.getCollectionQuery()).thenReturn(collectionQuery);
//            TagsType tags = new TagsType();
//            TagType tag1 = new TagType();
//            tag1.setId("host");
//            tag1.setValue("inttca01.sandbox.dev.wdc1.wildblue.net");
//            tags.getTag().add(tag1);
//            TagType tag2 = new TagType();
//            tag1.setId("hostGroup");
//            tag1.setValue("inttca01");
//            tags.getTag().add(tag2);
//            TagType tag3 = new TagType();
//            tag1.setId("instance");
//            tag1.setValue("provisioningvolubill01");
//            tags.getTag().add(tag3);
//            TagType tag4 = new TagType();
//            tag1.setId("cluster");
//            tag1.setValue("fcd-provisioningvolubill");
//            tags.getTag().add(tag4);
//            TagType tag5 = new TagType();
//            tag1.setId("service");
//            tag1.setValue("http://fcd-provisioningvolubill01.sandbox.dev.wdc1.wildblue.net:8080/Facade-Provisioning-Volubill/v4/services/ProvisioningFacade");
//            tags.getTag().add(tag5);
//            TagType tag6 = new TagType();
//            tag1.setId("version");
//            tag1.setValue("v4");
//            tags.getTag().add(tag6);
//            when(cst.getTags()).thenReturn(tags);
//            URL mockURL = PowerMockito.mock(URL.class);
//            when(colConfigType.getCredentials()).thenReturn(ct);
//            when(ct.getUser()).thenReturn("tomcat");
//            when(ct.getPassword()).thenReturn("tomcat");
//            when(cst.getCollectionQuery()).thenReturn(collectionQuery);
//            whenNew(URL.class).withArguments(collectionQuery).thenReturn(mockURL);
//            when(mockURL.openConnection()).thenReturn(mockURLConnection);
//            when(mockURLConnection.getInputStream()).thenReturn(is);
//            when(cit.getCollectionSource()).thenReturn(cst);
//            when(cst.getTags()).thenReturn(tags);
//
//            whenNew(InputStreamReader.class).withArguments(is).thenReturn(isr);
//            whenNew(BufferedReader.class).withArguments(isr).thenReturn(mockReader);
//            when(mockReader.readLine()).thenReturn(mbeanData,null);
//            String databusEndPoint = "http://databus.preprod.idb.viasat.io:8080";
//            String streamName = "denveritmbeandev";
//            when(dbct.getEndpoint()).thenReturn(databusEndPoint);
//            when(dbct.getStreamName()).thenReturn(streamName);
////            Databus bus = mock(Databus.class);
////            whenNew(Databus.class).withArguments(databusEndPoint,streamName).thenReturn(bus);
//            when(cst.getVersionfilter()).thenReturn(versionFilter);
//            //Schema schema = mock(Schema.class);
//            Schema schema = loadSchema();
//            GenericRecord metricData = mock(GenericRecord.class);
//            //when(bus.getSchema()).thenReturn(schema);
//            //when(cit.getMetricDefinitions()).thenReturn(collectorConfigType.getMetricDefinitions());
//            //whenNew(org.apache.avro.generic.Record(schema)).thenReturn(metricData);
//            long unixTime = System.currentTimeMillis() / 1000L;
//            MbeanCollect mbc = new MbeanCollect(cit,colConfigType,COLLECTOR_ID,dbct,unixTime,smp,metricsCache);
//            mbc.run();
//
//        }
//        catch (Exception ex)
//        {
//            LOGGER.error("Exception when attempting configure ", ex);
//            ex.printStackTrace();
//            fail();
//        }
//    }
//
//    private ConfigType loadCollectorConfigType()
//    {
//        ConfigType ct = null;
//        try
//        {
//            InputStreamReader reader = new InputStreamReader(
//                    this.getClass().getResourceAsStream("/data/collectorConfig.xml"), "UTF-8");
//            XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
//            xsr.nextTag(); // Advance to the first tag within "Body"
//            Unmarshaller unmarshaller = JAXBContext.newInstance(ConfigType.class)
//                    .createUnmarshaller();
//            JAXBElement<ConfigType> je = unmarshaller.unmarshal(xsr,
//                    ConfigType.class);
//            ct = je.getValue();
//        }
//        catch (Exception ex)
//        {
//            LOGGER.error("Exception loading collector config. ", ex);
//        }
//        return ct;
//    }
//    private String loadMbeanData() {
//        String mbeanData = new String();
//        try {
//            BufferedReader inReader = new BufferedReader(new InputStreamReader(
//                    this.getClass().getResourceAsStream("/data/mbeanData.txt")));
//            String inputLine;
//            mbeanData = inReader.readLine();
//        }
//        catch (Exception e){
//            LOGGER.error("Error getting unit test mbean data",e);
//        }
//        return mbeanData;
//    }
//
//    private Schema loadSchema(){
//        try {
//            System.out.println("path: "+this.getClass().getResource("/data/denveritmbean.json").toString());
//            File schemaFile = new File(this.getClass().getResource("/data/denveritmbean.json").toURI());
//            Schema schema = AvroUtils.getSchemaFromFile(schemaFile);
//            //Schema schema = AvroUtils.getSchemaFromFile(this.getClass().getResource("./data/schema.json").toString());
//            return  schema;
//        }catch (Exception e){
//            LOGGER.error("error loading schema file ",e);
//            e.printStackTrace();
//        }
//        return null;
//    }

}
