package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Context
 * android.widget.LinearLayout
 * android.widget.LinearLayout.LayoutParams
 * android.widget.ScrollView
 * android.widget.TextView
 * android.widget.View
*/

class ChangeLogScrollView extends java.lang.Object
{
    class LineHandler extends java.lang.Object
    implements org.wheatgenetics.inventory.ChangeLog.LineHandler
    {
        // region Protected Fields
        protected android.content.Context context            = null;
        protected android.content.Context applicationContext = null;

        protected android.widget.LinearLayout.LayoutParams layoutParams = null;

        protected android.widget.LinearLayout linearLayout = null;
        // endregion


        // region Protected Method
        protected android.widget.TextView makeTextView()
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


    // region Protected Fields
    protected android.content.Context   context            = null;
    protected android.content.Context   applicationContext = null;
    protected java.io.InputStreamReader inputStreamReader  = null;

    protected android.widget.LinearLayout linearLayout = null;
    protected android.widget.ScrollView   scrollView   = null;

    protected org.wheatgenetics.inventory.ChangeLogScrollView.LineHandler lineHandler = null;
    protected org.wheatgenetics.inventory.ChangeLog                       changeLog   = null;
    // endregion


    // region Package Methods
    ChangeLogScrollView(final android.content.Context context,
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
                this.lineHandler = new org.wheatgenetics.inventory.ChangeLogScrollView.LineHandler(
                    this.context, this.applicationContext, this.linearLayout);
            this.changeLog =
                new org.wheatgenetics.inventory.ChangeLog(this.inputStreamReader, this.lineHandler);
        }

        this.changeLog.iterate();                                      // throws java.io.IOException
        return this.scrollView;
    }
    // endregion
}