package epic_code;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import epic_code.helper.MathStuff;
import epic_code.sprites.Particle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @todo: Paint particles with current text color
 * Created by m.lobanov on 04.12.2015
 */
public class EpicTypedHandler implements TypedActionHandler {

    public static Integer MAX_PARTICLES = 8;
    public static Integer FLY_ITERATIONS = 40;
    public static Integer PARTICLE_SIZE = 3;

    public static Long PARTICLE_SPEED = 6L;

    private TypedActionHandler myOriginalHandler;
    public EpicTypedHandler(TypedActionHandler originalHandler){
        myOriginalHandler = originalHandler;
    }

    @Override
    public void execute(@NotNull Editor editor, final char c, @NotNull DataContext dataContext) {

        final Project project = editor.getProject();
        if (project == null) {
            return;
        }
        myOriginalHandler.execute(editor, c, dataContext);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                // Toggler
                if (!EpicCodeAction.IS_ENABLED) {
                    return;
                }

                FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
                final Editor editor = fileEditorManager.getSelectedTextEditor();
                if (editor == null) {
                    return;
                }
                final Point position = editor.visualPositionToXY(editor.getCaretModel().getVisualPosition());

                final Component c = editor.getContentComponent();

                final Integer x = (int) position.getX();
                final Integer y = (int) position.getY();

                final ArrayList<Particle> pts = new ArrayList<Particle>();
                Integer particles_created = 0;
                while (particles_created < MAX_PARTICLES) {
                    pts.add(new Particle(x, y));
                    particles_created++;
                }

                final Graphics2D g = (Graphics2D) c.getGraphics();
                g.setPaint(getRandColor());

                // Do the harlem shake
                /*final Component ccc = editor.getComponent(); #todo: Nope, this works very buggy
                ccc.getParent().setLayout(null);
                ccc.setLocation(MathStuff.randInt(0, 2), MathStuff.randInt(0, 2));*/

                final TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        c.repaint(); // Erase prev. frame
                        boolean still_alive = false;
                        float opacity = 1.0f;
                        for (Particle pt : pts) {
                            // Till' at least one particle still alive
                            boolean is_live = pt.iterate();
                            if (is_live) {
                                g.fillRect(pt.getX(), pt.getY(), PARTICLE_SIZE, PARTICLE_SIZE);
                                opacity = pt.getOpacity();
                            }
                            still_alive = still_alive || is_live;
                        }
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                        if (!still_alive) {
                            this.cancel();
                        }
                    }
                };
                Timer t = new Timer();
                t.schedule(task, 0L, PARTICLE_SPEED);
            }

        };
        WriteCommandAction.runWriteCommandAction(project, runnable);
    }

    protected Color getRandColor() {
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(256);
        int green = randomGenerator.nextInt(256);
        int blue = randomGenerator.nextInt(256);

        return new Color(red,green,blue);
    }

}