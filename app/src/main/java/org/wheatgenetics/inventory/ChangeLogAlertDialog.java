package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.widget.LinearLayout
 * android.widget.LinearLayout.LayoutParams
 * android.widget.ScrollView
 * android.widget.TextView
 *
 * org.wheatgenetics.changelog.ChangeLog
 * org.wheatgenetics.changelog.ChangeLog.LineHandler
 */

class ChangeLogAlertDialog extends java.lang.Object
{
    class ScrollView extends java.lang.Object
    {
        class LineHandler extends java.lang.Object
        implements org.wheatgenetics.changelog.ChangeLog.LineHandler
        {
            // region Private Fields
            private final android.content.Context                  context, applicationContext;
            private final android.widget.LinearLayout              linearLayout               ;
            private final int                                      versionResId, contentResId ;
            private       android.widget.LinearLayout.LayoutParams layoutParams = null        ;
            // endregion

            // region Private Method
            private android.widget.TextView makeTextView()
            {
                if (this.layoutParams == null)
                {
                    this.layoutParams = new android.widget.LinearLayout.LayoutParams(
                        /* width  => */ android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                        /* height => */ android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
                    this.layoutParams.setMargins(
                        /* left   => */ 20,
                        /* top    => */  5,
                        /* right  => */ 20,
                        /* bottom => */  0);
                }

                final android.widget.TextView textView = new android.widget.TextView(this.context);
                textView.setLayoutParams(this.layoutParams);
                return textView;
            }
            // endregion

            // region Public Overridden Methods
            @java.lang.Override
            public void handleBlankLine()
            {
                final android.widget.TextView spacerTextView = this.makeTextView();
                assert spacerTextView != null;
                spacerTextView.setTextSize(5);
                spacerTextView.setText("\n");

                assert this.linearLayout != null;
                this.linearLayout.addView(spacerTextView);
            }

            @java.lang.Override
            public void handleVersionLine(final java.lang.String version)
            {
                final android.widget.TextView headerTextView = this.makeTextView();
                assert headerTextView != null;
                headerTextView.setTextAppearance(this.applicationContext, this.versionResId);
                headerTextView.setText(version);

                assert this.linearLayout != null;
                this.linearLayout.addView(headerTextView);
            }

            @java.lang.Override
            public void handleContentLine(final java.lang.String content)
            {
                final android.widget.TextView contentTextView = this.makeTextView();
                assert contentTextView != null;
                contentTextView.setTextAppearance(this.applicationContext, this.contentResId);
                contentTextView.setText(content);

                assert this.linearLayout != null;
                this.linearLayout.addView(contentTextView);
            }
            // endregion

            // region Package Constructor Method
            LineHandler(final android.content.Context context,
            final android.content.Context applicationContext,
            final android.widget.LinearLayout linearLayout, final int versionResId,
            final int contentResId)
            {
                super();

                assert context != null;
                this.context = context;

                assert applicationContext != null;
                this.applicationContext = applicationContext;

                assert linearLayout != null;
                this.linearLayout = linearLayout;

                this.versionResId = versionResId;
                this.contentResId = contentResId;
            }
            // endregion
        }

        // region Private Fields
        private final android.content.Context   context, applicationContext;
        private final java.io.InputStreamReader inputStreamReader          ;
        private final int                       versionResId, contentResId ;

        private android.widget.LinearLayout linearLayout = null;
        private android.widget.ScrollView   scrollView   = null;

        private org.wheatgenetics.inventory.ChangeLogAlertDialog.ScrollView.LineHandler
            lineHandler = null;
        private org.wheatgenetics.changelog.ChangeLog changeLog = null;
        // endregion

        // region Package Methods
        ScrollView(final android.content.Context context,
        final android.content.Context applicationContext,
        final java.io.InputStreamReader inputStreamReader, final int versionResId,
        final int contentResId)
        {
            super();

            assert context != null;
            this.context = context;

            assert applicationContext != null;
            this.applicationContext = applicationContext;

            assert inputStreamReader != null;
            this.inputStreamReader = inputStreamReader;

            this.versionResId = versionResId;
            this.contentResId = contentResId;
        }

        android.widget.ScrollView get() throws java.io.IOException
        {
            if (this.scrollView == null)
            {
                this.scrollView = new android.widget.ScrollView(this.context);
                this.scrollView.removeAllViews();

                if (this.linearLayout == null)
                    this.linearLayout = new android.widget.LinearLayout(this.context);
                this.scrollView.addView(this.linearLayout);
            }

            assert this.linearLayout != null;
            this.linearLayout.setOrientation(android.widget.LinearLayout.VERTICAL);

            if (this.changeLog == null)
            {
                if (this.lineHandler == null)
                    this.lineHandler =
                        new org.wheatgenetics.inventory.ChangeLogAlertDialog.ScrollView.LineHandler(
                            this.context, this.applicationContext, this.linearLayout,
                            this.versionResId, this.contentResId);
                this.changeLog = new org.wheatgenetics.changelog.ChangeLog(
                    this.inputStreamReader, this.lineHandler);
            }

            this.changeLog.iterate();                                  // throws java.io.IOException
            return this.scrollView;
        }
        // endregion
    }

    // region Private Fields
    // region scrollView Private Fields
    private final android.content.Context   context, applicationContext;
    private final java.io.InputStreamReader inputStreamReader          ;
    private final int                       versionResId, contentResId ;

    private org.wheatgenetics.inventory.ChangeLogAlertDialog.ScrollView scrollView = null;
    // endregion

    // region builder Private Fields
    private final android.content.Context activityClass            ;
    private final java.lang.CharSequence  title, positiveButtonText;

    private android.app.AlertDialog.Builder builder = null;
    // endregion

    private android.app.AlertDialog alertDialog = null;
    // endregion

    // region Package Methods
    ChangeLogAlertDialog(final android.content.Context context,
    final android.content.Context applicationContext,
    final java.io.InputStreamReader inputStreamReader, final int versionResId,
    final int contentResId, final android.content.Context activityClass,
    final java.lang.CharSequence title, final java.lang.CharSequence positiveButtonText)
    {
        super();

        // For scrollView:
        assert context != null;
        this.context = context;

        assert applicationContext != null;
        this.applicationContext = applicationContext;

        assert inputStreamReader != null;
        this.inputStreamReader = inputStreamReader;

        this.versionResId = versionResId;
        this.contentResId = contentResId;


        // For builder:
        assert activityClass != null;
        this.activityClass = activityClass;

        assert title != null;
        this.title = title;

        assert positiveButtonText != null;
        this.positiveButtonText = positiveButtonText;
    }

    void show() throws java.io.IOException
    {
        if (this.alertDialog == null)
        {
            if (this.builder == null)
            {
                if (this.scrollView == null) this.scrollView =
                    new org.wheatgenetics.inventory.ChangeLogAlertDialog.ScrollView(
                        this.context, this.applicationContext, this.inputStreamReader,
                        this.versionResId, this.contentResId);

                this.builder = new android.app.AlertDialog.Builder(this.activityClass);
                this.builder.setTitle(this.title           )
                    .setView         (this.scrollView.get())           // throws java.io.IOException
                    .setCancelable   (true                 )
                    .setPositiveButton(this.positiveButtonText,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                assert dialog != null;
                                dialog.dismiss();
                            }
                        });
            }
            this.alertDialog = this.builder.create();
            assert this.alertDialog != null;
        }
        this.alertDialog.show();
    }
    // endregion
}