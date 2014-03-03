package com.cpaulus.music_thing;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class EntityCamera {
	private OrthographicCamera _camera;
	private Vector2 _velocity;
	
	public EntityCamera(int width, int height) {
		_camera = new OrthographicCamera(width, height);
		_camera.update();
		_velocity = Vector2.Zero;
	}
	
	public void resize(int width, int height) {
		_camera.setToOrtho(false);
		_camera.update();
	}
	
	public void update(float frametime) {
		float cur = getZoom()*5;
		_camera.translate(_velocity.x * frametime * cur, _velocity.y * frametime * cur);
		_camera.update();
		
	}
	
	public OrthographicCamera getUnderlyingCamera() {
		return _camera;
	}
	
	public Vector2 getVelocity() {
		return _velocity;
	}
	
	public void setZoom(float val) {
		_camera.zoom = val;
	}
	
	public float getZoom() {
		return _camera.zoom;	
	}
	
	public Vector2 UiToWorld(Vector2 ui) {
		Vector3 rslt = new Vector3(ui.x, ui.y, 0);
		_camera.unproject(rslt);
		return new Vector2(rslt.x, rslt.y);
	}
	
	public Vector2 WorldToUi(Vector2 world) {
		Vector3 rslt = new Vector3(world.x, world.y, 0);
		_camera.project(rslt);
		return new Vector2(rslt.x, rslt.y); 
	}
	
}
