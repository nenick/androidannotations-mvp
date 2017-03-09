package tools.builder

import tools.builder.base.ClassBuilder

class FragmentBuilder extends ClassBuilder<FragmentBuilder> {

    @Override
    String buildContent(String projectId, String className, String importAnnotations, String classAnnotations, String fieldEntries) {
        return """

package ${projectId};

import android.app.Fragment;
import ${projectId}.R;
${importAnnotations}

${classAnnotations}
public class ${className} extends Fragment {

    ${fieldEntries}

}
        """
    }
}
