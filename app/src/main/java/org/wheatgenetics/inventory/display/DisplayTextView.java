package org.wheatgenetics.inventory.display;

/**
 * Uses:
 * android.annotation.SuppressLint
 * android.content.Context
 * android.graphics.Color
 * android.support.v7.widget.AppCompatTextView
 * android.view.Gravity
 * android.view.ViewGroup.LayoutParams
 * android.widget.TableRow.LayoutParams
 */
@android.annotation.SuppressLint({"ViewConstructor"})
class DisplayTextView extends android.support.v7.widget.AppCompatTextView
{
    DisplayTextView(final android.content.Context context, final java.lang.CharSequence text,
    final float initWeight)
    {
        super(context);

        this.setGravity  (android.view.Gravity.CENTER | android.view.Gravity.BOTTOM);
        this.setTextColor(android.graphics.Color.BLACK                             );
        this.setTextSize (20.0f                                                    );
        this.setText     (text                                                     );
        this.setLayoutParams(new android.widget.TableRow.LayoutParams(
            /* w          => */0,
            /* h          => */ android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            /* initWeight => */ initWeight));
    }

    DisplayTextView(final android.content.Context context, final java.lang.CharSequence text)
    { this(context, text,0.16f); }
}