package tools.helper

import tools.builder.AndroidProjectBuilder
import tools.wrapper.ActivityInstance
import tools.wrapper.ViewInstance

trait ProjectHelper implements AndroidFileHelper, ProjectFilesHelper {

    ClassLoader cl

    AndroidProjectBuilder androidProjectBuilder() {
        new AndroidProjectBuilder(localRepo: localRepo, dir: dir)
    }

    ActivityInstance activityInstance(String name) {
        new ActivityInstance(name + "_", classLoader(), androidApplicationProjectId)
    }

    ViewInstance viewInstance(String name) {
        new ViewInstance(name + "_", classLoader(), androidApplicationProjectId)
    }

    ClassLoader classLoader() {
        if(cl != null) {
            return cl
        };

        // Create a File object on the root of the directory containing the class file
        File file = file("build/intermediates/classes/debug/")

        // Convert File to a URL
        URL url = file.toURL();
        URL[] urls = [url];

        // Create a new class loader with the directory
        cl = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
    }
}