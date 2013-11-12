package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FPerm;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.cmd.arg.ARUPlayer;
import com.massivecraft.factions.cmd.req.ReqFactionsEnabled;
import com.massivecraft.factions.cmd.req.ReqHasFaction;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.event.FactionsEventInvitedChange;
import com.massivecraft.mcore.cmd.arg.ARBoolean;
import com.massivecraft.mcore.cmd.req.ReqHasPerm;
import com.massivecraft.mcore.cmd.req.ReqIsPlayer;

public class CmdFactionsInvite extends FCommand
{
	public CmdFactionsInvite()
	{
		this.addAliases("inv", "����","�������");
		
		this.addRequiredArg("�����");
		this.addOptionalArg("��/��", "toggle");
		
		this.addRequirements(ReqFactionsEnabled.get());
		this.addRequirements(ReqHasPerm.get(Perm.INVITE.node));
		this.addRequirements(ReqHasFaction.get());
		this.addRequirements(ReqIsPlayer.get());
	}
	
	@Override
	public void perform()
	{
		// Args
		UPlayer uplayer = this.arg(0, ARUPlayer.getStartAny(sender));
		if (uplayer == null) return;
		
		Boolean newInvited = this.arg(1, ARBoolean.get(), !usenderFaction.isInvited(uplayer));
		if (newInvited == null) return;
		
		// Allready member?
		if (uplayer.getFaction() == usenderFaction)
		{
			msg("%s<i> �Ѿ��� %s ����ĳ�Ա", uplayer.getName(), usenderFaction.getName());
			msg("<i>����Ҫ: " + Factions.get().getOuterCmdFactions().cmdFactionsKick.getUseageTemplate(false));
			return;
		}
		
		// FPerm
		if ( ! FPerm.INVITE.has(usender, usenderFaction, true)) return;
		
		// Event
		FactionsEventInvitedChange event = new FactionsEventInvitedChange(sender, uplayer, usenderFaction, newInvited);
		event.run();
		if (event.isCancelled()) return;
		newInvited = event.isNewInvited();

		// Apply
		usenderFaction.setInvited(uplayer, newInvited);
		
		// Inform
		if (newInvited)
		{
			uplayer.msg("%s<i> ��������� %s", usender.describeTo(uplayer, true), usenderFaction.describeTo(uplayer));
			usenderFaction.msg("%s<i> ���� %s<i> ������Ĺ���.", usender.describeTo(usenderFaction, true), uplayer.describeTo(usenderFaction));
		}
		else
		{
			uplayer.msg("%s<i> ����������� <h>%s<i>.", usender.describeTo(uplayer), usenderFaction.describeTo(uplayer));
			usenderFaction.msg("%s<i> ���� %s's<i> ����.", usender.describeTo(usenderFaction), uplayer.describeTo(usenderFaction));
		}
	}
	
}
