package com.viasat.wildblue.common.adapter;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

/**
 * This is now the old way as it still results in Adapter1 classes being created
 * which are proving to cause conflicts due to the hierarchy of the dependent
 * services. For the new approach use:
 * {@link com.viasat.commonxsd.adapter.XmlDateToJavaDate} and/or
 * {@link com.viasat.commonxsd.adapter.XmlDateTimeToJavaDate}
 */
@Deprecated
public final class XmlDataTypeAdapter
{

	private XmlDataTypeAdapter()
	{
	}

	public static Date parseDate(String s)
	{
		if (s == null)
		{
			return null;
		}
		else
		{
			return DatatypeConverter.parseDate(s).getTime();
		}
	}

	public static String printDate(Date dt)
	{
		if (dt == null)
		{
			return null;
		}
		else
		{
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			return DatatypeConverter.printDate(c);
		}
	}

	public static Date parseTime(String s)
	{
		if (s == null)
		{
			return null;
		}
		else
		{
			return DatatypeConverter.parseTime(s).getTime();
		}
	}

	public static String printTime(Date dt)
	{
		if (dt == null)
		{
			return null;
		}
		else
		{
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			return DatatypeConverter.printTime(c);
		}
	}

	public static Date parseDateTime(String s)
	{
		if (s == null)
		{
			return null;
		}
		else
		{
			return DatatypeConverter.parseDateTime(s).getTime();
		}
	}

	public static String printDateTime(Date dt)
	{
		if (dt == null)
		{
			return null;
		}
		else
		{
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			return DatatypeConverter.printDateTime(c);
		}
	}

}
