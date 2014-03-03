package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;
import com.cpaulus.music_thing.Cell.Type;

public class NoteSplitter extends Cell {


	public NoteSplitter(World w) {
		super(w);
		_sprite = new Sprite(new Texture(Gdx.files.internal("data/splitter.png")));
		 _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
		 _type = Type.SPLITTER;
	}
	
	public boolean consume(Cell c) {
		c.setRotation(this.getRotation());
		this.rotate(2);
		return true;
	}
	
}
