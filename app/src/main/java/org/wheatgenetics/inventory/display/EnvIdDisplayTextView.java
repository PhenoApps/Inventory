package org.wheatgenetics.inventory.display;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.Context
 * android.view.View.OnLongClickListener
 *
 * org.wheatgenetics.inventory.display.DisplayTextView
 */
@android.annotation.SuppressLint({"ViewConstructor"})
class EnvIdDisplayTextView extends org.wheatgenetics.inventory.display.DisplayTextView
{
    EnvIdDisplayTextView(final android.content.Context context, final java.lang.CharSequence text,
    final java.lang.Object tag, final android.view.View.OnLongClickListener onLongClickListener)
    {
        super(context, text,0.5f);

        this.setTag(tag); this.setLongClickable(true);
        this.setOnLongClickListener(onLongClickListener);
    }
}