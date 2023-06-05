package com.cpaulus.music_thing.Cells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cpaulus.music_thing.Cell;
import com.cpaulus.music_thing.World;

public class Note extends Cell {

    public static Sound cNote = Gdx.audio.newSound(Gdx.files.internal("data/A.mp3"));
    public static float SPEED = 10;

    private int MAX_LIVES = 50;
    private int _lives = 0;
    private int n = 0;
    private boolean _enabled = false;
    private float _acc = 0;

    //init colors
    //SO CLEAN
    private static Color COLORS[] = {
            new Color(174/255.f ,0   ,0   ,255),
            new Color(207/255.f ,0   ,0   ,255),
            new Color(255/255.f ,0   ,0   ,255),
            new Color(255/255.f ,102/255.f ,0   ,255),
            new Color(255/255.f ,239/255.f ,0   ,255),
            new Color(153/255.f ,255/255.f ,0   ,255),
            new Color(40/255.f  ,255/255.f ,0   ,255),
            new Color(0   ,255/255.f ,242/255.f ,255),
            new Color(0   ,122/255.f ,255/255.f ,255),
            new Color(5/255.f   ,0   ,255/255.f ,255),
            new Color(71/255.f  ,0   ,237/255.f ,255),
            new Color(99/255.f  ,0   ,178/255.f ,255),
    };

    public void addPitch(int amount) {
        n += amount;
        int index;
        if(n >= 0)
            index = (n + 3) % 12;
        else
            index = (n + (-n / 12 + 1) * 12 + 3) % 12;

        _sprite.setColor(COLORS[index]);
    }

    public void up() {
        n++;
        int index;
        if(n >= 0)
            index = (n + 3) % 12;
        else
            index = (n + (-n / 12 + 1) * 12 + 3) % 12;

        _sprite.setColor(COLORS[index]);
    }

    public void down() {
        n--;
        int index;
        if(n >= 0)
            index = (n + 3) % 12;
        else
            index = (n + (-n / 12 + 1) * 12 + 3) % 12;

        _sprite.setColor(COLORS[index]);
    }
    public Note(World w) {
        super(w);
        _sprite = new Sprite(new Texture(Gdx.files.internal("data/note.png")));
        _sprite.setOrigin(CELL_WIDTH / 2.f, CELL_HEIGHT / 2.f);
        _type = Type.NOTE;
        _sprite.setColor(COLORS[3]);
    }

    @Override
    public void update(float frametime) {
        int rotation = getRotation();
        _acc += frametime;
        if (_enabled) {
            _enabled = false;

            int xPos = getX();
            int yPos = getY();

            if (rotation == 0)
                xPos++;
            else if (rotation == 1)
                yPos++;
            else if (rotation == 2)
                xPos--;
            else if (rotation == 3)
                yPos--;
            setPosition(xPos, yPos);

            for (Cell d : _world.getCells(getX(), getY())) {
                if(d.consume(this))
                    _lives = 0;
            }

            _lives++;
            _acc = 0;
        }

        //Speed 1 cell / 0.1s >= 10cell/s >= 10 * CELL_SIZE /s


        if(rotation == 0)
            _sprite.setX(getX() * CELL_WIDTH + SPEED*CELL_WIDTH * _acc);
        else if(rotation == 1)
            _sprite.setY(getY() * CELL_HEIGHT + SPEED*CELL_HEIGHT * _acc);
        else if(rotation == 2)
            _sprite.setX(getX() * CELL_WIDTH - SPEED*CELL_WIDTH * _acc);
        else if(rotation == 3)
            _sprite.setY(getY() * CELL_HEIGHT - SPEED*CELL_HEIGHT * _acc);

        if(_lives > MAX_LIVES)
            _world.removeCell(this);

    }

    public void play() {
        float pitch = (float)Math.pow(1.059463094359, n);

        cNote.play(1.0f, pitch, 0);
    }

    public void setEnabled(boolean enabled) {
        _enabled = enabled;
    }


}
