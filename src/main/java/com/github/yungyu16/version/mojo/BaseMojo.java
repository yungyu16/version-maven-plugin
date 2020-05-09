package com.github.yungyu16.version.mojo;

import com.github.yungyu16.version.util.LogUtil;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/8.
 */
public abstract class BaseMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject mavenProject;

    public void execute() throws MojoFailureException {
        LogUtil.initDelegateLog(getLog());//初始化全局日志
        InputStream bannerStream = getClass().getResourceAsStream("/banner.txt");
        String banner = null;
        try {
            banner = String.join(System.lineSeparator(), IOUtils.readLines(bannerStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            if (getLog().isDebugEnabled()) {
                getLog().debug("打印banner异常", e);
            }
        }
        if (getLog().isInfoEnabled()) {
            getLog().info(System.lineSeparator() + banner);
        }
        doExecute(mavenProject);
    }

    protected abstract void doExecute(MavenProject mavenProject) throws MojoFailureException;
}