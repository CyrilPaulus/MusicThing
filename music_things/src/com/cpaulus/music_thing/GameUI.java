package com.cpaulus.music_thing;

import java.awt.Label;
import java.io.Console;
import java.util.ArrayList;

import sun.nio.cs.ext.ISCII91;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cpaulus.music_thing.Cell.Type;
import com.cpaulus.music_thing.Cells.IHasParameter;

public class GameUI {
	
	
	//For my cool UI
	private Stage _stage;	

	private int _width;
	private int _height;

	private Cell.Type _selectedType;
	private TextField _field;
	BitmapFont _font = new BitmapFont(Gdx.files.internal("data/default.fnt"));
	
	ImageButton _btPlus = CreatePanelButton("data/plus.png");
	ImageButton _btMinus = CreatePanelButton("data/minus.png");
	
	private ArrayList<Cell.Type> _types;
	
	private Cell _selectedCell = null;
	
	private ImageButton CreateButton(String file) {
		Texture t = new Texture(Gdx.files.internal(file));
		TextureRegion r = new TextureRegion(t);
		
		Pixmap base = new Pixmap(Gdx.files.internal(file));
		Pixmap overlay = new Pixmap(Gdx.files.internal("data/select.png"));
		Texture rslt = new Texture(t.getWidth(), t.getHeight(), Pixmap.Format.RGBA8888);
		base.drawPixmap(overlay, 0, 0);
		rslt.draw(base, 0, 0);
		TextureRegion sr = new TextureRegion(rslt);
		
	
		ImageButton rtn = new ImageButton(new TextureRegionDrawable(r), new TextureRegionDrawable(sr), new TextureRegionDrawable(sr));
		return rtn;
	}
	
	private ImageButton CreatePanelButton(String file) {
		Pixmap base = new Pixmap(Gdx.files.internal(file));
		Pixmap up = new Pixmap(Gdx.files.internal("data/empty.png"));
		Pixmap down = new Pixmap(Gdx.files.internal("data/deleter.png"));
		
		Texture upTxt = new Texture(base.getWidth(), base.getHeight(), Pixmap.Format.RGBA8888);
		up.drawPixmap(base, 0, 0);
		upTxt.draw(up, 0, 0);
		TextureRegion upR = new TextureRegion(upTxt);
		
		Texture downTxt = new Texture(base.getWidth(), base.getHeight(), Pixmap.Format.RGBA8888);
		down.drawPixmap(base, 0, 0);
		downTxt.draw(down, 0, 0);
		TextureRegion downR = new TextureRegion(downTxt);
		
		ImageButton rtn = new ImageButton(new TextureRegionDrawable(upR), new TextureRegionDrawable(downR));
		return rtn;
	}
	
	private com.esotericsoftware.tablelayout.Cell CreateTypeButton(String file, final Cell.Type t, Table table, ButtonGroup group) {
		ImageButton btn = CreateButton(file);
		group.add(btn);		
		
		btn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(((ImageButton)actor).isChecked()) {
					_selectedType = t;
				}	
			}
		});
		return table.add(btn);
	}
	
	private void SetText(TextField tf, BitmapFont f, String text) {
		float spaceWidth = f.getSpaceWidth();
		float textWidth = f.getBounds(text).width;
		
		float diff = (tf.getStyle().background.getMinWidth() - textWidth);
		
		for(float i = 0; i < diff / 2; i += spaceWidth) {
			text = " " + text;
		}
		tf.setText(text);

	}
	
	public GameUI(int width,int height) {
		
		_btPlus.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				IncParameter();
			}
		});
		
		_btMinus.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				DecParameter();
			}
		});
				
		
		_stage = new Stage();
		
		TextureRegion bkg = new TextureRegion(new Texture(Gdx.files.internal("data/empty.png")));
		TextFieldStyle tfs = new TextFieldStyle();
		tfs.font = _font;
		tfs.background = new TextureRegionDrawable(bkg);
		tfs.fontColor = new Color(0.76f,0.76f,0.76f,1.0f);
		
		_field = new TextField("Test", tfs);
		SetText(_field, _font, "test");
			
		ButtonGroup group = new ButtonGroup();
		
		Table table = new Table();
		table.setFillParent(true);
		table.debug();
		table.add().expandY();
		table.row();
		
		table.add(_btPlus);
		table.row();
		table.add(_field).width(64);
		table.row();
		
		table.add(_btMinus);
		table.row();
		table.add().expandY();
		table.row();
		
		table.center().bottom();
						
		_stage.addActor(table);
			
		table.add();
		table.add().expandX();
		CreateTypeButton("data/note_generator.png", Cell.Type.NOTE_GENERATOR, table, group);
		CreateTypeButton("data/player.png", Cell.Type.PLAYER, table, group);
		CreateTypeButton("data/splitter.png", Cell.Type.SPLITTER, table, group);
		CreateTypeButton("data/rotator.png", Cell.Type.ROTATOR, table, group);
		CreateTypeButton("data/pitch.png", Cell.Type.PITCH, table, group);
		CreateTypeButton("data/deleter.png", Cell.Type.DELETER, table, group);
		table.add().expandX();
		group.setMaxCheckCount(1);
		
		_selectedType = Cell.Type.NOTE_GENERATOR;

		
		resize(width, height);
		
	
		setSelectedCell(null);
		
			
	}
	
	public Stage getStage() {
		return _stage;
	}
	
	public Type getType() {
		return _selectedType;
	}
	
	public void resize(int width, int height) {
		_width = width;
		_height = height;
		_stage.setViewport(width, height, true);
	}	


	public void draw(EntityCamera cam) {		
		_stage.act(Gdx.graphics.getDeltaTime());
		_stage.draw();
		Table.drawDebug(_stage);
	}
	
	public void setSelectedCell(Cell cell) {
	
		if(_selectedCell != null)
			_selectedCell.setSelected(false);
		
		if(cell instanceof IHasParameter) {
			IHasParameter instance = (IHasParameter)cell;
			String val = "" + instance.getParameter();
			SetText(_field, _font, val);
			_btMinus.setDisabled(false);
			_btPlus.setDisabled(false);						
		} else {
			_btMinus.setDisabled(true);
			_btPlus.setDisabled(true);
			SetText(_field, _font, "");
		}
		
		
		_selectedCell = cell;
		if(_selectedCell != null)
			_selectedCell.setSelected(true);
		
		
	}
	
	private void IncParameter() {
		IHasParameter instance = (IHasParameter)_selectedCell;
		instance.setParamater(instance.getParameter() + 1);	
		String val = "" + instance.getParameter();
		SetText(_field, _font, val);
	}
	
	private void DecParameter() {
		IHasParameter instance = (IHasParameter)_selectedCell;
		instance.setParamater(instance.getParameter() - 1);
		String val = "" + instance.getParameter();
		SetText(_field, _font, val);
	}
}
