package com.edwardstlouis.client.widgets;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public class DissolveAnimation extends Animation {

	private final Widget w;
	boolean isFadeOut;

	private DissolveAnimation(Widget w, boolean isFadeOut) {
		this.w = w;
		this.isFadeOut = isFadeOut;
	}

	public static void fadeOut(Widget w) {
		fadeOut(w, 300);
	}

	public static void fadeOut(Widget w, int durationInMillis) {
		DissolveAnimation a = new DissolveAnimation(w, true);
		a.run(durationInMillis);
	}
	
	public static void fadeOutAndRemove(final Widget w, int durationInMillis) {
		DissolveAnimation a = new DissolveAnimation(w, true);
		a.run(durationInMillis);
		Timer t = new Timer() {
			@Override
			public void run() {
				// Turns on display:none.
				w.addStyleName("nodisplay");
				// Set the opacity back to normal so we can make it reappear if needed.
				w.getElement().getStyle().setProperty("opacity", "1");
			}
		};
		t.schedule(durationInMillis + 1);
	}
	
	public static void fadeIn(Widget w) {
		DissolveAnimation a = new DissolveAnimation(w, false);
		a.run(300);
	}

	@Override
	protected void onUpdate(double progress) {
		double opacity = 1.0;
		if (isFadeOut) {
			opacity = 1.0 - progress;
		} else {
			opacity = progress;
		}
		w.getElement().getStyle().setProperty("opacity", "" + (opacity));
	}

}
