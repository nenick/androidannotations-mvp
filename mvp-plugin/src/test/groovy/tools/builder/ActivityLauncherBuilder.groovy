package tools.builder

import de.nenick.androidannotations.plugin.mvp.ActivityLauncher
import tools.builder.base.ClassBuilder

class ActivityLauncherBuilder extends ClassBuilder<ActivityLauncherBuilder> {

    ActivityLauncherBuilder(ActivityBuilder activity, String projectId) {
        importClass = projectId + "." + activity.name + "_"
        name = ActivityLauncher.name + "<" + activity.name + "_.IntentBuilder_>"
    }

    @Override
    String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries) {
        throw new UnsupportedOperationException()
    }
}
