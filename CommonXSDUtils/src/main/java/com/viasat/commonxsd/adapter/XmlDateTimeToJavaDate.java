package com.viasat.commonxsd.adapter;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlDateTimeToJavaDate extends XmlAdapter<String, Date>
{

	@Override
	public Date unmarshal(String v) throws Exception
	{
		if (v != null && v.length() > 0)
			return DatatypeConverter.parseDateTime(v).getTime();

		return null;
	}

	@Override
	public String marshal(Date v) throws Exception
	{
		if (v != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(v);

			return DatatypeConverter.printDateTime(cal);
		}

		return null;
	}

}
