package org.tnt.org.tnt.dontforgettoruntests

import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vcs.VcsListener
import com.intellij.openapi.vcs.VcsNotifier
import com.intellij.openapi.vcs.changes.ChangeListManagerImpl
import com.intellij.openapi.vcs.impl.ProjectLevelVcsManagerImpl
import com.intellij.testFramework.LightProjectDescriptor
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import org.junit.Test


class AcceptanceTests : LightPlatformCodeInsightFixture4TestCase() {
    override fun getProjectDescriptor(): LightProjectDescriptor {
        return DefaultLightProjectDescriptor()
    }

    @Test
    fun `reminds to run tests before commit`() {
/*

        val changeListManager = ChangeListManagerImpl.getInstanceImpl(project)
        val vcsManager = ProjectLevelVcsManager.getInstance(project) as ProjectLevelVcsManagerImpl

changeListManager.

*/


    }
}