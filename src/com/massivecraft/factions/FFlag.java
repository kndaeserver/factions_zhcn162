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
	PERMANENT("����", "<i>���ù����޷�����ɢ.", false),
	PEACEFUL("��ƽ", "<i>����������ʼ�մ�����ս״̬.", false),
	INFPOWER("��������", "<i>�����ǿ��Ը���Ĺ�����������.", false),
	// This faction has infinite power: TODO: Add faction has enough method. Replace the permanentpower level 
	
	// (Faction) Territory flags
	// If a faction later could have many different territories this would probably be in another enum
	POWERLOSS("��������", "<i>������������Ƿ��������ֵ?", true),
	PVP("pvp", "<i>��������Ƿ����PVP?", true),
	FRIENDLYFIRE("�����˺�", "<i>�Ƿ��������˺�?", false),
	MONSTERS("����", "<i>������Ƿ�����ˢ��?", true),
	EXPLOSIONS("��ը", "<i>������Ƿ�����ը?", true),
	OFFLINE_EXPLOSIONS("���߱�ը", "<i>�Ƿ�����������ʱ������ը?", false),
	FIRESPREAD("��������", "<i>������Ƿ������������?", true),
	ENDERGRIEF("ˢ��С��", "<i>�Ƿ�����С����������ε�?", false),
	
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
		String ret = (value ? "<g>��" : "<b>��") + "<c> " + this.getNicename();
		if (withDesc)
		{
			ret += " " + this.getDescription();
		}
		return ret;
	}
	
}
