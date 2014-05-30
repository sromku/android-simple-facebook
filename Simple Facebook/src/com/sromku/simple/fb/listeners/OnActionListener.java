package com.sromku.simple.fb.listeners;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.sromku.simple.fb.actions.Cursor;

public abstract class OnActionListener<T> implements OnThinkingListetener {

	private Cursor<T> mCursor;

	public OnActionListener() {
	}

	public void onComplete(T response) {
	}

	@Override
	public void onException(Throwable throwable) {
	}

	@Override
	public void onFail(String reason) {
	}

	@Override
	public void onThinking() {
	}

	public void setCursor(Cursor<T> cursor) {
		mCursor = cursor;
	}

	/**
	 * Return <code>True</code> if there is another next page with more results.
	 * You can iterate to the next page and get more results by calling to
	 * {@link #getNext()} method.
	 * 
	 * @return <code>True</code> if more results exist.
	 */
	public boolean hasNext() {
		if (mCursor != null) {
			return mCursor.hasNext();
		}
		return false;
	}

	/**
	 * Return <code>True</code> if there is another previous page with more
	 * results. You can iterate to the next page and get more results by calling
	 * to {@link #getPrev()} method.
	 * 
	 * @return <code>True</code> if more results exist.
	 */
	public boolean hasPrev() {
		if (mCursor != null) {
			return mCursor.hasPrev();
		}
		return false;
	}

	/**
	 * Ask for the next page results in async way. When the response will arrive
	 * {@link #onComplete(Object)} method will be invoked again.
	 */
	public void getNext() {
		if (mCursor != null) {
			mCursor.next();
		}
	}

	/**
	 * Ask for the prev page results in async way. When the response will arrive
	 * {@link #onComplete(Object)} method will be invoked again.
	 */
	public void getPrev() {
		if (mCursor != null) {
			mCursor.prev();
		}
	}

	/**
	 * Get the cursor that actually does the 'getMore()' action. For example, if
	 * you want to hold this instance of cursor somewhere in your app and only
	 * in some point of time to use it.
	 * 
	 * @return {@link Cursor} for iteration over pages of response.
	 */
	public Cursor<T> getCursor() {
		return mCursor;
	}

	/**
	 * Get the last page number that was retrieved.
	 * 
	 * @return The page number.
	 */
	public int getPageNum() {
		return mCursor.getPageNum();
	}

	public Type getGenericType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return parameterizedType.getActualTypeArguments()[0];
	}

}
