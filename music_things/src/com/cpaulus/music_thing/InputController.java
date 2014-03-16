package com.cpaulus.music_thing;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputProcessor;
import com.cpaulus.music_thing.Cells.Note;


public class InputController implements InputProcessor, GestureListener {

	private World _world;
	private EntityCamera _camera;
	private GameUI _gameUI;
	private EntityCamera _uiCam;

	public InputController(World w, EntityCamera c, GameUI ui, EntityCamera uiCam) {
		_world = w;
		_camera = c;
		_gameUI = ui;
		_uiCam = uiCam;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.LEFT)
			_camera.getVelocity().x = -100;
		else if(keycode == Keys.RIGHT)
			_camera.getVelocity().x = 100;
		else if(keycode == Keys.UP)
			_camera.getVelocity().y = 100;
		else if (keycode == Keys.DOWN)
			_camera.getVelocity().y = -100;
		return true;
		
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.LEFT)
			_camera.getVelocity().x = 0;
		else if(keycode == Keys.RIGHT)
			_camera.getVelocity().x = 0;
		else if(keycode == Keys.UP)
			_camera.getVelocity().y = 0;
		else if (keycode == Keys.DOWN)
			_camera.getVelocity().y = 0;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		float cur = _camera.getZoom();
		_camera.setZoom( cur + amount * cur / 10);
		
		return true;
	}
	

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		
		Vector2 uiPos = _uiCam.UiToWorld(new Vector2(x, y));
		
		Vector2 pos = getCellPos(x, y);
		int i = 0;
		ArrayList<Cell> c = _world.getCells((int)pos.x, (int)pos.y);
		
		
		
		while(i < c.size() && c.get(i) instanceof Note)
			i++;
		
		if(i < c.size()) {
			Cell cell = c.get(i);
			if(cell.isSelected())
				cell.rotate();
			_gameUI.setSelectedCell(cell);
			
		}
		else {
			Cell cell = _world.addCell(_gameUI.getType(), (int)pos.x, (int)pos.y);
			_gameUI.setSelectedCell(cell);
		}
				
		return true;
	}
	
	private Vector2 getCellPos(float x, float y) {
		Vector2 pos = _camera.UiToWorld(new Vector2(x, y));
		if(pos.x < 0)
			pos.x -= Cell.CELL_WIDTH;
		if(pos.y < 0)
			pos.y -= Cell.CELL_HEIGHT;
		pos.x = pos.x / Cell.CELL_WIDTH;
		pos.y = pos.y / Cell.CELL_HEIGHT;
		return pos;
	}

	@Override
	public boolean longPress(float x, float y) {
		Vector2 pos = getCellPos(x, y);
		ArrayList<Cell> c = _world.getCells((int)pos.x, (int)pos.y);
		for(Cell a : c) {
			_world.removeCell(a);
			_gameUI.setSelectedCell(null);
		}
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//We have to inverse x
		_camera.getVelocity().x = -deltaX*10;
		_camera.getVelocity().y = deltaY*10;
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		_camera.getVelocity().x = 0;
		_camera.getVelocity().y = 0;
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		
			
		float cur = _camera.getZoom();
		if(distance > initialDistance) {
			_camera.setZoom(cur - 0.1f * cur / 10);
		} else {
			_camera.setZoom(cur + 0.1f * cur / 10);
		}
		
		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
