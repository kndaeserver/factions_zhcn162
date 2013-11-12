package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Perm;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.cmd.arg.ARUPlayer;
import com.massivecraft.factions.cmd.req.ReqFactionsEnabled;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.mcore.cmd.req.ReqHasPerm;

public class CmdFactionsDemote extends FCommand
{
	
	public CmdFactionsDemote()
	{
		this.addAliases("贬职","降低等级");
		
		this.addRequiredArg("玩家名");
		
		this.addRequirements(ReqFactionsEnabled.get());
		this.addRequirements(ReqHasPerm.get(Perm.DEMOTE.node));
		
		//To demote someone from member -> recruit you must be an officer.
		//To demote someone from officer -> member you must be a leader.
		//We'll handle this internally
	}
	
	@Override
	public void perform()
	{	
		UPlayer you = this.arg(0, ARUPlayer.getStartAny(usender));
		if (you == null) return;
		
		if (you.getFaction() != usenderFaction)
		{
			msg("%s<b> 不是你的公会成员.", you.describeTo(usender, true));
			return;
		}
		
		if (you == usender)
		{
			msg("<b>对象不能是你自己.");
			return;
		}

		if (you.getRole() == Rel.MEMBER)
		{
			if (!usender.getRole().isAtLeast(Rel.OFFICER))
			{
				msg("<b>你必须是公会助理才能把会员降级为新兵.");
				return;
			}
			you.setRole(Rel.RECRUIT);
			usenderFaction.msg("%s<i> 被降级为新兵.", you.describeTo(usenderFaction, true));
		}
		else if (you.getRole() == Rel.OFFICER)
		{
			if (!usender.getRole().isAtLeast(Rel.LEADER))
			{
				msg("<b>你必须是公会会长才能把公会助理降级为会员.");
				return;
			}
			you.setRole(Rel.MEMBER);
			usenderFaction.msg("%s<i> 被降级为会员.", you.describeTo(usenderFaction, true));
		}
	}
	
}
