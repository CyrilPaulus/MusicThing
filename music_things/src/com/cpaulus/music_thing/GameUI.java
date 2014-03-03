package com.cpaulus.music_thing;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cpaulus.music_thing.Cell.Type;

public class GameUI {
	
	private ArrayList<Sprite> _buttons;
	private int _width;
	private int _height;
	private SpriteBatch _batch;
	private int _selectIndex = 0;
	private Sprite _select;
	
	private ArrayList<Cell.Type> _types;
	
	public GameUI(int width,int height) {
		resize(width, height);
		_buttons = new ArrayList<Sprite>();
		_types = new ArrayList<Cell.Type>();
		_types.add(Type.NOTE_GENERATOR);
		_buttons.add(new Sprite(new Texture(Gdx.files.internal("data/note_generator.png"))));
		_types.add(Type.PLAYER);
		_buttons.add(new Sprite(new Texture(Gdx.files.internal("data/player.png"))));
		_types.add(Type.SPLITTER);
		_buttons.add(new Sprite(new Texture(Gdx.files.internal("data/splitter.png"))));
		_types.add(Type.ROTATOR);
		_buttons.add(new Sprite(new Texture(Gdx.files.internal("data/rotator.png"))));
		_types.add(Type.UP);
		_buttons.add(new Sprite(new Texture(Gdx.files.internal("data/up.png"))));
		_types.add(Type.DOWN);
		_buttons.add(new Sprite(new Texture(Gdx.files.internal("data/down.png"))));
		_types.add(Type.DELETER);
		_buttons.add(new Sprite(new Texture(Gdx.files.internal("data/deleter.png"))));
		
		_select = new Sprite(new Texture(Gdx.files.internal("data/select.png")));
		_batch = new SpriteBatch();
	}
	
	public Type getType() {
		return _types.get(_selectIndex);
	}
	
	public void resize(int width, int height) {
		_width = width;
		_height = height;
	}	

	public boolean click(float x, float y) {
		float startY = 0;
		float startX = (_width - _buttons.size() * Cell.CELL_WIDTH) / 2;
		float width = (_buttons.size() * Cell.CELL_WIDTH);
		float height = Cell.CELL_HEIGHT;
		
		if(x >= startX && x <= startX + width && y >= startY && y <= startY + height ) {
			_selectIndex = (int)((x - startX) / Cell.CELL_WIDTH);
			return true;
		}
		return false;
	}
	
	public void draw(EntityCamera cam) {
		float startY = -_height / 2;
		float startX =- (_buttons.size() * Cell.CELL_WIDTH) / 2;
		
		_batch.setProjectionMatrix(cam.getUnderlyingCamera().projection);
		_batch.begin();
		for(int i = 0; i < _buttons.size(); i++) {
			Sprite s = _buttons.get(i);
			s.setPosition(startX + i * Cell.CELL_WIDTH, startY);
			s.draw(_batch);			
		}
		_select.setPosition(startX + _selectIndex * Cell.CELL_WIDTH, startY);
		_select.draw(_batch);
		_batch.end();
	}
}
