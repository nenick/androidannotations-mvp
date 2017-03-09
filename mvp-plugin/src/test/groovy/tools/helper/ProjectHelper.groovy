package tools.helper

import tools.builder.AndroidProjectBuilder

trait ProjectHelper implements AndroidFileHelper, ProjectFilesHelper {

    AndroidProjectBuilder androidProjectBuilder() {
        new AndroidProjectBuilder(localRepo: localRepo, dir: dir)
    }
}