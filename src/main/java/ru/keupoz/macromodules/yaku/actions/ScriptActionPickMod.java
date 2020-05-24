package ru.keupoz.macromodules.yaku.actions;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.ReturnValue;
import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import ru.keupoz.macromodules.yaku.ItemID;
import ru.keupoz.macromodules.yaku.ModuleInfo;

@APIVersion(ModuleInfo.API_VERSION)
public class ScriptActionPickMod extends ScriptAction {
    public ScriptActionPickMod() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionPickMod(ScriptContext context) {
        super(context, "pickmod");
    }

    @Override
    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams,
            String[] params) {
        ReturnValue retVal = new ReturnValue(-1);

        String lastParam = provider.expand(macro, params[params.length - 1], false).toLowerCase();
        boolean pickInCreative = false;

        int idsLength = params.length;

        if (lastParam.equals("true") || lastParam.equals("false")) {
            pickInCreative = lastParam.equals("true");
            idsLength = params.length - 1;
        }

        for (int paramIndex = 0; paramIndex < idsLength; paramIndex++) {
            String itemIDString = provider.expand(macro, params[paramIndex], false);
            ItemID itemId = new ItemID(itemIDString);

            if (itemId.inventoryPick(pickInCreative)) {
                retVal.setString(itemId.identifier);
                break;
            }
        }

        return retVal;
    }

    @Override
    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
