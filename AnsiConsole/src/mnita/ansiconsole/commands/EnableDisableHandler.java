package mnita.ansiconsole.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import mnita.ansiconsole.preferences.AnsiConsolePreferenceUtils;

public class EnableDisableHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        boolean isAnsiconEnabled = AnsiConsolePreferenceUtils.isAnsiConsoleEnabled();
        isAnsiconEnabled = !isAnsiconEnabled;
        AnsiConsolePreferenceUtils.setAnsiConsoleEnabled(isAnsiconEnabled);
        return null;
    }
}
