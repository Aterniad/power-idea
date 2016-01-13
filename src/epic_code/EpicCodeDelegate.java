package epic_code;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by m.lobanov on 04.12.2015
 * Вся эта штука только ради того, чтобы после печати символа продолжалось его стандартное поведение
 */
public class EpicCodeDelegate extends TypedHandlerDelegate {

    @Override
    public Result charTyped(char c, Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        return Result.CONTINUE;
    }

}