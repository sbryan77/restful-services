package com.viasat.common.applicationstatus.utility;

/**
 * Constant values.
 */
public final class AppStatusConstants
{
	// Defaults
	public static final String DEFAULT_VERSION_PROPERTIES = "version";

	// Properties
	public static final String APP_NAME_PROP = "applicationName";
	public static final String ARTIFACT_GID_PROP = "artifactGroupId";
	public static final String ARTIFACT_ID_PROP = "artifactId";
	public static final String BUILD_TS_PROP = "buildTimestamp";
	public static final String VERSION_PROP = "version";

	// Code Constants
	public static final String CONTENT_TYPE_TXT_XML = "text/xml";
	public static final String CONTENT_TYPE_APP_JSON = "application/json";
	public static final String HTTP_HDR_ACCEPT = "Accept";
	public static final String CONFIG_SVC_PROP = "configurationServiceEndpoint";
	public static final String WEBLOGIC_NAME_PROP = "weblogic.Name";
	public static final String JAVA_RT_VER = "java.runtime.version";
	public static final String JAVA_VENDOR = "java.vendor";
	public static final String TC_MBEAN_NAME = "Catalina:type=Server";
	public static final String TC_MBEAN_ATTRIBUTE = "serverInfo";
	public static final String GEN_DB_CHECK = "genericDatabaseConnectionCheck";
	public static final String DEF_CONF_SVC_CHECK = "configurationServiceCheck";
	public static final String HTTPS_PREFIX = "https";
	public static final String CXF_SVC_PATH = "services";
	public static final String WSDL_SFX = "?WSDL";
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String UTF_8 = "UTF-8";

	// Date Format String
	public static final String DF_VERSION_PROPERTIES = "yyyy-MM-dd'T'HH:mm:ssXXX";
	public static final String DF_VERSION_PROPERTIES_OLD = "yyyyMMdd-HHmm";
	public static final String DF_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	// Environment Details
	public static final String ED_LOG_FILE = "logFileLocation";
	public static final String ED_HOST_NAME = "hostInstanceName";
	public static final String ED_HOST_DATE = "hostSystemDate";
	public static final String ED_SERVER = "hostServerInfo";
	public static final String ED_JRE_VERSION = "javaRuntimeVersionInfo";
}
