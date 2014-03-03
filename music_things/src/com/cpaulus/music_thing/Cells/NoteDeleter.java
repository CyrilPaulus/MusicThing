package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;


public class NoteDeleter extends Cell {
	
	public NoteDeleter(World w) {
		super(w);
		_sprite = new Sprite(new Texture(Gdx.files.internal("data/deleter.png")));
		 _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
		 _type = Type.DELETER;
	}
	
	public boolean consume(Cell c) {
		if(c instanceof Note) {
			_world.removeCell(c);
		}
		return true;
	}
	
	public void rotate( ) {
		return;
	}

}
