package com.realapps.engine.core.util.physics;

import java.util.HashMap;
import java.util.LinkedList;

import android.graphics.Rect;

public class CollisionChecker {
	public static abstract class Collisionable {
		protected Object mTag = "";
		protected Rect mBox;
		
		/*
		 * Tag�� Collisionable ��ü�� ������ ������ �ٴ°����� �����ϸ��
		 * ex) �ش� ��ü�� "Drawable ID"�Ǵ�, ��ü ��ü ���
		 * ���߿� "����"�� �浹�ߴ��� �˻��Ҷ� ����
		 */
		public void setTag(Object tag) {
			mTag = tag;
		}
		public Object getTag() {
			return mTag;
		}
		
		public void setCollisionBox(int left, int top, int right, int bottom) {
			setCollisionBox(new Rect(left, top, right, bottom));
		}
		public void setCollisionBox(Rect rect) {
			mBox = rect;
		}
		
		public abstract void onIntersect(Collisionable object);
	}
	
	private HashMap<Collisionable, LinkedList<Collisionable>> mCheckList = new HashMap<Collisionable, LinkedList<Collisionable>>();
	public void add(Collisionable object, Collisionable collisionable) {
		LinkedList<Collisionable> data = new LinkedList<Collisionable>();
		data.add(collisionable);
		
		add(object, data);
	}
	public void add(Collisionable object, LinkedList<Collisionable> collisionable) {
		if(mCheckList.containsKey(object)) {
			mCheckList.get(object).addAll(collisionable);
		} else {
			mCheckList.put(object, collisionable);
		}
	}
	public void remove(Collisionable object, Collisionable collisionable) {
		LinkedList<Collisionable> data = new LinkedList<Collisionable>();
		data.add(collisionable);
		
		remove(object, data);
	}
	public void remove(Collisionable object, LinkedList<Collisionable> collisionable) {
		mCheckList.get(object).removeAll(collisionable);
	}
	public void clear(Collisionable object) {
		mCheckList.remove(object);
	}
	
	protected void check() {
		for(Collisionable target: mCheckList.keySet()) {
			for(Collisionable object: mCheckList.get(target)) {
				if(target.mBox.intersect(object.mBox)) {
					target.onIntersect(object);
				}
			}
		}
	}
}
