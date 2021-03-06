package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.arg.ARUPlayer;
import com.massivecraft.factions.cmd.req.ReqFactionsEnabled;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.mcore.cmd.req.ReqHasPerm;

public class CmdFactionsOfficer extends FCommand
{
	public CmdFactionsOfficer()
	{
		this.addAliases("公会助理","设置公会助理");
		
		this.addRequiredArg("玩家名");
		
		this.addRequirements(ReqFactionsEnabled.get());
		this.addRequirements(ReqHasPerm.get(Perm.OFFICER.node));
	}
	
	@Override
	public void perform()
	{
		UPlayer you = this.arg(0, ARUPlayer.getStartAny(sender));
		if (you == null) return;

		boolean permAny = Perm.OFFICER_ANY.has(sender, false);
		Faction targetFaction = you.getFaction();

		if (targetFaction != usenderFaction && !permAny)
		{
			msg("%s<b> 不是你的公会成员.", you.describeTo(usender, true));
			return;
		}
		
		if (usender != null && usender.getRole() != Rel.LEADER && !permAny)
		{
			msg("<b>你不是公会会长.");
			return;
		}

		if (you == usender && !permAny)
		{
			msg("<b>对象不能是你自己.");
			return;
		}

		if (you.getRole() == Rel.LEADER)
		{
			msg("<b>目标玩家是公会会长.必须先进行降级.");
			return;
		}

		if (you.getRole() == Rel.OFFICER)
		{
			// Revoke
			you.setRole(Rel.MEMBER);
			targetFaction.msg("%s<i> 不再担任你的公会助理.", you.describeTo(targetFaction, true));
			msg("<i>你取消了 %s<i> 的公会助理职务.", you.describeTo(usender, true));
		}
		else
		{
			// Give
			you.setRole(Rel.OFFICER);
			targetFaction.msg("%s<i> 晋升为你的公会助理.", you.describeTo(targetFaction, true));
			msg("<i>你晋升 %s<i> 成为公会助理.", you.describeTo(usender, true));
		}
	}
	
}
