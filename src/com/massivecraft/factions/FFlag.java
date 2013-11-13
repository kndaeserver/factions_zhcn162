package com.massivecraft.factions;

import java.util.LinkedHashMap;
import java.util.Map;

import com.massivecraft.factions.entity.UConf;


/**
 * Flags that describe the nature of a faction and it's territory.
 * Can monsters spawn there? May fire spread etc? Is the faction permanent?
 * These flags have nothing to do with player-permission.
 * 
 * The flags are either true or false.
 */
public enum FFlag
{
	// -------------------------------------------- //
	// ENUM
	// -------------------------------------------- //
	
	// Faction flags
	PERMANENT("永久", "<i>永久公会无法被解散.", false),
	PEACEFUL("和平", "<i>与其他公会始终处于休战状态.", false),
	INFPOWER("无限能量", "<i>这个标记可以给你的公会无限能量.", false),
	// This faction has infinite power: TODO: Add faction has enough method. Replace the permanentpower level 
	
	// (Faction) Territory flags
	// If a faction later could have many different territories this would probably be in another enum
	POWERLOSS("能量掉落", "<i>在领地上死亡是否掉落能量值?", true),
	PVP("pvp", "<i>在领地上是否可以PVP?", true),
	FRIENDLYFIRE("队友伤害", "<i>是否开启队友伤害?", false),
	MONSTERS("怪物", "<i>领地上是否允许刷怪?", true),
	EXPLOSIONS("爆炸", "<i>领地上是否允许爆炸?", true),
	OFFLINE_EXPLOSIONS("离线爆炸", "<i>是否允许公会离线时发生爆炸?", false),
	FIRESPREAD("火势蔓延", "<i>领地上是否允许火焰蔓延?", true),
	ENDERGRIEF("刷新小黑", "<i>是否允许小黑在领地内游荡?", false),
	
	// END OF LIST
	;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final String nicename;
	public String getNicename() { return this.nicename; }
	
	private final String desc;
	public String getDescription() { return this.desc; }
	
	public final boolean defaultDefault;
	public boolean getDefaultDefault() { return this.defaultDefault; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	private FFlag(String nicename, final String desc, boolean defaultDefault)
	{
		this.nicename = nicename;
		this.desc = desc;
		this.defaultDefault = defaultDefault;
	}
	
	// -------------------------------------------- //
	// DEFAULTS
	// -------------------------------------------- //
	
	public boolean getDefault(Object o)
	{
		Boolean ret = UConf.get(o).defaultFactionFlags.get(this);
		if (ret == null) return this.getDefaultDefault();
		return ret; 
	}
	
	public static Map<FFlag, Boolean> getDefaultDefaults()
	{
		Map<FFlag, Boolean> ret = new LinkedHashMap<FFlag, Boolean>();
		for (FFlag flag : values())
		{
			ret.put(flag, flag.getDefaultDefault());
		}
		return ret;
	}
	
	// -------------------------------------------- //
	// PARSE
	// -------------------------------------------- //
	
	public static FFlag parse(String str)
	{
		str = str.toLowerCase();
		if (str.startsWith("per")) return PERMANENT;
		if (str.startsWith("pea")) return PEACEFUL;
		if (str.startsWith("i")) return INFPOWER;
		if (str.startsWith("pow")) return POWERLOSS;
		if (str.startsWith("pvp")) return PVP;
		if (str.startsWith("fr") || str.startsWith("ff")) return FRIENDLYFIRE;
		if (str.startsWith("m")) return MONSTERS;
		if (str.startsWith("ex")) return EXPLOSIONS;
		if (str.startsWith("o")) return OFFLINE_EXPLOSIONS;
		if (str.startsWith("fi")) return FIRESPREAD;
		if (str.startsWith("en")) return ENDERGRIEF;		
		return null;
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public String getStateInfo(boolean value, boolean withDesc)
	{
		String ret = (value ? "<g>是" : "<b>否") + "<c> " + this.getNicename();
		if (withDesc)
		{
			ret += " " + this.getDescription();
		}
		return ret;
	}
	
}
