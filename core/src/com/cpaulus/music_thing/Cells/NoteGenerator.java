package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;

public class NoteGenerator extends Cell {

    private boolean _enabled = false;
    //Direct start
    private float _acc = 0;
    private float bpm = 180;

    public NoteGenerator(World w) {
        super(w);
        _sprite = new Sprite(new Texture(Gdx.files.internal("data/note_generator.png")));
        _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
        _type = Type.NOTE_GENERATOR;
    }

    @Override
    public void update(float frametime) {
        _acc += frametime;
        if(_acc > 60 / bpm && _enabled) {
            _acc = (60 / bpm) - _acc;

            Cell c = _world.addCell(Type.NOTE, getX(), getY());
            c.setRotation(this.getRotation());

        }
    }

    public void setEnabled(boolean enabled) {
        _enabled = enabled;
        //Direct start
        if(_enabled = true)
            _acc = 60 / bpm + 1;
    }

}

