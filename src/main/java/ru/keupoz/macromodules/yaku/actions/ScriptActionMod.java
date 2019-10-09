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
import ru.keupoz.macromodules.yaku.ModuleInfo;

@APIVersion(ModuleInfo.API_VERSION)
public class ScriptActionMod extends ScriptAction {
    public ScriptActionMod() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionMod(ScriptContext context) {
        super(context, "mod");
    }

    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
        ReturnValue retVal = new ReturnValue(0);

        int divident = ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0);
        int divisor = ScriptCore.tryParseInt(provider.expand(macro, params[1], false), 0);

        retVal.setInt(divident % divisor);

        return retVal;
    }

    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
