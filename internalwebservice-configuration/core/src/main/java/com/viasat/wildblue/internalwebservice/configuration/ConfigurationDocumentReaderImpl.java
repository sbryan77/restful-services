/* ****************************************************************************
 * File:             $Source: /SoftwareDev/WildBlue/InternalWebService/Configuration/core/src/main/java/com/viasat/wildblue/internalwebservice/configuration/ConfigurationDocumentReaderImpl.java,v $
 * Revision:         $Revision: 1.7 $
 * Last Modified By: $Author: VIASAT\jkent $
 * Last Modified On: $Date: 2013/01/30 22:23:26 $
 *
 * ------------------------------------------------------------------
 *  Copyright (c) 2004-2010 WildBlue Communications. All rights reserved.
 * ------------------------------------------------------------------
 *
 * **************************************************************************** */


package com.viasat.wildblue.internalwebservice.configuration;

import com.viasat.wildblue.internalwebservice.configuration.data.ConfigurationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Parses a DOM document and generates a list of properties.
 */
public class ConfigurationDocumentReaderImpl
    implements ConfigurationDocumentReader
{
    /** Name of document node * */
    private static final String CONFIG = "config";

    /** Logger * */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigurationDocumentReaderImpl.class);

    @Override public List<ConfigurationItem> getConfigItemList(
        final Document doc)
    {
        List<ConfigurationItem> itemList = null;

        Element parent = doc.getDocumentElement();

        if (parent != null)
        {
            if (CONFIG.equalsIgnoreCase(parent.getNodeName()))
            {
                itemList = new ArrayList<ConfigurationItem>();

                parse(null, parent, itemList);
            }
            else
            {
                throw new ConfigurationFaultException(
                    "Document element must be named \"config\"");
            }
        }

        return itemList;
    }

    /**
     * Constructs a new ConfigurationItem object and adds it to the item list.
     *
     * @param  content   Value of the configuration item.
     * @param  itemList  List of configuration items being built.
     * @param  newName   Key for the configuration item.
     */
    private void addConfigItem(final String content,
        List<ConfigurationItem> itemList, final String newName)
    {
        char c = content.charAt(0);

        if ('\n' != c)
        {
            ConfigurationItem configItem = new ConfigurationItem();
            configItem.setKey(newName);
            configItem.setValue(content);
            itemList.add(configItem);

            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug(newName + "=" + content);
            }
        }
    }

    /**
     * Builds a concatenated name.
     *
     * @param   path  Current concatenated name.
     * @param   name  Name to add.
     *
     * @return  New concatenated name.
     */
    private String concatName(final String path, final String name)
    {
        String concatName;

        if (name == null)
        {
            return null;
        }

        if (path == null)
        {
            concatName = name;
        }
        else
        {
            concatName = path + '.' + name;
        }

        return concatName;
    }

    /**
     * Recursive routine to parse a DOM node and add configuration items to the
     * list.
     *
     * @param  path      The current path being processed.
     * @param  node      The DOM node to process.
     * @param  itemList  The list being built.
     */
    private void parse(final String path, final Node node,
        List<ConfigurationItem> itemList)
    {
        NodeList nodeList = node.getChildNodes();
        int listLen = nodeList.getLength();
        List<String> names = processNames(nodeList);

        int j = 0;

        for (int i = 0; i < listLen; i++)
        {
            Node nodeItem = nodeList.item(i);

            if (Node.TEXT_NODE == nodeItem.getNodeType())
            {
                // Skip and process next node
                continue;
            }

            String name = names.get(j++);
            String newName = concatName(path, name);

            NodeList children = nodeItem.getChildNodes();
            int childCount = children.getLength();
            short childType = 0;
            Node singleChild = null;

            if (childCount == 1)
            {
                singleChild = children.item(0);
                childType = singleChild.getNodeType();
            }

            if ((childCount == 1) && (Node.TEXT_NODE == childType))
            {
                if (Node.TEXT_NODE == singleChild.getNodeType())
                {
                    String content = singleChild.getTextContent();
                    System.out.println(newName + ':' + content);
                    addConfigItem(content, itemList, newName);
                }
            }
            else
            {
                // Recurse
                parse(newName, nodeItem, itemList);
            }
        }
    }

    /**
     * Process element names and sequentially number duplicates.
     *
     * @param   nodeList  Node list to process.
     *
     * @return  List of names, including sequential numbering where appropriate.
     */
    private List<String> processNames(final NodeList nodeList)
    {
        int listLen = nodeList.getLength();
        List<String> names = new ArrayList<String>();
        Map<String, Integer> map = new HashMap<String, Integer>();

        for (int i = 0; i < listLen; i++)
        {
            Node node = nodeList.item(i);

            if (Node.TEXT_NODE == node.getNodeType())
            {
                // Skip text nodes and process next node.
                continue;
            }

            String name = node.getNodeName();
            names.add(name);

            // Store count of keys and instance counts
            if (map.containsKey(name))
            {
                map.put(name, map.get(name) + 1);
            }
            else
            {
                map.put(name, 1);
            }
        }

        // Process duplicate names
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();

        for (Map.Entry<String, Integer> mapEntry : entrySet)
        {
            String key = mapEntry.getKey();
            Integer value = mapEntry.getValue();

            // Find keys with counts more than 1
            if (value > 1)
            {
                // Number sequentially
                int j = 1;

                for (int i = 0; i < names.size(); i++)
                {
                    String name = names.get(i);

                    if (name.equals(key))
                    {
                        names.set(i, name + '.' + j++);
                    }
                }
            }
        }

        return names;
    }
}
