package com.github.yungyu16.version.mojo;

import com.github.yungyu16.version.entity.ProjectVersion;
import com.github.yungyu16.version.util.LogUtil;
import com.github.yungyu16.version.util.PomUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/7.
 */
public abstract class BaseVersionMojo extends BaseMojo {
    @Override
    protected void doExecute(MavenProject mavenProject) {
        Model model = mavenProject.getModel();
        getLog().debug(model.toString() + "-" + System.identityHashCode(model));
        getLog().info("model:" + model.toString());
        ProjectVersion projectVersion = nextVersion(model);
        getLog().info("获取的下一个版本为:" + projectVersion);
        resolveAllRelatedPomPath(model)
                .forEach(path -> {
                    getLog().info("开始处理pom文件:" + path);
                    PomUtil.updateVersion(path, projectVersion);
                });
    }

    /**
     * 获取下一个版本
     *
     * @param model
     * @return
     */
    protected abstract ProjectVersion nextVersion(Model model);

    /**
     * 获取所有需要跟随变更的POM文件 包括父子pom
     *
     * @param pomModel
     * @return
     */
    private Set<Path> resolveAllRelatedPomPath(Model pomModel) {
        Preconditions.checkNotNull(pomModel, "mavenProject");

        File thisPomFile = pomModel.getPomFile();
        Path thisPomFilePath = thisPomFile.toPath();
        Path thisPomDirPath = thisPomFilePath.getParent();

        Set<Path> paths = Sets.newHashSet();
        paths.add(thisPomFilePath); //当前pom
        List<String> modules = pomModel.getModules();
        if (modules != null) {
            //所有子模块pom
            modules.stream()
                    .filter(Objects::nonNull)
                    .map(it -> thisPomDirPath.resolve(Paths.get(it, "pom.xml")))
                    .map(Path::toAbsolutePath)
                    .forEach(paths::add);
        }
        String relativePath = pomModel.getParent().getRelativePath();
        if (relativePath != null) {
            //父pom
            Path parentPath = thisPomDirPath.resolve(Paths.get(relativePath));
            if (Files.exists(parentPath)) {
                LogUtil.debug("存在本地父pom:" + parentPath);
                paths.add(parentPath);
            } else {
                LogUtil.debug("本地父pom不存在:" + parentPath);
            }
        }
        return paths;
    }
}

