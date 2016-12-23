package com.likebamboo.theme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment
{
    private TextView text;

    private String msg;

    public static BaseFragment newInstance(String msg)
    {
        BaseFragment fragment = new BaseFragment();
        Bundle data = new Bundle();
        data.putString("msg", msg);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        text = (TextView) view.findViewById(R.id.text);
        if (getArguments() != null)
        {
            msg = getArguments().getString("msg", "");
        }
        text.setText(msg);
    }

}
