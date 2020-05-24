package ru.keupoz.macromodules.yaku.actions;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.ReturnValue;
import net.eq2online.macros.scripting.exceptions.ScriptExceptionStackOverflow;
import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.parser.ScriptCore;
import ru.keupoz.macromodules.yaku.ModuleInfo;

@APIVersion(ModuleInfo.API_VERSION)
public class ScriptActionAckermann extends ScriptAction {
    public ScriptActionAckermann() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionAckermann(ScriptContext context) {
        super(context, "ackermann");
    }

    private int calculate(int m, int n) {
        if (m == 0) {
            return n + 1;
        } else if (m > 0 && n == 0) {
            return this.calculate(m - 1, 1);
        } else if (m > 0 && n > 0) {
            return this.calculate(m - 1, this.calculate(m, n - 1));
        }

        return 0;
    }

    @Override
    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams,
            String[] params) {
        int m = ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0);
        int n = ScriptCore.tryParseInt(provider.expand(macro, params[1], false), 0);

        int result = 0;

        try {
            result = this.calculate(m, n);
        } catch (StackOverflowError err) {
            throw new ScriptExceptionStackOverflow();
        }

        return new ReturnValue(result);
    }

    @Override
    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
