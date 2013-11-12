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
		this.addAliases("officer");
		
		this.addRequiredArg("player");
		
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
			msg("%s<b> ������Ĺ����Ա.", you.describeTo(usender, true));
			return;
		}
		
		if (usender != null && usender.getRole() != Rel.LEADER && !permAny)
		{
			msg("<b>�㲻�ǹ���᳤.");
			return;
		}

		if (you == usender && !permAny)
		{
			msg("<b>Ŀ�겻�������Լ�.");
			return;
		}

		if (you.getRole() == Rel.LEADER)
		{
			msg("<b>Ŀ���û��ǹ���᳤.�����Ƚ��н���.");
			return;
		}

		if (you.getRole() == Rel.OFFICER)
		{
			// Revoke
			you.setRole(Rel.MEMBER);
			targetFaction.msg("%s<i> ���ٵ�����Ĺ�������.", you.describeTo(targetFaction, true));
			msg("<i>��ȡ���� %s<i> �Ĺ�������ְ��.", you.describeTo(usender, true));
		}
		else
		{
			// Give
			you.setRole(Rel.OFFICER);
			targetFaction.msg("%s<i> ����Ϊ��Ĺ�������.", you.describeTo(targetFaction, true));
			msg("<i>����� %s<i> ��Ϊ��������.", you.describeTo(usender, true));
		}
	}
	
}
