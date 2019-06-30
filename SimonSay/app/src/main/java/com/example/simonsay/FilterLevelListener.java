package com.example.simonsay;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class FilterLevelListener implements DialogInterface.OnClickListener
{
    Context m_Context;
    public FilterLevelListener(Context i_Context)
    {
        m_Context =i_Context;
    }
    @Override
    public void onClick(DialogInterface dialog, int i)
    {

        if(i == DialogInterface.BUTTON_POSITIVE )
        {
            Toast.makeText(m_Context,"ok",Toast.LENGTH_SHORT).show();
        }
    }
}
