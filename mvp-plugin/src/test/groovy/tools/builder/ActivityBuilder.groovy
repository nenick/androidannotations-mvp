package tools.builder

import tools.builder.base.ClassBuilder

class ActivityBuilder extends ClassBuilder<ActivityBuilder> {

    @Override
    String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries) {

        return """

package ${projectId};

import android.app.Activity;
import ${projectId}.R;
${importAnnotations}

${classAnnotations}
public class ${className} extends Activity {

    ${fieldEntries}

}
        """
    }
}
