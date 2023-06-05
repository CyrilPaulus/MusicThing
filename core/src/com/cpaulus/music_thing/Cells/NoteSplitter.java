package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;

public class NoteSplitter extends Cell implements IHasParameter {

    int _tresh = 1;
    int _count = 1;

    public NoteSplitter(World w) {
        super(w);
        _sprite = new Sprite(new Texture(Gdx.files.internal("data/splitter.png")));
        _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
        _type = Type.SPLITTER;
        setParameter(1);
    }

    public boolean consume(Cell c) {

        _count++;

        if(_count > _tresh) {
            _count = 1;
            this.rotate(2);
        }
        _text = _count + "/" + _tresh;
        c.setRotation(this.getRotation());
        return true;
    }

    @Override
    public int getParameter() {
        return _tresh;
    }

    @Override
    public void setParameter(int param) {
        _count = 1;
        if(param >= 1)
            _tresh = param;
        _text = _count + "/" + _tresh;


    }

}
