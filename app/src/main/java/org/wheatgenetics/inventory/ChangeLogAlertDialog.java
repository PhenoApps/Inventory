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
 * org.wheatgenetics.inventory.ChangeLog
 * org.wheatgenetics.inventory.ChangeLog.LineHandler
 * org.wheatgenetics.inventory.R
 */

class ChangeLogAlertDialog extends java.lang.Object
{
    class ScrollView extends java.lang.Object
    {
        class LineHandler extends java.lang.Object
        implements org.wheatgenetics.inventory.ChangeLog.LineHandler
        {
            // region Private Fields
            private android.content.Context context = null, applicationContext = null;
            private android.widget.LinearLayout.LayoutParams layoutParams = null;
            private android.widget.LinearLayout              linearLayout = null;
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
            @Override
            public void handleBlankLine()
            {
                final android.widget.TextView spacerTextView = this.makeTextView();
                assert spacerTextView != null;
                spacerTextView.setTextSize(5);
                spacerTextView.setText("\n");

                assert this.linearLayout != null;
                this.linearLayout.addView(spacerTextView);
            }

            @Override
            public void handleVersionLine(final java.lang.String version)
            {
                final android.widget.TextView headerTextView = this.makeTextView();
                assert headerTextView != null;
                headerTextView.setTextAppearance(this.applicationContext,
                    org.wheatgenetics.inventory.R.style.ChangelogTitles);
                headerTextView.setText(version);

                assert this.linearLayout != null;
                this.linearLayout.addView(headerTextView);
            }

            @Override
            public void handleContentLine(final java.lang.String content)
            {
                final android.widget.TextView contentTextView = this.makeTextView();
                assert contentTextView != null;
                contentTextView.setTextAppearance(this.applicationContext,
                    org.wheatgenetics.inventory.R.style.ChangelogContent);
                contentTextView.setText(content);

                assert this.linearLayout != null;
                this.linearLayout.addView(contentTextView);
            }
            // endregion


            // region Package Constructor Method
            LineHandler(final android.content.Context context,
            final android.content.Context applicationContext,
            final android.widget.LinearLayout linearLayout)
            {
                super();

                assert context != null;
                this.context = context;

                assert applicationContext != null;
                this.applicationContext = applicationContext;

                assert linearLayout != null;
                this.linearLayout = linearLayout;
            }
            // endregion
        }


        // region Private Fields
        private android.content.Context   context           = null, applicationContext = null;
        private java.io.InputStreamReader inputStreamReader = null;

        private android.widget.LinearLayout linearLayout = null;
        private android.widget.ScrollView   scrollView   = null;

        private org.wheatgenetics.inventory.ChangeLogAlertDialog.ScrollView.LineHandler
            lineHandler = null;
        private org.wheatgenetics.inventory.ChangeLog changeLog = null;
        // endregion


        // region Package Methods
        ScrollView(final android.content.Context context,
        final android.content.Context applicationContext,
        final java.io.InputStreamReader inputStreamReader)
        {
            super();

            assert context != null;
            this.context = context;

            assert applicationContext != null;
            this.applicationContext = applicationContext;

            assert inputStreamReader != null;
            this.inputStreamReader = inputStreamReader;
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
                            this.context, this.applicationContext, this.linearLayout);
                this.changeLog = new org.wheatgenetics.inventory.ChangeLog(
                    this.inputStreamReader, this.lineHandler);
            }

            this.changeLog.iterate();                                  // throws java.io.IOException
            return this.scrollView;
        }
        // endregion
    }


    // region Private Fields
    // region scrollView Private Fields
    private android.content.Context   context           = null, applicationContext = null;
    private java.io.InputStreamReader inputStreamReader = null;

    private org.wheatgenetics.inventory.ChangeLogAlertDialog.ScrollView scrollView = null;
    // endregion


    // region builder Private Fields
    private android.content.Context activityClass = null;
    private java.lang.CharSequence  title         = null, positiveButtonText = null;

    private android.app.AlertDialog.Builder builder = null;
    // endregion


    private android.app.AlertDialog alertDialog = null;
    // endregion


    // region Package Methods
    ChangeLogAlertDialog(final android.content.Context context,
    final android.content.Context applicationContext,
    final java.io.InputStreamReader inputStreamReader, final android.content.Context activityClass,
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
                        this.context, this.applicationContext, this.inputStreamReader);

                this.builder = new android.app.AlertDialog.Builder(this.activityClass);
                this.builder.setTitle(this.title           )
                    .setView         (this.scrollView.get())           // throws java.io.IOException
                    .setCancelable   (true                 )
                    .setPositiveButton(this.positiveButtonText,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @Override
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