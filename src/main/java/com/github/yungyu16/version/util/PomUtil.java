package com.github.yungyu16.version.util;

import com.github.yungyu16.version.entity.ProjectVersion;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/8.
 */
public interface PomUtil {
    String GROUP_ID = "groupId";
    String ARTIFACT_ID = "artifactId";
    String VERSION = "version";
    Set<String> depXPaths = Sets.newHashSet("/project", "//default:parent", "//default:dependency");
    String NS = "http://maven.apache.org/POM/4.0.0";

    static void updateVersion(Path pomPath, ProjectVersion projectVersion) {
        Preconditions.checkNotNull(pomPath, "pomPath");
        Preconditions.checkNotNull(projectVersion, "projectVersion");
        try (InputStream inputStream = Files.newInputStream(pomPath)) {
            SAXReader saxReader = new SAXReader();
            configNs(saxReader);
            Document document = saxReader.read(inputStream);
            Element root = document.getRootElement();
            collectDependencyEl(root).forEach(projectVersion::resolveNewVersion);
            FileWriter pomWriter = new FileWriter(pomPath.toFile());
            document.write(pomWriter);
            pomWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException("xml文件处理异常", e);
        }
    }

    /**
     * xml 命名空间配置
     *
     * @param saxReader
     */
    static void configNs(SAXReader saxReader) {
        Map<String, String> xpathParam = Maps.newHashMap();
        xpathParam.put("default", NS);
        saxReader.getDocumentFactory().setXPathNamespaceURIs(xpathParam);
    }

    /**
     * 收集所有可能需要变更版本号的xml节点
     *
     * @param root
     * @return
     */
    static List<Element> collectDependencyEl(Element root) {
        Preconditions.checkNotNull(root, "root");
        List<Element> els = depXPaths.stream()
                .flatMap(path -> {
                    List<?> list = root.selectNodes(path);
                    if (list == null) {
                        return Stream.empty();
                    }
                    return list.stream().map(it -> ((Element) it));
                }).peek(it -> LogUtil.debug(it.getPath()))
                .collect(Collectors.toList());
        LogUtil.info("收集到" + els.size() + "个需要更新版本号的xml节点");
        return els;
    }

    static InputStream pomStream(Path pomPath) throws IOException {
        return Files.newInputStream(pomPath);
    }

    static Model pomModel(InputStream pomStream) throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        return reader.read(pomStream);
    }
}