package epic_code;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by m.lobanov on 04.12.2015
 */
public class EpicCodeAction extends AnAction {

    public static boolean IS_ENABLED = false;

    static {
        final EditorActionManager actionManager = EditorActionManager.getInstance();
        final TypedAction typedAction = actionManager.getTypedAction();
        typedAction.setupHandler(new EpicTypedHandler(typedAction.getHandler()));
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // Very primitive toggler, sorry
        IS_ENABLED = !IS_ENABLED;
    }

    @Override
    public void update(@NotNull final AnActionEvent anActionEvent) {
        final Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
        final Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        anActionEvent.getPresentation().setVisible((project != null && !editor.getCaretModel().getAllCarets().isEmpty()));
    }
}