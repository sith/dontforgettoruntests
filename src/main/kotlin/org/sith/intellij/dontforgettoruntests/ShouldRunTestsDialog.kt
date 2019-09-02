package org.sith.intellij.dontforgettoruntests

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

sealed class ShouldRunTestsDialog {
    abstract fun show(): Int

    companion object {
        fun instance(project: Project): ShouldRunTestsDialog = Instance(project)
    }
}

internal class Instance constructor(private val project: Project) : ShouldRunTestsDialog() {
    override fun show(): Int {
        return Messages.showYesNoCancelDialog(
            project,
            "Have you run tests before commit?",
            "Run Tests!",
            "Yeah I forgot to run tests",
            "Yes, I am ready to commit",
            "Cancel",
            Messages.getWarningIcon()
        )
    }
}
