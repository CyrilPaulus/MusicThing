package com.cpaulus.music_thing;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.cpaulus.music_thing.Cells.Note;
import com.cpaulus.music_thing.Cells.NoteDeleter;
import com.cpaulus.music_thing.Cells.NoteDown;
import com.cpaulus.music_thing.Cells.NoteGenerator;
import com.cpaulus.music_thing.Cells.NotePlayer;
import com.cpaulus.music_thing.Cells.NoteRotator;
import com.cpaulus.music_thing.Cells.NoteSplitter;
import com.cpaulus.music_thing.Cells.NoteUp;

public class Cell {

	
	public static int CELL_WIDTH = 64;
	public static int CELL_HEIGHT = 64;
	private int _rotation = 0; // 0 LEFT, 1; UP, ..
	protected Type _type;
	protected World _world;
	private int _posX = 0;
	private int _posY = 0;
	
	public enum Type {
		EMPTY,
		NOTE_GENERATOR,
		NOTE,
		PLAYER,
		SPLITTER,
		ROTATOR,
		UP,
		DOWN,
		DELETER
	}
	
	protected Sprite _sprite;
	
	protected Cell(World w) {		
		 _sprite = new Sprite(new Texture(Gdx.files.internal("data/empty.png")));
		 _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
		 _type = Type.EMPTY;
		 _world = w;
	}
	
	public void setPosition(int x, int y) {
		_posX = x;
		_posY = y;
		_sprite.setPosition(x * CELL_WIDTH, y * CELL_HEIGHT);
	}
	
	public void draw(SpriteBatch batch) {
		_sprite.draw(batch);
	}
	
	public int getX() {
		return _posX;
	}
	
	public int getY() {
		return _posY;
	}
	
	public static Cell CreateCell(Type type, World world) {
		Cell c = null;
		switch(type) {
		case NOTE_GENERATOR:
			c = new NoteGenerator(world);
			break;
		case EMPTY:
			c = new Cell(world);
			break;
		case NOTE:
			c = new Note(world);
			break;
		case PLAYER:
			c = new NotePlayer(world);
			break;
		case SPLITTER:
			c = new NoteSplitter(world);
			break;
		case ROTATOR:
			c = new NoteRotator(world);
			break;
		case DOWN:
			c = new NoteDown(world);
			break;
		case UP:
			c = new NoteUp(world);
			break;
		case DELETER:
			c = new NoteDeleter(world);
			break;
		}
		return c;		
	
	}
	
	public void rotate(int inc) {		
		_rotation = (_rotation + inc) % 4;
		float diff = _sprite.getRotation() - _rotation * 90;
		_sprite.rotate(-diff);		
	}
	
	public void rotate() {
		rotate(1);	
	}
	
	public void setRotation(int i) {
	    _rotation = i % 4;
		float diff = _sprite.getRotation() - _rotation * 90;
		_sprite.rotate(-diff);	
	}
	
	public int getRotation() {
		return _rotation;
	}
	
	public void update(float frametime) {
		
	}
	
	public boolean consume(Cell c) {
		return false;

	}


}

