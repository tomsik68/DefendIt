package sk.jasbar.defendit.engine.render;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import sk.jasbar.defendit.DefendItGame;
import sk.jasbar.defendit.engine.IRenderable;
import sk.tomsik68.gamedev.engine3d.Renderer;
import static org.lwjgl.opengl.GL11.*;

public class LightingRenderable implements IRenderable {
	private FloatBuffer lightPos0;

	public LightingRenderable() {
		lightPos0 = BufferUtils.createFloatBuffer(4);
		lightPos0.put(new float[] { 0.f, 0.f, 0.f, 1.f });
		lightPos0.flip();
	}

	@Override
	public void render(DefendItGame defendItGame,
			ICameraCoordsProvider camCoords) {
		
		lightPos0.put(new float[] { -camCoords.getCamX(), -camCoords.getCamY()+100, -camCoords.getCamZ(), 1.f });
		lightPos0.flip();
		
		glLight(GL_LIGHT0, GL_POSITION, lightPos0);

	}

}
