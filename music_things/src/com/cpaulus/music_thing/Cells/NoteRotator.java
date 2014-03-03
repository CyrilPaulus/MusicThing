package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;

public class NoteRotator extends Cell{

	public NoteRotator(World w) {
		super(w);
		_sprite = new Sprite(new Texture(Gdx.files.internal("data/rotator.png")));
		 _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
		 _type = Type.ROTATOR;
	}
	
	public boolean consume(Cell c) {
		c.setRotation(this.getRotation());
		return true;
	}
}
