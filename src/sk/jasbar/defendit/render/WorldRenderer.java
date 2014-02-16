package sk.jasbar.defendit.render;

import sk.jasbar.defendit.DefendItGame;
import sk.jasbar.defendit.engine.IRenderable;
import sk.jasbar.defendit.engine.render.ICameraCoordsProvider;
import sk.jasbar.defendit.game.Blocks;
import sk.jasbar.defendit.game.World;

public class WorldRenderer implements IRenderable {
	private final World world;
	// 1000 = far clipping plane. Dalej nema vyznam renderovat, kedze to OpenGL
	// aj tak odsekne...
	private static final int renderDistance = (int) (100);

	public WorldRenderer(World world) {
		this.world = world;

		for (int x = 0; x < World.SIZE_X; ++x) {
			for (int z = 0; z < World.SIZE_Z; ++z) {
				for (int y = 0; y < World.SIZE_Y; ++y) {
					world.setVisibility(x, y, z, visibleTest(x,y,z));
				}
			}
		}

	}

	@Override
	public void render(DefendItGame game, ICameraCoordsProvider cam) {
		int xBegin = (int) Math.max(0, -cam.getCamX()
				/ BlockRenderer.BLOCK_SIZE - renderDistance / 2);
		int yBegin = (int) Math.max(0, -cam.getCamY()
				/ BlockRenderer.BLOCK_SIZE - renderDistance / 2);
		int zBegin = (int) Math.max(0, -cam.getCamZ()
				/ BlockRenderer.BLOCK_SIZE - renderDistance / 2);

		int xEnd = (int) Math.min(xBegin + renderDistance, World.SIZE_X - 1);
		int yEnd = (int) Math.min(yBegin + renderDistance, World.SIZE_Y - 1);
		int zEnd = (int) Math.min(zBegin + renderDistance, World.SIZE_Z - 1);

		for (int x = xBegin; x < xEnd; ++x) {
			for (int z = zBegin; z < zEnd; ++z) {
				for (int y = yBegin; y < yEnd; ++y) {
					if (Blocks.block(world.getBlockIdAt(x, y, z)).renders(
							world, x, y, z) && world.getVisibility(x, y, z)) {
						BlockRenderer renderer = BlockRendererRegistry.instance
								.getRenderer(world.getBlockIdAt(x, y, z));
						renderer.renderBlock(world, x, y, z);
					}
				}
			}
		}
	}

	private boolean visibleTest(int x, int y, int z) {
		if (x > 1 && x < World.SIZE_X - 1 && y > 1 && y < World.SIZE_Y - 1
				&& z > 1 && z < World.SIZE_Z - 1) {
			return (!Blocks.block(world.getBlockIdAt(x + 1, y, z)).renders(
					world, x + 1, y, z)
					|| !Blocks.block(world.getBlockIdAt(x - 1, y, z)).renders(
							world, x - 1, y, z)
					|| !Blocks.block(world.getBlockIdAt(x, y + 1, z)).renders(
							world, x, y + 1, z)
					|| !Blocks.block(world.getBlockIdAt(x, y - 1, z)).renders(
							world, x, y - 1, z)
					|| !Blocks.block(world.getBlockIdAt(x, y, z - 1)).renders(
							world, x, y, z - 1) || !Blocks.block(
					world.getBlockIdAt(x - 1, y, z + 1)).renders(world, x - 1,
					y, z + 1));
		} else
			return false;
	}
}
