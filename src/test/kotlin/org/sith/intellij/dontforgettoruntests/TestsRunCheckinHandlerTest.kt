package org.sith.intellij.dontforgettoruntests

import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitExecutor
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.util.PairConsumer
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestsRunCheckinHandlerTest {
    private val ignoredAdditionalDataConsumer: PairConsumer<Any, Any>? = null
    @Mock
    private lateinit var shouldRunTestsDialog: ShouldRunTestsDialog
    @Mock
    private lateinit var panel: CheckinProjectPanel
    @Mock
    private lateinit var testsRunStatusManager: TestsRunStatusManager
    @Mock
    private lateinit var commitExecutor: CommitExecutor

    private lateinit var testsRunCheckinHandler: TestsRunCheckinHandler

    @Before
    fun setUp() {
        testsRunCheckinHandler = TestsRunCheckinHandler(panel, testsRunStatusManager, shouldRunTestsDialog)
    }

    @Test
    fun `commits as there is no need to run tests again`() {
        `when`(testsRunStatusManager.shouldRunTests()).thenReturn(false)

        val beforeCheckinResult =
            this.testsRunCheckinHandler.beforeCheckin(commitExecutor, ignoredAdditionalDataConsumer)
        assertThat(beforeCheckinResult, `is`(CheckinHandler.ReturnResult.COMMIT))
        verifyZeroInteractions(shouldRunTestsDialog)
    }

    @Test
    fun `cancels commit because tests must be re-run`() {
        `when`(testsRunStatusManager.shouldRunTests()).thenReturn(true)
        `when`(shouldRunTestsDialog.show()).thenReturn(Messages.YES)

        val beforeCheckinResult =
            this.testsRunCheckinHandler.beforeCheckin(commitExecutor, ignoredAdditionalDataConsumer)
        assertThat(beforeCheckinResult, `is`(CheckinHandler.ReturnResult.CLOSE_WINDOW))
    }

    @Test
    fun `does nothing if cancel`() {
        `when`(testsRunStatusManager.shouldRunTests()).thenReturn(true)
        `when`(shouldRunTestsDialog.show()).thenReturn(Messages.CANCEL)

        val beforeCheckinResult =
            this.testsRunCheckinHandler.beforeCheckin(commitExecutor, ignoredAdditionalDataConsumer)
        assertThat(beforeCheckinResult, `is`(CheckinHandler.ReturnResult.CANCEL))
    }

    @Test
    fun `commits when user ignores warning`() {
        `when`(testsRunStatusManager.shouldRunTests()).thenReturn(true)
        `when`(shouldRunTestsDialog.show()).thenReturn(Messages.NO)

        val beforeCheckinResult =
            this.testsRunCheckinHandler.beforeCheckin(commitExecutor, ignoredAdditionalDataConsumer)
        assertThat(beforeCheckinResult, `is`(CheckinHandler.ReturnResult.COMMIT))
    }

}
