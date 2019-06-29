package tanvd.grazi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPlainText
import com.intellij.spellchecker.inspections.SpellCheckingInspection
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.junit.Before
import tanvd.grazi.ide.GraziInspection
import tanvd.grazi.spellcheck.IdeaSpellchecker
import tanvd.grazi.utils.filterFor
import java.io.File

abstract class GraziTestBase(private val withSpellcheck: Boolean) : LightCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return File("src/test/resources").canonicalPath
    }

    @Before
    fun beforeEach() {
        GraziPlugin.invalidateCaches()
    }

    override fun setUp() {
        GraziPlugin.isTest = true

        super.setUp()
        myFixture.enableInspections(*inspectionTools)

        if (withSpellcheck) {
            GraziConfig.state.enabledSpellcheck = true
            IdeaSpellchecker.init(myFixture.project)
        }
    }

    protected fun runHighlightTestForFile(file: String) {
        myFixture.configureByFile(file)
        myFixture.testHighlighting(true, false, false, file)
    }

    fun plain(vararg texts: String) = plain(texts.toList())

    fun plain(texts: List<String>): Collection<PsiElement> {
        return texts.flatMap { myFixture.configureByText("${it.hashCode()}.txt", it).filterFor<PsiPlainText>() }
    }


    companion object {
        private val inspectionTools by lazy { arrayOf(GraziInspection(), SpellCheckingInspection()) }
    }
}