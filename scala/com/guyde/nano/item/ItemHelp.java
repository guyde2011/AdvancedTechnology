package com.guyde.nano.item;

import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.IEnergyContainerItem;

import com.google.common.collect.Multimap;


public class ItemHelp extends net.minecraft.item.Item implements IEnergyContainerItem{
	public UUID getUUID(){
		return this.field_111210_e;
	}
	public ItemHelp(){
		super();
	    this.maxStackSize = 1;
	    	    this.setCreativeTab(CreativeTabs.tabTools);
	}
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		// TODO Auto-generated method stub
		return super.getAttributeModifiers(stack);
	}
	
	public Item abc(){
		return this;
	}
	
	public int getEnergyStored(ItemStack container) {
		// TODO Auto-generated method stub
		return EnergyHelper.getEnergy(container);
	}
	@Override
	public int getMaxEnergyStored(ItemStack container) {
		// TODO Auto-generated method stub
		return EnergyHelper.getCapacity(container);
	}
	
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(EnergyHelper.getCapacity(container) - energy, Math.min(5000, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(5000, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyExtracted;
	}


	

}