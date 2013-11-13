package com.massivecraft.factions;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UConf;
import com.massivecraft.mcore.ps.PS;
import com.massivecraft.mcore.util.Txt;

/**
 * Permissions that you (a player) may or may not have in the territory of a certain faction.
 * Each faction have many Rel's assigned to each one of these Perms. 
 */
public enum FPerm
{
	// -------------------------------------------- //
	// ENUM
	// -------------------------------------------- //
	
	BUILD(true, "build", "编辑地形",              Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.ALLY),
	PAINBUILD(true, "painbuild", "编辑, 破坏"),
	DOOR(true, "door", "使用门",                       Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY),
	BUTTON(true, "button", "使用按钮",           Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY),
	LEVER(true, "lever", "使用拉杆",                    Rel.LEADER, Rel.OFFICER, Rel.MEMBER, Rel.RECRUIT, Rel.ALLY),
	CONTAINER(true, "container", "使用容器",        Rel.LEADER, Rel.OFFICER, Rel.MEMBER),
	
	INVITE(false, "invite", "邀请玩家",             Rel.LEADER, Rel.OFFICER),
	KICK(false, "kick", "开除会员",                   Rel.LEADER, Rel.OFFICER),
	SETHOME(false, "sethome", "设置回城点",             Rel.LEADER, Rel.OFFICER),
	WITHDRAW(false, "withdraw", "取钱",         Rel.LEADER, Rel.OFFICER),
	TERRITORY(false, "territory", "圈地或放弃领地",     Rel.LEADER, Rel.OFFICER),
	ACCESS(false, "access", "grant territory",            Rel.LEADER, Rel.OFFICER),
	DISBAND(false, "disband", "解散公会",      Rel.LEADER),
	PERMS(false, "perms", "管理权限",           Rel.LEADER),
	
	// END OF LIST
	;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final boolean territoryPerm;
	public boolean isTerritoryPerm() { return this.territoryPerm; }
	
	private final String nicename;
	public String getNicename() { return this.nicename; }
	
	private final String desc;
	public String getDescription() { return this.desc; }
	
	public final Set<Rel> defaultDefault;
	public Set<Rel> getDefaultDefault() { return new LinkedHashSet<Rel>(this.defaultDefault); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	private FPerm(boolean territoryPerm, final String nicename, final String desc, final Rel... rels)
	{
		this.territoryPerm = territoryPerm;
		this.nicename = nicename;
		this.desc = desc;
		
		Set<Rel> defaultDefaultValue = new LinkedHashSet<Rel>();
		defaultDefaultValue.addAll(Arrays.asList(rels));
		defaultDefaultValue = Collections.unmodifiableSet(defaultDefaultValue);
		this.defaultDefault = defaultDefaultValue;
	}
	
	// -------------------------------------------- //
	// DEFAULTS
	// -------------------------------------------- //
	
	public Set<Rel> getDefault(Object o)
	{
		Set<Rel> ret = UConf.get(o).defaultFactionPerms.get(this);
		if (ret == null) return this.getDefaultDefault();
		ret = new LinkedHashSet<Rel>(ret);
		return ret;
	}
	
	public static Map<FPerm, Set<Rel>> getDefaultDefaults()
	{
		Map<FPerm, Set<Rel>> ret = new LinkedHashMap<FPerm, Set<Rel>>();
		for (FPerm fperm : values())
		{
			ret.put(fperm, fperm.getDefaultDefault());
		}
		return ret;
	}
	
	// -------------------------------------------- //
	// PARSE
	// -------------------------------------------- //

	public static FPerm parse(String str)
	{
		str = str.toLowerCase();
		if (str.startsWith("a"))   return ACCESS;
		if (str.startsWith("bui")) return BUILD;
		if (str.startsWith("pa"))  return PAINBUILD;
		if (str.startsWith("do"))  return DOOR;
		if (str.startsWith("but")) return BUTTON;
		if (str.startsWith("l"))   return LEVER;
		if (str.startsWith("co"))  return CONTAINER;
		if (str.startsWith("i"))   return INVITE;
		if (str.startsWith("k"))   return KICK;
		if (str.startsWith("s"))   return SETHOME;
		if (str.startsWith("w"))   return WITHDRAW;
		if (str.startsWith("t"))   return TERRITORY;
		if (str.startsWith("di"))  return DISBAND;
		if (str.startsWith("pe"))  return PERMS;
		return null;
	}
	
	// -------------------------------------------- //
	// HAS?
	// -------------------------------------------- //
	
	public String createDeniedMessage(UPlayer uplayer, Faction hostFaction)
	{
		String ret = Txt.parse("%s<b> 不允许你 %s<b>.", hostFaction.describeTo(uplayer, true), this.getDescription());
		if (Perm.ADMIN.has(uplayer.getPlayer()))
		{
			ret += Txt.parse("\n<i>你不能越权使用 " + Factions.get().getOuterCmdFactions().cmdFactionsAdmin.getUseageTemplate(false));
		}
		return ret;
	}
	
	public boolean has(Faction faction, Faction hostFaction)
	{
		Rel rel = faction.getRelationTo(hostFaction);
		return hostFaction.getPermittedRelations(this).contains(rel);
	}
	
	public boolean has(UPlayer uplayer, Faction hostFaction, boolean verboose)
	{
		if (uplayer.isUsingAdminMode()) return true;
		
		Rel rel = uplayer.getRelationTo(hostFaction);
		if (hostFaction.getPermittedRelations(this).contains(rel)) return true;
		
		if (verboose) uplayer.sendMessage(this.createDeniedMessage(uplayer, hostFaction));
		
		return false;
	}
	
	public boolean has(UPlayer uplayer, PS ps, boolean verboose)
	{
		if (uplayer.isUsingAdminMode()) return true;
		
		TerritoryAccess ta = BoardColls.get().getTerritoryAccessAt(ps);
		Faction hostFaction = ta.getHostFaction(ps);
		
		if (this.isTerritoryPerm())
		{
			Boolean hasTerritoryAccess = ta.hasTerritoryAccess(uplayer);
			if (hasTerritoryAccess != null)
			{
				if (verboose && !hasTerritoryAccess)
				{
					uplayer.sendMessage(this.createDeniedMessage(uplayer, hostFaction));
				}
				return hasTerritoryAccess;
			}
		}
		
		return this.has(uplayer, hostFaction, verboose);
	}

	// -------------------------------------------- //
	// UTIL: ASCII
	// -------------------------------------------- //
	
	public static String getStateHeaders()
	{
		String ret = "";
		for (Rel rel : Rel.values())
		{
			ret += rel.getColor().toString();
			ret += rel.toString().substring(0, 3);
			ret += " ";
		}
		
		return ret;
	}
	
	public String getStateInfo(Set<Rel> value, boolean withDesc)
	{
		String ret = "";
		
		for (Rel rel : Rel.values())
		{
			if (value.contains(rel))
			{
				ret += "<g>是";
			}
			else
			{
				ret += "<b>否";
			}
			ret += " ";
		}
		
		ret +="<c>"+this.getNicename();
		if (withDesc)
		{
			ret += " <i>" + this.getDescription(); 
		}
		return ret;
	}
	
}
