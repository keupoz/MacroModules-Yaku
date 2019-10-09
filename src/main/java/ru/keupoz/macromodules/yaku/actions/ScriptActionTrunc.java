package ru.keupoz.macromodules.yaku.actions;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.ReturnValue;
import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.parser.ScriptCore;
import ru.keupoz.macromodules.yaku.ModuleInfo;;

@APIVersion(ModuleInfo.API_VERSION)
public class ScriptActionTrunc extends ScriptAction {
    public ScriptActionTrunc() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionTrunc(ScriptContext context) {
        super(context, "trunc");
    }

    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
        ReturnValue retVal = new ReturnValue(0);

        float number = ScriptCore.tryParseFloat(provider.expand(macro, params[0], false), 0F);

        if (number != 0) {
            number -= number % 1;
        }

        retVal.setInt((int)number);

        return retVal;
    }

    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
