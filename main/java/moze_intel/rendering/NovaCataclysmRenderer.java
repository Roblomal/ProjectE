package moze_intel.rendering;

import moze_intel.gameObjs.ObjHandler;
import moze_intel.gameObjs.entity.NovaCataclysmPrimed;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NovaCataclysmRenderer extends Render
{
	private RenderBlocks blockRenderer = new RenderBlocks();

    public NovaCataclysmRenderer()
    {
        this.shadowSize = 0.5F;
    }
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float f1, float f2)
    {
        this.doRender((NovaCataclysmPrimed) entity, x, y, z, f1, f2);
    }

    public void doRender(NovaCataclysmPrimed entity, double x, double y, double z, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        float f2;

        if ((float) entity.fuse - par9 + 1.0F < 10.0F)
        {
            f2 = 1.0F - ((float) entity.fuse - par9 + 1.0F) / 10.0F;

            if (f2 < 0.0F)
                f2 = 0.0F;

            if (f2 > 1.0F)
                f2 = 1.0F;

            f2 *= f2;
            f2 *= f2;
            float f3 = 1.0F + f2 * 0.3F;
            GL11.glScalef(f3, f3, f3);
        }

        f2 = (1.0F - ((float) entity.fuse - par9 + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entity);
        this.blockRenderer.renderBlockAsItem(ObjHandler.novaCataclysm, 0, entity.getBrightness(par9));

        if (entity.fuse / 5 % 2 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
            this.blockRenderer.renderBlockAsItem(ObjHandler.novaCataclysm, 0, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
    }

	protected ResourceLocation getEntityTexture(NovaCataclysmPrimed entity) 
	{
		return TextureMap.locationBlocksTexture;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((NovaCataclysmPrimed) entity);
    }
}
