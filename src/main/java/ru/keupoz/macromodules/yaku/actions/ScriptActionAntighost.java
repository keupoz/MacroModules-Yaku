package ru.keupoz.macromodules.yaku.actions;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.ReturnValue;
import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import ru.keupoz.macromodules.yaku.ModuleInfo;

@APIVersion(ModuleInfo.API_VERSION)
public class ScriptActionAntighost extends ScriptAction {
    public ScriptActionAntighost() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionAntighost(ScriptContext context) {
        super(context, "antighost");
    }

    @Override
    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams,
            String[] params) {
        ReturnValue retVal = new ReturnValue(false);

        Minecraft mc = Minecraft.getMinecraft();
        NetHandlerPlayClient connection = mc.getConnection();

        if (connection != null) {
            EntityPlayerSP player = mc.player;
            BlockPos pos = player.getPosition();

            for (int dx = -4; dx <= 4; dx++) {
                for (int dy = -4; dy <= 4; dy++) {
                    for (int dz = -4; dz <= 4; dz++) {
                        CPacketPlayerDigging packet = new CPacketPlayerDigging(
                                CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos.add(dx, dy, dz), EnumFacing.UP);

                        connection.sendPacket(packet);
                    }
                }
            }

            retVal.setBool(true);
        }

        return retVal;
    }

    @Override
    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
