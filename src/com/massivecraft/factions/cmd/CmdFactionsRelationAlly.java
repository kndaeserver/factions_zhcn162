package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;

public class CmdFactionsRelationAlly extends CmdFactionsRelationAbstract
{
	public CmdFactionsRelationAlly()
	{
		this.addAliases("����");
		
		this.targetRelation = Rel.ALLY;
	}
}
