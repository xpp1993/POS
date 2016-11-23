package com.lxkj.administrator.pos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences�ļ��洢������
 * 
 * @author stefan
 *
 */
public class SharedPreferencesUtil {
	private SharedPreferences sp;

	/**
	 * ��ʱ����xml
	 */

	public SharedPreferencesUtil(Context context, String fileName) {
		this.sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
	}

	/**
	 * ���SharedPreferences���������
	 */
	public void clearSPValue() {
		if (!AppUtils.isEmpty(sp)) {
			Editor editor = sp.edit();
			editor.clear();
			editor.commit();
		}
	}

	/**
	 * ��������
	 * 
	 * @param key
	 *            �ڵ�����
	 * @param value
	 *            �ڵ�ֵ?
	 * @return
	 */
	public boolean saveSPValue(String key, Object value) {
		if (AppUtils.isEmpty(sp)) {
			return false;
		}
		Editor editor = sp.edit();
		if (value instanceof Integer) {
			try {

				Integer i = Integer.parseInt(value.toString());
				editor.putInt(key, i);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, Boolean.parseBoolean(value.toString()));
		} else if (value instanceof Float) {
			try {
				Float f = Float.parseFloat(value.toString());
				editor.putFloat(key, f);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else if (value instanceof Long) {
			try {
				Long l = Long.parseLong(value.toString());
				editor.putLong(key, l);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return editor.commit();
	}

	/**
	 * ��ȡxml�ļ��ڵ�ֵ?
	 * 
	 * @param key
	 *            �ڵ�����
	 * @param defValue
	 *            ȱʡֵ?
	 * @return
	 */
	public Object getSPValue(String key, Object defValue) {
		if (AppUtils.isEmpty(sp) || AppUtils.isEmpty(key)) {
			throw new RuntimeException(
					"<T> Object com.sythealth.fitness.util.SharedPreferencesUtil.getSPValue��������Ϊnull�쳣");
		}
		if (defValue instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defValue);
		}
		if (defValue instanceof String || AppUtils.isEmpty(defValue)) {
			return sp.getString(key, defValue + "");
		}
		if (defValue instanceof Integer) {
			return sp.getInt(key, (Integer) defValue);
		}
		if (defValue instanceof Float) {
			return sp.getFloat(key, (Float) defValue);
		}
		if (defValue instanceof Long) {
			return sp.getLong(key, (Long) defValue);
		}
		return null;
	}
}
