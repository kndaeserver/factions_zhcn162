package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FPerm;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.arg.ARFaction;
import com.massivecraft.factions.cmd.req.ReqFactionsEnabled;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UConf;
import com.massivecraft.factions.event.FactionsEventHomeChange;
import com.massivecraft.mcore.cmd.req.ReqHasPerm;
import com.massivecraft.mcore.cmd.req.ReqIsPlayer;
import com.massivecraft.mcore.ps.PS;

public class CmdFactionsSethome extends FCommand
{
	public CmdFactionsSethome()
	{
		this.addAliases("sethome","����Ϊ��");
		
		this.addOptionalArg("Ĭ�Ϲ���", "���ڵ�");
		
		this.addRequirements(ReqFactionsEnabled.get());
		this.addRequirements(ReqIsPlayer.get());
		this.addRequirements(ReqHasPerm.get(Perm.SETHOME.node));
	}
	
	@Override
	public void perform()
	{
		// Args
		Faction faction = this.arg(0, ARFaction.get(usenderFaction), usenderFaction);
		if (faction == null) return;
		
		PS newHome = PS.valueOf(me.getLocation());
		
		// Validate
		if ( ! UConf.get(faction).homesEnabled)
		{
			usender.msg("<b>�Բ���,��������ֹ����سǵ㹦��.");
			return;
		}
		
		// FPerm
		if ( ! FPerm.SETHOME.has(usender, faction, true)) return;
		
		// Verify
		if (!usender.isUsingAdminMode() && !faction.isValidHome(newHome))
		{
			usender.msg("<b>�Բ���,��ֻ����������������ù���سǵ�.");
			return;
		}
		
		// Event
		FactionsEventHomeChange event = new FactionsEventHomeChange(sender, faction, newHome);
		event.run();
		if (event.isCancelled()) return;
		newHome = event.getNewHome();

		// Apply
		faction.setHome(newHome);
		
		// Inform
		faction.msg("%s<i> ��������Ĺ���سǵ�.�����ڿ���ʹ�ûس�����:", usender.describeTo(usenderFaction, true));
		faction.sendMessage(Factions.get().getOuterCmdFactions().cmdFactionsHome.getUseageTemplate());
		if (faction != usenderFaction)
		{
			usender.msg("<b>�������� "+faction.getName(usender)+"<i> ����Ļسǵ�.");
		}
	}
	
}
