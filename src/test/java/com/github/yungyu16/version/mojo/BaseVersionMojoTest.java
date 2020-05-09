package com.github.yungyu16.version.mojo;

import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * CreatedDate: 2020/5/8
 * Author: yunyu16
 */
public class BaseVersionMojoTest {
    @Test
    public void testXpath() throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.xml"));
        String defaultNamespace = document.getRootElement().getNamespaceURI();
        System.out.println(defaultNamespace);
        Map<String, String> xpathParam = Maps.newHashMap();
        xpathParam.put("default", defaultNamespace);
        XPath x = document.createXPath("//default:parent");
        x.setNamespaceURIs(xpathParam);
        List list = x.selectNodes(document);
        list.forEach(it -> System.out.println(((Node) it).getText()));
        System.out.println(list.size());
    }
}