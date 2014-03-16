package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

public class NotePitch extends Cell implements IHasParameter {

	private int _amount = 1;
	
	
	
	public NotePitch(World w) {
		super(w);
		_sprite = new Sprite(new Texture(Gdx.files.internal("data/pitch.png")));
		 _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
		 _type = Type.PITCH;
		 setParamater(1);
	}
	
	public boolean consume(Cell c) {
		if(c instanceof Note)
			((Note)c).addPitch(_amount);
		return true;
	}
	
	public void rotate() {
		return;
	}

	@Override
	public int getParameter() {
		return _amount;
	}

	@Override
	public void setParamater(int param) {
		_amount = param;
		
		if(_amount > 0)
			_text = "+" + _amount;
		else if(_amount < 0)
			_text = "" + _amount;
		else
			_text = "";
	}

	
}
