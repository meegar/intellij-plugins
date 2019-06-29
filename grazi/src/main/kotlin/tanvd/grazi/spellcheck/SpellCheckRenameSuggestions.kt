package tanvd.grazi.spellcheck

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.codeStyle.SuggestedNameInfo
import com.intellij.refactoring.rename.PreferrableNameSuggestionProvider
import tanvd.grazi.utils.blankCharRegex
import tanvd.grazi.utils.withOffset

class SpellCheckRenameSuggestions : PreferrableNameSuggestionProvider() {
    var active: Boolean = false

    override fun shouldCheckOthers(): Boolean {
        return !active
    }

    override fun getSuggestedNames(element: PsiElement, nameSuggestionContext: PsiElement?, result: MutableSet<String>): SuggestedNameInfo? {
        if (!active || nameSuggestionContext == null) {
            return null
        }
        val text: String = if (element is PsiNamedElement) {
            element.name
        } else {
            element.text
        } ?: return null

        val indexInName = element.text.indexOf(text)

        GraziSpellchecker.getFixes(element).forEach { typo ->
            typo.fixes.filterNot { it.contains(blankCharRegex) }.forEach {
                result.add(text.replaceRange(typo.location.range.withOffset(-indexInName), it))
            }
        }

        return SuggestedNameInfo.NULL_INFO
    }
}