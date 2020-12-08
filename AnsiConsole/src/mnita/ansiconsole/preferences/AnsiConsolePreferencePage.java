package mnita.ansiconsole.preferences;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.handlers.RegistryToggleState;

import mnita.ansiconsole.AnsiConsoleActivator;
import mnita.ansiconsole.commands.EnableDisableHandler;
import mnita.ansiconsole.utils.AnsiConsoleColorPalette;

public class AnsiConsolePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    public AnsiConsolePreferencePage() {
        super(GRID);
        setPreferenceStore(AnsiConsoleActivator.getDefault().getPreferenceStore());
        setDescription("Preferences for Ansi Console");
    }

    @Override
    public void createFieldEditors() {
        Composite fieldEditorParent = getFieldEditorParent();

        addField(new BooleanFieldEditor(AnsiConsolePreferenceConstants.PREF_ANSI_CONSOLE_ENABLED,
                "Enabled", fieldEditorParent));

        addField(new BooleanFieldEditor(AnsiConsolePreferenceConstants.PREF_ENABLE_PERFORMANCE_WARNING,
                "Enable performance check", getFieldEditorParent()));

        addField(new BooleanFieldEditor(AnsiConsolePreferenceConstants.PREF_WINDOWS_MAPPING,
                "Use &Windows color mapping (bold => intense, italic => reverse)", fieldEditorParent));

        addField(new BooleanFieldEditor(AnsiConsolePreferenceConstants.PREF_SHOW_ESCAPES,
                "&Show the escape sequences", fieldEditorParent));

        addField(new BooleanFieldEditor(AnsiConsolePreferenceConstants.PREF_KEEP_STDERR_COLOR,
                "&Try using the standard error color setting for stderr output", fieldEditorParent));

        addField(new RadioGroupFieldEditor(AnsiConsolePreferenceConstants.PREF_COLOR_PALETTE, "&Color palette", 1,
                new String[][] {
                        { "Standard VGA colors", AnsiConsoleColorPalette.PALETTE_VGA },
                        { "Windows XP command prompt", AnsiConsoleColorPalette.PALETTE_WINXP },
                        { "Windows 10 command prompt", AnsiConsoleColorPalette.PALETTE_WIN10 },
                        { "Mac OS X Terminal.app", AnsiConsoleColorPalette.PALETTE_MAC },
                        { "PuTTY", AnsiConsoleColorPalette.PALETTE_PUTTY },
                        { "xterm", AnsiConsoleColorPalette.PALETTE_XTERM },
                        { "mIRC", AnsiConsoleColorPalette.PALETTE_MIRC },
                        { "Ubuntu", AnsiConsoleColorPalette.PALETTE_UBUNTU }
                },
                fieldEditorParent));
    }

    @Override
    public void init(IWorkbench workbench) {
        // Nothing to do, but we are forced to implement it for IWorkbenchPreferencePage
    }

    @Override
    public boolean performOk() {
        boolean result = super.performOk();
        IHandlerService handlerService = PlatformUI.getWorkbench().getService(IHandlerService.class);
        try {
            handlerService.executeCommand(EnableDisableHandler.COMMAND_ID, null);
        } catch (Exception ex) {
            System.out.println("Command '" + EnableDisableHandler.COMMAND_ID + "' not found");
        }
        return result;
    }
}
