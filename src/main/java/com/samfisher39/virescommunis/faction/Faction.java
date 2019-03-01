package com.samfisher39.virescommunis.faction;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Faction {
	
	private String name;
	private ArrayList<UUID> membersUUIDList = new ArrayList<UUID>();
	private ArrayList<String> membersNameList = new ArrayList<String>();
	private ArrayList<UUID> adminUUIDList = new ArrayList<UUID>();
	public GameMaster gameMaster;
	public UUID adminUUID;
	
	public Faction(String nameString, UUID adminUUID){
		//World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
		this.setName(nameString);
		this.membersUUIDList.add(adminUUID);
		this.adminUUIDList.add(adminUUID);
		this.adminUUID = adminUUID;
		//this.membersNameList.add(world.getPlayerEntityByUUID(adminUUID).getName());
		this.gameMaster = new GameMaster(adminUUID);
	}
	
	public Faction(UUID adminUUID){
		this(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getPlayerEntityByUUID(adminUUID).getName().concat("'s Faction!"), adminUUID);
	}
	
	public EntityPlayer GetAdmin() {
		if (Minecraft.getMinecraft().world.isRemote) {
			System.out.println("WORLD IS REMOTE");
			return Minecraft.getMinecraft().world.getPlayerEntityByUUID(this.adminUUIDList.get(0));
		}
		System.out.println("WORLD IS NOT REMOTE");
		return null;
	}
	
	public ArrayList<String> GetMembers()
	{
		return membersNameList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void KickPlayer(EntityPlayerMP player)
	{
		UUID uuid = player.getUniqueID();
		KickPlayer(uuid);
	}
	
	public void KickPlayer(UUID uuid)
	{
		this.adminUUIDList.remove(uuid);
		this.membersUUIDList.remove(uuid);
		this.membersNameList.remove(Minecraft.getMinecraft().world.getPlayerEntityByUUID(uuid).getName());
	}
	
	public boolean ContainsPlayer(EntityPlayerMP player)
	{
		if (membersUUIDList.contains(player.getUniqueID())) {
			return true;
		} else {
			return false;
		}
	}
}
