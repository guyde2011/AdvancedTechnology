package com.guyde.nano.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class NanoItemTool extends Item implements INanoItem
{
	public ToolData toolData;
	private final String Name;
	private final String TexName;
    public NanoItemTool(ToolData td , String name , String tex_name) 
    {
        this.maxStackSize = 1;
        toolData = td;
        this.setCreativeTab(CreativeTabs.tabTools);
        Name = name;
        TexName = tex_name;
        this.setUnlocalizedName(name);
    }
    
    public void reg(){
        ItemRegistry.regItem(this);
    }
    public NanoItemTool(ToolData td , String name) 
    {
    	this(td , name , name);
    }

    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        return 0F;
    }
    
    
    
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
        return toolData.hitEntity(p_77644_1_, p_77644_2_ , p_77644_3_);
    }

    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        if ((double)p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D)
        {
            return toolData.onBlockDestroyed(p_150894_1_, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
        }

        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return toolData.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName()
    {
        return toolData.getName();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
    	return toolData.isRepairableWith(p_82789_1_, p_82789_2_);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", toolData.getDamage(stack), 0));
        return multimap;
    }


    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
    	if (getToolClasses(stack).contains(toolClass)){
            return toolData.getMineLevel(stack , toolClass);
    	}
    	return 0;

    }
    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return toolData.asToolClasses(stack);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
        if (ForgeHooks.isToolEffective(stack, block, meta))
        {
            return toolData.getSpeed(stack);
        }
        return super.getDigSpeed(stack, block, meta);
    }

	@Override
	public boolean isItemTool(ItemStack p_77616_1_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String UnlocalName() {
		// TODO Auto-generated method stub
		return Name;
	}

	@Override
	public String TextureName() {
		// TODO Auto-generated method stub
		return TexName;
	}

	@Override
	public Item getItemRef() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void registerIcons(IIconRegister ir) {
		// TODO Auto-generated method stub
		this.itemIcon = ir.registerIcon("advtech:" + TextureName());
	}
}
