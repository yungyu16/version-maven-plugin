package com.github.yungyu16.version.util;

import com.github.yungyu16.version.entity.ProjectVersion;
import org.apache.maven.model.Model;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 命令模式生成版本号
 *
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/8.
 */
public interface VersionUtil {
    String SNAPSHOT = "-SNAPSHOT";

    static ProjectVersion nextMajor(Model model) {
        String v = newVersion(model, (x, y, z) -> IntStream.of(x + 1, 0, 0).mapToObj(String::valueOf).collect(Collectors.joining(".", "", SNAPSHOT)));
        return new ProjectVersion(model.getGroupId(), model.getArtifactId(), model.getVersion(), v);
    }

    static ProjectVersion nextMinor(Model model) {
        String v = newVersion(model, (x, y, z) -> IntStream.of(x, y + 1, 0).mapToObj(String::valueOf).collect(Collectors.joining(".", "", SNAPSHOT)));
        return new ProjectVersion(model.getGroupId(), model.getArtifactId(), model.getVersion(), v);
    }

    static ProjectVersion nextPatch(Model model) {
        String v = newVersion(model, (x, y, z) -> IntStream.of(x, y, z + 1).mapToObj(String::valueOf).collect(Collectors.joining(".", "", SNAPSHOT)));
        return new ProjectVersion(model.getGroupId(), model.getArtifactId(), model.getVersion(), v);
    }

    static ProjectVersion makeRelease(Model model) {
        String v = newVersion(model, (x, y, z) -> IntStream.of(x, y, z).mapToObj(String::valueOf).collect(Collectors.joining(".")));
        return new ProjectVersion(model.getGroupId(), model.getArtifactId(), model.getVersion(), v);
    }

    /**
     * 仅支持 x.y.z 模式版本号 xyz为有效正整数
     *
     * @param model
     * @param handler
     * @return
     */
    static String newVersion(Model model, VersionHandler handler) {
        if (model == null) {
            throw new NullPointerException("model");
        }
        String version = model.getVersion();
        LogUtil.info("开始解析版本号:" + version);
        if (version == null) {
            throw new IllegalArgumentException("版本号为空");
        }
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        if (version.endsWith(SNAPSHOT)) {
            version = version.substring(0, version.length() - 9);
        }
        String[] fields = version.split("\\.");
        LogUtil.debug("版本号字段:" + Arrays.toString(fields));
        return handler.newVersion(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
    }

    interface VersionHandler {
        String newVersion(int major, int minor, int path);
    }
}
