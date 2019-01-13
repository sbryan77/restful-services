/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/warSimulator/src/test/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationDAOSimulatorImplTest.java,v $
 * Revision:         $Revision: 1.4 $
 * Last Modified By: $Author: VIASAT\gfidler $
 * Last Modified On: $Date: 2012/02/27 21:43:47 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import org.w3c.dom.Document;

import java.util.List;


/**
 * Created by: gfidler Date: Oct 11, 2010
 */

public class ConfigurationDAOSimulatorImplTest
{
    private static final String CONFIGURATION = "CONFIGURATION";
    ConfigurationDAOSimulatorImpl impl;

    @Before public void setUp() throws Exception
    {
        impl = new ConfigurationDAOSimulatorImpl();
        System.out.println("PWD: " + System.getProperty("user.dir"));
        System.setProperty("configDir", "warSimulator" + System.getProperty("file.separator") + "localConfig");
    }

    @Test public void testDelete() throws Exception
    {
    }

    @Test public void testParse() throws Exception
    {
        Document doc = impl.query(CONFIGURATION);
        ConfigurationDocumentReader reader =
            new ConfigurationDocumentReaderImpl();
        List<ConfigurationItem> itemList = reader.getConfigItemList(doc);
        int mapSize = itemList.size();
        System.out.println("mapSize:" + mapSize);
    }

    @Test public void testQuery() throws Exception
    {
        Document doc = impl.query(CONFIGURATION);
        assertNotNull("doc should not be null", doc);
    }

    @Test public void testQueryString() throws Exception
    {
    }

    @Test public void testUpdate() throws Exception
    {
    }
}
