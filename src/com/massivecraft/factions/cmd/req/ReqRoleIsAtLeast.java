package com.massivecraft.factions.cmd.req;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.mcore.cmd.MCommand;
import com.massivecraft.mcore.cmd.req.ReqAbstract;
import com.massivecraft.mcore.util.Txt;

public class ReqRoleIsAtLeast extends ReqAbstract
{
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final Rel rel;
	public Rel getRel() { return this.rel; }
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	public static ReqRoleIsAtLeast get(Rel rel) { return new ReqRoleIsAtLeast(rel); }
	private ReqRoleIsAtLeast(Rel rel) { this.rel = rel; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(CommandSender sender, MCommand command)
	{
		UPlayer uplayer = UPlayer.get(sender);
		return uplayer.getRole().isAtLeast(this.rel);
	}
	
	@Override
	public String createErrorMessage(CommandSender sender, MCommand command)
	{
		return Txt.parse("<b>你必须是 <h>%s <b>或者更高 "+(command == null ? "才能这么做" : command.getDesc())+".", Txt.getNicedEnum(this.rel));
	}
	
}
