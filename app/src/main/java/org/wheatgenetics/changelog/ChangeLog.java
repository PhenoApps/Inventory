package org.wheatgenetics.changelog;

class ChangeLog extends java.lang.Object
{
    interface LineHandler
    {
        void handleBlankLine();
        void handleVersionLine(final java.lang.String version);
        void handleContentLine(final java.lang.String content);
    }

    private final java.io.InputStreamReader                         inputStreamReader;
    private final org.wheatgenetics.changelog.ChangeLog.LineHandler lineHandler      ;
    private       java.lang.String                                  lines[] = null   ;

    ChangeLog(final java.io.InputStreamReader inputStreamReader,
    final org.wheatgenetics.changelog.ChangeLog.LineHandler lineHandler)
    {
        super();

        assert inputStreamReader != null;
        this.inputStreamReader = inputStreamReader;

        assert lineHandler != null;
        this.lineHandler = lineHandler;
    }

    void iterate() throws java.io.IOException
    {
        if (this.lines == null)
        {
            final java.util.ArrayList<java.lang.String> arrayList =
                new java.util.ArrayList<java.lang.String>();
            {
                final java.io.BufferedReader bufferedReader =
                    new java.io.BufferedReader(this.inputStreamReader, 8192);
                java.lang.String line;

                while ((line = bufferedReader.readLine()) != null)     // throws java.io.IOException
                    arrayList.add(line);
            }
            this.lines = new java.lang.String[arrayList.size()];
            this.lines = arrayList.toArray(this.lines)         ;
        }

        java.lang.String version = null;
        assert this.lineHandler != null;
        for (final java.lang.String line: this.lines)
            if (line.length() == 0)
            {
                version = null;
                this.lineHandler.handleBlankLine();
            }
            else
                if (version == null)
                {
                    {
                        final java.lang.String splitLine[] = line.split("/");
                        version = splitLine[1];
                    }
                    this.lineHandler.handleVersionLine(version);
                }
                else this.lineHandler.handleContentLine("•  " + line);
    }
}