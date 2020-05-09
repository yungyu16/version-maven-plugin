package com.github.yungyu16.version.mojo;

import com.github.yungyu16.version.entity.ProjectVersion;
import com.github.yungyu16.version.util.VersionUtil;
import org.apache.maven.model.Model;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 变更为下一个次版本 x.y.z -> x.(y+1).0-SNAPSHOT
 *
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/7.
 */
@Mojo(name = "nextMinorVersion", defaultPhase = LifecyclePhase.NONE)
public class NextMinorVersionMojo extends BaseVersionMojo {
    @Override
    protected ProjectVersion nextVersion(Model model) {
        return VersionUtil.nextMinor(model);
    }
}
