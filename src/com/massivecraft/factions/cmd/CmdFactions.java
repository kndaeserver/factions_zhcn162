package com.massivecraft.factions.cmd;

import java.util.Collections;

import com.massivecraft.factions.ConfServer;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.mcore.cmd.HelpCommand;
import com.massivecraft.mcore.cmd.VersionCommand;

public class CmdFactions extends FCommand
{
	public CmdFactionsList cmdFactionsList = new CmdFactionsList();
	public CmdFactionsFaction cmdFactionsFaction = new CmdFactionsFaction();
	public CmdFactionsPlayer cmdFactionsPlayer = new CmdFactionsPlayer();
	public CmdFactionsJoin cmdFactionsJoin = new CmdFactionsJoin();
	public CmdFactionsLeave cmdFactionsLeave = new CmdFactionsLeave();
	public CmdFactionsHome cmdFactionsHome = new CmdFactionsHome();
	//public CmdFactionsMap cmdFactionsMap = new CmdFactionsMap();
	public CmdFactionsCreate cmdFactionsCreate = new CmdFactionsCreate();
	public CmdFactionsName cmdFactionsName = new CmdFactionsName();
	public CmdFactionsDescription cmdFactionsDescription = new CmdFactionsDescription();
	public CmdFactionsSethome cmdFactionsSethome = new CmdFactionsSethome();
	public CmdFactionsOpen cmdFactionsOpen = new CmdFactionsOpen();
	public CmdFactionsInvite cmdFactionsInvite = new CmdFactionsInvite();
	public CmdFactionsKick cmdFactionsKick = new CmdFactionsKick();
	public CmdFactionsTitle cmdFactionsTitle = new CmdFactionsTitle();
	public CmdFactionsPromote cmdFactionsPromote = new CmdFactionsPromote();
	public CmdFactionsDemote cmdFactionsDemote = new CmdFactionsDemote();
	public CmdFactionsOfficer cmdFactionsOfficer = new CmdFactionsOfficer();
	public CmdFactionsLeader cmdFactionsLeader = new CmdFactionsLeader();
	public CmdFactionsMoney cmdFactionsMoney = new CmdFactionsMoney();
	public CmdFactionsSeeChunk cmdFactionsSeeChunk = new CmdFactionsSeeChunk();
	public CmdFactionsClaim cmdFactionsClaim = new CmdFactionsClaim();
	public CmdFactionsAutoClaim cmdFactionsAutoClaim = new CmdFactionsAutoClaim();
	public CmdFactionsUnclaim cmdFactionsUnclaim = new CmdFactionsUnclaim();
	public CmdFactionsUnclaimall cmdFactionsUnclaimall = new CmdFactionsUnclaimall();
	public CmdFactionsAccess cmdFactionsAccess = new CmdFactionsAccess();
	public CmdFactionsRelationAlly cmdFactionsRelationAlly = new CmdFactionsRelationAlly();
	public CmdFactionsRelationTruce cmdFactionsRelationTruce = new CmdFactionsRelationTruce();
	public CmdFactionsRelationNeutral cmdFactionsRelationNeutral = new CmdFactionsRelationNeutral();
	public CmdFactionsRelationEnemy cmdFactionsRelationEnemy = new CmdFactionsRelationEnemy();
	public CmdFactionsPerm cmdFactionsPerm = new CmdFactionsPerm();
	public CmdFactionsFlag cmdFactionsFlag = new CmdFactionsFlag();
	public CmdFactionsDisband cmdFactionsDisband = new CmdFactionsDisband();
	public CmdFactionsAdmin cmdFactionsAdmin = new CmdFactionsAdmin();
	public CmdFactionsPowerBoost cmdFactionsPowerBoost = new CmdFactionsPowerBoost();
	//public VersionCommand cmdFactionsVersion = new VersionCommand(Factions.get(), Perm.VERSION.node, "v", "version");
	
	public CmdFactions()
	{
		this.aliases.addAll(ConfServer.baseCommandAliases);
		
		// remove any nulls from extra commas
		// TODO: When is this required? Should this be added to MCore?
		this.aliases.removeAll(Collections.singletonList(null));
		
		this.setDesc("公会基本命令");
		this.setHelp("公会管理与操作的所有命令如下。");
		
		this.addSubCommand(HelpCommand.get());
		this.addSubCommand(this.cmdFactionsList);
		this.addSubCommand(this.cmdFactionsFaction);
		this.addSubCommand(this.cmdFactionsPlayer);
		this.addSubCommand(this.cmdFactionsJoin);
		this.addSubCommand(this.cmdFactionsLeave);
		this.addSubCommand(this.cmdFactionsHome);
		//this.addSubCommand(this.cmdFactionsMap);
		this.addSubCommand(this.cmdFactionsCreate);
		this.addSubCommand(this.cmdFactionsName);
		this.addSubCommand(this.cmdFactionsDescription);
		this.addSubCommand(this.cmdFactionsSethome);
		this.addSubCommand(this.cmdFactionsOpen);
		this.addSubCommand(this.cmdFactionsInvite);
		this.addSubCommand(this.cmdFactionsKick);
		this.addSubCommand(this.cmdFactionsTitle);
		this.addSubCommand(this.cmdFactionsPromote);
		this.addSubCommand(this.cmdFactionsDemote);
		this.addSubCommand(this.cmdFactionsOfficer);
		this.addSubCommand(this.cmdFactionsLeader);
		this.addSubCommand(this.cmdFactionsMoney);
		this.addSubCommand(this.cmdFactionsSeeChunk);
		this.addSubCommand(this.cmdFactionsClaim);
		this.addSubCommand(this.cmdFactionsAutoClaim);
		this.addSubCommand(this.cmdFactionsUnclaim);
		this.addSubCommand(this.cmdFactionsUnclaimall);
		this.addSubCommand(this.cmdFactionsAccess);
		this.addSubCommand(this.cmdFactionsRelationAlly);
		this.addSubCommand(this.cmdFactionsRelationTruce);
		this.addSubCommand(this.cmdFactionsRelationNeutral);
		this.addSubCommand(this.cmdFactionsRelationEnemy);
		this.addSubCommand(this.cmdFactionsPerm);
		this.addSubCommand(this.cmdFactionsFlag);
		this.addSubCommand(this.cmdFactionsDisband);
		this.addSubCommand(this.cmdFactionsAdmin);
		this.addSubCommand(this.cmdFactionsPowerBoost);
		//this.addSubCommand(this.cmdFactionsVersion);
	}
	
	@Override
	public void perform()
	{
		this.getCommandChain().add(this);
		HelpCommand.getInstance().execute(this.sender, this.args, this.commandChain);
	}

}
