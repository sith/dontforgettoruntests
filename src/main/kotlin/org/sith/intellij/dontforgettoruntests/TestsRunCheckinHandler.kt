package org.sith.intellij.dontforgettoruntests

import com.intellij.openapi.ui.Messages.NO
import com.intellij.openapi.ui.Messages.YES
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.changes.CommitExecutor
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import com.intellij.util.PairConsumer

class TestsRunCheckinHandler(
    private val checkinProjectPanel: CheckinProjectPanel,
    private val testsRunStatusManager: TestsRunStatusManager,
    private val shouldRunTestsDialog: ShouldRunTestsDialog = ShouldRunTestsDialog.instance(checkinProjectPanel.project)

) : CheckinHandler() {
    override fun beforeCheckin(
        executor: CommitExecutor?,
        additionalDataConsumer: PairConsumer<Any, Any>?
    ): ReturnResult {
        if (testsRunStatusManager.shouldRunTests()) {
            when (shouldRunTestsDialog.show()) {
                YES -> {
                    return ReturnResult.CLOSE_WINDOW
                }
                NO -> return ReturnResult.COMMIT
            }
            return ReturnResult.CANCEL
        }
        return ReturnResult.COMMIT
    }
}

class TestsRunCheckinHandlerFactory : CheckinHandlerFactory() {
    override fun createHandler(panel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return TestsRunCheckinHandler(
            panel,
            object : TestsRunStatusManager {
                override fun shouldRunTests(): Boolean {
                    return true
                }
            })
    }

}
