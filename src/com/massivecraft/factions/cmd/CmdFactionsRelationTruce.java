package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;

public class CmdFactionsRelationTruce extends CmdFactionsRelationAbstract
{
	public CmdFactionsRelationTruce()
	{
		this.addAliases("ÐÝÕ½");
		
		this.targetRelation = Rel.TRUCE;
	}
}
