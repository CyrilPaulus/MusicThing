package com.cpaulus.music_thing;

import java.security.acl.NotOwnerException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.omg.stub.java.rmi._Remote_Stub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cpaulus.music_thing.Cell.Type;
import com.cpaulus.music_thing.Cells.Note;
import com.cpaulus.music_thing.Cells.NoteGenerator;
import com.cpaulus.music_thing.Cells.NotePlayer;

public class World {
	
	private Cell _bkgCell;
	//TODO: update to a map of map or something similar
	private ArrayList<Cell> _cells;
	private LinkedList<Cell> _notes;
	private ArrayList<Cell> _toRemove;
	private SpriteBatch _batch;
	private float _acc = 0;
	private float _noteAcc = 0;
	
	
	public World() {
		_bkgCell = Cell.CreateCell(Cell.Type.EMPTY, this);
		_batch = new SpriteBatch();
		_cells = new ArrayList<Cell>();
		_toRemove = new ArrayList<Cell>();
		_notes = new LinkedList<Cell>();
	}
	
	public Cell addCell(Cell.Type type, int x, int y) {
		Cell c = Cell.CreateCell(type, this);
		c.setPosition(x, y);
		
		if(type == Type.NOTE)
			_notes.add(c);
		else
			_cells.add(c);
		return c;
	}
	
	public ArrayList<Cell> getCells(int x, int y) {
		ArrayList<Cell> rtn = new ArrayList<Cell>();
		for (Cell c : _cells) {
			if(c.getX() == x && c.getY() == y)
				rtn.add(c);
		}		
		return rtn;
	}
	
	public void removeCells(int x, int y) {
		ArrayList<Cell> cells = getCells(x, y);
		for(Cell c : cells) {
			removeCell(c);
		}
	}
	
	public void removeCell(Cell c) {
		_toRemove.add(c);
	}
	
	public void update(float frametime) {
		boolean enabled = false;
		boolean noteEnabled = false;
		
		_acc += frametime;
		_noteAcc += frametime;
		//Tries to sync starter,
		if(_acc > 0.5) {
			enabled = true;
			_acc = _acc - 0.5f;
		}
		
		if(_noteAcc > 1 / Note.SPEED) {
			noteEnabled = true;
			_noteAcc = _noteAcc - 1 / Note.SPEED;
		}
		
		for(Cell n : _notes) {
			 if(noteEnabled && n instanceof Note)
				((Note)n).setEnabled(true);
			
			n.update(frametime);
		}
		
		for(int i = _cells.size() - 1; i >= 0; i--) {
			Cell c = _cells.get(i);
			if(enabled && c instanceof NoteGenerator)
				((NoteGenerator)c).setEnabled(true);
			
			
			c.update(frametime);
			
		}
		
		for(Cell r : _toRemove) {
			if(r instanceof Note)
				_notes.remove(r);
			else
				_cells.remove(r);
		}
		_toRemove.clear();
	}
	
	public void render(EntityCamera c, float frametime) {
		//First render background grid
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		int width = (int)(c.getUnderlyingCamera().viewportWidth * c.getZoom()) / Cell.CELL_WIDTH;
		int height = (int)(c.getUnderlyingCamera().viewportHeight * c.getZoom()) / Cell.CELL_HEIGHT;
		
		int startX = (int)c.getUnderlyingCamera().position.x / Cell.CELL_WIDTH;
		int startY = (int)c.getUnderlyingCamera().position.y / Cell.CELL_HEIGHT;
		
		_batch.setProjectionMatrix(c.getUnderlyingCamera().combined);
		_batch.begin();
		for(int x = -1; x <= width+1; x++) {
			for(int y = -1; y <= height+1; y++) {
				_bkgCell.setPosition(startX + x - width / 2, startY + y - height / 2);
				_bkgCell.draw(_batch);
				
			}
		}
		
		for(Cell note : _notes)
			note.draw(_batch);
		
		for(Cell cell : _cells)
			cell.draw(_batch);
		
		_batch.end();
		
		
	}

}
