package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.arg.ARUPlayer;
import com.massivecraft.factions.cmd.req.ReqFactionsEnabled;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.mcore.cmd.req.ReqHasPerm;

public class CmdFactionsPromote extends FCommand
{
	public CmdFactionsPromote()
	{
		this.addAliases("提升","提升等级");
		
		this.addRequiredArg("玩家名");
		
		this.addRequirements(ReqFactionsEnabled.get());
		this.addRequirements(ReqHasPerm.get(Perm.PROMOTE.node));
		
		//To promote someone from recruit -> member you must be an officer.
		//To promote someone from member -> officer you must be a leader.
		//We'll handle this internally
	}
	
	@Override
	public void perform()
	{
		UPlayer you = this.arg(0, ARUPlayer.getStartAny(sender));
		if (you == null) return;
		
		if (you.getFaction() != usenderFaction)
		{
			msg("%s<b> 不是你的公会成员.", you.describeTo(usender, true));
			return;
		}
		
		if (you == usender)
		{
			msg("<b>提升成员不能是你自己.");
			return;
		}

		if (you.getRole() == Rel.RECRUIT)
		{
			if (!usender.getRole().isAtLeast(Rel.OFFICER))
			{
				msg("<b>你必须是公会助理才能提升别人成为会员.");
				return;
			}
			you.setRole(Rel.MEMBER);
			usenderFaction.msg("%s<i> 被提升为你的公会会员.", you.describeTo(usenderFaction, true));
		}
		else if (you.getRole() == Rel.MEMBER)
		{
			if (!usender.getRole().isAtLeast(Rel.LEADER))
			{
				msg("<b>你必须是公会会长才能提升别人成为公会助理.");
				return;
			}
			// Give
			you.setRole(Rel.OFFICER);
			usenderFaction.msg("%s<i> 被提升为你的公会助理.", you.describeTo(usenderFaction, true));
		}
	}
	
}
