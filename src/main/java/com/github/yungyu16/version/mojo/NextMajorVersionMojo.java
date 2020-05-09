package com.github.yungyu16.version.mojo;

import com.github.yungyu16.version.entity.ProjectVersion;
import com.github.yungyu16.version.util.VersionUtil;
import org.apache.maven.model.Model;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 变更为下一个主版本 x.y.z -> （x+1).0.0-SNAPSHOT
 *
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/7.
 */
@Mojo(name = "nextMajorVersion", defaultPhase = LifecyclePhase.NONE)
public class NextMajorVersionMojo extends BaseVersionMojo {
    @Override
    protected ProjectVersion nextVersion(Model model) {
        return VersionUtil.nextMajor(model);
    }
}
