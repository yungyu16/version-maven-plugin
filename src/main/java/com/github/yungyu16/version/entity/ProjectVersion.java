package com.github.yungyu16.version.entity;

import org.dom4j.Element;

import static com.github.yungyu16.version.util.PomUtil.*;

/**
 * 维护新老版本对比信息
 * 用于替换pom.xml的信息
 *
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/8.
 */
public class ProjectVersion {
    private String groupId;
    private String artifactId;
    private String oldVersion;
    private String newVersion;

    public ProjectVersion(String groupId, String artifactId, String oldVersion, String newVersion) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(String oldVersion) {
        this.oldVersion = oldVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public void resolveNewVersion(Element dependencyEl) {
        String dGroupId = dependencyEl.elementText(GROUP_ID);
        String dArtifactId = dependencyEl.elementText(ARTIFACT_ID);
        String dVersion = dependencyEl.elementText(VERSION);
        if (!groupId.equals(dGroupId)) {
            return;
        }
        if (!artifactId.equals(dArtifactId)) {
            return;
        }
        if (!oldVersion.equals(dVersion)) {
            return;
        }
        dependencyEl.element(VERSION).setText(newVersion);
    }

    @Override
    public String toString() {
        return "groupId='" + groupId + "', artifactId='" + artifactId + "', oldVersion='" + oldVersion + "', newVersion='" + newVersion + "'}";
    }
}
