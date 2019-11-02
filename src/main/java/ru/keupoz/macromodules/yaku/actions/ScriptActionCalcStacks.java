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
public class ScriptActionCalcStacks extends ScriptAction {
    public ScriptActionCalcStacks() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionCalcStacks(ScriptContext context) {
        super(context, "calcstacks");
    }

    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
        int count = ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0);
        int stacks = count / 64;
        int leftovers = count % 64;

        if (params.length > 1) {
            provider.setVariable(macro, params[1], stacks);
        }

        if (params.length > 2) {
            provider.setVariable(macro, params[2], leftovers);
        }

        return new ReturnValue(String.format("%d items form %d stacks and %d items", count, stacks, leftovers));
    }

    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
