package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Rel;

public class CmdFactionsRelationEnemy extends CmdFactionsRelationAbstract
{
	public CmdFactionsRelationEnemy()
	{
		this.addAliases("�ж�");
		
		this.targetRelation = Rel.ENEMY;
	}
}
