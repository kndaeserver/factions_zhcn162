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
		this.addAliases("��ְ","���͵ȼ�");
		
		this.addRequiredArg("�����");
		
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
			msg("%s<b> ������Ĺ����Ա.", you.describeTo(usender, true));
			return;
		}
		
		if (you == usender)
		{
			msg("<b>�����������Լ�.");
			return;
		}

		if (you.getRole() == Rel.MEMBER)
		{
			if (!usender.getRole().isAtLeast(Rel.OFFICER))
			{
				msg("<b>������ǹ���������ܰѻ�Ա����Ϊ�±�.");
				return;
			}
			you.setRole(Rel.RECRUIT);
			usenderFaction.msg("%s<i> ������Ϊ�±�.", you.describeTo(usenderFaction, true));
		}
		else if (you.getRole() == Rel.OFFICER)
		{
			if (!usender.getRole().isAtLeast(Rel.LEADER))
			{
				msg("<b>������ǹ���᳤���ܰѹ���������Ϊ��Ա.");
				return;
			}
			you.setRole(Rel.MEMBER);
			usenderFaction.msg("%s<i> ������Ϊ��Ա.", you.describeTo(usenderFaction, true));
		}
	}
	
}
