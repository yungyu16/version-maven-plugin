package com.github.yungyu16.version.mojo;

import com.github.yungyu16.version.entity.ProjectVersion;
import com.github.yungyu16.version.util.VersionUtil;
import org.apache.maven.model.Model;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 变更为下一个补丁版本 x.y.z -> x.y.(z+1)-SNAPSHOT
 *
 * @author yunyu16
 * @description Created by yunyu16 on 2020/5/7.
 */
@Mojo(name = "nextPatchVersion", defaultPhase = LifecyclePhase.NONE)
public class NextPatchVersionMojo extends BaseVersionMojo {
    @Override
    protected ProjectVersion nextVersion(Model model) {
        return VersionUtil.nextPatch(model);
    }
}
