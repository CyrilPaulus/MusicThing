package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;

public class NoteDown extends Cell
{
	public NoteDown(World w) {
		super(w);
		_sprite = new Sprite(new Texture(Gdx.files.internal("data/down.png")));
		 _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
		 _type = Type.DOWN;
	}
	
	public boolean consume(Cell c) {
		if(c instanceof Note)
			((Note)c).down();
		return true;
	}
	
	public void rotate() {
		return;
	}

}
