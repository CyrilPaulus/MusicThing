package com.cpaulus.music_thing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	//private OrthographicCamera _camera;
	private EntityCamera _camera;
	//Dirty stuff
	private EntityCamera _uiCam;
	private InputController _ic;
	private World _world;
	private GameUI _ui;

	public GameScreen() {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();

		_camera = new EntityCamera(w, h);
		_uiCam = new EntityCamera(w, h);			
		
		_world = new World();
		_ui = new GameUI(w, h, _world);
		
		_ic = new InputController(_world, _camera, _ui, _uiCam);
		InputMultiplexer mult = new InputMultiplexer();
		mult.addProcessor(_ui.getStage());
		mult.addProcessor(_ic);
		mult.addProcessor(new GestureDetector(_ic));
	    Gdx.input.setInputProcessor(mult);
	    
	    FileHandle file = Gdx.files.local("save.txt");
	    if(file.exists())
	    	_world.Load(file);
	}

	@Override
	public void render(float delta) {
		// Update
		_camera.update(delta);

		// Draw
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		_world.update(delta);
		_world.render(_camera, delta);
		_ui.draw(_uiCam);
	}

	@Override
	public void resize(int width, int height) {
		_camera.resize(width, height);
		_uiCam.resize(width, height);
		
		_ui.resize(width, height);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		
		_ui = new GameUI(w, h, _world);		
	
		_ic = new InputController(_world, _camera, _ui, _uiCam);
		InputMultiplexer mult = new InputMultiplexer();
		mult.addProcessor(_ui.getStage());
		mult.addProcessor(_ic);
		mult.addProcessor(new GestureDetector(_ic));
		Gdx.input.setInputProcessor(mult);
	}

	@Override
	public void dispose() {

	}

}
