package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;

public class CmdFactionsRelationTruce extends CmdFactionsRelationAbstract
{
	public CmdFactionsRelationTruce()
	{
		this.addAliases("��ս");
		
		this.targetRelation = Rel.TRUCE;
	}
}
